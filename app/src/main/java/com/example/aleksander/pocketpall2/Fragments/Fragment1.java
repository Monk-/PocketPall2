package com.example.aleksander.pocketpall2.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.aleksander.pocketpall2.Classes.ExIn;
import com.example.aleksander.pocketpall2.List.ListItemAdapter;
import com.example.aleksander.pocketpall2.R;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.aleksander.pocketpall2.MainActivity.*;

public class Fragment1 extends Fragment {
    public static View view;
    public static ExIn exIn;
    public static ListView listView;
    public static Fragment1 fragment1;
    public static Activity f1Act;
    public static ListItemAdapter listItemAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment1 = this;
        f1Act = fragment1.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment1, container, false);
        listView = (ListView)view.findViewById(R.id.listViewMainFrag);
        refreshList(listView, this);
        return view;

    }

    public static List<Object> getList()
    {
        final List<ExIn> comes = incDB.getAllComes();
        comes.addAll(expDB.getAllComes());
        Collections.sort(comes, new LexicographicComparator());
        final List<Object> come = new ArrayList<>();
        int curMonth = 0;
        int month;
        if (comes.size() > 0) {
            if (Integer.parseInt((comes.get(0)).getMonth()) == 0) {
                come.add(getMonth(curMonth));
            }
            for (int i = 0; i < comes.size(); i++) {
                month = Integer.parseInt((comes.get(i)).getMonth());
                if (month > curMonth) {
                    curMonth = month;
                    come.add(getMonth(curMonth));
                }
                come.add(comes.get(i));

            }
        }
        return come;
    }

    public static void refreshList(ListView listView, Fragment1 fragment1)
    {
        final List<Object> come = getList();
        //if (come.size() > 0) {
            listItemAdapter = new ListItemAdapter(fragment1, come);
            listView.setAdapter(listItemAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    exIn = (ExIn)come.get(position);
                    invoker.setCommand(choseDialFrag);
                    invoker.show();
                }
            });
      //  }
    }

    public static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }

    static class LexicographicComparator implements Comparator<ExIn> {

        @Override
        public int compare(ExIn lhs, ExIn rhs) {
            String[] dateL = lhs.getDate().split(":");
            String[] dateR = rhs.getDate().split(":");
            if (Integer.parseInt(dateL[1]) > Integer.parseInt(dateR[1]) )
            {
                return 1;
            }
            else if(Integer.parseInt(dateL[1]) == Integer.parseInt(dateR[1]))
            {
                if(Integer.parseInt(dateL[0]) > Integer.parseInt(dateR[0]))
                {
                    return 1;
                }
                else if(Integer.parseInt(dateL[0]) == Integer.parseInt(dateR[0]))
                {
                    return 0;
                }
                else
                {
                    return -1;
                }
            }
            else
            {
                return -1;
            }
        }
    }

}
