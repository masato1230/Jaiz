package com.jp_funda.jaiz.Recyclers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.jp_funda.jaiz.R;
import com.jp_funda.jaiz.ViewModles.LessonViewModel;
import com.jp_funda.jaiz.models.Lesson;
import com.jp_funda.jaiz.models.LessonResultRowData;

import java.util.ArrayList;
import java.util.List;


public class LessonResultViewAdapter extends RecyclerView.Adapter<LessonResultViewHolder> {
    private List<LessonResultRowData> lessonResultsList;
    private LessonViewModel lessonViewModel;
    private Context viewModelOwner;

    public LessonResultViewAdapter(List<LessonResultRowData> lessonResultsList, Context viewModelOwner) {
        this.lessonResultsList = lessonResultsList;
        this.viewModelOwner = viewModelOwner;
        this.lessonViewModel = new ViewModelProvider((ViewModelStoreOwner) viewModelOwner).get(LessonViewModel.class);
    }

    @NonNull
    @Override
    public LessonResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_lesson_result, parent, false);
        LessonResultViewHolder viewHolder = new LessonResultViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LessonResultViewHolder holder, int position) {
        holder.wordJPTextView.setText(lessonResultsList.get(position).getWordJP());
        holder.wordENTextView.setText(lessonResultsList.get(position).getWordEN());
        holder.goodCheckBox.setChecked(lessonResultsList.get(position).isGood());
        // todo check change listener
        holder.goodCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    ArrayList<String> updatedIncorrectlyAnsweredWords = lessonViewModel.currentStatus.getIncorrectlyAnsweredWords();
                    ArrayList<String> updatedCorrectlyAnsweredWords = lessonViewModel.currentStatus.getCorrectlyAnsweredWords();
                    if (isChecked) {
                        // 1. remove from Incorrect
                        // 2. add to learned correct
                        updatedIncorrectlyAnsweredWords.remove(lessonResultsList.get(position).getWordJP());
                        updatedCorrectlyAnsweredWords.add(lessonResultsList.get(position).getWordJP());
                    }
                    if (!isChecked) {
                        // 1. add to Incorrect
                        // 2. remove from correct
                        updatedIncorrectlyAnsweredWords.add(lessonResultsList.get(position).getWordJP());
                        updatedCorrectlyAnsweredWords.remove(lessonResultsList.get(position).getWordJP());
                    }
                    lessonViewModel.currentStatus.setIncorrectlyAnsweredWords(updatedIncorrectlyAnsweredWords);
                    lessonViewModel.currentStatus.setCorrectlyAnsweredWords(updatedCorrectlyAnsweredWords);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lessonResultsList.size();
    }
}
