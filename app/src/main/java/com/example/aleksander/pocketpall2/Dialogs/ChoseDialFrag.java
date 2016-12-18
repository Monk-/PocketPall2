package com.example.aleksander.pocketpall2.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.aleksander.pocketpall2.Fragments.Fragment1;
import com.example.aleksander.pocketpall2.R;

import static com.example.aleksander.pocketpall2.Fragments.Fragment1.fragment1;
import static com.example.aleksander.pocketpall2.Fragments.Fragment1.listView;
import static com.example.aleksander.pocketpall2.MainActivity.editDialFrag;
import static com.example.aleksander.pocketpall2.MainActivity.incDB;
import static com.example.aleksander.pocketpall2.MainActivity.expDB;
import static com.example.aleksander.pocketpall2.Fragments.Fragment1.exIn;
import static com.example.aleksander.pocketpall2.MainActivity.invoker;

public class ChoseDialFrag extends DialogFragment implements Command {
    public View dialogView;

    public ChoseDialFrag() {
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dial_frag_edir_or_del, null);
        TextView tV = (TextView)dialogView.findViewById(R.id.delete);
        tV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incDB.deleteCome(exIn);
                expDB.deleteCome(exIn);
                Fragment1.refreshList(listView, fragment1);
                dismiss();
            }
        });
        TextView tV1 = (TextView)dialogView.findViewById(R.id.edit);
        tV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invoker.setCommand(editDialFrag);
                invoker.show();
                dismiss();
            }
        });
        builder.setView(dialogView)
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
