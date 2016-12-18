package com.example.aleksander.pocketpall2.Classes;


public enum Categories {

    Car(0, "Car"),
    Clothing(1, "Clothing"),
    Electronics(2, "Electronics"),
    Expenses(3, "Expenses"),
    Home(4, "Home"),
    Income(5, "Income"),
    Work(6, "Work"),
    Education(7, "Education"),
    Sports(8, "Sports");

    protected int code;
    protected String title;

    public static int getInt(String category)
    {
        switch(category)
        {
            case "Car":
                return 0;
            case "Clothing":
                return 1;
            case "Electronics":
                return 2;
            case "Expenses":
                return 3;
            case "Home":
                return 4;
            case "Income":
                return 5;
            case "Work":
                return 6;
            case "Education":
                return 7;
            case "Sports":
                return 8;
            default:
                return -1;
        }
    }

    public static String getStr(int pos)
    {
        switch(pos)
        {
            case 0:
                return "Car";
            case 1:
                return "Clothing";
            case 2:
                return "Electronics";
            case 3:
                return "Expenses";
            case 4:
                return "Home";
            case 5:
                return "Income";
            case 6:
                return "Work";
            case 7:
                return "Education";
            case 8:
                return "Sports";
            default:
                return "Income";
        }
    }

    public static String[] getListStr()
    {
        return new String[] {
        "Car","Clothing","Electronics","Expenses","Home","Income","Work","Education","Sports"
        };
    }

    Categories(int code, String title) {
        this.code = code;
        this.title = title;
    }

    public int getCode() {
        return this.code;
    }

    public String getString(Categories d) {
        return d.title;
    }
}
