package com.jp_funda.jaiz.ViewModles;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jp_funda.jaiz.models.Lesson;

public class LessonViewModel extends ViewModel {
    private MutableLiveData<Lesson> lesson;

    public LiveData<Lesson> getLessonData() {
        return lesson;
    }

    public void loadLessonData() {

    }
}
