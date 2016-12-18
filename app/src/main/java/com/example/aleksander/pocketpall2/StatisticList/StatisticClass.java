package com.example.aleksander.pocketpall2.StatisticList;


public class StatisticClass
{
    private String name;
    private Double amount;

    public StatisticClass(String name, Double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
