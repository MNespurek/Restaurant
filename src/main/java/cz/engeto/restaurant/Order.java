package cz.engeto.restaurant;

import java.time.LocalTime;

public class Order {
    private Dish dish;
    private Integer dishAmount;
    private LocalTime orderedTime;
    private LocalTime fulfilmentTime;
    private Boolean alreadyPayed;


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

    public String getDishNumber() {
        return String.valueOf(dish.getId());
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Integer getDishAmount() {
        return dishAmount;
    }

    public void setDishAmount(Integer dishAmount) {
        this.dishAmount = dishAmount;
    }

    public LocalTime getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(LocalTime orderedTime) {
        this.orderedTime = orderedTime;
    }

    public LocalTime getFulfilmentTime() {
        return fulfilmentTime;
    }

    public void setFulfilmentTime(LocalTime fulfilmentTime) {
        this.fulfilmentTime = fulfilmentTime;
    }

    public Boolean getAlreadyPayed() {
        return alreadyPayed;
    }

    public void setAlreadyPayed(Boolean alreadyPayed) {
        this.alreadyPayed = alreadyPayed;
    }
}
