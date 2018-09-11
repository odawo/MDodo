package com.example.odawo.dodo_drive;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.odawo.dodo_drive.model_class.Common;
import com.example.odawo.dodo_drive.model_class.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.loginemail);
        password = findViewById(R.id.loginpassword);

        //firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();


        findViewById(R.id.forgotPass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass email from activity to popup dialog
//                SharedPreferences sharedPreferences = getSharedPreferences(email.getText().toString(), );

                loadPopup();
            }
        });

        findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                loadFragment(new Registration());
            }
        });

    }

    private void loginUser() {

        if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            email.setError("empty");
            password.setError("empty");
            return;
        } else if(password.length() < 5) {
            password.setError("Requires a minimum of 6 values");
        } else {

            firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(
                    new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseDatabase.getInstance().getReference(Common.DRIVER_TB).child(FirebaseAuth.getInstance().getCurrentUser().
                            getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Common.currentUser = dataSnapshot.getValue(User.class);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    email.setText("");
                    password.setText("");

                    startActivity(new Intent(MainActivity.this, HomeMap.class));

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "error : " + e.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void loadPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.password_reset, null);
        builder.setView(view);
        EditText email = findViewById(R.id.resetEmail);
        Button send = findViewById(R.id.sendBtn);
        AlertDialog d = builder.create();
        d.show();
    }

    private void loadFragment(Registration registration) {
        //hide when fragment loads
        email.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        findViewById(R.id.etikalogo).setVisibility(View.INVISIBLE);
        findViewById(R.id.tt).setVisibility(View.INVISIBLE);
        findViewById(R.id.forgotPass).setVisibility(View.INVISIBLE);
        findViewById(R.id.loginBtn).setVisibility(View.INVISIBLE);
        findViewById(R.id.signUp).setVisibility(View.INVISIBLE);

        Registration registration1 = new Registration();
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new Registration());
        ft.addToBackStack(null).commit();
    }
}
