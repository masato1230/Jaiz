package com.jp_funda.jaiz.Recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jp_funda.jaiz.R;
import com.jp_funda.jaiz.models.LessonResultRowData;

import java.util.List;


public class LessonResultViewAdapter extends RecyclerView.Adapter<LessonResultViewHolder> {
    private List<LessonResultRowData> lessonResultsList;

    public LessonResultViewAdapter(List<LessonResultRowData> lessonResultsList) {
        this.lessonResultsList = lessonResultsList;
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
    }

    @Override
    public int getItemCount() {
        return lessonResultsList.size();
    }
}
