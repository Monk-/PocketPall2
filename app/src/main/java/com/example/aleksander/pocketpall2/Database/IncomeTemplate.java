package com.example.aleksander.pocketpall2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.aleksander.pocketpall2.Classes.ExIn;
import com.example.aleksander.pocketpall2.Classes.Income;

import java.util.ArrayList;
import java.util.List;

public class IncomeTemplate extends Template {

    public IncomeTemplate(Context context) {
        super(context);
    }

    @Override
    public ContentValues setValues(ExIn come) {
        ContentValues values = new ContentValues();
        values.put(ColumnNames.Income.COLUMN_INCOME_TITLE, come.getTitle()); // get title
        values.put(ColumnNames.Income.COLUMN_INCOME_COMMENT, come.getComment()); // get author
        values.put(ColumnNames.Income.COLUMN_INCOME_AMOUNT, come.getAmount()); // get amount
        values.put(ColumnNames.Income.COLUMN_INCOME_CATEGORY, come.getCategory()); // get category
        values.put(ColumnNames.Income.COLUMN_INCOME_DATE, come.getDate()); // get date
        return values;
    }

    @Override
    public void insertToDb(SQLiteDatabase db, ContentValues values) {
        db.insert(ColumnNames.Income.TABLE_NAME, // table
                null,
                values);
    }

    @Override
    public String buildQuery() {
        return "SELECT  * FROM " + ColumnNames.Income.TABLE_NAME;
    }

    @Override
    public Cursor createCursor(SQLiteDatabase db, String query) {
        return db.rawQuery(query, null);
    }

    @Override
    public List getAllData(Cursor cursor, SQLiteDatabase db) {
        List<Income> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                try {
                    Income income = new Income(cursor.getString(0), cursor.getString(1),
                            Double.parseDouble(cursor.getString(2)), Integer.parseInt(cursor.getString(3)), cursor.getString(4));
                    list.add(income);
                } catch (NumberFormatException ex) {
                    // handle exception
                }
            }
            while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    @Override
    public void delete(SQLiteDatabase db, ExIn come) {
        db.delete(ColumnNames.Income.TABLE_NAME, //table name
                " title = ? and date = ?",  // selections
                new String[]{come.getTitle(), come.getDate()}); //selections args
    }

    @Override
    public List<ExIn> getMonthCome(SQLiteDatabase db, int month, int cat) {
        Cursor cursor = db.query(ColumnNames.Income.TABLE_NAME, // a. table
                setColumns(), // b. column names
                " category = ?", // c. selections
                new String[]{"" + cat}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        List<ExIn> listo = new ArrayList<>();
        while(cursor.moveToNext())
        {
            Income income = new Income(cursor.getString(0), cursor.getString(1),
                    Double.parseDouble(cursor.getString(2)), Integer.parseInt(cursor.getString(3)), cursor.getString(4));
            if (Integer.parseInt(income.getMonth())== month)
            listo.add(income);
        }
        closeDb(db);
        return listo;
    }


    @Override
    public String[] setColumns() {
        return new String[]{ColumnNames.Income.COLUMN_INCOME_TITLE,
                ColumnNames.Income.COLUMN_INCOME_COMMENT,
        ColumnNames.Income.COLUMN_INCOME_AMOUNT,
        ColumnNames.Income.COLUMN_INCOME_CATEGORY,
        ColumnNames.Income.COLUMN_INCOME_DATE
        };
    }

    @Override
    public List<ExIn> getCategory(SQLiteDatabase db, int cat) {
        Cursor cursor = db.query(ColumnNames.Income.TABLE_NAME, // a. table
                setColumns(), // b. column names
                " category = ?", // c. selections
                new String[]{"" + cat}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        List<ExIn> listo = new ArrayList<>();
        while(cursor.moveToNext())
        {
            listo.add(new Income(cursor.getString(0), cursor.getString(1),
                    Double.parseDouble(cursor.getString(2)), Integer.parseInt(cursor.getString(3)), cursor.getString(4)));
        }
        closeDb(db);
        return listo;
    }



    @Override
    public boolean find(SQLiteDatabase db, ExIn come) {
        Cursor cursor = setCursor(db, ColumnNames.Income.TABLE_NAME, setColumns(), come);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return false;
        }
        return true;
    }
}
