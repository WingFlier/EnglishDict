package com.cs_final.englishdict;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.cs_final.englishdict.fragments.MainPageFragment;
import com.cs_final.englishdict.fragments.SignUpFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cs_final.com.englishdict.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static FirebaseUser currentUser = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (currentUser == null)
        {
            // no user is signed in
            // function to create user with mail and password
//            mAuth.createUserWithEmailAndPassword("tiko1581@gmail.com", "abaawkdajk");
            SignUpFragment fragment = new SignUpFragment();
            moveToFragment(fragment);
        } else
        {
            Toast.makeText(this, "signed in already", Toast.LENGTH_SHORT).show();
            // user is signed in
            MainPageFragment mainPageFragment = MainPageFragment.newInstance(null, null);
            moveToFragment(mainPageFragment);
            //change user display name
//            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                    .setDisplayName("user" + Math.random()).build();
//            currentUser.updateProfile(profileUpdates);
        }
    }

    public void moveToFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, fragment, null)
                .commit();
    }

    @Override
    public void onClick(View view)
    {
        System.out.println("");
    }
}