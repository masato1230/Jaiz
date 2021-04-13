package com.jp_funda.jaiz.ViewModles;

import androidx.lifecycle.ViewModel;

import com.jp_funda.jaiz.models.CurrentStatus;
import com.jp_funda.jaiz.models.Lesson;
import com.jp_funda.jaiz.models.UserLessonStatus;

public class LessonViewModel extends ViewModel {
    public Lesson lesson;
    public UserLessonStatus lessonStatus;
    public CurrentStatus currentStatus;
}
