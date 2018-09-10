package com.vanessaodawo.r_client;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.vanessaodawo.r_client.Fragments.CodeFRag;
import com.vanessaodawo.r_client.Fragments.Login;

public class MainActivity extends AppCompatActivity {

    Button emailSignIn;
    EditText phone;
    TextView timertxt, whyPhone;
    ImageButton sendCode;
    RelativeLayout rlay1, rlay2;

    android.support.v4.app.FragmentManager fm;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rlay1.setVisibility(View.VISIBLE);
            rlay2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler.postDelayed(runnable, 10000);

        emailSignIn = findViewById(R.id.emailSignIn);
        phone = findViewById(R.id.phone);
        sendCode = findViewById(R.id.sendCode);
        whyPhone = findViewById(R.id.whyPhone);
        rlay1 = findViewById(R.id.childrlay);
        rlay2 = findViewById(R.id.childrlay2);

        whyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "either a page or link explaining" +
                        " reasons for the use of phone number authentication", Toast.LENGTH_LONG).show();
            }
        });

        emailSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Login());
            }
        });

        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (phone.length() < 10) {
                    phone.setError("invalid phone number");
                    phone.requestFocus();
                    return;
                } else {

                    loadFragment(new CodeFRag());

                }

            }
        });

    }

//    private void starttimer() {
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//
//            int second = 60;
//
//            @Override
//            public void run() {
//                if (second <= 0) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            timertxt.setText("RESEND CODE");
//                            timer.cancel();
//                        }
//                    });
//
//                } else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            timertxt.setText("00:" + second--);
//                        }
//                    });
//                }
//
//            }
//        }, 0, 1000);
//    }


    private void loadFragment(Login login) {
//        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.frameLayout, new Login());
//        ft.commit();
        emailSignIn.setVisibility(View.INVISIBLE);
        findViewById(R.id.phone).setVisibility(View.INVISIBLE);
        findViewById(R.id.whyPhone).setVisibility(View.INVISIBLE);
        findViewById(R.id.viewdiv).setVisibility(View.INVISIBLE);
        findViewById(R.id.sendCode).setVisibility(View.INVISIBLE);

        Login lgn = new Login();
        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frameLayout, lgn).commit();
    }

    private void loadFragment(CodeFRag codeFRag) {

        findViewById(R.id.phone).setVisibility(View.INVISIBLE);
        findViewById(R.id.whyPhone).setVisibility(View.INVISIBLE);
        findViewById(R.id.viewdiv).setVisibility(View.INVISIBLE);
        findViewById(R.id.sendCode).setVisibility(View.INVISIBLE);
        findViewById(R.id.emailSignIn).setEnabled(false);

        String mobile = phone.getText().toString().trim();
        CodeFRag cfr = new CodeFRag();

        Bundle bundle = new Bundle();
        bundle.putString("mobile", mobile);

        cfr.setArguments(bundle);

        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frameLayout, cfr).commit();
    }

    //    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        updateUI(currentUser);
//    }

//    private void updateUI(FirebaseUser currentUser) {
//        if (currentUser != null) {
//            startActivity(new Intent(this, HomeActivity.class));
//        } else {
//            startActivity(new Intent(this, MainActivity.class));
//        }
//    }

}
