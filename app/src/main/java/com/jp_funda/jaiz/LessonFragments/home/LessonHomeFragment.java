package com.jp_funda.jaiz.LessonFragments.home;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jp_funda.jaiz.Database.LessonDatabaseHandler;
import com.jp_funda.jaiz.LessonFragments.lesson.LessonFragment;
import com.jp_funda.jaiz.R;
import com.jp_funda.jaiz.ViewModles.LessonViewModel;

import java.util.ArrayList;

public class LessonHomeFragment extends Fragment {

    private FragmentTransaction transaction;

    // db
    LessonDatabaseHandler lessonDB;

    // view properties
    private View root;
    private PieChart pieChart;
    private ImageView listButton;
    private TextView notGoodButton;
    private TextView studyButton;
    private TextView fullReviewButton;

    // Constructor
    public static LessonHomeFragment newInstance() {
        return new LessonHomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_lesson_home, container, false);

        // Get parent activity's LessonViewModel
        LessonViewModel lessonViewModel = new ViewModelProvider(getActivity())
                .get(LessonViewModel.class);

        // initialize views
        listButton = root.findViewById(R.id.lesson_home_list_button);
        notGoodButton = root.findViewById(R.id.lesson_home_not_good_button);
        studyButton = root.findViewById(R.id.lesson_home_study_button);
        fullReviewButton = root.findViewById(R.id.lesson_home_full_review_button);

        // Click listeners
        studyButton.setOnClickListener(this::onStudyClick);

        // pie chart
        createPieChart();

        return root;
    }

    private void onStudyClick(View view) {
        transaction = getParentFragmentManager().beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out);
        transaction.add(R.id.lesson_fragment_container, new LessonFragment());
        transaction.remove(this);
        transaction.commit();
    }


    // todo add: notGoodButton listener
    // todo add: fullReviewButton listener
    // todo add: listButton listener
    // todo add: pieChart data


    // Pie chart
    private void createPieChart() {
        pieChart = (PieChart) root.findViewById(R.id.lesson_home_pie_chart);

        pieChart.setDrawHoleEnabled(true); // ???????????????????????????????????????
        pieChart.setHoleRadius(70f);       // ???????????????????????????(%??????)
        pieChart.setTransparentCircleRadius(55f);
        pieChart.setRotationAngle(270);          // ?????????????????????
        pieChart.setRotationEnabled(true);   // ????????????????????????
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

}