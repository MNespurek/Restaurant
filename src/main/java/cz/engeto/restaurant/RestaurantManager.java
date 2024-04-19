package cz.engeto.restaurant;

import java.io.*;
import java.time.LocalTime;
import java.util.*;

public class RestaurantManager {


    private List<Table> tablesList = new ArrayList<>();

    public List<Table> getTableList() {
        return tablesList;
    }

    public void addTable(Table table) {
        tablesList.add(table);
    }

    public String getOrdersBeforeFulfilmentTime() throws IOException, RestaurantException {
        Integer notFulfilmentTimeTableOrders = 0;
        List<Table> sortedTableList = new ArrayList<>(tablesList);
        for (Table table : sortedTableList) {
            table.loadOrderList();
            Integer numberOfNotFulfilmentTimeOrders = table.getNotFulfilmentTimeOrders();
            notFulfilmentTimeTableOrders = +numberOfNotFulfilmentTimeOrders;

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
            return "Průměrná doba realizace objednávky je: " + totalPreparationTime / totalNumberOfOrders + ".";
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


}