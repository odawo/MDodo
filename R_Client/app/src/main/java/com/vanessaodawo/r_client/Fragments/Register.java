package com.vanessaodawo.r_client.Fragments;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vanessaodawo.r_client.HomeActivity;
import com.vanessaodawo.r_client.MainActivity;
import com.vanessaodawo.r_client.POJO.Clients;
import com.vanessaodawo.r_client.R;

public class Register extends Fragment {

    EditText u_name, email, password;
    Button continueBtn;
    ImageButton backBtn;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference clientDb;

    public Register() {
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
        View view  = inflater.inflate(R.layout.fragment_register, container, false);

        u_name = view.findViewById(R.id.regUsername);
        email = view.findViewById(R.id.regemail);
        password = view.findViewById(R.id.regpassword);
        continueBtn = view.findViewById(R.id.continueBtn);
        backBtn = view.findViewById(R.id.backBt);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        clientDb = firebaseDatabase.getReference("ClientInformation");

        return view;
    }

    private void registerUser() {

        if (u_name.length() == 0 || email.length() == 0) {

            u_name.setError("empty field!");
            u_name.requestFocus();
            return;

        } else if (password.length() == 5 ) {

            password.setError("password requires 6 or more characters!");
            password.requestFocus();
            return;

        } else {

            firebaseAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    final Clients clients = new Clients();
                    clients.setC_email(email.getText().toString().trim());
                    clients.setC_uname(u_name.getText().toString().trim());

                    if (task.isSuccessful()) {
//                        registers new users to firebaseuser & db
                        clientDb.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(clients).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Registered " + u_name.getText(), Toast.LENGTH_SHORT).show();
                                firebaseUser = firebaseAuth.getCurrentUser();
//                                updateUI(firebaseUser);

                                startActivity(new Intent(getActivity(), HomeActivity.class));
                            }
                        });

                    } else {
                        FirebaseAuthException e = (FirebaseAuthException)task.getException();
                        Toast.makeText(getActivity(), "Reg ERROR : " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        updateUI(null);
                        password.setText("");

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( getActivity(), "Failed: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

//    private void updateUI(FirebaseUser firebaseUser) {
//        if(firebaseUser != null){
//            startActivity(new Intent(getActivity(), HomeActivity.class));
//        }
//    }

}
