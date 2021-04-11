package com.jp_funda.jaiz.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.jp_funda.jaiz.Constants;
import com.jp_funda.jaiz.Database.LessonDatabaseHandler;
import com.jp_funda.jaiz.Database.UserDatabaseHandler;
import com.jp_funda.jaiz.LessonFragments.home.LessonHomeFragment;
import com.jp_funda.jaiz.R;
import com.jp_funda.jaiz.ViewModles.LessonViewModel;
import com.jp_funda.jaiz.models.Lesson;
import com.jp_funda.jaiz.models.UserLessonStatus;

public class LessonActivity extends AppCompatActivity {
    private int lessonNumber;

    // Data
    public LessonViewModel lessonViewModel;
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

        // todo delete
        UserLessonStatus testLessonStatus = new UserLessonStatus();
        testLessonStatus.setLessonNumber(lessonNumber);
        testLessonStatus.setWords(lessonDB.getLesson(lessonNumber).getWordsJP());
        userDB.addOrUpdateLessonStatus(testLessonStatus);

        // Store lesson data in ViewModel
        lessonViewModel = new ViewModelProvider(this).get(LessonViewModel.class);
        Lesson lesson = lessonDB.getLesson(lessonNumber);
        UserLessonStatus lessonStatus = userDB.getUserLessonStatus(lessonNumber);
        lessonViewModel.lesson = lesson;
        lessonViewModel.lessonStatus = lessonStatus;

        // todo delete
        Log.d("number", String.valueOf(lessonStatus.getLessonNumber()));
        Log.d("words", String.valueOf(lessonStatus.getWords().get(10)));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.lesson_fragment_container, new LessonHomeFragment());
        transaction.commit();
    }
}