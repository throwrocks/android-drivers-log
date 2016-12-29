package athrow.rocks.android_drivers_log.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jose on 12/29/16.
 */

public class UpdateDBService extends IntentService {
    private static final String SERVICE_NAME = "UpdateDBService";
    public static final String UPDATE_SITES_DB_SERVICE_BROADCAST = "UpdateSitesroadcast";
    public static final String UPDATE_TRAVEL_LOG_DB_SERVICE_BROADCAST = "UpdateTravelLogBroadcast";
    public static final String DATA = "data";
    public UpdateDBService(){
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        Bundle arguments = workIntent.getExtras();
        String data = arguments.getString(DATA);
        try {
            JSONArray sitesArray = new JSONArray(data);
            int sitesCount = sitesArray.length();
            for (int i = 0; i < sitesArray.length(); i++) {
                String siteName = new JSONObject(sitesArray.getString(i)).getString("FIELD1");

            }
        }catch (JSONException e){
            Log.e(SERVICE_NAME, e.toString());
        }
    }
}
