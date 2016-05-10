package com.example.savi.firebasedemo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

public class EditProfileActivity extends AppCompatActivity {
    EditText  mEditTextName ,mEditTextAge ,mEditTextSex ,mEditTextCity ,mEditTextPhone ;
    String uid ;
    Firebase mRef ;
    Button mButtonSave ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://cloudsave.firebaseio.com/");
        uid = getIntent().getStringExtra("uid");
        mRef.child(uid);
        mEditTextName = (EditText)findViewById(R.id.edittext_name);
        mEditTextAge = (EditText)findViewById(R.id.edittext_age);
        mEditTextCity = (EditText)findViewById(R.id.edittext_city);
        mEditTextPhone = (EditText)findViewById(R.id.edittext_phone);
        mEditTextSex = (EditText)findViewById(R.id.edittext_sex);
        mButtonSave = (Button)findViewById(R.id.button_save);
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef.child(uid).child("name").setValue(mEditTextName.getText().toString());
                mRef.child(uid).child("age").setValue(mEditTextAge.getText().toString());
                mRef.child(uid).child("sex").setValue(mEditTextSex.getText().toString());
                mRef.child(uid).child("phone").setValue(mEditTextPhone.getText().toString());
                mRef.child(uid).child("city").setValue(mEditTextCity.getText().toString());
                startActivity(new Intent(getBaseContext(),ProfileViewActivity.class).putExtra("uid",uid));

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
