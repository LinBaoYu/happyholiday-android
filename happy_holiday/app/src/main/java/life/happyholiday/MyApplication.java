package life.happyholiday;

import android.app.Application;
import android.content.Intent;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;
import life.happyholiday.activities.HomeActivity;
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

        SyncCredentials myCredentials = SyncCredentials.usernamePassword("me@liuyang.tech", "Qwe123!@#", false);

        if (SyncUser.currentUser() != null) {
            SyncConfiguration syncConfiguration = new SyncConfiguration.Builder(SyncUser.currentUser(), "realm://111.221.89.106:9080/~/happyholiday").build();
            Realm.setDefaultConfiguration(syncConfiguration);

            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            SyncUser.loginAsync(myCredentials, "http://111.221.89.106:9080/auth", new SyncUser.Callback() {
                @Override
                public void onSuccess(SyncUser user) {
                    Timber.e("login async onSuccess");
                    final SyncConfiguration syncConfiguration = new SyncConfiguration.Builder(user, "realm://111.221.89.106:9080/~/happyholiday").build();
                    Realm.setDefaultConfiguration(syncConfiguration);
                }

                @Override
                public void onError(ObjectServerError error) {
                    Timber.e("login async onError: " + error.getErrorMessage());
                }
            });
        }
    }
}
