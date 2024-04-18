package cz.engeto.restaurant;

import java.io.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.*;

public class DishManager {

    private static final Scanner scanner = new Scanner(System.in);
    private List<Dish> dishStack = new ArrayList<>();

    public List<Dish> getDishStack() {
        return dishStack;
    }

    public void addDish(Dish dish) {
        dishStack.add(dish);
    }

    public void deleteDish(Dish dish) {
        dishStack.remove(dish);
    }

    public void getDishTitles() {
        for(Dish dish : dishStack) {
            System.out.println(dish.getTitle());
        }
    }

    public Boolean wantToEdit(String answer) {
        answer = answer.toUpperCase(Locale.ROOT);
        return answer.equals("Y");
    }

    public void editTitle(Dish dish) {
        System.out.println("Přejete si editovat název?\n Napiš Y pro ano a N pro ne\n");
        String editTitleVariable = scanner.nextLine();
        if (wantToEdit(editTitleVariable)) {
            System.out.println("Zadejte nový název: \n");
            String newTitle = scanner.nextLine();
            dish.setTitle(newTitle);
        }
    }
    public void editPrice(Dish dish) throws RestaurantException {
        String editPrice = scanner.nextLine();
        System.out.println("Přejete si editovat cenu?\nNapiš Y pro ano a N pro ne\n");
        if (wantToEdit(editPrice)) {
            System.out.println("Zadejte novou hodnotu ceny: \n");
            String newPrice = scanner.nextLine();
            try {
                BigDecimal decimal = new BigDecimal(newPrice);
                dish.setPrice(decimal);
            } catch (NumberFormatException e) {
                throw new RestaurantException("Nezadali jste hodnotu, která by byla převeditelná na Bigdecimal" + e.getLocalizedMessage() + ".");
            }
        }
    }
    public void editTime(Dish dish) throws RestaurantException {
        System.out.println("Přete si editovat čas: \n");
        String editTime = scanner.nextLine();
        if (wantToEdit(editTime)) {
            System.out.println("Zadejte nový čas: \n");
            String newTime = scanner.nextLine();
            try {
                Duration duration = Duration.parse(newTime);
                if (duration.isNegative()) {
                    throw new RestaurantException("Zadali jste zápornou hodnotu jídla " + duration + ".");
                }
                dish.setPreparationTime(duration);
            } catch (DateTimeParseException e) {
                throw new RestaurantException(
                        "Nezadali jste hodnotu, která by byla převeditelná na časový úsek. Zadali jste" +newTime+ "hodnotu, tu nelze převést!" + e.getLocalizedMessage() + ".");
            }
        }
    }

    public void editImage(Dish dish) {
        System.out.println("Přejete si editovat umístění obrázku? \nNapiš Y pro ano a N pro ne\n");
        String editImage = scanner.nextLine();
        if (wantToEdit(editImage)) {
            System.out.println("Zadejte nový odkaz: \n");
            String newImage = scanner.nextLine();
            dish.setImage(newImage);
        }
    }

    public void editDish(String title) throws RestaurantException, NumberFormatException {
        Boolean found = false;
        for (Dish dish : dishStack) {
            if (dish.getTitle().equals(title)) {
                found = true;
                editTitle(dish);
                editPrice(dish);
                editTime(dish);
                editImage(dish);
            }
        }
        if (!found) {
            throw new RestaurantException("Jídlo s názvem " + title + "se nepodařilo najít.");
        }
    }


    public void saveDishStack(String file) throws RestaurantException {
        String delimiter = Settings.getTab();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)))){
            for(Dish dish : dishStack) {
                writer.println(dish.getId() + delimiter
                        + dish.getTitle() + delimiter
                        + dish.getPrice() + delimiter
                        + dish.getPreparationTime() + delimiter
                        + dish.getImage() + delimiter
                );
            }
        }catch (FileNotFoundException e) {
            throw new RestaurantException("Soubor "+file+"nebyl nalezen"+e.getLocalizedMessage()+".");
        }catch (IOException e) {
            throw new RestaurantException("Chyba při načítání souboru "+file+" "+e.getLocalizedMessage()+".");
        }
    }

    public void loadDishStack(String file) throws RestaurantException {
        dishStack.clear();
        int lineCounter = 0;
        try(Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
            while (scanner.hasNextLine()) {
                lineCounter++;
                String lineToSeparate = scanner.nextLine();
                String[] lineField = lineToSeparate.split(Settings.getTab());
                int id = Integer.parseInt(lineField[0]);
                String title = lineField[1];
                BigDecimal price = new BigDecimal(lineField[2]);
                Duration preparationTime = Duration.parse(lineField[3]);
                String image = lineField[4];
                Dish dish = new Dish(id, title, price, preparationTime, image);
                dishStack.add(dish);
                }
            }catch (FileNotFoundException e) {
            System.err.println("Soubor " + file + "nebyl nalezen " + e.getLocalizedMessage() + ".");
        }catch (SecurityException e) {
            throw new RestaurantException("Nemáte dostatečné oprávnění pro přístup!" +e.getLocalizedMessage()+".");
        }
        catch (NoSuchElementException e) {
            throw new RestaurantException("Soubor má neplatný formát nebo strukturu " +e.getLocalizedMessage()+".");
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new RestaurantException("Chyba při zpracování řádku "+lineCounter+ "v souboru" +file+ "." +e.getLocalizedMessage());
        }
    }

    public Boolean isDishById(Integer id) {
        for (Dish dish : dishStack) {
            if(dish.getId().equals(id)) {
                return true;
            }
        }
        return false;
        }

    public Dish chosenDish(int id) {
        try {
            for (Dish dish : dishStack) {
                if (dish.getId() == id) {
                    return dish;
                }
            }

        } catch (NullPointerException e) {
            System.out.println("Jídlo s Id" + id + "nebylo nalezen!");
        }
        System.out.println("Nebylo nalezeno žádné jídlo");
        return null;
    }
}