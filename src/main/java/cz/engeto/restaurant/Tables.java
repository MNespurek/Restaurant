package cz.engeto.restaurant;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Tables {

    private static final Scanner scanner = new Scanner(System.in);
    private Integer number;

    private List<Order> orderList = new ArrayList<>();



    public Tables(Integer number)
    {
        this.number = number;

    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void createOrder() throws RestaurantException {
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
                    LocalTime orderedTime = LocalTime.now();
                    LocalTime fulfilmentTime = LocalTime.now().plus(dishById.getPreparationTime());
                    System.out.println("Je zaplaceno? (Y/N) \n");
                    String answer = scanner.nextLine();
                    if (answer.equalsIgnoreCase("Y")) {
                        Boolean alreadyPayed = true;

                        Order order = new Order(dishById, dishAmount, orderedTime, fulfilmentTime, alreadyPayed);
                        orderList.add(order);
                        System.out.println("Objednávka byla vytvořena!");
                    } else if (answer.equalsIgnoreCase("N")) {
                        Boolean alreadyPayed = false;
                        Order order = new Order(dishById, dishAmount, orderedTime, fulfilmentTime, alreadyPayed);
                        orderList.add(order);
                        System.out.println("Objednávka byla vytvořena!");;
                    } else {
                        System.out.println("Nezadali jste ani jednu hodnotu!");
                    }
                } catch (NumberFormatException e) {
                    throw new RestaurantException("Zadali jste špatný formát čísla" + e.getLocalizedMessage());
                }
            }else System.out.println("Zadali jste číslo, které není v seznamu jídel ");
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

            public List<Order> getOrderList () {
                return orderList;
            }
            public void saveOrderList (String file) throws RestaurantException {
                String delimiter = Settings.getTab();
                String coma = Settings.getComa();
                String space = Settings.getSpace();
                String beginBracket = Settings.getBeginbracket();
                String endBracket = Settings.getEndbracket();
                String currency = Settings.getCurrency();
                String colon = Settings.getColon();
                String dash = Settings.getDash();

                try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
                    Integer itemNumber = 0;
                    writer.println(Settings.getTableStars() + Settings.getOrderingtext() + this.getNumber() +Settings.getTableStars());
                    for (Order order : orderList) {
                        writer.println(
                                itemNumber++ +coma + space
                                + order.getDish().getId() + space
                                + order.getDishAmount() + space + beginBracket + order.getTotalDishPrice() + currency + endBracket
                                + colon + delimiter
                                + order.getOrderedTime()
                                + dash
                                + order.getFulfilmentTime() + delimiter
                                + order.getAlreadyPayed() + delimiter
                        );
                    }//writer.println(Settings.getEndOrderingStars());
                } catch (FileNotFoundException e) {
                    throw new RestaurantException("Soubor " + file + "nebyl nalezen" + e.getLocalizedMessage() + ".");
                } catch (IOException e) {
                    throw new RestaurantException("Chyba při načítání souboru " + file + e.getLocalizedMessage() + ".");
                }
            }

            public void loadOrderList (String file) throws RestaurantException, IOException {
                //orderList.clear();
                int lineCounter = 0;
                try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
                    scanner.nextLine();
                    while (scanner.hasNextLine()) {
                        lineCounter++;
                        String sentence = scanner.nextLine();
                        String[] sentenceDevided = sentence.split(Settings.getSpace());
                        Integer dishId = Integer.parseInt(sentenceDevided[1]);
                        String[] getPayedAndTime = sentenceDevided[4].split(Settings.getTab());

                        String[] getTime = getPayedAndTime[1].split(Settings.getDash());
                        DishManager dishManager = new DishManager();
                        dishManager.loadDishStack(Settings.getDishes());
                        Dish dish = dishManager.chosenDish(dishId);
                        Integer dishAmount = Integer.parseInt(sentenceDevided[2]);
                        LocalTime orderTime = LocalTime.parse(getTime[0]);
                        LocalTime fulfilmentTime = LocalTime.parse(getTime[1]);

                        Boolean alreadyPayed = false;
                        if(Objects.equals(getPayedAndTime[2], "zaplaceno")) {
                            alreadyPayed = true;
                            System.out.println(alreadyPayed);
                            Order order = new Order(dish, dishAmount, orderTime, fulfilmentTime, alreadyPayed);
                            orderList.add(order);
                        }
                            else {
                            System.out.println(alreadyPayed);
                                Order order = new Order(dish, dishAmount, orderTime, fulfilmentTime, alreadyPayed);
                        orderList.add(order);
                        }
                    }
                } catch (FileNotFoundException e) {
                    throw new RestaurantException("Soubor" + file + "se nepovedlo najít." + e.getLocalizedMessage());
                } catch (IndexOutOfBoundsException e) {
                    throw new RestaurantException("Zadaný index nebyl při rozdělení nalezen " +e.getLocalizedMessage());
                }
            }

        }

