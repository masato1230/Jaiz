package com.jp_funda.jaiz.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jp_funda.jaiz.Constants;
import com.jp_funda.jaiz.Database.LessonDatabaseHandler;
import com.jp_funda.jaiz.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    LessonDatabaseHandler lessonDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create lesson Database
        lessonDB = new LessonDatabaseHandler(this);
        try {
            lessonDB.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        Intent intent = new Intent(this, LessonActivity.class);
        intent.putExtra(Constants.LESSON_NUMBER_LABEL, 1);
        startActivity(intent);
    }

}