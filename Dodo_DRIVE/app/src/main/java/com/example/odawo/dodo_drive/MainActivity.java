package com.example.odawo.dodo_drive;

import android.content.SharedPreferences;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText email = findViewById(R.id.loginemail);
        final EditText password = findViewById(R.id.loginpassword);

        //firebase auth
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
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
                Toast.makeText(MainActivity.this, "auth the user and loads map", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //hide when fragment loads
                email.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
                findViewById(R.id.etikalogo).setVisibility(View.INVISIBLE);
                findViewById(R.id.tt).setVisibility(View.INVISIBLE);
                findViewById(R.id.forgotPass).setVisibility(View.INVISIBLE);
                findViewById(R.id.loginBtn).setVisibility(View.INVISIBLE);
                findViewById(R.id.signUp).setVisibility(View.INVISIBLE);

                loadFragment(new Registration());
            }
        });

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
        Registration registration1 = new Registration();
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new Registration());
        ft.addToBackStack(null).commit();
    }
}
