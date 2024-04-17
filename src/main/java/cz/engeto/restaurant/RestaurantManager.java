package cz.engeto.restaurant;

import java.io.*;
import java.time.LocalTime;
import java.util.*;

public class RestaurantManager {


    private List<Tables> tablesList = new ArrayList<>();

    public List<Tables> getTableList() {
        return tablesList;
    }

    public void addTable(Tables table) {
        tablesList.add(table);
    }

    public String getOrdersBeforeFulfilmentTime() throws IOException, RestaurantException {
        Integer notFulfilmentTimeTableOrders = 0;
        List<Tables> sortedTableList = new ArrayList<>(tablesList);
        for(Tables table : sortedTableList) {
            table.loadOrderList();
            Integer numberOfNotFulfilmentTimeOrders = table.getNotFulfilmentTimeOrders();
            notFulfilmentTimeTableOrders =+ numberOfNotFulfilmentTimeOrders;

            }
        return "Počet nedokončených objednávek je: "+notFulfilmentTimeTableOrders+".";
    }

    public String averageGetReadyOrders() throws IOException, RestaurantException {

        List<Tables> sortedTableList = new ArrayList<>(tablesList);

        Long totalPreparationTime = 0L;
        Long totalNumberOfOrders = 0L;

        for(Tables table : sortedTableList) {
            table.loadOrderList();
            Long numberOfOrders = (long) table.getOrderList().size();
            totalNumberOfOrders += numberOfOrders;
            List<Long> listOfTotalPreparationTime = table.getListOfTotalPreparationTime();
            totalPreparationTime += table.getTotalPreparationTime(listOfTotalPreparationTime);
        }
        return "Průměrná doba realizace objednávky je: "+totalPreparationTime/totalNumberOfOrders+ ".";
    }
}









