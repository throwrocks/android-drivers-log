package athrow.rocks.android_drivers_log.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        //TODO: Declare view
        ViewHolder(View view) {
            super(view);
            //TODO: Initialize views
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View serviceTicketCardView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_log, parent, false);
        return new ViewHolder(serviceTicketCardView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
    }

    @Override
    public int getItemCount() {
        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }
}
