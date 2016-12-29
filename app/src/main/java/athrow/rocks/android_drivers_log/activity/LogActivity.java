package athrow.rocks.android_drivers_log.activity;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import athrow.rocks.android_drivers_log.R;
import athrow.rocks.android_drivers_log.fragment.DatePickerFragment;
import athrow.rocks.android_drivers_log.util.Utilities;

public class LogActivity extends AppCompatActivity {

    private final static String DATE_DISPLAY = "MM/dd/yy";
    private final static String FIELD_SELECTED_DATE = "selected_date";
    EditText dateField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        String[] newSites = getSitesFromJSON();
        dateField = (EditText) findViewById(R.id.date);
        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedDate = dateField.getText().toString();
                showDatePicker(selectedDate);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, newSites);
        AutoCompleteTextView fromSite = (AutoCompleteTextView) findViewById(R.id.from_site);
        AutoCompleteTextView toSite = (AutoCompleteTextView) findViewById(R.id.to_site);
        fromSite.setAdapter(adapter);
        toSite.setAdapter(adapter);
        showDatePicker("");
    }

    /**
     * getSitesFromJSON
     */
    //TODO: Move to the MainActivity to load the Sites tables
    //TODO: Swap the JSON file to an API call to the FileMaker Server
    private String[] getSitesFromJSON() {
        StringBuilder builder = new StringBuilder();
        Resources res = getResources();
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
            JSONArray sitesArray = new JSONArray(builder.toString());
            int sitesCount = sitesArray.length();
            String[] sitesList = new String[sitesCount];
            for (int i = 0; i < sitesArray.length(); i++) {
                String siteName = new JSONObject(sitesArray.getString(i)).getString("FIELD1");
                sitesList[i] = siteName;
            }
            return sitesList;
        } catch (JSONException e) {
            Log.e("JSONException", e.toString());
            return null;
        }
    }

    /**
     * showDatePicker
     */
    private void showDatePicker(String selectedDate) {
        Bundle bundle = new Bundle();
        bundle.putString(FIELD_SELECTED_DATE, selectedDate);
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setArguments(bundle);
        datePickerFragment.setCallBack(dateSet);
        datePickerFragment.show(getFragmentManager(), "Date Picker");
    }

    /**
     * dateSet
     * Listener to set the date
     */
    private final DatePickerDialog.OnDateSetListener dateSet = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Date selectedDate = new Date(year - 1900, monthOfYear, dayOfMonth);
            String date = Utilities.getDateAsString(selectedDate, DATE_DISPLAY, null);
            dateField.setText(date);
        }
    };

}
