package athrow.rocks.android_drivers_log.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

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
        dateField = (EditText) findViewById(R.id.date);
        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedDate = dateField.getText().toString();
                showDatePicker(selectedDate);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, SITES);
        AutoCompleteTextView fromSite = (AutoCompleteTextView) findViewById(R.id.from_site);
        AutoCompleteTextView toSite = (AutoCompleteTextView) findViewById(R.id.to_site);
        fromSite.setAdapter(adapter);
        toSite.setAdapter(adapter);
        showDatePicker("");
    }

    private static final String[] SITES = new String[]{
            "Belgium", "France", "Italy", "Germany", "Spain"
    };

    /**
     * showDatePicker
     *
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
