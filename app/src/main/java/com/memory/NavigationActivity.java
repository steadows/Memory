package com.memory;

import static androidx.navigation.Navigation.findNavController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class NavigationActivity extends AppCompatActivity {

    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        //setContentView(R.layout.fragment_main_menu);
    }

    /*************************************
     * Changes what the back button does
     *************************************/
//    @Override
//    public void onBackPressed() {
//        //setContentView(R.layout.activity_navigation);
//    }
}