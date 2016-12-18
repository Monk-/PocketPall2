package com.example.aleksander.pocketpall2.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.aleksander.pocketpall2.Classes.Categories;
import com.example.aleksander.pocketpall2.Classes.ExIn;
import com.example.aleksander.pocketpall2.Database.Template;
import com.example.aleksander.pocketpall2.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.aleksander.pocketpall2.MainActivity.*;


public class Fragment2 extends Fragment {

    public static int period = 12;
    private static View view;
    private static PieChart mChart;
    private static RadarChart rChart;
    private static HorizontalBarChart bChart;
    private static Spinner categ;
    private static Spinner chartSpin;
    private static Spinner expIncSpin;
    public static int incOrExp = 0;
    public static Fragment2 fragment2;
    public static int whichChart = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment2 = this;

    }

    private void init()
    {
        mChart = (PieChart) view.findViewById(R.id.piechart);
        rChart = (RadarChart) view.findViewById(R.id.radarchart);
        bChart = (HorizontalBarChart) view.findViewById(R.id.barchart);
        categ = (Spinner)view.findViewById(R.id.periodSpinner);
        chartSpin = (Spinner)view.findViewById(R.id.chartSpinner);
        expIncSpin = (Spinner)view.findViewById(R.id.expIncSpinner);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment2, container, false);
        init();
        String[] items = new String[] {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "Decemver", "Year"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, items);
        categ.setAdapter(adapter);
        categ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                period = position;
                BarChart(period, incOrExp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        String[] items1 = new String[]{"PieChart", "RadarChart", "BarChart"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, items1);
        chartSpin.setAdapter(adapter1);
        chartSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        PieChart();
                        whichChart = 0;
                        rChart.setVisibility(View.GONE);
                        bChart.setVisibility(View.GONE);
                        break;
                    case 1:
                        RadarChart();
                        whichChart = 1;
                        mChart.setVisibility(View.GONE);
                        bChart.setVisibility(View.GONE);
                        break;
                    case 2:
                        barChartSettings();
                        whichChart = 2;
                        BarChart(period, incOrExp);
                        mChart.setVisibility(View.GONE);
                        rChart.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                PieChart();
            }

        });
        String[] items2 = new String[]{"Incomes", "Expenses"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, items2);
        expIncSpin.setAdapter(adapter2);
        expIncSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    incOrExp = position;
                    BarChart(period, incOrExp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                PieChart();
            }

        });
        return view;
    }

    public static void BarChart(int period, int incExp)
    {
        ArrayList<String> xVals = new ArrayList<>(Arrays.asList(Categories.getListStr()));

        ArrayList<BarEntry> yVals1 = barChartPer(period, incExp);

        BarDataSet set1 = new BarDataSet(yVals1, "");
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);

        bChart.setData(data);
        bChart.invalidate();
    }

    public static ArrayList<BarEntry> barChartPer(int period, int incExp)
    {
        Template exIn;
        if (incExp == 0) {
            exIn = incDB;
        }
        else
        {
            exIn = expDB;
        }
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        if (period >= 0 && period <= 11)
        {
            double g;
            for (int i=0;i< Categories.values().length;i++)
            {
                g = 0;
                for (ExIn inca :exIn.getAllComesForParticulatMonth(period, i))
                {
                    g += inca.getAmount();
                }
                yVals1.add(new BarEntry((float)g,i));
            }
        }
        else
        {
            double g;
            for (int i=0;i< Categories.values().length;i++)
            {
                g = 0;
                for (ExIn inca :exIn.getCategoryAll(i))
                {
                    g += inca.getAmount();
                }
                yVals1.add(new BarEntry((float)g,i));
            }
        }
        return yVals1;
    }

    public static void barChartSettings()
    {
        categ.setVisibility(View.VISIBLE);
        expIncSpin.setVisibility(View.VISIBLE);
        categ.setSelection(12);
        bChart.setVisibility(View.VISIBLE);
        bChart.setMaxVisibleValueCount(60);
        bChart.setPinchZoom(false);
        bChart.setDrawGridBackground(false);

        XAxis xAxis = bChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);
        YAxis leftAxis = bChart.getAxisLeft();

        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = bChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
    }

    public static void RadarChart()
    {
        categ.setVisibility(View.GONE);
        expIncSpin.setVisibility(View.GONE);
        rChart.setVisibility(View.VISIBLE);
        rChart.setWebLineWidth(1.5f);
        rChart.setWebLineWidthInner(0.75f);
        rChart.setWebAlpha(100);
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<Entry> entries2 = new ArrayList<>();
        double g, g1;
        for (int i=0;i< Categories.values().length;i++)
        {
            g = 0;
            for (ExIn inca :incDB.getCategoryAll(i))
            {
                g += inca.getAmount();
            }
            entries.add(new Entry((float)g,i));
            g1 = 0;
            for (ExIn inca :expDB.getCategoryAll(i))
            {
                g1 += inca.getAmount();
            }
            entries2.add(new Entry((float)g1,i));
        }

        RadarDataSet dataset_comp1 = new RadarDataSet(entries, "Income");

        RadarDataSet dataset_comp2 = new RadarDataSet(entries2, "Expense");

        dataset_comp1.setColor(Color.parseColor("#009688"));

        dataset_comp2.setColor(Color.parseColor("#F06292"));

        dataset_comp1.setDrawFilled(true);

        dataset_comp2.setDrawFilled(true);

        ArrayList<RadarDataSet> kom = new ArrayList<>();
        kom.add(dataset_comp1);
        kom.add(dataset_comp2);
        RadarData data = new RadarData(Categories.getListStr(), kom);
        rChart.setData(data);
        rChart.invalidate();
    }

    public static void PieChart()
    {
        categ.setVisibility(View.GONE);
        expIncSpin.setVisibility(View.GONE);
        mChart.setVisibility(View.VISIBLE);
        List<ExIn> inco = new ArrayList<>();
        for (int i=0;i< Categories.values().length;i++)
        {
            inco.addAll(incDB.getCategoryAll(i));
        }

        List<ExIn> expe = new ArrayList<>();
        for (int i=0;i< Categories.values().length;i++)
        {
            expe.addAll(expDB.getCategoryAll(i));
        }

        double j = 0.0;

        for (int i=0;i< inco.size();i++)
        {
            j += inco.get(i).getAmount();
        }


        double f = 0.0;

        for (int i=0;i< expe.size();i++)
        {
            f += expe.get(i).getAmount();
        }


        ArrayList<Entry> yVals1 = new ArrayList<>();
        yVals1.add(new Entry((float)j, 0));
        yVals1.add(new Entry((float)f, 1));
        PieDataSet dataSet = new PieDataSet(yVals1, "Overall statistic");
        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("Income");
        xVals.add("Expense");
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#009688"));
        colors.add(Color.parseColor("#F06292"));
        dataSet.setColors(colors);
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        mChart.setData(data);
        mChart.setUsePercentValues(true);
        mChart.invalidate();
    }
}
