package cz.engeto.restaurant;

import java.io.*;
import java.time.LocalTime;
import java.util.*;

public class RestaurantManager {

    private List<Table> tablesList = new ArrayList<>();

    public void addTable(Table table) {
        tablesList.add(table);
    }

    public String getOrdersBeforeFulfilmentTime() throws IOException, RestaurantException {
        Integer notFulfilmentTimeTableOrders = 0;
        List<Table> sortedTableList = new ArrayList<>(tablesList);
        for (Table table : sortedTableList) {
            Integer numberOfNotFulfilmentTimeOrders = table.getNotFulfilmentTimeOrders();
            notFulfilmentTimeTableOrders += numberOfNotFulfilmentTimeOrders;

        }
        return "Počet nedokončených objednávek je: " + notFulfilmentTimeTableOrders + ".";
    }
    public String averageGetReadyOrders() throws IOException, RestaurantException {

        List<Table> sortedTableList = new ArrayList<>(tablesList);

        Long totalPreparationTime = 0L;
        Long totalNumberOfOrders = 0L;

        for (Table table : sortedTableList) {
            table.loadOrderList();
            Long numberOfOrders = (long) table.getOrderList().size();
            totalNumberOfOrders += numberOfOrders;
            List<Long> listOfTotalPreparationTime = table.getListOfTotalPreparationTime();
            totalPreparationTime += table.getTotalPreparationTime(listOfTotalPreparationTime);
        }
        try {
            return "Průměrná doba realizace objednávky je: " + totalPreparationTime / totalNumberOfOrders + "minut.";
        } catch (ArithmeticException e) {
            throw new RestaurantException("Došlo k chybě při dělení nulou: " + e.getLocalizedMessage());
        }
    }

    public List<Order> allOrders() throws IOException, RestaurantException {
        List<Table> sortedTableList = new ArrayList<>(tablesList);
        List<Order> notSortedOrderTimeList = new ArrayList<>();
        for (Table table : sortedTableList) {
            table.loadOrderList();
            List<Order> orderList = table.getOrdersSortedBeginOrderedTime();
            notSortedOrderTimeList.addAll(orderList);
        }
        return notSortedOrderTimeList;
    }

    public List<Order> sortedOrdersByOrderedTime() throws IOException, RestaurantException {

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