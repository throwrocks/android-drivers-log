package athrow.rocks.android_drivers_log.realmadapter;

import android.content.Context;

import athrow.rocks.android_drivers_log.data.TravelLog;
import io.realm.RealmResults;

/**
 * RealmTravelLogAdapter
 * I'm going to need one more convenience class to help create a RealmModelAdapter
 * supporting the RealmObject type I want
 * http://gradlewhy.ghost.io/realm-results-with-recyclerview/
 */
public class RealmTravelLogAdapter extends RealmModelAdapter<TravelLog> {
    public RealmTravelLogAdapter(Context context, RealmResults<TravelLog> realmResults) {
        super(context, realmResults);
    }
}
