package com.jp_funda.jaiz.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.jp_funda.jaiz.Constants;
import com.jp_funda.jaiz.Database.LessonDatabaseHandler;
import com.jp_funda.jaiz.LessonFragments.home.LessonHomeFragment;
import com.jp_funda.jaiz.R;
import com.jp_funda.jaiz.ViewModles.LessonViewModel;
import com.jp_funda.jaiz.models.Lesson;

public class LessonActivity extends AppCompatActivity {
    private int lessonNumber;

    // Data
    public LessonViewModel lessonViewModel;
    private LessonDatabaseHandler lessonDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        // initialize DB
        lessonDB = new LessonDatabaseHandler(this);

        // Detect lesson number from intent
        lessonNumber = (int) getIntent().getExtras().get(Constants.LESSON_NUMBER_LABEL);
        // Store lesson data in ViewModel
        lessonViewModel = new ViewModelProvider(this).get(LessonViewModel.class);
        Lesson lesson = lessonDB.getLesson(1);
        lessonViewModel.lesson = lesson;

        Log.d("LessonNumber", String.valueOf(lessonViewModel.lesson.getLessonNumber()));
        Log.d("LessonName", String.valueOf(lessonViewModel.lesson.getLessonName()));
        Log.d("LessonNameJP", String.valueOf(lessonViewModel.lesson.getLessonNameJP()));
        for (String word: lessonViewModel.lesson.getWords()) {
            Log.d("Word", word);
        }


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.lesson_fragment_container, new LessonHomeFragment());
        transaction.commit();
    }
}