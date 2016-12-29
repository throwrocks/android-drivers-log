package athrow.rocks.android_drivers_log.data;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import athrow.rocks.android_drivers_log.R;
import athrow.rocks.android_drivers_log.interfaces.OnTaskComplete;

/**
 * Created by jose on 12/29/16.
 */

public class FetchSites extends AsyncTask<String, Void, JSONArray> {
    private Context mContext;
    private OnTaskComplete mListener = null;

    public FetchSites(Context context, OnTaskComplete listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    protected JSONArray doInBackground(String... params) {
        JSONArray sitesArray;
        StringBuilder builder = new StringBuilder();
        Resources res = mContext.getResources();
        InputStream in = res.openRawResource(R.raw.museums);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            Log.e("IOException", e.toString());
            return null;
        }
        try {
            sitesArray = new JSONArray(builder.toString());
        } catch (JSONException e) {
            Log.e("JSONException", e.toString());
            return null;
        }
        return sitesArray;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
    }
}
