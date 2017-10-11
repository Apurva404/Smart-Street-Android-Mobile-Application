package com.apurva.assignment.smartstreet.activities;

/**
 * Created by Apurva on 9/2/2017.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.apurva.assignment.smartstreet.BuildConfig;
import com.apurva.assignment.smartstreet.R;
import com.apurva.assignment.smartstreet.constants.MiscConstants;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShareActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        populateListView();
    }

    private void populateListView() {
        listView = (ListView) findViewById(R.id.list);
        File mediaStorageDir = new File(MiscConstants.IMAGE_ROOT_DIRECTORY, MiscConstants.IMAGE_DIRECTORY_NAME);

        if((mediaStorageDir.exists()) && (mediaStorageDir.isDirectory())){
            File[] files = mediaStorageDir.listFiles();
            String[] fileNames = new String[files.length];
            for(int i = 0; i< files.length; i++) {
                fileNames[i] = files[i].getName();
            }

            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_multiple_choice, fileNames);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listView.setAdapter(adapter);
        }
    }

    public void share(View view) {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<Uri> filesToShare = new ArrayList<>();
        for (int i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);
            if (checked.valueAt(i)) {
                String fileName = adapter.getItem(position);
                filesToShare.add(getOutputMediaFileUri(fileName));
            }
        }

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, filesToShare);
        shareIntent.setType("*/*");
        startActivity(Intent.createChooser(shareIntent, "Choose sharing method.."));
    }

    public Uri getOutputMediaFileUri(String fileName) {
        return FileProvider.getUriForFile(ShareActivity.this,
                BuildConfig.APPLICATION_ID + ".provider",
                getOutputMediaFile(fileName));
    }

    private static File getOutputMediaFile(String fileName) {
        File mediaStorageDir = new File(MiscConstants.IMAGE_ROOT_DIRECTORY, MiscConstants.IMAGE_DIRECTORY_NAME);
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "Check it out. Your message goes here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}