package es.flakiness.bffl;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.functions.Action1;


public class MainActivity extends Activity {

    @Inject PictureStore mPictureStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.get(this).inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        if (BuildConfig.DEBUG)
            getMenuInflater().inflate(R.menu.main_debug, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_clear_picture:
                mPictureStore.clear().subscribe(new Action1<TrivialResults>() {
                    @Override
                    public void call(TrivialResults trivialResults) {
                        Log.d(getClass().getSimpleName(), "done clear!");
                        Toast.makeText(MainActivity.this, "Cleared Picture.", Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
