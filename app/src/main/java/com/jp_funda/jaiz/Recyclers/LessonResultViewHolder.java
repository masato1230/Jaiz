package com.jp_funda.jaiz.Recyclers;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jp_funda.jaiz.R;


public class LessonResultViewHolder extends RecyclerView.ViewHolder {
    public TextView wordJPTextView;
    public TextView wordENTextView;
    public CheckBox goodCheckBox;

    public LessonResultViewHolder(@NonNull View itemView) {
        super(itemView);
        wordJPTextView = itemView.findViewById(R.id.lesson_result_row_japanese);
        wordENTextView = itemView.findViewById(R.id.lesson_result_row_english);
        goodCheckBox = itemView.findViewById(R.id.lesson_result_row_good_check_box);
    }
}
