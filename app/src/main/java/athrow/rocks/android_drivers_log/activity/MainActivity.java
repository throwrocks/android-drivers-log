package athrow.rocks.android_drivers_log.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import athrow.rocks.android_drivers_log.R;
import athrow.rocks.android_drivers_log.data.APIResponse;
import athrow.rocks.android_drivers_log.interfaces.OnTaskComplete;
import athrow.rocks.android_drivers_log.service.UpdateDBService;

public class MainActivity extends AppCompatActivity implements OnTaskComplete {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final OnTaskComplete onTaskCompleted = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LogActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onTaskComplete(APIResponse apiResponse) {
        if (apiResponse != null) {
            Intent updateDBIntent = new Intent(this, UpdateDBService.class);
            switch (apiResponse.getResponseCode()) {
                case 200:
                    String responseText = apiResponse.getResponseText();
                    updateDBIntent.putExtra(UpdateDBService.DATA, responseText);
                    LocalBroadcastManager.getInstance(this).
                            registerReceiver(new ResponseReceiver(),
                                    new IntentFilter(UpdateDBService.UPDATE_SITES_DB_SERVICE_BROADCAST));
                    this.startService(updateDBIntent);
                    break;
                default:
                    Context context = getApplicationContext();
                    CharSequence text = apiResponse.getResponseText();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
            }
        }
    }

    @Override
    public void OnTaskComplete(APIResponse apiResponse) {
        onTaskComplete(apiResponse);
    }

    /**
     * ResponseReceiver
     * A class to manage handling the UpdateDBService response
     */
    private class ResponseReceiver extends BroadcastReceiver {

        private ResponseReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String text = "Sites updated!";
            int duration = Toast.LENGTH_SHORT;
            final Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();
        }
    }
}
