package cz.engeto.restaurant;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Dish {

    private static int nextId = 1;
    private int id;
    private String title;
    private BigDecimal price;

    private Duration preparationTime;
    private String image;

    public Dish (String title, BigDecimal price, Duration preparationTime, String image) throws RestaurantException{
        this.id = nextId++;
        this.title = title;
        this.price = price;
       setPreparationTime(preparationTime);
       this.image = image;
    }

    public Dish (String title, BigDecimal price, Duration preparationTime) throws RestaurantException {
        this(title, price, preparationTime, "blank");
    }

    public Dish(int id, String title, BigDecimal price, Duration preparationTime, String image) throws RestaurantException{
        this.id = id;
        this.title = title;
        this.price = price;
        this.preparationTime = preparationTime;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Duration getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Duration preparationTime) throws RestaurantException {
        if(preparationTime.isNegative()) {
            throw new RestaurantException("Zadali jste negativní časovou hodnotu " +preparationTime+ ".");
        }else {
            this.preparationTime = preparationTime;
        }
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
