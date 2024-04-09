package cz.engeto.restaurant;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public void loadOrderList(String file) throws RestaurantException, IOException {
        orderList.clear();
        int lineCounter = 0;
        try(Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
            while(scanner.hasNextLine()) {
                lineCounter++;
                String sentence = scanner.nextLine();
                String[] sentenceDevided = sentence.split(Settings.getDelimiter());
                int dishId = Integer.parseInt(sentenceDevided[0]);
                DishManager dishManager = new DishManager();
                Boolean isDish = dishManager.isDishById(dishId);
                if(isDish) {
                    Dish dish = dishManager.chosenDish(dishId);
                    Integer dishAmount = Integer.parseInt(sentenceDevided[1]);
                    LocalTime orderTime = LocalTime.parse(sentenceDevided[2]);
                    LocalTime fulfilmentTime = LocalTime.parse(sentenceDevided[3]);
                    Boolean alreadyPayed = Boolean.parseBoolean(sentenceDevided[4]);
                    Order order = new Order(dish, dishAmount, orderTime, fulfilmentTime, alreadyPayed);
                    orderList.add(order);
                }
                else throw new RestaurantException("jídlo s Id" +dishId+ "nebylo nalezeno, není tudíž možné vytvořit objednávku");
                }
        }catch (FileNotFoundException e) {
            throw new RestaurantException("Soubor" +file+ "se nepovedlo najít."+e.getLocalizedMessage());
        }
    }
}


