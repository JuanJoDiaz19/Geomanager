package model;

import java.util.ArrayList;
import java.util.Arrays;

public class GeograficControler {
    private ArrayList<Country> countries;
    private ArrayList<City> cities;

    public GeograficControler() {
        countries = new ArrayList<>();
        cities = new ArrayList<>();
    }

    public void processCommand(String command) {
        String[] split = command.split(" ");
        String firstCommand = split[0];
        if (firstCommand.equals("INSERT")) {
            addData(command);
        } else if (firstCommand.equals("SELECT")) {
            boolean flag = false;
            for (String s: split) {
                if(s.equals("ORDER")) {
                    flag = true;
                }
            }
            if(!flag) {
                searchData(split);
            } else {
                orderData(split);
            }
        } else if (firstCommand.equals("DELETE")) {
            deleteInformation(split);
        }
    }

    public void addData(String command){
        String[] arrCommand = command.split(" VALUES ");
        if(arrCommand[0].equals("INSERT INTO countries(id, name, population, countryCode)")){
            String values = arrCommand[1].replace("'" ,"");
            values = values.replace(" ", "");
            values = values.replace("(", "");
            values = values.replace(")", "");
            values = values.replace("'", "");
            String[] parameters = values.split(",");
            countries.add(new Country(parameters[0], parameters[1], Double.parseDouble(parameters[2]), parameters[3]));
            System.out.println("The country was added :)");
        } else if ( arrCommand[0].equals("INSERT INTO cities(id, name, countryID, population)")) {
            String values = arrCommand[1].replace("'" ,"");
            values = values.replace(" ", "");
            values = values.replace("(", "");
            values = values.replace(")", "");
            values = values.replace("'", "");
            String[] parameters = values.split(",");
            if (searchCountryByID(parameters[2])){
                cities.add(new City(parameters[0], parameters[1], parameters[2], Integer.parseInt(parameters[3])));
                System.out.println("The country was added :)");
            }

        }
    }

    public void searchData(String[] command) {

    }

    public void orderData(String[] command) {

    }
    public void deleteInformation(String[] command) {

    }

    public void importSQLFile() {

    }

    public void saveData(){

    }

    public boolean searchCountryByID(String id) {
        for (Country c: countries) {
            if(c.getId().equals(id)) return true;
        }
        return false;
    }
}
