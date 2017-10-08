package life.happyholiday;

import android.app.Application;

import io.realm.Realm;
import timber.log.Timber;

/**
 * Extend Android Application
 * Used to setup global state variables etc.
 *
 * Created by liuyang on 9/14/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Timber log
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        // Realm Database
        Realm.init(this);
    }
}
