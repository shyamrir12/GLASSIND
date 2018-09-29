package com.example.awizom.glassind;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.awizom.glassind.Model.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.awizom.glassind.R.id.textViewLogin;
public class SignUpActivity extends AppCompatActivity implements  View.OnClickListener  {
    ProgressBar progressBar;
    EditText editTextEmail, editTextPassword;
    DatabaseReference datauserprofile;
    private FirebaseAuth mAuth;
    UserProfile userProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up );
        getSupportActionBar().setTitle("User SignUp");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);

    }
   /* public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewLogin:
                finish();
                startActivity(new Intent(this,LogInActivity.class));
                break;

            case R.id.buttonSignUp:
                registerUser();
                break;
        }
    }
    private boolean addUserProfile( )
    {
        //getting the specified artist reference
        datauserprofile = FirebaseDatabase.getInstance().getReference( "userprofile" );
        String id = datauserprofile.push().getKey();
        String email=mAuth.getCurrentUser().getEmail();
        userProfile=new UserProfile( id, email,"User",false );

        datauserprofile.child( id ).setValue( userProfile );
        Toast.makeText( getApplicationContext(), "Profile Added", Toast.LENGTH_LONG ).show();
        return true;
    }
    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError( "Email is required" );
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher( email ).matches()) {
            editTextEmail.setError( "Please enter a valid email" );
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError( "Password is required" );
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError( "Minimum lenght of password should be 6" );
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility( View.VISIBLE );

        mAuth.createUserWithEmailAndPassword( email, password ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility( View.GONE );
                if (task.isSuccessful()) {

                    addUserProfile();
                    finish();
                    startActivity( new Intent( SignUpActivity.this, AdminHomeActivity.class ) );
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText( getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT ).show();

                    } else {
                        Toast.makeText( getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT ).show();
                    }

                }
            }
        } );

    }
}
