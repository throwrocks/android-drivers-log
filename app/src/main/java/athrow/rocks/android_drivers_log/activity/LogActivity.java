package athrow.rocks.android_drivers_log.activity;

import android.app.DatePickerDialog;
import android.content.res.Resources;
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
        fromSite.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        toSite.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        showDatePicker("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    private void saveLog(){
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
        int selectedRadioButtonId = entryTimeOfDay.getCheckedRadioButtonId();
        if ( selectedRadioButtonId > 0) {
            RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonId);
            String timeOfDay = selectedRadioButton.getText().toString();
        }
        String fromSite = entryFromSite.getText().toString();
        String toSite = entryToSite.getText().toString();
        String purpose = entryPurpose.getText().toString();
        String odometerStart = entryOdometerStart.getText().toString();
        String odometerEnd = entryOdometerEnd.getText().toString();

    }
    /**
     * getSitesFromJSON
     */
    //TODO: Move to the MainActivity to load the Sites tables
    //TODO: Swap the JSON file to an API call to the FileMaker Server
    private String[] getSitesFromJSON() {
       return null;
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
