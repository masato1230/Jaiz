package com.jp_funda.jaiz.LessonFragments.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jp_funda.jaiz.R;

public class LessonHomeFragment extends Fragment {

    private LessonHomeViewModel mViewModel;

    public static LessonHomeFragment newInstance() {
        return new LessonHomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lesson_home, container, false);

        return root;
    }
}