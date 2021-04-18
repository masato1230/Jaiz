package com.jp_funda.jaiz.LessonFragments.lesson;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jp_funda.jaiz.LessonFragments.LessonResultFragment;
import com.jp_funda.jaiz.LessonFragments.home.LessonHomeFragment;
import com.jp_funda.jaiz.R;
import com.jp_funda.jaiz.ViewModles.LessonViewModel;
import com.jp_funda.jaiz.models.UserLessonStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class LessonFragment extends Fragment {
    private LessonViewModel lessonViewModel;
    // View properties
    private ImageView breakButton;
    private ProgressBar progressBar;
    private ImageView resultImage;
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_lesson, container, false);

        // Get parent activity's LessonViewModel and lesson data
        lessonViewModel = new ViewModelProvider(getActivity())
                .get(LessonViewModel.class);

        // Initialize Views
        breakButton = root.findViewById(R.id.lesson_break_button);
        progressBar = root.findViewById(R.id.lesson_progressbar);
        resultImage = root.findViewById(R.id.lesson_result_image);
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
        // answerTexts
        updateViewsAndCurrentStatus(null, null);

        // Click listeners
        breakButton.setOnClickListener(this::onBreakButtonClick);
        return root;
    }

    private void onBreakButtonClick(View view) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.slide_out);
        transaction.remove(this);
        transaction.add(R.id.lesson_fragment_container, new LessonHomeFragment());
        transaction.commit();
    }

    public void updateViewsAndCurrentStatus(
            @Nullable String correctlyAnsweredWord,
            @Nullable String incorrectlyAnsweredWord
    ) {
        // if lesson finish transfer user to result fragment
        if (lessonViewModel.currentStatus.getProblemIndex()+1 > lessonViewModel.lessonStatus.getUnlearnedWords().size()) {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in, R.anim.fade_out);
            // transfer user to lessonResultFragment
            transaction.add(R.id.lesson_fragment_container, new LessonResultFragment());
            transaction.remove(this);
            transaction.commit();
            return;
        }

        // update correctlyAnsweredWords
        ArrayList<String> newCorrectlyAnsweredWords = new ArrayList<>();
        if (lessonViewModel.currentStatus.getCorrectlyAnsweredWords() != null) {
            newCorrectlyAnsweredWords.addAll(lessonViewModel.currentStatus.getCorrectlyAnsweredWords());
        }
        newCorrectlyAnsweredWords.add(correctlyAnsweredWord);
        lessonViewModel.currentStatus.setCorrectlyAnsweredWords(newCorrectlyAnsweredWords);
        // update incorrectlyAnsweredWords
        ArrayList<String> newIncorrectlyAnsweredWords = new ArrayList<>();
        if (lessonViewModel.currentStatus.getIncorrectlyAnsweredWords() != null) {
            newIncorrectlyAnsweredWords.addAll(lessonViewModel.currentStatus.getIncorrectlyAnsweredWords());
        }
        newIncorrectlyAnsweredWords.add(incorrectlyAnsweredWord);
        lessonViewModel.currentStatus.setIncorrectlyAnsweredWords(newIncorrectlyAnsweredWords);

        // progress
        progressBar.setMax(lessonViewModel.lesson.getWords().size());
        progressBar.setProgress(lessonViewModel.currentStatus.getProblemIndex()+1);
        // progressBar.setMin(0);
        // todo commentText
        // progressText
        progressText.setText((lessonViewModel.currentStatus
                .getProblemIndex() + 1) +"/"+lessonViewModel.lesson.getWords().size());
        // problemWord
        problemWord.setText(lessonViewModel.lessonStatus.getUnlearnedWords().
                get(lessonViewModel.currentStatus.getProblemIndex()));
        // 1. update ViewModel
        // 2. update views
        updateCurrentStatus();
        answerText1.setText(lessonViewModel.currentStatus.getAnswer1());
        answerText2.setText(lessonViewModel.currentStatus.getAnswer2());
        answerText3.setText(lessonViewModel.currentStatus.getAnswer3());
        answerText4.setText(lessonViewModel.currentStatus.getAnswer4());
        // setClickListeners
        answerButton1.setOnClickListener(this::onIncorrectAnswerClick);
        answerButton2.setOnClickListener(this::onIncorrectAnswerClick);
        answerButton3.setOnClickListener(this::onIncorrectAnswerClick);
        answerButton4.setOnClickListener(this::onIncorrectAnswerClick);
        switch (lessonViewModel.currentStatus.getAnswerInt()) {
            case 1:
                answerButton1.setOnClickListener(this::onCorrectAnswerClick);
                break;
            case 2:
                answerButton2.setOnClickListener(this::onCorrectAnswerClick);
                break;
            case 3:
                answerButton3.setOnClickListener(this::onCorrectAnswerClick);
                break;
            case 4:
                answerButton4.setOnClickListener(this::onCorrectAnswerClick);
                break;
        }
    }

    private void onCorrectAnswerClick(View view) {
        // updateCurrentStatus
        lessonViewModel.currentStatus.setProblemIndex(
                lessonViewModel.currentStatus.getProblemIndex()+1
        );
        // update view and currentStatus
        updateViewsAndCurrentStatus(
                lessonViewModel.lessonStatus.getUnlearnedWords().get(lessonViewModel.currentStatus.getProblemIndex()-1),
                null
        );
        // Show Correct Image and set fade out animation
        resultImage.setImageResource(R.drawable.correct);
        resultImage.setAlpha(1f);

        resultImage.animate()
                .alpha(0f)
                .setDuration(1000)
                .setListener(null);
    }

    private void onIncorrectAnswerClick(View view) {
        // updateUserLessonStatus
        lessonViewModel.currentStatus.setProblemIndex(
                lessonViewModel.currentStatus.getProblemIndex()+1
        );
        // Show Incorrect Image and set fade out animation
        resultImage.setImageResource(R.drawable.incorrect);
        resultImage.setAlpha(1f);

        resultImage.animate()
                .alpha(0f)
                .setDuration(1000)
                .setListener(null);
        // update view and currentStatus
        updateViewsAndCurrentStatus(
                null,
                lessonViewModel.lessonStatus.getUnlearnedWords().get(lessonViewModel.currentStatus.getProblemIndex()-1)
        );
    }


    public int updateCurrentStatus() {
        // 1~4の内から１つの整数をランダムに生成し、answerIntに代入
        Random rand = new Random();
        int answerInt = rand.nextInt(4) + 1;
        Log.d("answerInt", String.valueOf(answerInt));
        // CurrentStatusに正解の選択肢番号を登録
        lessonViewModel.currentStatus.setAnswerInt(answerInt);
        switch (answerInt) {
            case 1:
                String answer1StringJP = lessonViewModel.lessonStatus.getUnlearnedWords()
                        .get(lessonViewModel.currentStatus.getProblemIndex());
                String answer1StringEN = lessonViewModel
                        .lesson.getWords()
                        .get(lessonViewModel.lesson.getWordsJP().indexOf(answer1StringJP));
                String[] incorrectAnswers = generateInCorrectAnswerStrings(answer1StringEN);
                // CurrentStatusに選択肢を登録
                lessonViewModel.currentStatus.setAnswer1(answer1StringEN);
                lessonViewModel.currentStatus.setAnswer2(incorrectAnswers[0]);
                lessonViewModel.currentStatus.setAnswer3(incorrectAnswers[1]);
                lessonViewModel.currentStatus.setAnswer4(incorrectAnswers[2]);
                break;
            case 2:
                String answer2StringJP = lessonViewModel.lessonStatus.getUnlearnedWords()
                        .get(lessonViewModel.currentStatus.getProblemIndex());
                String answer2StringEN = lessonViewModel
                        .lesson.getWords()
                        .get(lessonViewModel.lesson.getWordsJP().indexOf(answer2StringJP));
                incorrectAnswers = generateInCorrectAnswerStrings(answer2StringEN);
                // CurrentStatusに選択肢を登録
                lessonViewModel.currentStatus.setAnswer2(answer2StringEN);
                lessonViewModel.currentStatus.setAnswer1(incorrectAnswers[0]);
                lessonViewModel.currentStatus.setAnswer3(incorrectAnswers[1]);
                lessonViewModel.currentStatus.setAnswer4(incorrectAnswers[2]);
                break;
            case 3:
                String answer3StringJP = lessonViewModel.lessonStatus.getUnlearnedWords()
                        .get(lessonViewModel.currentStatus.getProblemIndex());
                String answer3StringEN = lessonViewModel
                        .lesson.getWords()
                        .get(lessonViewModel.lesson.getWordsJP().indexOf(answer3StringJP));
                incorrectAnswers = generateInCorrectAnswerStrings(answer3StringEN);
                // CurrentStatusに選択肢を登録
                lessonViewModel.currentStatus.setAnswer3(answer3StringEN);
                lessonViewModel.currentStatus.setAnswer1(incorrectAnswers[0]);
                lessonViewModel.currentStatus.setAnswer2(incorrectAnswers[1]);
                lessonViewModel.currentStatus.setAnswer4(incorrectAnswers[2]);
                break;
            case 4:
                String answer4StringJP = lessonViewModel.lessonStatus.getUnlearnedWords()
                        .get(lessonViewModel.currentStatus.getProblemIndex());
                String answer4StringEN = lessonViewModel
                        .lesson.getWords()
                        .get(lessonViewModel.lesson.getWordsJP().indexOf(answer4StringJP));
                incorrectAnswers = generateInCorrectAnswerStrings(answer4StringEN);
                // CurrentStatusに選択肢を登録
                lessonViewModel.currentStatus.setAnswer4(answer4StringEN);
                lessonViewModel.currentStatus.setAnswer1(incorrectAnswers[0]);
                lessonViewModel.currentStatus.setAnswer2(incorrectAnswers[1]);
                lessonViewModel.currentStatus.setAnswer3(incorrectAnswers[2]);
                break;
        }
        return answerInt;
    }

    public String[] generateInCorrectAnswerStrings(String correctAnswer) {
        Random rand = new Random();
        int lessonSize = lessonViewModel.lesson.getWords().size();
        String incorrectAnswer1 = lessonViewModel.lesson.getWords()
                .get(rand.nextInt(lessonSize));
        String incorrectAnswer2 = lessonViewModel.lesson.getWords()
                .get(rand.nextInt(lessonSize));
        String incorrectAnswer3 = lessonViewModel.lesson.getWords()
                .get(rand.nextInt(lessonSize));
        // 集合を用いて、選択肢に重複がないかをチェック
        Set<String> answersSet = new HashSet<>();
        Collections.addAll(
                answersSet, correctAnswer, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3
        );
        while (answersSet.size() < 4) {
            incorrectAnswer1 = lessonViewModel.lesson.getWords()
                    .get(rand.nextInt(lessonSize));
            incorrectAnswer2 = lessonViewModel.lesson.getWords()
                    .get(rand.nextInt(lessonSize));
            incorrectAnswer3 = lessonViewModel.lesson.getWords()
                    .get(rand.nextInt(lessonSize));
            answersSet.clear();
            Collections.addAll(
                    answersSet, correctAnswer, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3
            );
        }
        return new  String[] {incorrectAnswer1, incorrectAnswer2, incorrectAnswer3};
    }
}