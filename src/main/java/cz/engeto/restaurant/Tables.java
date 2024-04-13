package cz.engeto.restaurant;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

            public List<Order> getOrderList () {
                return orderList;
            }
            public void saveOrderList (String file) throws RestaurantException {
                String delimiter = Settings.getDelimiter();
                String coma = Settings.getComa();
                String space = Settings.getSpace();
                String beginBracket = Settings.getBeginbracket();
                String endBracket = Settings.getEndbracket();
                String currency = Settings.getCurrency();
                String colon = Settings.getColon();
                String dash = Settings.getDash();
                String orderingText = Settings.getOrderingtext();
                try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
                    Integer itemNumber = 0;
                    System.out.println(orderingText + this.getNumber());
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
                    }
                } catch (FileNotFoundException e) {
                    throw new RestaurantException("Soubor " + file + "nebyl nalezen" + e.getLocalizedMessage() + ".");
                } catch (IOException e) {
                    throw new RestaurantException("Chyba při načítání souboru " + file + e.getLocalizedMessage() + ".");
                }
            }

            public void loadOrderList (String file) throws RestaurantException, IOException {
                orderList.clear();
                int lineCounter = 0;
                try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
                    while (scanner.hasNextLine()) {
                        lineCounter++;
                        String sentence = scanner.nextLine();
                        String[] sentenceDevided = sentence.split(Settings.getSpace());
                        int dishId = Integer.parseInt(sentenceDevided[1]);
                        String[] getPayed = sentenceDevided[3].split(Settings.getDelimiter());
                        String[] timeDevide = getPayed[1].split(Settings.getDash());



                        DishManager dishManager = new DishManager();
                        dishManager.loadDishStack(Settings.getDishes());
                        Boolean isDish = dishManager.isDishById(dishId);
                        if (isDish) {
                            Dish dish = dishManager.chosenDish(dishId);
                            Integer dishAmount = Integer.parseInt(sentenceDevided[2]);
                            LocalTime orderTime = LocalTime.parse(timeDevide[0]);
                            LocalTime fulfilmentTime = LocalTime.parse(sentenceDevided[1]);
                            Boolean alreadyPayed = Boolean.parseBoolean(getPayed[2]);
                            Order order = new Order(dish, dishAmount, orderTime, fulfilmentTime, alreadyPayed);
                            orderList.add(order);
                        } else
                            throw new RestaurantException("jídlo s Id" + dishId + "nebylo nalezeno, není tudíž možné vytvořit objednávku");
                    }
                } catch (FileNotFoundException e) {
                    throw new RestaurantException("Soubor" + file + "se nepovedlo najít." + e.getLocalizedMessage());
                }
            }

        }

