package com.jp_funda.jaiz.MainFragments.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.PieChart;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jp_funda.jaiz.Activities.LessonActivity;
import com.jp_funda.jaiz.R;
import com.jp_funda.jaiz.models.Lesson;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View root;
    private PieChart pieChart;

    // Views
    private TextView studyButton;

    // Firebase Test
    private TextView homeTitleOne;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.home_title_one);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        // initialize Views
        studyButton = root.findViewById(R.id.home_study_button);

        // firebase Test
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Data");
        databaseReference = database.getReference("Lessons").child("lesson1");
        homeTitleOne = root.findViewById(R.id.home_title_one);

        // ?????????????????????
        createPieChart();

        // click listeners
        studyButton.setOnClickListener(this::onStudyClick);

        return root;
    }

    private void createPieChart() {
        pieChart = (PieChart) root.findViewById(R.id.home_pie_chart);

        pieChart.setDrawHoleEnabled(true); // ???????????????????????????????????????
        pieChart.setHoleRadius(50f);       // ???????????????????????????(%??????)
        pieChart.setTransparentCircleRadius(55f);
        pieChart.setRotationAngle(270);          // ?????????????????????
        pieChart.setRotationEnabled(true);       // ????????????????????????
        pieChart.getLegend().setEnabled(true);
        pieChart.setData(createPieChartData());

        // ??????
        pieChart.invalidate();
        // ?????????????????????
        pieChart.animateXY(2000, 2000); // ???????????????????????????
    }

    private PieData createPieChartData() {
        ArrayList<Entry> yVals = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        xVals.add("A");
        xVals.add("B");
        xVals.add("C");

        yVals.add(new Entry(20, 0));
        yVals.add(new Entry(30, 1));
        yVals.add(new Entry(50, 2));

        PieDataSet dataSet = new PieDataSet(yVals, "Data");
        dataSet.setSliceSpace(5f);
        dataSet.setSelectionShift(1f);

        // ????????????
        colors.add(ColorTemplate.COLORFUL_COLORS[0]);
        colors.add(ColorTemplate.COLORFUL_COLORS[1]);
        colors.add(ColorTemplate.COLORFUL_COLORS[2]);
        dataSet.setColors(colors);
        dataSet.setDrawValues(true);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());

        // ?????????????????????
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);
        return data;
    }

    private void onStudyClick(View view) {
        // Todo start LessonActivity
        Intent intent = new Intent(getActivity(), LessonActivity.class);
        intent.putExtra("lessonNumber", 1);
        startActivity(intent);
    }
}