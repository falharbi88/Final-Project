package edu.depaul.csc472.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.depaul.csc472.finalproject.Model.User;

public class ProfileActivity extends AppCompatActivity {
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        username = getIntent().getStringExtra("Username");
        final TextView customerName = (TextView) findViewById(R.id.customerName);
        final TextView customerUsername = (TextView) findViewById(R.id.customerUsername);
        final TextView customerEmail = (TextView) findViewById(R.id.customerEmail);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(username).exists()) {
                    User user = dataSnapshot.child(username).getValue(User.class);
                    customerName.setText(user.getFirstName() + " " + user.getLastName());
                    customerUsername.setText(username);
                    customerEmail.setText(user.getEmail());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
