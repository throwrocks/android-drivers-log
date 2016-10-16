package athrow.rocks.android_drivers_log.realmadapter;

import android.support.v7.widget.RecyclerView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;

/**
 * RealRecyclerViewAdapter
 * Wrapper class that allows a RealmBaseAdapter instance to serve
 * as the data source for a RecyclerView.Adapter.
 * http://gradlewhy.ghost.io/realm-results-with-recyclerview/
 */
public abstract class RealmRecyclerViewAdapter<T extends RealmObject> extends RecyclerView.Adapter {
    private RealmBaseAdapter<T> realmBaseAdapter;

    /* getItemCount() is not implemented in this class
     * This is left to concrete implementations */

    public void setRealmAdapter(RealmBaseAdapter<T> realmAdapter) {
        realmBaseAdapter = realmAdapter;
    }

    protected T getItem(int position) {
        return realmBaseAdapter.getItem(position);
    }

    protected RealmBaseAdapter<T> getRealmAdapter() {
        return realmBaseAdapter;
    }
}