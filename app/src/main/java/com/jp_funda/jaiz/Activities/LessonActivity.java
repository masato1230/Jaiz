package com.jp_funda.jaiz.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.app.FragmentManager;
import android.os.Bundle;

import com.jp_funda.jaiz.LessonFragments.home.LessonHomeFragment;
import com.jp_funda.jaiz.LessonFragments.lesson.LessonFragment;
import com.jp_funda.jaiz.R;
import com.jp_funda.jaiz.ViewModles.LessonViewModel;

public class LessonActivity extends AppCompatActivity {
    public LessonViewModel lessonViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        // detect lesson number from intent
        lessonViewModel = new ViewModelProvider(this).get(LessonViewModel.class);
        // lessonViewModel.loadLessonData();
        lessonViewModel.getLessonData();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.lesson_fragment_container, new LessonHomeFragment());
        transaction.commit();
    }
}