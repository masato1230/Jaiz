package com.jp_funda.jaiz.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.jp_funda.jaiz.Constants;
import com.jp_funda.jaiz.Database.LessonDatabaseHandler;
import com.jp_funda.jaiz.Database.UserDatabaseHandler;
import com.jp_funda.jaiz.LessonFragments.home.LessonHomeFragment;
import com.jp_funda.jaiz.R;
import com.jp_funda.jaiz.ViewModles.LessonViewModel;
import com.jp_funda.jaiz.models.CurrentStatus;
import com.jp_funda.jaiz.models.Lesson;
import com.jp_funda.jaiz.models.UserLessonStatus;

public class LessonActivity extends AppCompatActivity {
    private int lessonNumber;

    // Data
    public LessonViewModel lessonViewModel;
    public CurrentStatus currentStatus;
    private LessonDatabaseHandler lessonDB;
    private UserDatabaseHandler userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        // initialize DB
        lessonDB = new LessonDatabaseHandler(this);
        userDB = new UserDatabaseHandler(this);

        // Detect lesson number from intent
        lessonNumber = (int) getIntent().getExtras().get(Constants.LESSON_NUMBER_LABEL);

        // Store lesson data in ViewModel
        lessonViewModel = new ViewModelProvider(this).get(LessonViewModel.class);
        Lesson lesson = lessonDB.getLesson(lessonNumber);
        UserLessonStatus lessonStatus = userDB.getUserLessonStatus(lessonNumber);
        lessonViewModel.lesson = lesson;
        lessonViewModel.lessonStatus = lessonStatus;
        // set currentStatus
        CurrentStatus currentStatus = new CurrentStatus();
        currentStatus.setProblemIndex(0);
        lessonViewModel.currentStatus = currentStatus;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.lesson_fragment_container, new LessonHomeFragment());
        transaction.commit();
    }
}