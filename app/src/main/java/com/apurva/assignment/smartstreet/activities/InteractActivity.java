package com.apurva.assignment.smartstreet.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.apurva.assignment.smartstreet.R;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Apurva on 9/2/2017.
 */

public class InteractActivity extends Activity {

    private static final String remoteURL = "http://192.168.0.7:8080/SmartStreet_Backend/smartStreet/interact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interact);
    }

    public void postText(View V) {
        EditText commentBox = (EditText) findViewById(R.id.userInteractEditText);
        String payload = commentBox.getText().toString();

        new AsyncPostTask().execute(payload);
    }

    public void goBack(View v) {
        this.finish();
    }


    public class AsyncPostTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... params) {
            try {
                StringEntity body = new StringEntity(params[0]);

                HttpPost post = new HttpPost(remoteURL);
                post.setHeader("Content-Type", "text/plain");
                post.setEntity(body);
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse response = httpClient.execute(post);

                return response.getStatusLine().getStatusCode();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return -1;
        }

        @Override
        protected void onPostExecute(Integer status) {
            super.onPostExecute(status);
            if(status == HttpStatus.SC_OK) {
                Toast.makeText(getApplicationContext(), "Successfully sent content to Smart Tree.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Failed to send content to Smart Tree.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
