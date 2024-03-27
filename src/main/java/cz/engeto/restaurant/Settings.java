package cz.engeto.restaurant;

public class Settings {
    private static final String ORDERS = "resources/orders.txt";
    private static final String DISHES = "resources/dishes.txt";

    private static final String DELIMITER = "\t";

    public static String getDelimiter() {
        return DELIMITER;
    }

    public static String getOrders() {
        return DISHES;
    }

    public static String getDishes() {
        return ORDERS;
    }



}
