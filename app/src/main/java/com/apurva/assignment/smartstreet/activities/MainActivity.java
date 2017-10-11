package com.apurva.assignment.smartstreet.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.apurva.assignment.smartstreet.R;
import com.apurva.assignment.smartstreet.db.DataController;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public static final int MY_PERMISSIONS_REQUEST = 50;
    private static final String[] requiredPermissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataController dataController = new DataController(getBaseContext());
        dataController.open();
        dataController.close();

        checkAllPermissions();
    }

    public void checkAllPermissions() {
        /* In Android 6.0 and higher you need to request permissions at runtime, and the user has
         * the ability to grant or deny each permission. Users can also revoke a previously-granted
         * permission at any time, so your app must always check that it has access to each
         * permission, before trying to perform actions that require that permission. Here, we’re using
         * ContextCompat.checkSelfPermission to check whether this app currently has the required permissions
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> missingPermisssions = new ArrayList<>();
            for(String permission : requiredPermissions ) {
                if (ContextCompat.checkSelfPermission(this, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    missingPermisssions.add(permission);
                }
            }

            if(!missingPermisssions.isEmpty()) {
                requestPermissions(missingPermisssions.toArray(new String[0]), MY_PERMISSIONS_REQUEST);
            }
        }
    }

    // Once the user has granted or denied your permission request, the Activity’s
    // onRequestPermissionsResult method will be called, and the system will pass
    // the results of the ‘grant permission’ dialog, as an int//

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST:
            {
                boolean success = true;
                if((grantResults.length == 0) || (grantResults.length != permissions.length)) {
                    success = false;
                }

                for(int i=0; i< permissions.length; i++) {
                    if(grantResults[i] != PackageManager.PERMISSION_GRANTED)
                        success = false;
                }

                if(!success) {
                    // All permissions were not granted., crash app with a toast!
                    this.finish();
                }
            }
        }
    }

    public void about(View v) {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }
    public void interact(View v) {
        Intent intent = new Intent(MainActivity.this, InteractActivity.class);
        startActivity(intent);
    }
    public void camera(View v) {
        Intent intent = new Intent(MainActivity.this, CameraActivity.class);
        startActivity(intent);
    }

    public void comment(View v) {
        Intent intent = new Intent(MainActivity.this, CommentActivity.class);
        startActivity(intent);
    }

    public void share(View v) {
        Intent intent = new Intent(MainActivity.this, ShareActivity.class);
        startActivity(intent);
    }

    public void nearby(View v) {
        Intent intent = new Intent(MainActivity.this, NearbyActivity.class);
        startActivity(intent);
    }

    public void closeApp(View v){
        this.finish();
    }

}
