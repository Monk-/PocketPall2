package com.example.aleksander.pocketpall2.Database;


import android.provider.BaseColumns;

public class ColumnNames {

    public ColumnNames() {
    }

    public static abstract class Income implements BaseColumns
    {
        public static final String TABLE_NAME = "income";
        public static final String COLUMN_INCOME_TITLE = "title";
        public static final String COLUMN_INCOME_COMMENT = "comment";
        public static final String COLUMN_INCOME_AMOUNT = "amount";
        public static final String COLUMN_INCOME_CATEGORY = "category";
        public static final String COLUMN_INCOME_DATE = "date";
    }

    public static abstract class Expense implements BaseColumns
    {
        public static final String TABLE_NAME = "expence";
        public static final String COLUMN_EXPENSE_TITLE = "title";
        public static final String COLUMN_EXPENSE_COMMENT = "comment";
        public static final String COLUMN_EXPENSE_AMOUNT = "amount";
        public static final String COLUMN_EXPENSE_CATEGORY = "category";
        public static final String COLUMN_EXPENSE_DATE = "date";
    }
}
