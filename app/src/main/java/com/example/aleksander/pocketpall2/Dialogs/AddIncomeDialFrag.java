package com.example.aleksander.pocketpall2.Dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.aleksander.pocketpall2.Classes.Income;
import com.example.aleksander.pocketpall2.Fragments.Fragment1;
import com.example.aleksander.pocketpall2.Fragments.Fragment2;
import com.example.aleksander.pocketpall2.Fragments.Fragment3;
import com.example.aleksander.pocketpall2.MainActivity;
import com.example.aleksander.pocketpall2.R;

import java.util.List;

import com.example.aleksander.pocketpall2.Classes.Categories;

import static com.example.aleksander.pocketpall2.Fragments.Fragment1.fragment1;
import static com.example.aleksander.pocketpall2.Fragments.Fragment1.listView;
import static com.example.aleksander.pocketpall2.MainActivity.date;
import static com.example.aleksander.pocketpall2.MainActivity.incDB;


public class AddIncomeDialFrag extends DialogFragment implements Command {
    public View dialogView;
    public AutoCompleteTextView actv;
    public List<String> names;

    public AddIncomeDialFrag() {
    }

    public static void refreshCharts()
    {
        switch(Fragment2.whichChart)
        {
            case 0:
                Fragment2.PieChart();
                break;
            case 1:
                Fragment2.RadarChart();
                break;
            case 2:
                Fragment2.barChartSettings();
                Fragment2.BarChart(Fragment2.period, Fragment2.incOrExp);
                break;
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        dialogView = inflater.inflate(R.layout.dial_frag_add_income, null);
        Spinner dropdown = (Spinner) dialogView.findViewById(R.id.IncategorySpinner);
        String[] items = new String[]{"Car", "Clothing", "Electronics", "Expenses", "Home", "Income", "Work", "Education", "Sports"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText title1 = (EditText) dialogView.findViewById(R.id.Intitle);
                        EditText amount1 = (EditText) dialogView.findViewById(R.id.Inamount);
                        Spinner category1 = (Spinner) dialogView.findViewById(R.id.IncategorySpinner);
                        String title = title1.getText().toString();
                        Double amount = Double.parseDouble(!amount1.getText().toString().equals("") ? amount1.getText().toString() : "-1.0");
                        Integer category = Categories.getInt(category1.getSelectedItem().toString());
                        boolean p = true;
                        if (date.equals("")) {
                            date = MainActivity.DatePickerFragment.getCurrentDate();
                        }
                        if (title.equals("")  || amount == -1 || category == -1 || date.equals("")) {
                            p = false;
                        }
                        if (p) {
                            incDB.addToDb(new Income(title, "", amount, category, date));
                             }
                        date = "";
                        Fragment1.refreshList(listView, fragment1);
                        refreshCharts();
                        if (Fragment3.instant)
                        Fragment3.refresh3Frag();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        Dialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return dialog;
    }

    @Override
    public void execute(FragmentManager fg) {
        this.show(fg, "");
    }
}
