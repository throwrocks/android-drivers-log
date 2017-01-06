package athrow.rocks.android_drivers_log.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import athrow.rocks.android_drivers_log.R;
import athrow.rocks.android_drivers_log.data.TravelLog;
import athrow.rocks.android_drivers_log.realmadapter.RealmRecyclerViewAdapter;

/**
 * TravelLogAdapter
 * Created by jose on 1/6/17.
 */

public class TravelLogAdapter extends RealmRecyclerViewAdapter<TravelLog> {

    private final Context mContext;

    public TravelLogAdapter(Context context) {
        this.mContext = context;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateView;
        ImageView editView;
        TextView location1View;
        TextView location2View;
        TextView purposeView;
        TextView odometerStartView;
        TextView odometerEndView;
        TextView mileageView;

        ViewHolder(View view) {
            super(view);
            dateView = (TextView) view.findViewById(R.id.date);
            editView = (ImageView) view.findViewById(R.id.edit);
            location1View = (TextView) view.findViewById(R.id.location1);
            location2View = (TextView) view.findViewById(R.id.location2);
            purposeView = (TextView) view.findViewById(R.id.purpose);
            odometerStartView = (TextView) view.findViewById(R.id.odometer_start);
            odometerEndView = (TextView) view.findViewById(R.id.odometer_end);
            mileageView = (TextView) view.findViewById(R.id.mileage);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View logRecordCard = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_main, parent, false);
        return new ViewHolder(logRecordCard);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder travelLogViewHolder = (ViewHolder) viewHolder;
        TravelLog logRecord = getItem(position);
        final String date = logRecord.getDate();
        final String fromSite = logRecord.getFrom_site_name();
        final String toSite = logRecord.getTo_site_name();
        final String purpose = logRecord.getReason();
        final String odometerStart = Integer.toString(logRecord.getOdometer_start());
        final String odometerEnd = Integer.toString(logRecord.getOdometer_end());
        final String mileage = Integer.toString(logRecord.getMiles());
        travelLogViewHolder.dateView.setText(date);
        travelLogViewHolder.location1View.setText(fromSite);
        travelLogViewHolder.location2View.setText(toSite);
        travelLogViewHolder.purposeView.setText(purpose);
        travelLogViewHolder.odometerStartView.setText(odometerStart);
        travelLogViewHolder.odometerEndView.setText(odometerEnd);
        travelLogViewHolder.mileageView.setText(mileage);
    }

    @Override
    public int getItemCount() {
        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }
}
