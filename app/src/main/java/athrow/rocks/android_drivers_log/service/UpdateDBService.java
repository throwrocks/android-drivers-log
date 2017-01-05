package athrow.rocks.android_drivers_log.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import athrow.rocks.android_drivers_log.data.Sites;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by jose on 12/29/16.
 */

public class UpdateDBService extends IntentService {
    private static final String SERVICE_NAME = "UpdateDBService";
    public static final String UPDATE_SITES_DB_SERVICE_BROADCAST = "UpdateSitesBroadcast";
    public static final String UPDATE_TRAVEL_LOG_DB_SERVICE_BROADCAST = "UpdateTravelLogBroadcast";
    public static final String DATA = "data";

    public UpdateDBService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        Bundle arguments = workIntent.getExtras();
        String data = arguments.getString(DATA);
        try {
            JSONArray sitesArray = new JSONArray(data);
            int sitesCount = sitesArray.length();
            for (int i = 0; i < sitesCount; i++) {
                RealmConfiguration realmConfig = new RealmConfiguration.Builder(getApplicationContext()).build();
                Realm.setDefaultConfiguration(realmConfig);
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                Sites site = new Sites();
                String siteName = new JSONObject(sitesArray.getString(i)).getString("FIELD1");
                site.setId(Integer.toString(i));
                site.setName(siteName);
                realm.copyToRealmOrUpdate(site);
                realm.commitTransaction();
                realm.close();
            }
        } catch (JSONException e) {
            Log.e(SERVICE_NAME, e.toString());
            e.printStackTrace();
        }
    }
}
