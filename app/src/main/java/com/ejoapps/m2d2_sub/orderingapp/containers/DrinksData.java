package com.ejoapps.m2d2_sub.orderingapp.containers;


public class DrinksData {

    private String drinkName;
    private String drinkPrice;

    public DrinksData(String drinkName, String drinkPrice) {
        this.drinkName = drinkName;
        this.drinkPrice = drinkPrice;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public String getDrinkPrice() {
        return drinkPrice;
    }

    public void setDrinkPrice(String drinkPrice) {
        this.drinkPrice = drinkPrice;
    }
}
