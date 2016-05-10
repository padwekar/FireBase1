package com.example.savi.firebasedemo1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class LogIn extends AppCompatActivity {
    Firebase mRef ;
    EditText mEditTextUserName , mEditTextPassword ;
    Button mButtonLogin ;
    String mUserName , mPassword ;
    TextView mTextViewSignUp ;
    AlertDialog.Builder builder ;
    String uid  ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Firebase.setAndroidContext(this);
        builder = new AlertDialog.Builder(LogIn.this);
        setBuilder();
        mRef = new Firebase("https://cloudsave.firebaseio.com/");
        mEditTextUserName = (EditText)findViewById(R.id.edittext_userid);
        mEditTextPassword = (EditText)findViewById(R.id.edittext_password);
        mTextViewSignUp = (TextView)findViewById(R.id.textview_signup);
        mButtonLogin = (Button)findViewById(R.id.button_login);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = new ProgressDialog(LogIn.this);
                dialog.setMessage("Please wait");
                dialog.show();
                mUserName = mEditTextUserName.getText().toString();
                mPassword = mEditTextPassword.getText().toString();
                mRef.authWithPassword(mUserName, mPassword, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        uid = authData.getUid();
                        mButtonLogin.setText("Success");
                        dialog.cancel();
                        builder.show();
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        mButtonLogin.setText("Failed");
                    }
                });
            }
        });
        mTextViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),SignUpActvity.class));
            }
        });
    }

    private void setBuilder() {
        builder.setMessage("What Would you like to do");
        builder.setTitle("Login Successful");
        builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getBaseContext(), ProfileViewActivity.class).putExtra("uid", uid));
            }
        });

        builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getBaseContext(), EditProfileActivity.class).putExtra("uid", uid));

            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
