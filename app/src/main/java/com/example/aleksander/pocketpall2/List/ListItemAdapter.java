package com.example.aleksander.pocketpall2.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.aleksander.pocketpall2.Classes.Categories;
import com.example.aleksander.pocketpall2.Classes.ExIn;
import com.example.aleksander.pocketpall2.Classes.Expense;
import com.example.aleksander.pocketpall2.Classes.Income;
import com.example.aleksander.pocketpall2.Fragments.Fragment1;
import com.example.aleksander.pocketpall2.MainActivity;
import com.example.aleksander.pocketpall2.R;

import java.util.List;
import static  com.example.aleksander.pocketpall2.Fragments.Fragment1.*;

public class ListItemAdapter implements ListAdapter {
    private List<Object> personArray;
    private LayoutInflater inflater;
    private static final int TYPE_INCOME = 0;
    private static final int TYPE_EXPENSE = 1;
    private static final int TYPE_DIVIDER = 2;
    private static Fragment1 f;

    public ListItemAdapter(Fragment1 fragment1, List<Object> people) {
        f = fragment1;
        this.personArray = people;
        this.inflater = (LayoutInflater) f1Act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return (getItemViewType(position) == TYPE_INCOME || getItemViewType(position) == TYPE_EXPENSE);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return personArray.size();
    }

    @Override
    public Object getItem(int position) {
        return personArray.get(position);
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
                case TYPE_INCOME:
                    convertView = inflater.inflate(R.layout.row_item, parent, false);
                    break;
                case TYPE_EXPENSE:
                    convertView = inflater.inflate(R.layout.row_item, parent, false);
                    break;
                case TYPE_DIVIDER:
                    convertView = inflater.inflate(R.layout.row_header, parent, false);
                    break;
            }
        }

        switch (type) {
            case TYPE_INCOME:
                ExIn income = (ExIn)getItem(position);
                TextView title = (TextView)convertView.findViewById(R.id.InTitleLabel);
                title.setText(income.getTitle());
                TextView amount = (TextView)convertView.findViewById(R.id.InAmountLabel);
                amount.setText(income.getAmount().toString() + MainActivity.currency);
                TextView date = (TextView)convertView.findViewById(R.id.Indate);
                String [] s = income.getDate().split(":");
                date.setText(s[0] + " " +getMonth(Integer.parseInt(s[1])) + " "+ s[2]);
                ImageView category = (ImageView)convertView.findViewById(R.id.IncategoryImage);
                category.setImageResource(f.getActivity().getResources().getIdentifier(Categories.getStr(income.getCategory()).toLowerCase(), "drawable", f.getActivity().getPackageName()));
                ImageView greenbar = (ImageView)convertView.findViewById(R.id.bar);
                greenbar.setImageResource(f.getActivity().getResources().getIdentifier("greenbar", "drawable", f.getActivity().getPackageName()));
                break;
            case TYPE_EXPENSE:
                ExIn expence = (ExIn)getItem(position);
                TextView title1 = (TextView)convertView.findViewById(R.id.InTitleLabel);
                title1.setText(expence.getTitle());
                TextView amount1 = (TextView)convertView.findViewById(R.id.InAmountLabel);
                amount1.setText(expence.getAmount().toString() + " zł");
                TextView date1 = (TextView)convertView.findViewById(R.id.Indate);
                String [] s1 = expence.getDate().split(":");
                date1.setText(s1[0] + " " +getMonth(Integer.parseInt(s1[1])) + " "+ s1[2]);
                ImageView category1 = (ImageView)convertView.findViewById(R.id.IncategoryImage);
                category1.setImageResource(f.getActivity().getResources().getIdentifier(Categories.getStr(expence.getCategory()).toLowerCase(), "drawable", f.getActivity().getPackageName()));
                ImageView redbar = (ImageView)convertView.findViewById(R.id.bar);
                redbar.setImageResource(f.getActivity().getResources().getIdentifier("redbar", "drawable", f.getActivity().getPackageName()));
                break;
            case TYPE_DIVIDER:
                TextView month = (TextView)convertView.findViewById(R.id.month);
                String titleString = (String)getItem(position);
                month.setText(titleString);
                break;
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Income) {
            return TYPE_INCOME;
        }
        else if (getItem(position) instanceof Expense)
        {
            return TYPE_EXPENSE;
        }

        return TYPE_DIVIDER;
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
