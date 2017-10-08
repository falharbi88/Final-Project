package edu.depaul.csc472.finalproject;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class SignUp extends AppCompatActivity {

    public EditText usernameInput, emailInput, firstNameInput, lastNameInput, passwordInput;
    public Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUp= (Button) findViewById(R.id.SignUpButton);
        usernameInput = (EditText) findViewById(R.id.usernameInput);
        emailInput = (EditText) findViewById(R.id.emailInput);
        firstNameInput = (EditText) findViewById(R.id.firstNameInput);
        lastNameInput = (EditText) findViewById(R.id.lastNameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        final boolean check = usernameInput.getText().toString().isEmpty()||emailInput.getText().toString().isEmpty()||
                firstNameInput.getText().toString().isEmpty()||lastNameInput.getText().toString().isEmpty()||
                passwordInput.getText().toString().isEmpty();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!check) {
                final ProgressDialog myDialog = new ProgressDialog(SignUp.this);
                myDialog.setMessage("Please waiting....");
                myDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.child(usernameInput.getText().toString()).exists()) {
                            myDialog.dismiss();
                            User newUser = new User(emailInput.getText().toString(), firstNameInput.getText().toString(), lastNameInput.getText().toString(), passwordInput.getText().toString());
                            table_user.child(usernameInput.getText().toString()).setValue(newUser);
                            Toast.makeText(SignUp.this, "Sign up successfully", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            myDialog.dismiss();
                            Toast.makeText(SignUp.this, "User is exist", Toast.LENGTH_LONG).show();
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                }else{
                    Toast.makeText(SignUp.this, "Please Complete All Fields", Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}
