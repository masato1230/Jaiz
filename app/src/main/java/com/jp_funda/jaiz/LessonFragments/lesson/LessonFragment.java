package com.jp_funda.jaiz.LessonFragments.lesson;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.jp_funda.jaiz.LessonFragments.home.LessonHomeFragment;
import com.jp_funda.jaiz.R;


public class LessonFragment extends Fragment {
    public LessonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_lesson, container, false);
        ImageView button = root.findViewById(R.id.lesson_break_button);
        button.setOnClickListener(this::onClickButton);
        return root;
    }

    private void onClickButton(View view) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.slide_out);
        transaction.remove(this);
        transaction.add(R.id.lesson_fragment_container, new LessonHomeFragment());
        transaction.commit();
    }
}