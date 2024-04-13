package cz.engeto.restaurant;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RestaurantManager {


    private List<Tables> tablesList = new ArrayList<>();

    public List<Tables> getTableList() {
        return tablesList;
    }

    public void addTable(Tables table) {
        tablesList.add(table);
    }




}


