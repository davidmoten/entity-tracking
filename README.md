entity-tracking
===============

Geopositional entity tracking using geohashing for queries.

This is a java webapp configured to deploy to Google AppEngine. 

* persists lat long, time and identifier information to the Datastore 
* auto-populates geohash fields 
* is configured with geohash, time indexes
* executes bounding box queries

Having got this going I let go of the idea of trialling some big datasets in AppEngine because it might be kind of costly. Maybe later.
