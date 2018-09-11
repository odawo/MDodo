package com.example.odawo.dodo_drive;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.odawo.dodo_drive.model_class.Common;
import com.example.odawo.dodo_drive.model_class.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Registration extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Registration() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Registration.
     */
    // TODO: Rename and change types and number of parameters
    public static Registration newInstance(String param1, String param2) {
        Registration fragment = new Registration();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        final EditText ed_fn = view.findViewById(R.id.regfirstName);
        final EditText ed_ln = view.findViewById(R.id.reglastName);
        final EditText ed_em = view.findViewById(R.id.regemail);
        final EditText ed_pwd = view.findViewById(R.id.regpassword);
        final EditText ed_tel = view.findViewById(R.id.regtelephone);

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference userDB = firebaseDatabase.getReference(Common.DRIVERINFO_TB);

        view.findViewById(R.id.registerBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ed_fn.getText().toString().isEmpty() ||ed_em.getText().toString().isEmpty() ||
                        ed_pwd.getText().toString().isEmpty() || ed_tel.getText().toString().isEmpty()) {
                    ed_fn.setError("empty!");
                    ed_ln.setError("empty!");
                    ed_em.setError("empty!");
                    ed_pwd.setError("empty");
                    ed_tel.setError("empty!");

                    return;

                } else if (ed_pwd.length() < 5) {

                    ed_pwd.setError("Requires a minimum of 6 values");
                    ed_pwd.setFocusable(true);
                    return;

                } else {
                    firebaseAuth.createUserWithEmailAndPassword(ed_em.getText().toString().trim(), ed_pwd.getText().toString().trim()).
                            addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            User user = new User();
                            user.setName(ed_fn.getText().toString());
                            user.setEmail(ed_fn.getText().toString());
                            user.setPhone(ed_fn.getText().toString());

                            userDB.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(
                                    new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    startActivity(new Intent(getActivity(), HomeMap.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "failed : " + e.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "failed : " + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        return view;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
