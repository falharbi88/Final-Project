package edu.depaul.csc472.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onClickSignUp(View v){

    }

    public void onClickLogin(View v){
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);

    }
}
