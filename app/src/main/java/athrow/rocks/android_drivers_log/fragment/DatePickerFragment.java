package athrow.rocks.android_drivers_log.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

import athrow.rocks.android_drivers_log.util.Utilities;

/**
 * DatePickerFragment
 * Created by joselopez on 10/20/16.
 */

public class DatePickerFragment extends DialogFragment {

    private final static String FIELD_SELECTED_DATE = "selected_date";
    private final static String DATE_DISPLAY = "MM/dd/yy";
    private DatePickerDialog.OnDateSetListener ondateSet;

    public DatePickerFragment() {
    }

    public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
        ondateSet = ondate;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String selectedDate = bundle.getString(FIELD_SELECTED_DATE);
        Date date = Utilities.getStringAsDate(selectedDate, DATE_DISPLAY, null);
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(java.util.Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
    }

}