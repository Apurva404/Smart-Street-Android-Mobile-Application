package com.apurva.assignment.smartstreet.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.apurva.assignment.smartstreet.R;
import com.apurva.assignment.smartstreet.db.DataController;
import com.apurva.assignment.smartstreet.entity.SmartStreetLight;

/**
 * Created by Apurva on 9/2/2017.
 */

public class AboutActivity  extends Activity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private EditText idView, nameView, descriptionView, installationDateView, latitudeView, longitudeView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set the main content layout of the Activity
        setContentView(R.layout.activity_about);
        idView = (EditText) findViewById(R.id.lightIdEditBox);
        nameView = (EditText) findViewById(R.id.nameEditBox);
        descriptionView = (EditText) findViewById(R.id.descriptionEditBox);
        installationDateView = (EditText) findViewById(R.id.installationDateEditBox);
        latitudeView = (EditText) findViewById(R.id.itemLatitudeEditBox);
        longitudeView = (EditText) findViewById(R.id.itemLongitudeEditBox);

        scanQR();
    }

    public void scanQR() {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                DataController sdc = new DataController(getBaseContext());
                sdc.open();
                SmartStreetLight newLight = sdc.getData(contents);
                sdc.close();
                //populate layout
                idView.setText(Integer.toString(newLight.getId()));
                nameView.setText(newLight.getName());
                descriptionView.setText(newLight.getDescription());
                installationDateView.setText(newLight.getInstallationDate());
                latitudeView.setText(newLight.getLatitude());
                longitudeView.setText(newLight.getLongitude());

                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    public void backHome(View v){
        this.finish();
    }
}
