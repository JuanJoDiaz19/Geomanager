package model;

import exceptions.CountryNotFoundException;
import exceptions.WrongFormatParameterException;

import java.util.ArrayList;
import java.util.Arrays;

public class GeograficControler {
    private ArrayList<Country> countries;
    private ArrayList<City> cities;

    public GeograficControler() {
        countries = new ArrayList<>();
        cities = new ArrayList<>();
    }

    public void addData(String command) throws WrongFormatParameterException, CountryNotFoundException {
        String[] arrCommand = command.split(" VALUES ");
        boolean flag = true;
        if(arrCommand[0].equals("INSERT INTO countries(id, name, population, countryCode)")){
            String values = arrCommand[1].replace(" " ,"");
            if(!(values.charAt(0) == '(' && values.charAt(values.length()-1) == ')')) flag = false;
            values = values.replace("(", "");
            values = values.replace(")", "");
            String[] parameters = values.split(",");
            if(!(parameters[0].charAt(0) == '\'' && parameters[0].charAt(parameters[0].length() -1) == '\'') ) flag = false;
            if(!(parameters[1].charAt(0) == '\'' && parameters[1].charAt(parameters[1].length() -1) == '\'') ) flag = false;
            if(!(parameters[3].charAt(0) == '\'' && parameters[3].charAt(parameters[3].length() -1) == '\'') ) flag = false;
            try {
                Double.parseDouble(parameters[2]);
            } catch (NumberFormatException ex){
                flag = false;
            }
            if (!flag) throw new WrongFormatParameterException();
            values = values.replace("'","");
            String[] finalParameters = values.split(",");
            countries.add(new Country(finalParameters[0], finalParameters[1], Double.parseDouble(finalParameters[2]), finalParameters[3]));
            System.out.println("The country was added :)");
        } else if ( arrCommand[0].equals("INSERT INTO cities(id, name, countryID, population)")) {
            String values = arrCommand[1].replace(" " ,"");
            if(!(values.charAt(0) == '(' && values.charAt(values.length()-1) == ')')) flag = false;
            values = values.replace("(", "");
            values = values.replace(")", "");
            String[] parameters = values.split(",");
            if(!(parameters[0].charAt(0) == '\'' && parameters[0].charAt(parameters[0].length() -1) == '\'') ) flag = false;
            if(!(parameters[1].charAt(0) == '\'' && parameters[1].charAt(parameters[1].length() -1) == '\'') ) flag = false;
            if(!(parameters[2].charAt(0) == '\'' && parameters[2].charAt(parameters[3].length() -1) == '\'') ) flag = false;
            try {
                Integer.parseInt(parameters[3]);
            } catch (NumberFormatException ex){
                flag = false;
            }
            if (!flag) throw new WrongFormatParameterException();
            values = values.replace("'","");
            String[] finalParameters = values.split(",");
            if (searchCountryByID(finalParameters[2])){
                cities.add(new City(finalParameters[0], finalParameters[1], finalParameters[2], Integer.parseInt(finalParameters[3])));
                System.out.println("The country was added :)");
            } else {
                throw new CountryNotFoundException();
            }
        }
    }

    public void searchData(String command) {

    }

    public void orderData(String command) {

    }
    public void deleteInformation(String command) {

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
