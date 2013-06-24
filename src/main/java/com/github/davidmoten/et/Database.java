package com.github.davidmoten.et;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import com.github.davidmoten.geo.Coverage;
import com.github.davidmoten.geo.GeoHash;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * Encapsulates database access. GoogleAppEngine (BigTable) used for
 * persistence.
 * 
 * @author dxm
 * 
 */
public class Database {

	/**
	 * Get the current {@link User}.
	 * 
	 * @return
	 */
	private static User getUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}

	public void saveReport(Date time, double lat, double lon,
			Map<String, String> ids) {

		User user = getUser();

		// kind=db,name=schema,
		Key entityTrackingKey = KeyFactory.createKey("EntityTracking",
				"EntityTracking");
		// kind=table,entity=row
		Entity report = new Entity("Report", entityTrackingKey);
		report.setProperty("user", user);
		report.setProperty("lat", lat);
		report.setProperty("lon", lon);
		report.setProperty("time", time);
		// set ids
		for (Entry<String, String> entry : ids.entrySet()) {
			report.setProperty(entry.getKey(), entry.getValue());
		}
		// set geohashes
		for (int i = 1; i <= 12; i++) {
			report.setProperty("geohash" + i, GeoHash.encodeHash(lat, lon, i));
		}

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		datastore.put(report);
		System.out.println("saved");
	}

	public String getReports(String user, final double topLeftLat,
			final double topLeftLon, final double bottomRightLat,
			final double bottomRightLon, Date start, Date finish,
			String idName, String idValue) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter startTimeFilter = new FilterPredicate("time",
				FilterOperator.GREATER_THAN_OR_EQUAL, start);
		Filter finishTimeFilter = new FilterPredicate("time",
				FilterOperator.LESS_THAN, finish);
		Filter userFilter = new FilterPredicate("user", FilterOperator.EQUAL,
				user);
		Filter filter = CompositeFilterOperator.and(userFilter,
				startTimeFilter, finishTimeFilter);
		Coverage coverage = GeoHash.coverBoundingBox(topLeftLat, topLeftLon,
				bottomRightLat, bottomRightLon);
		for (String hash : coverage.getHashes()) {
			filter = CompositeFilterOperator.and(filter, new FilterPredicate(
					"geohash" + hash.length(), FilterOperator.EQUAL, hash));
		}
		if (idName != null && idValue != null) {
			filter = CompositeFilterOperator.and(filter, new FilterPredicate(
					idName, FilterOperator.EQUAL, idValue));
		}
		Query query = new Query("Report").setFilter(filter);
		FetchOptions options = FetchOptions.Builder.withChunkSize(1000);
		Iterable<Entity> it = datastore.prepare(query).asIterable(options);
		it = Iterables.filter(it, new Predicate<Entity>() {
			@Override
			public boolean apply(Entity e) {
				double lat = (double) e.getProperty("lat");
				double lon = (double) e.getProperty("lon");
				// TODO handle longitude between
				return lat >= bottomRightLat && lat <= topLeftLat
						&& longitudeBetween(topLeftLon, bottomRightLon, lon);
			}

		});
		// filter results within bounding box because geohash query inexact.

		return toJSON(it);
	}

	private static boolean longitudeBetween(double a, double b, double c) {
		// TODO implement
		a = GeoHash.to180(a);
		b = GeoHash.to180(b);
		c = GeoHash.to180(c);
		if (b < a)
			return longitudeBetween(a, b + 360, c);
		return a <= c && c <= b;
	}

	private String toJSON(Iterable<Entity> it) {
		return "";
	}
}
