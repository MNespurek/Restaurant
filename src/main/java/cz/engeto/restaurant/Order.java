package cz.engeto.restaurant;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Order {
    private Dish dish;
    private Integer dishAmount;
    private LocalTime orderedTime;
    private LocalTime fulfilmentTime;
    private Boolean alreadyPayed;

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public Order(Dish dish, Integer dishAmount, Boolean alreadyPayed) {
        this.dish = dish;
        this.dishAmount = dishAmount;
        this.orderedTime = LocalTime.now();
        setFulfilmentTime(LocalTime.now().plus(dish.getPreparationTime()));
        this.alreadyPayed = alreadyPayed;
    }

    public Order(Dish dish, Integer dishAmount, LocalTime orderedTime, LocalTime fulfilmentTime, Boolean alreadyPayed) {
        this.dish = dish;
        this.dishAmount = dishAmount;
        this.orderedTime = orderedTime;
        this.fulfilmentTime = fulfilmentTime;
        this.alreadyPayed = alreadyPayed;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Integer getDishAmount() {
        return dishAmount;
    }

    public String getDishToWriteNumberOfDishes() {
        return dishAmount + Settings.getMultiplyingamountoffood();
    }

    public BigDecimal getTotalDishPrice() {
        BigDecimal dishAmount = new BigDecimal(this.dishAmount);
        return dishAmount.multiply(this.dish.getPrice());
    }

    public void setDishAmount(Integer dishAmount) {
        this.dishAmount = dishAmount;
    }

    public String getOrderedTime() {
        return orderedTime.format(timeFormatter);
    }

    public LocalTime getOrderedTimeToCompare() {
        return orderedTime;
    }

    public void setOrderedTime(LocalTime orderedTime) {
        this.orderedTime = orderedTime;
    }

    public String getFulfilmentTime() {
        return fulfilmentTime.format(timeFormatter);
    }

    public LocalTime getFulfilmentTimeToCompare() {
        return fulfilmentTime;
    }

    public void setFulfilmentTime(LocalTime fulfilmentTime) {
        this.fulfilmentTime = fulfilmentTime;
    }

    public String getAlreadyPayed() {
        if (alreadyPayed) {
            return "zaplaceno";
        }
        return " ";
    }
}