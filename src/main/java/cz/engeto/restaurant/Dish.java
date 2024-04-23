package cz.engeto.restaurant;

import java.math.BigDecimal;
import java.time.Duration;


public class Dish {

    private static Integer nextId = 1;
    private Integer id;
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

    public Dish(Integer id, String title, BigDecimal price, Duration preparationTime, String image) throws RestaurantException{
        this.id = id;
        this.title = title;
        setPrice(price);
        setPreparationTime(preparationTime);
        this.image = image;
    }

    public Integer getId() {
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



    public void setPrice(BigDecimal price) throws RestaurantException {
        if(price.compareTo(BigDecimal.ZERO)<= 0) {
            throw new RestaurantException("Cena nemůže být záporná. Vámi zadaná cena je: " +price+ ".");
        }
        else this.price = price;
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
