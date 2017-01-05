package athrow.rocks.android_drivers_log.data;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import athrow.rocks.android_drivers_log.R;
import athrow.rocks.android_drivers_log.interfaces.OnTaskComplete;

/**
 * FetchSites
 * Created by jose on 12/29/16.
 */

public class FetchSites extends AsyncTask<String, Void, APIResponse> {
    private Context mContext;
    private OnTaskComplete mListener = null;

    public FetchSites(Context context, OnTaskComplete listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    protected APIResponse doInBackground(String... params) {
        APIResponse apiResponse = new APIResponse();
        String results;
        StringBuilder builder = new StringBuilder();
        Resources res = mContext.getResources();
        InputStream in = res.openRawResource(R.raw.museums);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            results = builder.toString();
            apiResponse.setResponseCode(200);
            apiResponse.setResponseText(results);
        } catch (IOException e) {
            apiResponse.setResponseCode(500);
            apiResponse.setResponseText("Could not find any sites.");
            Log.e("IOException", e.toString());
            e.printStackTrace();
        }
        return apiResponse;
    }

    @Override
    protected void onPostExecute(APIResponse apiResponse) {
        super.onPostExecute(apiResponse);
        mListener.OnTaskComplete(apiResponse);
    }
}
