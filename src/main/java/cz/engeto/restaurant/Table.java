package cz.engeto.restaurant;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Table {

    private static final Scanner scanner = new Scanner(System.in);
    private Integer number;
    private String name;


    private List<Order> orderList = new ArrayList<>();


    public Table(String name, Integer number) throws RestaurantException {

        this.name = name;
        this.number = number;
        createTableFile();
    }

    public String getNumber() {
        if(number>0 && number<=9) {
            return " " +number;
        }
        return String.valueOf(number);
    }

    public void setNumber(Integer number) {
        this.number = number;
    }


    public String getName() {
        return Settings.getResources() + name + number + Settings.getFileSuffix();
    }

    public void setName(String name) {
        this.name = name;
    }

    private void createTableFile() throws RestaurantException {

        File file = new File(getName());
        try {
            if (file.createNewFile()) {
                System.out.println("Soubor s názvem " + file + " byl úspěšně vytvořen.");
            } else {
                System.out.println("Soubor s názvem " + file + " již existuje.");
            }
        } catch (IOException e) {
            throw new RestaurantException("Soubor s názvem " + file + "se nepodařilo založit" + e.getLocalizedMessage());
        }
    }


    public void createOrder(Integer numberOfOrders) throws RestaurantException {
        Integer counter = 0;
        while(numberOfOrders>counter) {
            counter++;
            System.out.println("Zadejte číslo jídla: \n");
            try {
                Integer numberOfDish = Integer.parseInt(scanner.nextLine());
                DishManager dishManager = new DishManager();
                dishManager.loadDishStack(Settings.getDishes());
                    if (dishManager.isDishById(numberOfDish)) {
                    Dish dishById = dishManager.chosenDish(numberOfDish);
                    try {
                    System.out.println("Zadejte množství: \n");
                    Integer dishAmount = Integer.parseInt(scanner.nextLine());
                    System.out.println("Je zaplaceno? (Y/N) \n");
                    String answer = scanner.nextLine();
                    if (answer.equalsIgnoreCase("Y")) {
                        Boolean alreadyPayed = true;
                        Order order = new Order(dishById, dishAmount, alreadyPayed);
                        orderList.add(order);
                        System.out.println("Objednávka byla vytvořena!");
                    } else if (answer.equalsIgnoreCase("N")) {
                        Boolean alreadyPayed = false;
                        Order order = new Order(dishById, dishAmount, alreadyPayed);
                        orderList.add(order);
                        System.out.println("Objednávka byla vytvořena!");
                    } else {
                        System.out.println("Nezadali jste ani jednu hodnotu!");
                    }
                } catch (NumberFormatException e) {
                    throw new RestaurantException("Zadali jste špatný formát čísla" + e.getLocalizedMessage());
                }
            } else System.out.println("Zadali jste číslo, které není v seznamu jídel ");
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
    }
    public List<Order> getOrderList() {
        return orderList;
    }

    public void saveOrderList() throws RestaurantException {
        String delimiter = Settings.getTab();
        String coma = Settings.getComa();
        String space = Settings.getSpace();
        String beginBracket = Settings.getBeginbracket();
        String endBracket = Settings.getEndbracket();
        String currency = Settings.getCurrency();
        String colon = Settings.getColon();
        String dash = Settings.getDash();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(this.getName())))) {
            Integer itemNumber = 1;
            writer.println(Settings.getTableStars() + Settings.getOrderingtext() + this.getNumber() + Settings.getTableStars());
            writer.println(Settings.getBeginOrderingStars());
            for (Order order : orderList) {
                writer.println(
                        itemNumber++ + coma + space
                                + order.getDish().getId() + space
                                + order.getDishToWriteNumberOfDishes() + space + beginBracket + order.getTotalDishPrice() + currency + endBracket
                                + colon + delimiter
                                + order.getOrderedTime()
                                + dash
                                + order.getFulfilmentTime() + delimiter
                                + order.getAlreadyPayed()
                );
            }
            writer.println(Settings.getEndorderingstars());
        } catch (FileNotFoundException e) {
            throw new RestaurantException("Soubor " + this.getName() + "nebyl nalezen" + e.getLocalizedMessage() + ".");
        } catch (IOException e) {
            throw new RestaurantException("Chyba při načítání souboru " + this.getName() + e.getLocalizedMessage() + ".");
        }
    }

    public void loadOrderList() throws RestaurantException, IOException {
        int lineCounter = 0;
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(this.getName())))) {
            scanner.nextLine();
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                lineCounter++;
                String sentence = scanner.nextLine();
                if (sentence.trim().equals(Settings.getEndorderingstars())) {
                } else {
                    String[] getPayed = sentence.split(Settings.getTab());
                    String[] dishIdAndDishAmount = getPayed[0].split(Settings.getSpace());
                    Integer dishId = Integer.parseInt(dishIdAndDishAmount[1]);
                    String[] getTime = getPayed[1].split(Settings.getDash());
                    DishManager dishManager = new DishManager();
                    dishManager.loadDishStack(Settings.getDishes());
                    Dish dish = dishManager.chosenDish(dishId);
                    String[] dishAmountString = dishIdAndDishAmount[2].split("\\D+");
                    Integer dishAmountInteger = Integer.parseInt(dishAmountString[0]);
                    LocalTime orderTime = LocalTime.parse(getTime[0]);
                    LocalTime fulfilmentTime = LocalTime.parse(getTime[1]);
                    Boolean alreadyPayed = false;
                    if (Objects.equals(getPayed[2], "zaplaceno")) {
                        alreadyPayed = true;
                        Order order = new Order(dish, dishAmountInteger, orderTime, fulfilmentTime, alreadyPayed);
                        orderList.add(order);
                    } else {
                        Order order = new Order(dish, dishAmountInteger, alreadyPayed);
                        orderList.add(order);

                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RestaurantException("Soubor" + this.getName() + "se nepovedlo najít." + e.getLocalizedMessage());
        }catch (IndexOutOfBoundsException e) {
            throw new RestaurantException("Zadaný index nebyl při rozdělení nalezen. Chyba je na řádku " + lineCounter + " souboru " + this.getName() + "." + e.getLocalizedMessage());
        } catch (NoSuchElementException e) {
            throw new RestaurantException("V souboru " + this.getName() + " není žádná objednávka." + e.getLocalizedMessage());
        }catch (NullPointerException e) {
            System.err.println("Nebyl nalezen volaný objekt!" +e.getLocalizedMessage()+".");
        }
        catch (NumberFormatException e) {
            System.err.println("Soubor " +this.getName()+ " má neplatný formát nebo strukturu na řádku "+lineCounter+ "." +e.getLocalizedMessage()+".");
        }
    }

    public Integer getNotFulfilmentTimeOrders() throws IOException, RestaurantException {
        Integer notFulfilmentTimeOrders = 0;
        for (Order order : orderList) {
            if (order.getFulfilmentTimeToCompare().isAfter(LocalTime.now())) {
                notFulfilmentTimeOrders++;
            }
        }
        return notFulfilmentTimeOrders;

    }

    public List<Order> getOrdersSortedBeginOrderedTime() {
        List<Order> sortedOrders = new ArrayList<>(orderList);
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
        return sortedOrders;
    }

    public List<Long> getListOfTotalPreparationTime() {
        List<Order> orderListForTime = new ArrayList<>(orderList);
        List<Long> listOfTimeOrders = new ArrayList<>();
        for (Order order : orderListForTime) {
            Long durationTimePreparation = order.getOrderedTimeToCompare().until(order.getFulfilmentTimeToCompare(), ChronoUnit.MINUTES);
            listOfTimeOrders.add(durationTimePreparation);
        }
        return listOfTimeOrders;

    }

    public Long getTotalPreparationTime(List<Long> listOfPreparationTimeOrders) {
        Long totalDuration = 0L;
        for(Long duration : listOfPreparationTimeOrders) {
            totalDuration += duration;
        }
        return totalDuration;
    }
    public String getTotalTablePrice() {

        BigDecimal totalDishPrice = BigDecimal.ZERO;

        for(Order order : orderList) {
            BigDecimal dishPrice = order.getTotalDishPrice();
            totalDishPrice = totalDishPrice.add(dishPrice);
        }
    return "Celková cena objednávky u stolu č."+this.getNumber()+ " je: " +totalDishPrice+ " Kč";
    }

    public void tableOrderListToBePrinted() throws RestaurantException {
        System.out.println("velikost listu při tisku na začátku:  " +orderList.size());
        String delimiter = Settings.getTab();
        String coma = Settings.getComa();
        String space = Settings.getSpace();
        String beginBracket = Settings.getBeginbracket();
        String endBracket = Settings.getEndbracket();
        String currency = Settings.getCurrency();
        String colon = Settings.getColon();
        String dash = Settings.getDash();
        Integer itemNumber = 1;
        System.out.println(Settings.getTableStars() + Settings.getOrderingtext() + this.getNumber() + Settings.getTableStars());
        System.out.println(Settings.getBeginOrderingStars());

        for (Order order : orderList) {
            System.out.println(
                    itemNumber++ + coma + space
                            + order.getDish().getTitle() + space
                            + order.getDishToWriteNumberOfDishes() + space + beginBracket + order.getTotalDishPrice() + currency + endBracket
                            + colon + delimiter
                            + order.getOrderedTime()
                            + dash
                            + order.getFulfilmentTime() + delimiter
                            + order.getAlreadyPayed()
            );
        }
        System.out.println(Settings.getEndorderingstars());
    }
}

