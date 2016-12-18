package com.example.aleksander.pocketpall2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.aleksander.pocketpall2.Classes.ExIn;

import java.util.List;


public abstract class Template extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BudgetDB";

    public Template(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    public void addToDb(ExIn come)
    {
        SQLiteDatabase db = connectToDb();
        ContentValues values = setValues(come);
        insertToDb(db,values);
        closeDb(db);
    }

    public SQLiteDatabase connectToDb()
    {
        return this.getWritableDatabase();
    }
    public abstract ContentValues setValues(ExIn come);
    public abstract void insertToDb(SQLiteDatabase db,ContentValues values);
    public void closeDb(SQLiteDatabase db)
    {
        db.close();
    }

    public List getAllComes()
    {
        String query = buildQuery();
        SQLiteDatabase db = connectToDb();
        Cursor cursor = createCursor(db,query);
        return getAllData(cursor,db);
    }

    public abstract String buildQuery();
    public abstract Cursor createCursor(SQLiteDatabase db,String query);
    public abstract List getAllData(Cursor cursor, SQLiteDatabase db);

    public void deleteCome(ExIn come)
    {
        SQLiteDatabase db = connectToDb();
        delete(db, come);
        closeDb(db);

    }

    public abstract void delete(SQLiteDatabase db, ExIn come);

    public boolean checkIfExist(ExIn come)
    {
        SQLiteDatabase db = connectToDb();
        boolean g = find(db, come);
        closeDb(db);
        return g;
    }

    public abstract boolean find(SQLiteDatabase db, ExIn come);

    public Cursor setCursor(SQLiteDatabase db, String column, String[] columns, ExIn come)
    {
        return db.query(column, // a. table
                columns, // b. column names
                " title = ? and date = ?", // c. selections
                new String[]{come.getTitle(), come.getDate()}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
    }

    public abstract String[] setColumns();

    public List<ExIn> getCategoryAll(int cat)
    {
        SQLiteDatabase db = connectToDb();
        List <ExIn> temp = getCategory(db, cat);
        closeDb(db);
        return temp;
    }

    public abstract List<ExIn> getCategory(SQLiteDatabase db, int cat);

    public List<ExIn> getAllComesForParticulatMonth(int month, int cat)
    {
        SQLiteDatabase db = connectToDb();
        List<ExIn> temp = getMonthCome(db, month, cat);
        return temp;
    }

    public abstract List<ExIn> getMonthCome(SQLiteDatabase db, int month, int cat);

    public void delete()
    {   SQLiteDatabase db = connectToDb();
        try {
            db.isOpen();
        } catch (SQLException sqle) {
            Log.e("TAG", "Never ignore exception!!! " + sqle);
        }
        db.execSQL("DROP TABLE IF EXISTS " + ColumnNames.Income.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ColumnNames.Expense.TABLE_NAME);
        this.onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_INCOME_TABLE = "CREATE TABLE " + ColumnNames.Income.TABLE_NAME + " ( " +
                ColumnNames.Income.COLUMN_INCOME_TITLE + " TEXT, " +
                ColumnNames.Income.COLUMN_INCOME_COMMENT + " TEXT, " +
                ColumnNames.Income.COLUMN_INCOME_AMOUNT + " DOUBLE, " +
                ColumnNames.Expense.COLUMN_EXPENSE_CATEGORY + " INTEGER, " +
                ColumnNames.Income.COLUMN_INCOME_DATE + " TEXT )";
        db.execSQL(CREATE_INCOME_TABLE);

        String CREATE_EXPENSE_TABLE = "CREATE TABLE " + ColumnNames.Expense.TABLE_NAME + " ( " +
                ColumnNames.Expense.COLUMN_EXPENSE_TITLE+ " TEXT, " +
                ColumnNames.Expense.COLUMN_EXPENSE_COMMENT + " TEXT, " +
                ColumnNames.Expense.COLUMN_EXPENSE_AMOUNT + " DOUBLE, " +
                ColumnNames.Expense.COLUMN_EXPENSE_CATEGORY + " INTEGER, " +
                ColumnNames.Expense.COLUMN_EXPENSE_DATE + " TEXT )";
        db.execSQL(CREATE_EXPENSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ColumnNames.Income.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ColumnNames.Expense.TABLE_NAME);
        this.onCreate(db);
    }


}
