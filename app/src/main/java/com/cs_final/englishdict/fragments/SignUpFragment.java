package com.cs_final.englishdict.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cs_final.englishdict.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.UserProfileChangeRequest;

import cs_final.com.englishdict.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener
{
    TextView googleLogin, login, resetPassword;
    EditText email, password, password_confirm;
    Button signup;

    public SignUpFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        googleLogin = view.findViewById(R.id.google_login);
        login = view.findViewById(R.id.login);
        resetPassword = view.findViewById(R.id.forgot_password);
        signup = view.findViewById(R.id.signup_btn);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        password_confirm = view.findViewById(R.id.password_confirm);

        googleLogin.setOnClickListener(this);
        login.setOnClickListener(this);
        resetPassword.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.signup_btn:
                Toast.makeText(getActivity().getApplicationContext(), "signup_btn", Toast.LENGTH_SHORT).show();
                //TODO show pgDialog
                signup();
                break;
            //TODO
            case R.id.google_login:
                Toast.makeText(getActivity().getApplicationContext(), "google_login", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login:
                Toast.makeText(getActivity().getApplicationContext(), "login", Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_frame,
                                LoginFragment.newInstance(null, null)).commit();
                break;
            case R.id.forgot_password:
                Toast.makeText(getActivity().getApplicationContext(), "forgot password", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void signup()
    {
        //TODO validate email and passwords before sending
        String email_text = email.getText().toString();
        String password_confirm_text = password_confirm.getText().toString();
        String password_text = password.getText().toString();
        // if validation is passed do next line
        MainActivity.mAuth.createUserWithEmailAndPassword(email_text, password_text)
                .addOnSuccessListener(getActivity(), new OnSuccessListener<AuthResult>()
                {
                    @Override
                    public void onSuccess(AuthResult authResult)
                    {
                        //TODO hide pgDialog
                        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                        AlertDialog ad = new AlertDialog.Builder(getActivity())
                                .create();
                        ad.setCancelable(false);
                        ad.setTitle("Choose your nickname");
                        final EditText input = new EditText(getActivity().getApplicationContext());
                        ad.setView(input);
                        ad.setButton("Ok", new DialogInterface.OnClickListener()
                        {

                            public void onClick(DialogInterface dialog, int which)
                            {
                                //TODO show pgDialog again
                                addNickname(input);
                                dialog.dismiss();
                            }
                        });
                        ad.show();
                    }

                    private void addNickname(EditText input)
                    {
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(input.getText().toString()).build();
                        Task<Void> changeNickname = MainActivity.mAuth.getCurrentUser().updateProfile(profileUpdates);
                        changeNickname.addOnSuccessListener(getActivity(), new OnSuccessListener<Void>()
                        {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                Toast.makeText(getActivity().getApplicationContext(), "Nickname has been set", Toast.LENGTH_SHORT).show();
                                //TODO move to main page fragment
                                MainPageFragment fragment = MainPageFragment.newInstance(null, null);
                                getFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_frame,
                                                fragment,
                                                null)
                                        .addToBackStack(null)
                                        .commit();
                            }
                        });
                        changeNickname.addOnFailureListener(getActivity(), new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        //TODO hide pgDialog
                    }
                })
                .addOnFailureListener(getActivity(), new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        //TODO hide pgDialog
                    }
                });
//        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                .setDisplayName(nickname_text).build();
//        MainActivity.mAuth.getCurrentUser().updateProfile(profileUpdates);
    }
}