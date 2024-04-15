package cz.engeto.restaurant;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Order {
    private Dish dish;
    private Integer dishAmount;
    private LocalTime orderedTime;
    private LocalTime fulfilmentTime;
    private Boolean alreadyPayed;

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");


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

    public void setOrderedTime(LocalTime orderedTime) {
        this.orderedTime = orderedTime;
    }

    public String getFulfilmentTime() { return fulfilmentTime.format(timeFormatter);
    }

    public void setFulfilmentTime(LocalTime fulfilmentTime) {
        this.fulfilmentTime = fulfilmentTime;
    }

    public String getAlreadyPayed() {
        if (alreadyPayed) {
            return "zaplaceno";
        }
        return "";
    }

    public String getAlreadyPayedStatus(Boolean alreadyPayed) {
        this.alreadyPayed = alreadyPayed;
        String status = alreadyPayed ? "zaplaceno" : "";
        return status;
    }

    public void setAlreadyPayed(Boolean alreadyPayed) {
        this.alreadyPayed = alreadyPayed;
    }
}
