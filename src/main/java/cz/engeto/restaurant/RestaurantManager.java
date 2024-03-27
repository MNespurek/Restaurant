package cz.engeto.restaurant;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantManager {

    private List<Order> orderList = new ArrayList<>();

    public void addOrder(Order order) {
        orderList.add(order);
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void saveOrderList(String file) throws RestaurantException {
        String delimiter = Settings.getDelimiter();
        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            for(Order order : orderList) {
                writer.println(order.getDishNumber()+ delimiter
                        +order.getDishAmount()+ delimiter
                        +order.getOrderedTime()+ delimiter
                        +order.getFulfilmentTime() + delimiter
                        +order.getAlreadyPayed() + delimiter

                );
            }
        }catch (FileNotFoundException e) {
            throw new RestaurantException("Soubor " +file+ "nebyl nalezen" +e.getLocalizedMessage()+ ".");
        }catch (IOException e) {
            throw new RestaurantException("Chyba při načítání souboru "+file+ e.getLocalizedMessage()+".");
        }
    }
}
