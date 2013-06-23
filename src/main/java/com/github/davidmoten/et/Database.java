package com.github.davidmoten.et;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.base.Preconditions;

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

	public void saveReport(String id, Date time, double lat, double lon,
			Map<String, String> ids) {
		Preconditions.checkNotNull(id);

		User user = getUser();

		// kind=db,name=schema,
		Key entityTrackingKey = KeyFactory.createKey("EntityTracking",
				"EntityTracking");
		// kind=table,entity=row
		Entity report = new Entity("Report", entityTrackingKey);
		report.setProperty("id", id);
		report.setProperty("user", user);
		report.setProperty("lat", lat);
		report.setProperty("lon", lon);
		for (Entry<String, String> en : ids.entrySet()) {
			report.setProperty(en.getKey(), en.getValue());
		}

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		datastore.put(report);
		System.out.println("saved");

	}

}
