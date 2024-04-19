package cz.engeto.restaurant;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws RestaurantException, IOException {
        /*Úkol č. 1. soubor pro jídla se vytváří při inicializaci objektu třídy dishManager (plním podmínku, že není nutné definovat žádný soubor dopředu).
         Stoly se vytváři rovněž inicializací (nevím, kolik jich bude, je to automatický proces)*/

        //úkol č. 2 a 3
        createDishes();
        Table table15 = createTableOrder("stůl", 15);
        Table table2 = createTableOrder("stůl", 2);
        table15.getTotalTablePrice();
        RestaurantManager restaurantManager = new RestaurantManager();
        System.out.println(restaurantManager.getOrdersBeforeFulfilmentTime());
        System.out.println(restaurantManager.averageGetReadyOrders());
    }

    public static DishManager createDishes() throws RestaurantException {
        Dish rizek = new Dish("Kuřecí řízek obalovaný 150 g", BigDecimal.valueOf(120), Duration.ofMinutes(10), "rizek.png");
        Dish hranolky = new Dish("Hranolky 150 g", BigDecimal.valueOf(70), Duration.ofMinutes(10), "hranolky.png");
        Dish pstruh = new Dish("Pstruh na víně 200 g", BigDecimal.valueOf(190), Duration.ofMinutes(15), "pstruh.png");
        Dish kofola = new Dish("Kofola 0,5 l", BigDecimal.valueOf(40), Duration.ofMinutes(0), "kofola.png");
        DishManager dishManager = new DishManager();
        dishManager.addDish(rizek);
        dishManager.addDish(hranolky);
        dishManager.addDish(pstruh);
        dishManager.addDish(kofola);
        return dishManager;
    }

    public static Table createTableOrder(String name, Integer number) throws RestaurantException {
        DishManager dishManager = createDishes();
        dishManager.loadDishStack(Settings.getDishes());
        Table table = new Table(name, number);
        table.createOrder();
        return table;
    }



    public static void getTotalTablePrice(Table table) {
        BigDecimal totalOrderPrice = BigDecimal.ZERO;
        for(Order order : table.getOrderList()) {
            totalOrderPrice = totalOrderPrice.add(order.getTotalDishPrice());
        }

    }

    public static List<Order> sortedOrders() throws IOException, RestaurantException {

        RestaurantManager restaurantManager = new RestaurantManager();
        List<Order> sortedOrders = restaurantManager.allOrders();
        for(Order order: sortedOrders) {
            Collections.sort(sortedOrders, new Comparator<Order>() {

                @Override
                public int compare(Order order1, Order order2) {
                    if (order1.getOrderedTimeToCompare().isBefore(order2.getOrderedTimeToCompare())) {
                        return 1;
                    }
                    if (order1.getOrderedTimeToCompare().isAfter(order2.getOrderedTimeToCompare())) {
                        return -1;
                    } else return 0;

                }
            });
        }
         return sortedOrders;
        }


    }





        /*DishManager dishManager = new DishManager();


        dishManager.saveDishStack(Settings.getDishes());
        dishManager.loadDishStack(Settings.getDishes());
        //dishManager.saveDishStack(Settings.getDishes());


        //Tables table4 = new Tables("table4", 4);
        //table4.loadOrderList();
        //table4.createOrder();
        //table4.saveOrderList();
        //table4.saveOrderList();

        //table4.createOrder();
        //table4.createOrder();
        //table4.saveOrderList(Settings.getOrders());

        //Tables table5 = new Tables("table5", 5);
        //table5.loadOrderList();
        //table5.createOrder();

        //table5.saveOrderList();
        //table5.createOrder();




        /*System.out.println(dishManager.getDishStack().size());
        dishManager.saveDishStack(Settings.getDishes());
        dishManager.loadDishStack(Settings.getDishes());
        System.out.println(dishManager.getDishStack().size());
        Dish rizek1 = new Dish("Kuřecí řízek obalovaný 150 g", BigDecimal.valueOf(120), Duration.ofMinutes(10), "rizek.png");
        dishManager.addDish(rizek1);
        System.out.println(dishManager.getDishStack().size());
        dishManager.saveDishStack(Settings.getDishes());
        dishManager.loadDishStack(Settings.getDishes());
        System.out.println(dishManager.getDishStack().size());*/



