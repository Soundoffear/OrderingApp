package com.ejoapps.m2d2_sub.orderingapp.containers;


public class OrderedDrinksData {

    private String drinkName;
    private String drinkQuantity;
    private String drinkPrice;

    public OrderedDrinksData(String drinkName, String drinkQuantity, String drinkPrice) {
        this.drinkName = drinkName;
        this.drinkQuantity = drinkQuantity;
        this.drinkPrice = drinkPrice;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public String getDrinkQuantity() {
        return drinkQuantity;
    }


    public String getDrinkPrice() {
        return drinkPrice;
    }

}
