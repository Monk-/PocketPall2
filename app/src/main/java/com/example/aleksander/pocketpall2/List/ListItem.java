package com.example.aleksander.pocketpall2.List;


public class ListItem {

    private String title;
    private Double amount;
    private int category;
    private boolean positive;

    public ListItem(String title, Double amount, int category) {
        this.title = title;
        this.amount = amount;
        this.category = category;
        if (this.amount > 0)
        {
            this.positive = true;
        }
        else
        {
            this.positive = false;
        }

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }
}
