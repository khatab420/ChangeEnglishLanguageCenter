package com.khataab.changeenglishlanguagecenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OnboardingFragment extends Fragment {
    private int position;
    ImageView imageView_for_on_boarding;
    TextView heading_for_on_boarding, description_for_on_boarding;
    EditText username, password;
    Button login;
    SharedPreferences preferences;
    View progessBar;

    public static OnboardingFragment newInstance(int position) {
        OnboardingFragment fragment = new OnboardingFragment();
        fragment.position = position;
        return fragment;

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for the specific onboarding slide
        View view = inflater.inflate(R.layout.onboarding, container, false);
        // Customize the slide content based on the position
        imageView_for_on_boarding = view.findViewById(R.id.imageview_for_onboarding);
        heading_for_on_boarding = view.findViewById(R.id.heading_for_on_boarding);
        description_for_on_boarding = view.findViewById(R.id.description_for_on_boarding);
        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());


        if (position == 4) {
            // This is the last page, display the login form
            view = inflater.inflate(R.layout.fragment_login_, container, false);
            // Initialize your login form elements and set click listeners.
            // You can find them using view.findViewById and add your logic.
            username = view.findViewById(R.id.username);
            password = view.findViewById(R.id.password);
            login = view.findViewById(R.id.btn_login);
            progessBar = view.findViewById(R.id.loginprogress);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String enteredUsername = username.getText().toString();
                    String enteredPassword = password.getText().toString();
                    //   progessBar.setVisibility(View.VISIBLE);
                    authenticationIsSuccessful(enteredUsername, enteredPassword);

                }
            });






        } else {
            // Customize the slide content based on the position
            view = inflater.inflate(R.layout.onboarding, container, false);
            // Customize the content for other slides as you did before.
            imageView_for_on_boarding = view.findViewById(R.id.imageview_for_onboarding);
            heading_for_on_boarding = view.findViewById(R.id.heading_for_on_boarding);
            description_for_on_boarding = view.findViewById(R.id.description_for_on_boarding);
            if (position == 0) {
                // Customize content for slide 1
                imageView_for_on_boarding.setImageResource(R.drawable.first_image_for_on_boarding);
                heading_for_on_boarding.setText("With Change");
                description_for_on_boarding.setText("Get your English on!");
            } else if (position == 1) {
                // Customize content for slide 2
                imageView_for_on_boarding.setImageResource(R.drawable.second_images_for_onboarding);
                heading_for_on_boarding.setText("With Change");
                description_for_on_boarding.setText("Speak like a native!");
            }
            else if (position == 2) {
                // Customize content for slide 2
                imageView_for_on_boarding.setImageResource(R.drawable.third_image_for_on_boarding);
                heading_for_on_boarding.setText("With Change");
                description_for_on_boarding.setText("Learn English like a boss!");
            }
            else if (position == 3) {
                // Customize content for slide 2
                imageView_for_on_boarding.setImageResource(R.drawable.forurth_images_for_on_boarding);
                heading_for_on_boarding.setText("Thanks");
                description_for_on_boarding.setText(" For Joining the English revolution!");
            }
        }

        return view;
    }

    private void authenticationIsSuccessful(String enteredUsername, String enteredPassword) {

        DatabaseReference metadataRef = FirebaseDatabase.getInstance().getReference("user_data");
        //progessBar.setVisibility(View.GONE);

        metadataRef.orderByChild("ID").equalTo(username.getText().toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            // Check if the 'Type' is also "Listening"
                            String firebase_username = childSnapshot.child("ID").getValue(String.class);


                            if (firebase_username != null && firebase_username.equals(password.getText().toString())) {
                                Toast.makeText(getActivity(), "You are logged in succeesfully", Toast.LENGTH_SHORT).show();

                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("isFirstRun", false);
                                editor.putString("Logged_In_User_Name",firebase_username);
                                editor.apply();
                                username.setText("");
                                password.setText("");
                                Intent mainIntent = new Intent(getActivity(),Splash_Activity.class);
                                startActivity(mainIntent);
                                getActivity().finish(); // Close the welcome screen
                                //progessBar.setVisibility(View.GONE);

                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("errorMIne", error.getMessage());

                    }

                });



    }
}