package athrow.rocks.android_drivers_log.activity;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.UUID;

import athrow.rocks.android_drivers_log.R;
import athrow.rocks.android_drivers_log.data.Sites;
import athrow.rocks.android_drivers_log.data.TravelLog;
import athrow.rocks.android_drivers_log.fragment.DatePickerFragment;
import athrow.rocks.android_drivers_log.util.Utilities;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class LogActivity extends AppCompatActivity {

    private final static String DATE_DISPLAY = "MM/dd/yy";
    private final static String FIELD_SELECTED_DATE = "selected_date";
    EditText dateField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        String[] newSites = loadSites();
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
        fromSite.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        toSite.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        showDatePicker("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_log_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_log:
                saveLog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * saveLog
     * A method to save the log entries to the database
     */
    private void saveLog() {
        TextInputLayout dateInput = (TextInputLayout) findViewById(R.id.input_date);
        TextInputLayout timeInput = (TextInputLayout) findViewById(R.id.input_time);
        TextInputLayout fromSiteInput = (TextInputLayout) findViewById(R.id.input_from_site);
        TextInputLayout toSiteInput = (TextInputLayout) findViewById(R.id.input_to_site);
        TextInputLayout reasonInput = (TextInputLayout) findViewById(R.id.input_reason);
        TextInputLayout odometerStartInput = (TextInputLayout) findViewById(R.id.input_odometer_start);
        TextInputLayout odometerEndInput = (TextInputLayout) findViewById(R.id.input_odometer_end);

        EditText entryDate = (EditText) findViewById(R.id.date);
        EditText entryTime = (EditText) findViewById(R.id.time);
        RadioGroup entryTimeOfDay = (RadioGroup) findViewById(R.id.time_of_day);
        AutoCompleteTextView entryFromSite = (AutoCompleteTextView) findViewById(R.id.from_site);
        AutoCompleteTextView entryToSite = (AutoCompleteTextView) findViewById(R.id.to_site);
        EditText entryPurpose = (EditText) findViewById(R.id.reason);
        EditText entryOdometerStart = (EditText) findViewById(R.id.odometer_start);
        EditText entryOdometerEnd = (EditText) findViewById(R.id.odometer_end);

        String date = entryDate.getText().toString();
        String time = entryTime.getText().toString();
        String timeOfDay = "";
        int selectedRadioButtonId = entryTimeOfDay.getCheckedRadioButtonId();
        if (selectedRadioButtonId > 0) {
            RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonId);
            timeOfDay = selectedRadioButton.getText().toString();
        }
        String fromSite = entryFromSite.getText().toString();
        String toSite = entryToSite.getText().toString();
        String purpose = entryPurpose.getText().toString();
        String odometerStart = entryOdometerStart.getText().toString();
        String odometerEnd = entryOdometerEnd.getText().toString();

        if (date.isEmpty()) {
            dateInput.setError("Date is required.");
            return;
        }
        if (time.isEmpty()) {
            timeInput.setError("Time is required.");
            return;
        }
        if (timeOfDay.isEmpty()) {
            timeInput.setError("AM/PM is required.");
            return;
        }
        if (fromSite.isEmpty()) {
            fromSiteInput.setError("Departure site is required.");
            return;
        }
        if (toSite.isEmpty()) {
            toSiteInput.setError("Destination site is required.");
            return;
        }
        if (purpose.isEmpty()) {
            reasonInput.setError("Purpose is required.");
            return;
        }
        if (odometerStart.isEmpty()) {
            odometerStartInput.setError("Odometer start is required.");
            return;
        }
        if (odometerEnd.isEmpty()) {
            odometerEndInput.setError("Odometer end is required.");
            return;
        }

        int odometerStartInt = Integer.parseInt(odometerStart);
        int odometerEndInt = Integer.parseInt(odometerEnd);
        int miles = odometerEndInt - odometerStartInt;

        if (miles == 0 || miles < 0) {
            // TODO: Show toast with error
            return;
        }
        String id = UUID.randomUUID().toString();
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getApplicationContext()).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        TravelLog log = new TravelLog();
        log.setId(id);
        log.setDate(date);
        log.setTime(time);
        log.setTime_of_day(timeOfDay);
        log.setReason(purpose);
        log.setFrom_site_name(fromSite);
        log.setTo_site_name(toSite);
        log.setOdometer_start(odometerStartInt);
        log.setOdometer_end(odometerEndInt);
        log.setMiles(miles);
        realm.copyToRealmOrUpdate(log);
        realm.commitTransaction();
        realm.close();
    }

    /**
     * loadSites
     * A method to load all the sites from the database
     * Used to set the value lists for the from/to site fields
     * @return a String array with all the sites
     */
    private String[] loadSites() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getApplicationContext()).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Sites> query = realm.where(Sites.class);
        RealmResults<Sites> sites = query.findAll();
        int sitesCount = sites.size();
        String[] sitesArray = new String[sitesCount];
        for (int i = 0; i < sitesCount; i++) {
            String siteName = sites.get(i).getName();
            sitesArray[i] = siteName;
        }
        realm.close();
        return sitesArray;
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
