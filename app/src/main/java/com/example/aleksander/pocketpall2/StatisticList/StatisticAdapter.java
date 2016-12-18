package com.example.aleksander.pocketpall2.StatisticList;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.aleksander.pocketpall2.Classes.ExIn;
import com.example.aleksander.pocketpall2.Classes.Expense;
import com.example.aleksander.pocketpall2.Classes.Income;
import com.example.aleksander.pocketpall2.Fragments.Fragment3;
import com.example.aleksander.pocketpall2.MainActivity;
import com.example.aleksander.pocketpall2.R;

import java.util.List;

public class StatisticAdapter implements ListAdapter {

    private List<Object> statisticArray;
    Fragment3 fragment3;
    private LayoutInflater inflater;
    private static final int TYPE_STATISTICS_INC = 1;
    private static final int TYPE_STATISTICS_EXP = 2;
    private static final int TYPE_HEADER = 0;
    private static Typeface faceBold;
    private static Typeface faceRegular;
    Double sum;


    public StatisticAdapter(Fragment3 fragment3, List<Object> people, double sum) {
        this.fragment3 = fragment3;
        this.statisticArray = people;
        this.inflater = (LayoutInflater)Fragment3.f3Act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        faceBold = Typeface.createFromAsset(Fragment3.f3Act.getAssets(),
                "fonts/OpenSans-Bold.ttf");
        faceRegular = Typeface.createFromAsset(Fragment3.f3Act.getAssets(),
                "fonts/OpenSans-Regular.ttf");
        this.sum = sum;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return (getItemViewType(position) == TYPE_STATISTICS_INC || getItemViewType(position) == TYPE_STATISTICS_EXP);
    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return statisticArray.size();
    }

    @Override
    public Object getItem(int position) {
        return statisticArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case TYPE_STATISTICS_INC:
                    convertView = inflater.inflate(R.layout.statistic_layout_item, parent, false);
                    break;
                case TYPE_STATISTICS_EXP:
                    convertView = inflater.inflate(R.layout.statistic_layout_item, parent, false);
                    break;
                case TYPE_HEADER:
                    convertView = inflater.inflate(R.layout.statistic_layour_header, parent, false);
                    break;
            }
        }

        switch (type) {
            case TYPE_STATISTICS_EXP:
                TextView name3 = (TextView)convertView.findViewById(R.id.name);
                name3.setTypeface(faceRegular);
                ExIn exIn1 = (ExIn)getItem(position);
                name3.setText(exIn1.getTitle());
                TextView amount3 = (TextView)convertView.findViewById(R.id.amount);
                amount3.setTypeface(faceRegular);
                amount3.setText(exIn1.getAmount().toString() + MainActivity.currency);
                amount3.setTextColor(Color.parseColor("#F44336"));
                break;
            case TYPE_STATISTICS_INC:
                TextView name1 = (TextView)convertView.findViewById(R.id.name);
                name1.setTypeface(faceRegular);
                ExIn exIn = (ExIn)getItem(position);
                name1.setText(exIn.getTitle());
                TextView amount2 = (TextView)convertView.findViewById(R.id.amount);
                amount2.setTypeface(faceRegular);
                amount2.setText(exIn.getAmount().toString() + MainActivity.currency);
                amount2.setTextColor(Color.parseColor("#43A047"));
                break;
            case TYPE_HEADER:
                TextView name = (TextView)convertView.findViewById(R.id.name);
                name.setTypeface(faceBold);
                String titleString = (String)getItem(position);
                name.setText(titleString);
                TextView amount1 = (TextView)convertView.findViewById(R.id.amount);
                amount1.setTypeface(faceRegular);
                amount1.setText(sum.toString());
                if (sum >= 0)
                {
                    amount1.setTextColor(Color.parseColor("#43A047"));
                }
                else
                {
                    amount1.setTextColor(Color.parseColor("#F44336"));
                }
                break;
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Expense) {
            return TYPE_STATISTICS_EXP;
        }
        else if (getItem(position) instanceof Income)
        {
            return TYPE_STATISTICS_INC;
        }

        return TYPE_HEADER;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
