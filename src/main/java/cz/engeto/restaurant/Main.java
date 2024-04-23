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

        //úkol č. 2
        DishManager dishManager = createDishes();
        Table table15 = createTableAndOrderSaveOrderFortable("stůl", 15, 3);
        Table table2 = createTableAndOrderSaveOrderFortable("stůl", 2, 1);
        //úkol č. 3
        System.out.println(table15.getTotalTablePrice());
        table15.tableOrderListToBePrinted();

        //úkol č. 4
        RestaurantManager restaurantManager = new RestaurantManager();
        restaurantManager.addTable(table2);
        restaurantManager.addTable(table15);
        System.out.println(restaurantManager.getOrdersBeforeFulfilmentTime());
        System.out.println(restaurantManager.averageGetReadyOrders());

        //úkol č. 5

        dishManager.loadDishStack(Settings.getDishes());
        table15.loadOrderList();
        table2.loadOrderList();

        //úkol č. 6
        //table15.createOrder(1);
        //table15.saveOrderList();

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
        dishManager.createDishFile();
        dishManager.saveDishStack(Settings.getDishes());
        return dishManager;

    }

    public static Table createTableAndOrderSaveOrderFortable(String name, Integer number, Integer numberOfOrders) throws RestaurantException {
        Table table = new Table(name, number);
        table.createOrder(numberOfOrders);
        table.saveOrderList();
        return table;
    }
}
