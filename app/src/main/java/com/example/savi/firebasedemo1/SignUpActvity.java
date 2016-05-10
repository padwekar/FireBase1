package com.example.savi.firebasedemo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class SignUpActvity extends AppCompatActivity {
    Firebase mRef ;
    EditText mEditTextUserName , mEditTextPassword ;
    Button mButtonSignUp ;
    String mUserName , mPassword ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_actvity);
        mRef = new Firebase("https://cloudsave.firebaseio.com/");
        mEditTextUserName = (EditText)findViewById(R.id.edittext_userid);
        mEditTextPassword = (EditText)findViewById(R.id.edittext_password);
        mButtonSignUp = (Button)findViewById(R.id.button_signup);
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserName = mEditTextUserName.getText().toString();
                mPassword = mEditTextPassword.getText().toString();
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(mUserName).matches()){
                    Toast.makeText(getApplicationContext(),"not a valid email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mPassword.length()<6){
                    Toast.makeText(getApplicationContext(),"password too short",Toast.LENGTH_SHORT).show();
                    return;
                }

                mRef.createUser(mUserName, mPassword, new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        mButtonSignUp.setText("success");
                        startActivity(new Intent(getBaseContext(), LogIn.class));
                        Toast.makeText(getApplicationContext(),"You are successfully registered",Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Login to Continue..",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {

                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up_actvity, menu);
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
