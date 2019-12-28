package com.egorovsoft.myrain.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import com.egorovsoft.myrain.MainPresenter;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class Permission implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int PERMISSION_REQUEST_CODE = 10;

    public boolean checkPermissionLoacation(Context context){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            MainPresenter.getInstance().setPermissionLocation(true);
            return true;
        } else {
            MainPresenter.getInstance().setPermissionLocation(false);
            return false;
        }
    }

    public void requestLocationPermissions(Activity activity) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE)) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Это та самая пермиссия, что мы запрашивали?
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length == 2 &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                MainPresenter.getInstance().setPermissionLocation(true);
            }
        }
    }
}
