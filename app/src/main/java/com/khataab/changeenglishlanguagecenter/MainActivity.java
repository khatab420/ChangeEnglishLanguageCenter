package com.khataab.changeenglishlanguagecenter;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;


import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity  {

    private DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigation_view);
        toolbar=findViewById(R.id.toolbar);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id=item.getItemId();
                if(id == R.id.home) {
                    // Handle Home item click for NavigationView
                    // ...

                    Toast.makeText(MainActivity.this, " home is working", Toast.LENGTH_SHORT).show();
                   return true;

                } else if (id == R.id.settings) {

                    Toast.makeText(MainActivity.this, "setting is working", Toast.LENGTH_SHORT).show();
                     return  true;

                }
                return false;
            }
        });


        loadFragment();

//        if (savedInstanceState == null) {
//
//            navigationView.setCheckedItem(R.id.nav_home);
//        }
    }

    private void loadFragment() {

        Books_Fragments books_fragments = new Books_Fragments();

        // Get the fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Start a new fragment transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the fragment_container with your fragment
        fragmentTransaction.replace(R.id.fragment_container, books_fragments);

        // Commit the transaction
        fragmentTransaction.commit();
    }



//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        if (R.id.person == item.getItemId()) {
//            Toast.makeText(this, "Person Clicked", Toast.LENGTH_SHORT).show();
//            return true;
//        } else if (R.id.home == item.getItemId()) {
//            Toast.makeText(this, "Home Clicked", Toast.LENGTH_SHORT).show();
//            return true;
//        } else if (R.id.settings == item.getItemId()) {
//
//            Fragment fragment = new Setting_Fragment();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.fragment_container, fragment);
//            transaction.addToBackStack(null);
//            transaction.commit();
//            return true;
//        } else if (R.id.Tasbih == item.getItemId()) {
//            Toast.makeText(this, "Tasbih Clicked", Toast.LENGTH_SHORT).show();
//            return true;
//        } else if (R.id.nav_logout == item.getItemId()) {
//            Toast.makeText(this, "Logout is clicked", Toast.LENGTH_SHORT).show();
//            finish();
//
//
//        }
//        else if (R.id.nav_home == item.getItemId()) {
//            Toast.makeText(this, "Logout is clicked", Toast.LENGTH_SHORT).show();
//
//
//        }
//
//        drawerLayout.closeDrawer(GravityCompat.START);
//
//        return false;
//
//    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


//
//
//    private void setupBottomNavigationViewListener(BottomNavigationView bottomNavigationView) {
//        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
//            @Override
//            public void onNavigationItemReselected(@NonNull MenuItem item) {
//                int id=item.getItemId();
//                if(id == R.id.home) {
//                    // Handle Home item click for NavigationView
//                    // ...
//
//                    Toast.makeText(MainActivity.this, " home is working", Toast.LENGTH_SHORT).show();
//
//
//                } else if (id == R.id.settings) {
//
//                    Toast.makeText(MainActivity.this, "setting is working", Toast.LENGTH_SHORT).show();
//
//
//                }
//            }
//        });
//    }
//




//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//
//        if(item.getItemId()==R.id.settings) {
//            Fragment fragment = new Setting_Fragment();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.fragment_container, fragment);
//            transaction.addToBackStack(null);
//            transaction.commit();
//            return true;
//        }
//
//        if(item.getItemId()==R.id.person) {
//
//            Toast.makeText(this, "Person Added", Toast.LENGTH_SHORT).show();
//
//        }
//
//        return false;
//    }

    public static final class Setting_Fragment extends PreferenceFragmentCompat {
        private static final String PREF_KEY_FONT_PREFERENCE = "font_preference";

        public interface FontChangeListener {
            void onFontChanged(String selectedFont);
        }


        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            SwitchPreferenceCompat switchPreferenceCompat = findPreference("example_switch_preference");
            switchPreferenceCompat.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    boolean value = (Boolean) newValue;
                    // Do something with the new value
                    Toast.makeText(getActivity(), "toas :" +value, Toast.LENGTH_SHORT).show();
                    applyTheme(value);
                    return true; // Return true to allow the change to be saved
                }
            });

            ListPreference fontPreference = findPreference(PREF_KEY_FONT_PREFERENCE);
            fontPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String selectedFont = (String) newValue;
//                    updateFontInFragment(selectedFont);
                    return true;
                }
            });

        }


        private void applyTheme(boolean isDarkModeEnabled) {
            if (isDarkModeEnabled) {
             AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);


            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

        }



    }


}
