package com.example.aleksander.pocketpall2.Classes;


public abstract class ExIn {
    private String title;
    private String comment;
    private String date;
    private Double amount;
    private Integer category;

    public ExIn(String title, String comment, Double amount, Integer category, String date) {
        this.title = title;
        this.comment = comment;
        this.date = date;
        this.amount = amount;
        this.category = category;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public String getMonth()
    {
        return date.split(":")[1];
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ExIn{" +
                "title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                ", category=" + category +
                '}';
    }
}
