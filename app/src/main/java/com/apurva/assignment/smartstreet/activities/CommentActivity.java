package com.apurva.assignment.smartstreet.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.apurva.assignment.smartstreet.R;
import com.apurva.assignment.smartstreet.db.DataController;

/**
 * Created by Apurva on 9/2/2017.
 */

public class CommentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

    }

    public void saveComment(View view) {
        EditText editText1 = (EditText)findViewById(R.id.userCommentEditText);
        String comment = editText1.getText().toString();

        DataController dataController = new DataController(getBaseContext());
        dataController.open();

        long retValue = dataController.insertComment(comment);
        dataController.close();

        if(retValue != -1) {
            Toast.makeText(getApplicationContext(), getString(R.string.save_comment_success_msg), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.save_comment_failure_msg), Toast.LENGTH_LONG).show();
        }

        editText1.setText("");
    }

}
