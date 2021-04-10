package com.jp_funda.jaiz.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import com.jp_funda.jaiz.Data.LessonDatabaseHandler;
import com.jp_funda.jaiz.LessonFragments.home.LessonHomeFragment;
import com.jp_funda.jaiz.LessonFragments.lesson.LessonFragment;
import com.jp_funda.jaiz.R;
import com.jp_funda.jaiz.ViewModles.LessonViewModel;
import com.jp_funda.jaiz.models.Lesson;

public class LessonActivity extends AppCompatActivity {
    public LessonViewModel lessonViewModel;
    private LessonDatabaseHandler lessonDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        // initialize DB
        lessonDB = new LessonDatabaseHandler(this);
        Lesson lesson = lessonDB.getLesson(1);
        Log.d("LessonNumber", String.valueOf(lesson.getLessonNumber()));

        // detect lesson number from intent
        lessonViewModel = new ViewModelProvider(this).get(LessonViewModel.class);
        // lessonViewModel.loadLessonData();
        lessonViewModel.getLessonData();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.lesson_fragment_container, new LessonHomeFragment());
        transaction.commit();
    }
}