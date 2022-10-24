package model;

import com.google.gson.Gson;
import exceptions.AlreadyExists;
import exceptions.CountryNotFoundException;
import exceptions.WrongFormatParameterException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class GeograficControler implements Comparable<Location>{
    private ArrayList<Country> countries;
    private ArrayList<City> cities;

    public GeograficControler() {
        countries = new ArrayList<>();
        cities = new ArrayList<>();
        loadData();
    }

    public void selectionOfCommand(String s) {
        String[] split = s.split(" ");
        if (split[0].equals("INSERT")) {
            try {
                addData(s);
            } catch (WrongFormatParameterException | CountryNotFoundException | AlreadyExists ex) {
                ex.printStackTrace();
            }
        } else if (split[0].equals("SELECT")) {
            try {
                if (s.contains("ORDER BY")) {
                    orderData(s);
                } else {
                    searchingData(s);
                }
            } catch (WrongFormatParameterException ex) {
                ex.printStackTrace();
            }
        } else if (split[0].equals("DELETE")) {
            try {
                deleteInformation(s);
            } catch (WrongFormatParameterException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("\nPlease enter a valid command");
        }
    }
    public void addData(String command) throws WrongFormatParameterException, CountryNotFoundException, AlreadyExists {
        String[] arrCommand = command.split(" VALUES ");
        if(arrCommand[0].equals("INSERT INTO countries(id, name, population, countryCode)")){
            String values = arrCommand[1].replace(" " ,"");
            if(isStringFormatPar(values)) {
                values = values.replace("(", "");
                values = values.replace(")", "");
                String[] parameters = values.split(",");
                if (isStringFormat(parameters[0]) || isStringFormat(parameters[1]) || isStringFormat(parameters[3]) || isDoubleNumber(parameters[2])){
                    values = values.replace("'", "");
                    String[] finalParameters = values.split(",");
                    if(!searchCountryByID(finalParameters[0])) {
                        countries.add(new Country(finalParameters[0], finalParameters[1], Double.parseDouble(finalParameters[2]), finalParameters[3]));
                        System.out.println("The country was added :)");
                        return;
                    }else {
                        throw new AlreadyExists();
                    }
                }
            }
            throw new WrongFormatParameterException();
        } else if ( arrCommand[0].equals("INSERT INTO cities(id, name, countryID, population)")) {
            String values = arrCommand[1].replace(" " ,"");
            if(isStringFormatPar(values)) {
                values = values.replace("(", "");
                values = values.replace(")", "");
                String[] parameters = values.split(",");
                if (isStringFormat(parameters[0]) || isStringFormat(parameters[1]) || isStringFormat(parameters[2]) || isDoubleNumber(parameters[3])){
                    values = values.replace("'", "");
                    String[] finalParameters = values.split(",");
                    if(!searchCityByID(finalParameters[0])){
                        if (searchCountryByID(finalParameters[2])) {
                            cities.add(new City(finalParameters[0], finalParameters[1], finalParameters[2], Double.parseDouble(finalParameters[3])));
                            System.out.println("The city was added :)");
                            return;
                        } else {
                            throw new CountryNotFoundException();
                        }
                    }else {
                        throw new AlreadyExists();
                    }
                }
            }
            throw new WrongFormatParameterException();
        }
    }
    public void orderData(String command) throws WrongFormatParameterException {
        String [] commands = command.split(" ORDER BY ");
        ArrayList<Location> tem = searchData(commands[0]);
        if(commands[0].contains("countries") && ( commands[1].equals("id") || commands[1].equals("name") || commands[1].equals("population") || commands[1].equals("countryCode"))){
            ArrayList<Country> country = new ArrayList<>();
            for (Location l :tem){
                country.add((Country)l);
            }
            country=orderingDataCountry(country,commands[1]);
            tem.clear();
            tem.addAll(country);
            printLocation(tem);
            return;
        }else if (commands[0].contains("cities") && ( commands[1].equals("id") || commands[1].equals("name") || commands[1].equals("countryID") || commands[1].equals("population"))){
            ArrayList<City> cities = new ArrayList<>();
            for (Location l :tem){
                cities.add((City)l);
            }
            cities=orderingDataCity(cities,commands[1]);
            tem.clear();
            tem.addAll(cities);
            printLocation(tem);
            return;
        }
        throw new WrongFormatParameterException();
    }
    public ArrayList<Country> orderingDataCountry(ArrayList<Country> arr, String parameter){
        if(parameter.equals("id")) {
            Collections.sort(arr, Comparator.comparing(Location::getId));
        } else if(parameter.equals("name")) {
            Collections.sort(arr, Comparator.comparing(Location::getName));
        } else if(parameter.equals("population")) {
            Collections.sort(arr, Comparator.comparing(Country::getPopulation));
        }else if(parameter.equals("countryCode")) {
            Collections.sort(arr, Comparator.comparing(Country::getCountryCode));
        }
        return arr;
    }
    public ArrayList<City> orderingDataCity(ArrayList<City> arr, String parameter){
        if(parameter.equals("id")) {
            Collections.sort(arr, Comparator.comparing(Location::getId));
        } else if(parameter.equals("name")) {
            Collections.sort(arr, Comparator.comparing(Location::getName));
        } else if(parameter.equals("population")) {
            Collections.sort(arr, Comparator.comparing(City::getCountryID));
        }else if(parameter.equals("countryCode")) {
            Collections.sort(arr, Comparator.comparing(City::getPopulation));
        }
        return arr;
    }
    public void searchingData(String command) throws WrongFormatParameterException {
        ArrayList<Location> tem;
        tem = searchData(command);
        printLocation(tem);
    }
    public void printLocation(ArrayList<Location> tem){
        if(tem.size()!=0) {
            for (Location l : tem) {
                System.out.println(l.print());
            }
        }else {
            System.out.println("No locations have been found that meet these conditions");
        }
    }
    public ArrayList<Location> searchData(String command) throws WrongFormatParameterException {
        //System.out.println(command);
        String[] iniCommands = command.split(" ");
        if(iniCommands[1].equals("*") && iniCommands[2].equals("FROM")){
            if(iniCommands.length==8){
                //Que de un pais/ciudad en especifico
                boolean flag1=iniCommands[6].equals(">") || iniCommands[6].equals("<");
                boolean flag2=iniCommands[6].equals("=");
                if(iniCommands[4].equals("WHERE") && flag1||flag2){
                    if(flag2 ){ //Cuando es un 'igual' se compara a un pais (String)
                        if(isStringFormat(iniCommands[7])){
                            iniCommands[7]=iniCommands[7].replace("'","");
                            if (iniCommands[3].equals("countries") && (iniCommands[5].equals("id") || iniCommands[5].equals("name") || iniCommands[5].equals("population") || iniCommands[5].equals("countryCode")) ) {
                                //metodo de busqueda por string
                                return searchingDataEquals(iniCommands[5],true,iniCommands[7]);
                            } else if (iniCommands[3].equals("cities") && (iniCommands[5].equals("id") || iniCommands[5].equals("name") || iniCommands[5].equals("countryID") || iniCommands[5].equals("population"))) {
                                //metodo de busqueda por string
                                return searchingDataEquals(iniCommands[5],false,iniCommands[7]);
                            }
                        }
                    }else { //Cuando es un '<' o '>' se compara a un valor entero

                        if(isNumber(iniCommands[7])){
                            int number = Integer.parseInt(iniCommands[7]);
                            if (iniCommands[3].equals("countries") && iniCommands[5].equals("population")) {
                                //Metodo de busqueda por numero
                                if(iniCommands[6].equals(">")){
                                    return searchingDataInequality(number,true,true);
                                }else {
                                    return searchingDataInequality(number,true,false);
                                }
                            } else if (iniCommands[3].equals("cities") && iniCommands[5].equals("population") ) {
                                //Metodo de busqueda por numero
                                if(iniCommands[6].equals(">")){
                                    return searchingDataInequality(number,false,true);
                                }else {
                                    return searchingDataInequality(number,false,false);
                                }
                            }
                        }

                    }
                }
            }else if(iniCommands.length==4){
                //Que muestra todo de un pais o ciudad
                if(iniCommands[3].equals("countries")){
                    return searchingDataAll(true);
                } else if (iniCommands[3].equals("cities")) {
                    return searchingDataAll(false);
                }
            }
        }
        throw new WrongFormatParameterException();
    }
    public ArrayList<Location> searchingDataAll(boolean isCountry){
    ArrayList<Location> tem = new ArrayList<>();
    if(isCountry){
        tem.addAll(countries);
    }else {
        tem.addAll(cities);
    }
    return tem;
    }
    public ArrayList<Location> searchingDataInequality(int searching, boolean isCountry, boolean isGreater){
        ArrayList<Location> tem = new ArrayList<>();
        if(isCountry) {
            if(isGreater){
                for (Country c: countries){
                    if(c.getPopulation()>searching){
                        tem.add(c);
                    }
                }
            }else {
                for (Country c: countries){
                    if(c.getPopulation()<searching){
                        tem.add(c);
                    }
                }
            }
        } else {
            if(isGreater){
                for (City c: cities){
                    if(c.getPopulation()>searching){
                        tem.add(c);
                    }
                }
            }else {
                for (City c: cities){
                    if(c.getPopulation()<searching){
                        tem.add(c);
                    }
                }
            }
        }
        return tem;
    }
    public ArrayList<Location> searchingDataEquals(String searchBy,boolean isCountry,String searching){
        ArrayList<Location> tem = new ArrayList<>();
        if(isCountry) {
            if(searchBy.equals("id")) {
                for (Country c: countries){
                    if(c.getId().equals(searching)){
                        tem.add(c);
                    }
                }
            } else if (searchBy.equals("name")) {
                for (Country c: countries){
                    if(c.getName().equals(searching)){
                        tem.add(c);
                    }
                }
            }
            else if (searchBy.equals("population")) {
                isNumber(searching);
                int searched = Integer.parseInt(searching);
                for (Country c: countries){
                    if(c.getPopulation()==searched){
                        tem.add(c);
                    }
                }
            }
            else if (searchBy.equals("countryCode")) {
                for (Country c: countries){
                    if(c.getCountryCode().equals(searching)){
                        tem.add(c);
                    }
                }
            }
        } else {
            if(searchBy.equals("id")) {
                for (City c: cities){
                    if(c.getId().equals(searching)){
                        tem.add(c);
                    }
                }
            } else if (searchBy.equals("name")) {
                for (City c: cities){
                    if(c.getName().equals(searching)){
                        tem.add(c);
                    }
                }
            }
            else if (searchBy.equals("population")) {
                isNumber(searching);
                int searched = Integer.parseInt(searching);
                for (City c: cities){
                    if(c.getPopulation()==searched){
                        tem.add(c);
                    }
                }
            }
            else if (searchBy.equals("countryID")) {
                for (City c: cities){
                    if(c.getCountryID().equals(searching)){
                        tem.add(c);
                    }
                }
            }
        }
        return tem;
    }
    public boolean isStringFormatPar(String string){
        return string.charAt(0) == '(' && string.charAt(string.length()-1) == ')';
    }
    public boolean isStringFormat(String string){
        return string.charAt(0) == '\'' && string.charAt(string.length() -1) == '\'';
    }

    public boolean isNumber(String number){
        try {
            Integer.parseInt(number);
        }catch (NumberFormatException ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean isDoubleNumber(String number){
        try {
            Double.parseDouble(number);
        }catch (NumberFormatException ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    public void deleteInformation(String command) throws WrongFormatParameterException {
        ArrayList<Location> tem;
        String out = command.replace("DELETE ", "SELECT * ");
        tem=searchData(out);
        if(command.contains("countries")){
            ArrayList<Country> country = new ArrayList<>();
            for (Location l :tem){
                country.add((Country)l);
            }
            countries.removeAll(country);
            return;
        } else if (command.contains("cities")) {
            ArrayList<City> city = new ArrayList<>();
            for (Location l :tem){
                city.add((City)l);
            }
            cities.removeAll(city);
            return;
        }
        throw new WrongFormatParameterException();
    }
    public boolean searchCityByID(String id) {
        for (City c: cities) {
            if(c.getId().equals(id)) return true;
        }
        return false;
    }
    public boolean searchCountryByID(String id) {
        for (Country c: countries) {
            if(c.getId().equals(id)) return true;
        }
        return false;
    }
    @Override
    public int compareTo(Location o) {
        return 0;
    }
    public void saveData() {
        Gson gsonCountries = new Gson();
        String jsonCountries = gsonCountries.toJson(countries);
        try {
            FileOutputStream fos = new FileOutputStream(new File("DataBaseCountries.json"));
            fos.write(jsonCountries.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gsonCities = new Gson();
        String jsonCities = gsonCities.toJson(countries);
        //System.out.println(json);
        try {
            FileOutputStream fos = new FileOutputStream(new File("DataBaseCities.json"));
            fos.write(jsonCities.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        try {
            File file = new File("DataBaseCountries.json");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String json = "";
            String line;
            while ((line = reader.readLine()) != null) {
                json += line;
            }
            if(!json.equals("")) {
                Gson gson = new Gson();
                Country[] count = gson.fromJson(json, Country[].class);
                countries.addAll(Arrays.asList(count));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            File file = new File("DataBaseCities.json");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String json = "";
            String line;
            while ((line = reader.readLine()) != null) {
                json += line;
            }
            if(!json.equals("")) {
                Gson gson = new Gson();
                City[] cit = gson.fromJson(json, City[].class);
                cities.addAll(Arrays.asList(cit));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importSQLFile(String path) {
        File file = new File(path);
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("\n********** Executing command **********\n" + line);
                selectionOfCommand(line);

            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
