package edu.depaul.csc472.finalproject;

import android.app.ProgressDialog;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFields()) {
                final ProgressDialog myDialog = new ProgressDialog(SignUp.this);
                myDialog.setMessage("Please waiting....");
                myDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!(dataSnapshot.child(usernameInput.getText().toString()).exists())) {
                            myDialog.dismiss();
                            User newUser = new User(emailInput.getText().toString(), firstNameInput.getText().toString(), lastNameInput.getText().toString(), passwordInput.getText().toString());
                            table_user.child(usernameInput.getText().toString()).setValue(newUser);
                            Toast.makeText(SignUp.this, newUser.toString(), Toast.LENGTH_LONG).show();
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
        }else if (passwordInput.getText().length()<=8) {
            passwordInput.setError("Password length must be greater than 8 characters");
            focusView = passwordInput;
            return false;
        } else if (TextUtils.isEmpty(firstNameInput.getText().toString())) {
            firstNameInput.setError(getString(R.string.error_field_required));
            focusView = firstNameInput;
            Toast.makeText(this, "First name can't be empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (TextUtils.isEmpty(lastNameInput.getText().toString())) {
            lastNameInput.setError(getString(R.string.error_field_required));
            focusView = lastNameInput;
            Toast.makeText(this, "Last name can't be empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (TextUtils.isEmpty(emailInput.getText().toString())) {
            emailInput.setError(getString(R.string.error_field_required));
            focusView = emailInput;
            Toast.makeText(this, "Email name can't be empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (!isEmailValid(emailInput.getText().toString())) {
            emailInput.setError("Invalid email format");
            focusView = emailInput;
            return false;
        }
        return true;
    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
