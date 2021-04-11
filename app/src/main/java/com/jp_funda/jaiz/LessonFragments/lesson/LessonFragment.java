package com.jp_funda.jaiz.LessonFragments.lesson;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jp_funda.jaiz.LessonFragments.home.LessonHomeFragment;
import com.jp_funda.jaiz.R;
import com.jp_funda.jaiz.ViewModles.LessonViewModel;
import com.jp_funda.jaiz.models.Lesson;


public class LessonFragment extends Fragment {
    private Lesson lesson;
    // View properties
    private ImageView breakButton;
    private ProgressBar progressBar;
    private TextView commentText;
    private TextView progressText;
    private TextView problemWord;
    private LinearLayout answerButton1;
    private LinearLayout answerButton2;
    private LinearLayout answerButton3;
    private LinearLayout answerButton4;
    private TextView answerText1;
    private TextView answerText2;
    private TextView answerText3;
    private TextView answerText4;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_lesson, container, false);

        // Get parent activity's LessonViewModel and lesson data
        LessonViewModel lessonViewModel = new ViewModelProvider(getActivity())
                .get(LessonViewModel.class);
        lesson = lessonViewModel.lesson;

        // Initialize Views
        breakButton = root.findViewById(R.id.lesson_break_button);
        progressBar = root.findViewById(R.id.lesson_progressbar);
        commentText = root.findViewById(R.id.lesson_comment);
        progressText = root.findViewById(R.id.lesson_progress_text);
        problemWord = root.findViewById(R.id.lesson_word);
        answerButton1 = root.findViewById(R.id.lesson_answer_button1);
        answerButton2 = root.findViewById(R.id.lesson_answer_button2);
        answerButton3 = root.findViewById(R.id.lesson_answer_button3);
        answerButton4 = root.findViewById(R.id.lesson_answer_button4);
        answerText1 = root.findViewById(R.id.lesson_answer1);
        answerText2 = root.findViewById(R.id.lesson_answer2);
        answerText3 = root.findViewById(R.id.lesson_answer3);
        answerText4 = root.findViewById(R.id.lesson_answer4);
        // Update Views by Data
        // todo setUserStatus on ViewModel

        // Click listeners
        breakButton.setOnClickListener(this::onClickButton);
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