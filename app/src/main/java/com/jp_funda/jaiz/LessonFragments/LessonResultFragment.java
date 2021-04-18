package com.jp_funda.jaiz.LessonFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jp_funda.jaiz.R;
import com.jp_funda.jaiz.Recyclers.LessonResultViewAdapter;
import com.jp_funda.jaiz.ViewModles.LessonViewModel;
import com.jp_funda.jaiz.models.Lesson;
import com.jp_funda.jaiz.models.LessonResultRowData;

import java.util.ArrayList;
import java.util.List;


public class LessonResultFragment extends Fragment {
    // View properties
    private TextView commentText;
    private TextView studiedWordsNumberText;
    private TextView correctRateText;
    private RecyclerView recyclerView;

    // ViewModel
    private LessonViewModel lessonViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_lesson_result, container, false);

        // initialize view model
        lessonViewModel = new ViewModelProvider(getActivity()).get(LessonViewModel.class);

        // RecyclerView
        recyclerView = root.findViewById(R.id.lesson_result_recycler_view);
        LessonResultViewAdapter adapter = new LessonResultViewAdapter(this.createRecyclerViewDataSet());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return root;
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
}