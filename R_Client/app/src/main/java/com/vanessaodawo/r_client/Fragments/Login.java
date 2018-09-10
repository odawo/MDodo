package com.vanessaodawo.r_client.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vanessaodawo.r_client.HomeActivity;
import com.vanessaodawo.r_client.MainActivity;
import com.vanessaodawo.r_client.R;

import dmax.dialog.SpotsDialog;

public class Login extends Fragment {

    EditText email, password;
    Button loginBtn;
    ImageButton back;
    TextView forgotPass, resendLink;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference clientDb;

    android.support.v4.app.FragmentManager fm;

    public Login() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        email = view.findViewById(R.id.logemail);
        password = view.findViewById(R.id.logpassword);
        loginBtn = view.findViewById(R.id.loginBtn);
        forgotPass = view.findViewById(R.id.forgotPass);
        back = view.findViewById(R.id.backIB);
        resendLink = view.findViewById(R.id.resendLink);

        resendLink.setVisibility(View.INVISIBLE);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        clientDb = firebaseDatabase.getReference("ClientInformation");

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassMod();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        return view;
    }

    private void loginUser() {
        String log_email = email.getText().toString().trim();
        String log_pass = password.getText().toString().trim();

        if (email.length() == 0) {

            email.setError("empty field!");
            email.requestFocus();
            return;

        } else if(password.length() == 0){

            password.setError("empty field!");
            password.requestFocus();
            return;

        }else {

//            AlertDialog.Builder waitingD = new AlertDialog.Builder(getActivity());
//            SpotsDialog.Builder ad = new SpotsDialog.Builder(getActivity());

            firebaseAuth.signInWithEmailAndPassword(log_email, log_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "ACCESS GRANTED", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        updateUI(firebaseUser);
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    } else {
                        Toast.makeText(getActivity(), "AUTHENTICATION ERROR. TRY AGAIN", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "ERROR : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    updateUI(null);
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
//            loadFragment(new Register());
            startActivity(new Intent(getActivity(), HomeActivity.class));
        }
    }

    private void loadFragment(Register register) {
        Register reg = new Register();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, reg).commit();
    }

    private void forgotPassMod() {

        if (email.length() == 0) {

            email.setError("empty field!");
            email.requestFocus();
            return;

        } else {
            firebaseAuth.sendPasswordResetEmail(email.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getActivity(), "sent..", Toast.LENGTH_SHORT).show();
                    loadResetPass();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    resendLink.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "unable to send a reset link. Click RESEND.", Snackbar.LENGTH_LONG).show();

                    resendLink_meth();
                }
            });
        }

    }

    private void resendLink_meth() {
        resendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassMod();
            }
        });
    }

    private void loadResetPass() {

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.card_forgot_pass);
        Button btn = dialog.findViewById(R.id.ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "LINK TO OPEN EMAIL", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

}
