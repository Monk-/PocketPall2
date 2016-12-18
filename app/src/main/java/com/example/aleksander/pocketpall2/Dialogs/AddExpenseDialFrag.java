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

import com.example.aleksander.pocketpall2.Classes.Categories;
import com.example.aleksander.pocketpall2.Classes.Expense;
import com.example.aleksander.pocketpall2.Fragments.Fragment1;
import com.example.aleksander.pocketpall2.Fragments.Fragment3;
import com.example.aleksander.pocketpall2.MainActivity;
import com.example.aleksander.pocketpall2.R;

import java.util.List;

import static com.example.aleksander.pocketpall2.Dialogs.AddIncomeDialFrag.refreshCharts;
import static com.example.aleksander.pocketpall2.MainActivity.date;
import static com.example.aleksander.pocketpall2.MainActivity.expDB;

import static com.example.aleksander.pocketpall2.Fragments.Fragment1.*;


public class AddExpenseDialFrag extends DialogFragment implements Command {
    public View dialogView;
    public AutoCompleteTextView actv;
    public List<String> names;

    EditText title1;
    EditText amount1;
    Spinner category1;


    public AddExpenseDialFrag()
    {}

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        dialogView = inflater.inflate(R.layout.dial_frag_add_expense, null);
        Spinner dropdown = (Spinner)dialogView.findViewById(R.id.ExcategorySpinner);
        String[] items = new String[]{"Car", "Clothing", "Electronics", "Expenses", "Home", "Income", "Work", "Education", "Sports"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        initLayout();
                        String title = title1.getText().toString();
                        Double amount = Double.parseDouble(!amount1.getText().toString().equals("") ? amount1.getText().toString() : "-1.0");
                        Integer category = Categories.getInt(category1.getSelectedItem().toString());
                        boolean okey = true;
                        if (date.equals("")) {
                            date = MainActivity.DatePickerFragment.getCurrentDate();
                        }
                        if (title.equals("") || amount == -1 || category == -1 || date.equals("")) {
                            okey = false;
                        }
                        if (okey) {
                            expDB.addToDb(new Expense(title, "", amount, category, date));
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
        Dialog  dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return dialog;
    }

    private void initLayout()
    {
        title1 = (EditText) dialogView.findViewById(R.id.Extitle);

        amount1 = (EditText) dialogView.findViewById(R.id.Examount);
        category1 = (Spinner) dialogView.findViewById(R.id.ExcategorySpinner);
    }

    @Override
    public void execute(FragmentManager fg) {
        this.show(fg, "");
    }
}
