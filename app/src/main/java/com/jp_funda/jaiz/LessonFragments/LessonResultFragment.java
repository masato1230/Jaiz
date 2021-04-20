package com.jp_funda.jaiz.LessonFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jp_funda.jaiz.Database.UserDatabaseHandler;
import com.jp_funda.jaiz.LessonFragments.home.LessonHomeFragment;
import com.jp_funda.jaiz.LessonFragments.lesson.LessonFragment;
import com.jp_funda.jaiz.R;
import com.jp_funda.jaiz.Recyclers.LessonResultViewAdapter;
import com.jp_funda.jaiz.ViewModles.LessonViewModel;
import com.jp_funda.jaiz.models.Lesson;
import com.jp_funda.jaiz.models.LessonResultRowData;

import java.util.ArrayList;
import java.util.List;


public class LessonResultFragment extends Fragment {
    private UserDatabaseHandler userDB;
    // View properties
    private ImageView headerHomeButton;
    private TextView commentText;
    private TextView studiedWordsNumberText;
    private TextView correctRateText;
    private RecyclerView recyclerView;
    private TextView footerHomeButton;

    // ViewModel
    private LessonViewModel lessonViewModel;

    // Other
    FragmentTransaction transaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_lesson_result, container, false);

        // initialize userDB
        userDB = new UserDatabaseHandler(getActivity());

        // initialize view model
        lessonViewModel = new ViewModelProvider(getActivity()).get(LessonViewModel.class);

        // initialize views
        headerHomeButton = root.findViewById(R.id.lesson_result_header_home_button);
        commentText = root.findViewById(R.id.lesson_result_comment);
        studiedWordsNumberText = root.findViewById(R.id.lesson_result_studied_words_number);
        correctRateText = root.findViewById(R.id.lesson_result_correct_rate);
        footerHomeButton = root.findViewById(R.id.lesson_result_footer_home_button);

        // RecyclerView
        recyclerView = root.findViewById(R.id.lesson_result_recycler_view);
        LessonResultViewAdapter adapter = new LessonResultViewAdapter(this.createRecyclerViewDataSet(), getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Click listeners
        headerHomeButton.setOnClickListener(this::onHomeButtonClick);
        footerHomeButton.setOnClickListener(this::onHomeButtonClick);

        // Reflect the data to Views
        int correctlyAnsweredNumber = 0;
        int incorrectlyAnsweredNumber = 0;
        for (String word: lessonViewModel.currentStatus.getCorrectlyAnsweredWords()) {
            if (word != null) {
                correctlyAnsweredNumber++;
            }
        }
        for (String word: lessonViewModel.currentStatus.getIncorrectlyAnsweredWords()) {
            if (word != null) {
                incorrectlyAnsweredNumber++;
            }
        }
        int answeredNumber = correctlyAnsweredNumber + incorrectlyAnsweredNumber;
        studiedWordsNumberText.setText(
                String.valueOf(correctlyAnsweredNumber)
        );
        correctRateText.setText(correctlyAnsweredNumber+"/"+answeredNumber);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 1. update unlearnedWords
        // 2. update learnedWords
        // 3. update notGoodWords
        List<String> currentlyLearnedWords = lessonViewModel.currentStatus.getCorrectlyAnsweredWords();
        currentlyLearnedWords.addAll(lessonViewModel.currentStatus.getIncorrectlyAnsweredWords());
        // 1.
        ArrayList<String> updatedUnlearnedWords = new ArrayList<>(lessonViewModel.lessonStatus.getUnlearnedWords());
        updatedUnlearnedWords.removeAll(currentlyLearnedWords);
        lessonViewModel.lessonStatus.setUnlearnedWords(updatedUnlearnedWords);
        // 2.
        ArrayList<String> updatedLearnedWords = new ArrayList<>(lessonViewModel.lessonStatus.getLearnedWords());
        updatedLearnedWords.addAll(lessonViewModel.currentStatus.getCorrectlyAnsweredWords());
        lessonViewModel.lessonStatus.setLearnedWords(updatedLearnedWords);
        // 3.
        ArrayList<String> updatedNotGoodWords = new ArrayList<>(lessonViewModel.lessonStatus.getNotGoodWords());
        updatedNotGoodWords.addAll(lessonViewModel.currentStatus.getIncorrectlyAnsweredWords());
        lessonViewModel.lessonStatus.setNotGoodWords(updatedNotGoodWords);

        // update db with updated lessonStatus
        userDB.addOrUpdateLessonStatus(lessonViewModel.lessonStatus);
        // clean currentStatus
        lessonViewModel.currentStatus.cleanCurrentStatus();
    }

    private List<LessonResultRowData> createRecyclerViewDataSet() {
        List<LessonResultRowData> dataSet = new ArrayList<>();
        List<String> studiedWords = lessonViewModel.lessonStatus.getUnlearnedWords();
        List<String> correctlyAnsweredWords = lessonViewModel.currentStatus.getCorrectlyAnsweredWords();
        List<String> incorrectlyAnsweredWords = lessonViewModel.currentStatus.getIncorrectlyAnsweredWords();

        for (String studiedWord: studiedWords) {
            LessonResultRowData rowData = new LessonResultRowData();
            rowData.setWordJP(studiedWord);
            // 英語の取得
            int indexOfWord = lessonViewModel.lesson.getWordsJP().indexOf(studiedWord);
            rowData.setWordEN(lessonViewModel.lesson.getWords().get(indexOfWord));
            if (correctlyAnsweredWords.contains(studiedWord)) {
                rowData.setGood(true);
            } else {
                rowData.setGood(false);
            }
            dataSet.add(rowData);
        }
        return dataSet;
    }

    private void onHomeButtonClick(View view) {
        transaction = getParentFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.slide_out);
        transaction.add(R.id.lesson_fragment_container, new LessonHomeFragment());
        transaction.remove(this);
        transaction.commit();
    }
}