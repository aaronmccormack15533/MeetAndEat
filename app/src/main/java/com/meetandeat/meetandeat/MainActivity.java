package com.meetandeat.meetandeat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

//http://blog.iamsuleiman.com/using-bottom-navigation-view-android-design-support-library/
//https://github.com/1priyank1/BottomNavigation-Demo

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ViewPager myPager;
    private BottomNavigationView bottomNavigationView;
    private MenuItem prevMenuItem;
    private Button myButton;
    private Button logOutBtn;
    //Get the Image from facebook to display on the profile
    private TextView user_profile_name;
    private String name;
    private TextView user_profile_short_bio;
    private String bio;
    private TextView user_profile_age;
    private String age;
    private ImageButton user_profile_photo;
    private Firebase firebaseRef;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    //Spinner



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hobbies_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);

//        spinner.setOnItemSelectedListener( this );

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseRef = new Firebase("https://meet-and-eat-163108.firebaseio.com/");

        if(firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(this, Login.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        //Log Out Button
        logOutBtn = (Button) findViewById(R.id.logOutBtn);

        //fragments
        myPager = (ViewPager) findViewById(R.id.Pager);
        myPager.setAdapter(new SamplePagerAdapter(getSupportFragmentManager()));

        //navigation
        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Nullable
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_messenger:
                                myPager.setCurrentItem(0);
                                break;
                            case R.id.action_restaurants:
                                myPager.setCurrentItem(1);
                                break;
                            case R.id.action_profiles:
                                myPager.setCurrentItem(2);
                                break;
                        }
                        return true;
                    }
                }
        );
        //https://github.com/jaisonfdo/BottomNavigation
        myPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //logOutBtn.setOnClickListener(this);
        myButton = (Button) findViewById(R.id.Button);

        /*
        //new spinner test
        Spinner mySpinner = (Spinner) findViewById( R.id.spinner1 );
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>( MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray( R.array.hobbies) );
        myAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        mySpinner.setAdapter( myAdapter );
        /*




        //Profile Spinner
        /*
        final String [] arr = {"Test", "Test2"};
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.fragment_profile, arr);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this,R.array.Hobbies,android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText( getApplicationContext(),parent.getItemAtPosition(position)+"selected", Toast.LENGTH_LONG ).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );*/

        //Setting the name, bio and age to the profile.
        //Email Edit
        user_profile_name = (TextView) findViewById(R.id.user_profile_name);
        user_profile_short_bio = (TextView) findViewById(R.id.user_profile_short_bio);
        user_profile_age = (TextView) findViewById(R.id.user_profile_age);
/*
        //Not Working
        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        user_profile_name.setText(name);
        String age = intent.getStringExtra("Age");
        user_profile_age.setText(age);
        String bio = intent.getStringExtra("Bio");
        user_profile_short_bio.setText(bio);
*/
    }

    public class SamplePagerAdapter extends FragmentPagerAdapter {
        public SamplePagerAdapter(FragmentManager fragM) {
            super(fragM);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new MessageFragment();

            } else if (position == 1) {
                return new HomeFragment();
            } else {
                return new ProfileFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public void restFinder(View view){
        Intent i = new Intent(this, restaurantFinder.class);
        startActivity(i);
    }

    public void findSelection(View view){
        Intent j = new Intent(this, peopleSelection.class);
        startActivity(j);
    }
    public void logOutBtn(View view){
        firebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        finish();
        startActivity(new Intent(this, Login.class));
    }
    public void openEdit(View view){
        Intent l = new Intent(this, EditProfileActivity.class);
        startActivity(l);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String sSelected=parent.getItemAtPosition(pos).toString();
        Toast.makeText( this,sSelected,Toast.LENGTH_SHORT ).show();

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
