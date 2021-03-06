package edu.depaul.csc472.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.depaul.csc472.finalproject.Model.User;

public class SignIn extends AppCompatActivity {

    public EditText usernameInput, passwordInput;
    public Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        login= (Button) findViewById(R.id.loginButton);
        usernameInput = (EditText) findViewById(R.id.usernameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        usernameInput.setText("");
        passwordInput.setText("");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFields()) {
                    final ProgressDialog myDialog = new ProgressDialog(SignIn.this);
                    myDialog.setMessage("Please waiting....");
                    myDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(usernameInput.getText().toString()).exists()) {
                                myDialog.dismiss();
                                User user = dataSnapshot.child(usernameInput.getText().toString()).getValue(User.class);
                                if (user.getPassword().equals(passwordInput.getText().toString())) {
                                    Toast.makeText(SignIn.this, "Sign in successfully", Toast.LENGTH_LONG).show();
                                    Intent homeActivity = new Intent(SignIn.this, HomeActivity.class);
                                    homeActivity.putExtra("Username", usernameInput.getText().toString());
                                    startActivity(homeActivity);
                                } else
                                    Toast.makeText(SignIn.this, "Sign in Failed!!!!!", Toast.LENGTH_LONG).show();
                            } else {
                                myDialog.dismiss();
                                Toast.makeText(SignIn.this, "User not Found", Toast.LENGTH_LONG).show();

                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }else{
                    Toast.makeText(SignIn.this, "Please Complete All Fields", Toast.LENGTH_LONG).show();

                }
            }
        });

    }


    public boolean checkFields() {
        View focusView=null;
        if (TextUtils.isEmpty(usernameInput.getText().toString())) {
            usernameInput.setError(getString(R.string.error_field_required));
            focusView = usernameInput;
            Toast.makeText(this, "Username can't be empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (TextUtils.isEmpty(passwordInput.getText().toString())) {
            passwordInput.setError(getString(R.string.error_field_required));
            focusView = passwordInput;
            Toast.makeText(this, "Password can't be empty", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
