package life.happyholiday.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import timber.log.Timber;

/**
 * PermissionHelper helps request permission and handle the results.
 * It is helpful to handle the case with SDK_INT >= 23
 *
 * Created by liuyang on 9/23/2017.
 */

public class PermissionHelper {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 201;

    public static void checkPermission(Activity activity, int requestCode, CheckPermissionAction action) {
        Timber.d("SDK Version: %d", Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT < 23) {
            Timber.d("SDK under 23. Doing action.");
            action.doAction();
        } else {
            Timber.d("SDK 23 or higher. Checking for Permission.");

            String permissionName = getPermissionName(requestCode);
            if (ContextCompat.checkSelfPermission(activity, permissionName) != PackageManager.PERMISSION_GRANTED) {
                action.requestPermission();
            } else {
                action.doAction();
            }
        }
    }

    public static void handlePermissionResponse(Context context, String[] permissions, int[] grantResults, PermissionResponseAction action) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Timber.d("Permission Granted. Doing action.");
            action.doAction();
        } else {
            Timber.d("Permission Denied. Show Error Message.");
        }
    }

    private static String getPermissionName(int permissionCode) {
        switch (permissionCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                return Manifest.permission.ACCESS_FINE_LOCATION;
        }

        return "";
    }

    public interface CheckPermissionAction {
        void doAction();

        void requestPermission();
    }

    public interface PermissionResponseAction {
        void doAction();
    }
}
