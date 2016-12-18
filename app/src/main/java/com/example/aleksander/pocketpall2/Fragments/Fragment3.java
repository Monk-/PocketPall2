package com.example.aleksander.pocketpall2.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.aleksander.pocketpall2.Classes.Categories;
import com.example.aleksander.pocketpall2.Classes.ExIn;
import com.example.aleksander.pocketpall2.Classes.Expense;
import com.example.aleksander.pocketpall2.Classes.Income;
import com.example.aleksander.pocketpall2.MainActivity;
import com.example.aleksander.pocketpall2.R;
import com.example.aleksander.pocketpall2.StatisticList.ExpandableHeightListView;
import com.example.aleksander.pocketpall2.StatisticList.StatisticAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static com.example.aleksander.pocketpall2.MainActivity.*;
public class Fragment3 extends Fragment {

    private static View view;
    private static Fragment3 fragment3;
    public static Activity f3Act;
    private static List<ExpandableHeightListView>  listso;
    private static List <LinearLayout> linear;
    private  Spinner spinner;
    static TextView textView;
    static LinearLayout linearLayout;
    public static boolean instant = false;
    public static int allIncExp = 0; // 0 All 1 Inc 2 Exp

    void initListViews()
    {
        listso = new ArrayList<>();
        linear = new ArrayList<>();
        listso.add((ExpandableHeightListView) view.findViewById(R.id.Car));
        listso.add((ExpandableHeightListView)view.findViewById(R.id.Clothing));
        listso.add((ExpandableHeightListView)view.findViewById(R.id.Electronics));
        listso.add((ExpandableHeightListView)view.findViewById(R.id.Expenses));
        listso.add((ExpandableHeightListView)view.findViewById(R.id.Home));
        listso.add((ExpandableHeightListView)view.findViewById(R.id.Income));
        listso.add((ExpandableHeightListView)view.findViewById(R.id.Work));
        listso.add((ExpandableHeightListView)view.findViewById(R.id.Education));
        listso.add((ExpandableHeightListView) view.findViewById(R.id.Sports));
        for (ExpandableHeightListView lv: listso)
        {
            lv.setExpanded(true);
        }
        linear.add((LinearLayout) view.findViewById(R.id.CarLin));
        linear.add((LinearLayout)view.findViewById(R.id.ClothingLin));
        linear.add((LinearLayout)view.findViewById(R.id.ElectronicsLin));
        linear.add((LinearLayout)view.findViewById(R.id.ExpensesLin));
        linear.add((LinearLayout)view.findViewById(R.id.HomeLin));
        linear.add((LinearLayout)view.findViewById(R.id.IncomeLin));
        linear.add((LinearLayout)view.findViewById(R.id.WorkLin));
        linear.add((LinearLayout)view.findViewById(R.id.EducationLin));
        linear.add((LinearLayout) view.findViewById(R.id.SportsLin));

        textView = (TextView)view.findViewById(R.id.greenLineText);
        linearLayout = (LinearLayout)view.findViewById(R.id.greenLine);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        fragment3 = this;
        instant = true;
        f3Act = fragment3.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment3, container, false);
        init();
        setAdapter();
        initListViews();
        refresh3Frag();
        return view;
    }

    void init()
    {
        spinner = (Spinner)view.findViewById(R.id.incExpIncExp);
    }

    void setAdapter()
    {
        String[] items = new String[] {"All", "Income", "Expense"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                allIncExp = position;
                refresh3Frag();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
    }

    public static void refresh3Frag()
    {
        List<ExIn> exIns;
        String cat = "";
        List<Object> come;
        Double sumAll = 0.0;
        for (int i = 0;i < Categories.values().length; i++)
        {
            come = new ArrayList<>();
            cat = Categories.getStr(i);
            switch(Fragment3.allIncExp)
            {
                case 0:
                    exIns = incDB.getCategoryAll(i);
                    exIns.addAll(expDB.getCategoryAll(i));
                    break;
                case 1:
                    exIns = incDB.getCategoryAll(i);
                    break;
                case 2:
                    exIns = expDB.getCategoryAll(i);
                    break;
                default:
                    exIns = incDB.getCategoryAll(i);
                    exIns.addAll(expDB.getCategoryAll(i));
                    break;
            }

            Collections.sort(exIns, new LexicographicComparator());
            double sum = 0;
            for (ExIn exIn: exIns)
            {
                if (exIn instanceof Income)
                {
                    sum += exIn.getAmount();
                }
                else if (exIn instanceof Expense)
                {
                    sum -= exIn.getAmount();
                }
            }
            sumAll += sum;
            if (exIns.size() > 0)
            {
                come.add(cat);
                come.addAll(exIns);
                listso.get(i).setAdapter(new StatisticAdapter(fragment3, come, sum));
                linear.get(i).setVisibility(View.VISIBLE);
                linear.get(i).setPadding(10, 20, 20, 20);
            }
            else
            {
                linear.get(i).setVisibility(View.GONE);
                linear.get(i).setPadding(0,0,0,0);
            }

        }
        textView.setText("Balance: " + sumAll.toString() + MainActivity.currency);

        if (sumAll < 0)
        {
            linearLayout.setBackgroundColor(Color.parseColor("#E57373"));
        }
        else
        {
            linearLayout.setBackgroundColor(Color.parseColor("#61B329"));
        }
    }

    static class LexicographicComparator implements Comparator<ExIn> {

        @Override
        public int compare(ExIn lhs, ExIn rhs) {
            if (lhs.getAmount() > rhs.getAmount())
            {
                return -1;
            }
            else if(lhs.getAmount() < rhs.getAmount())
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }

}
