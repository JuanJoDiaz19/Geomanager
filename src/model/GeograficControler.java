package model;

import exceptions.CountryNotFoundException;
import exceptions.WrongFormatParameterException;

import java.util.ArrayList;

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
            if(isStringFormat(parameters[0]) || isStringFormat(parameters[1]) || isStringFormat(parameters[3]) || !isNumber(parameters[2])) throw new WrongFormatParameterException();
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
            if(isStringFormat(parameters[0]) || isStringFormat(parameters[1]) || isStringFormat(parameters[2]) || !isNumber(parameters[3])) throw new WrongFormatParameterException();
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

    public ArrayList<Location> searchData(String command) throws WrongFormatParameterException {
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
                            if (iniCommands[3].equals("countries") && (iniCommands[5].equals("id") || iniCommands[5].equals("name") || iniCommands[5].equals("population") || iniCommands[5].equals("countryCode"))) {
                                //metodo de busqueda por string
                                return;
                            } else if (iniCommands[3].equals("cities") && (iniCommands[5].equals("id") || iniCommands[5].equals("name") || iniCommands[5].equals("countryID") || iniCommands[5].equals("population")))) {
                                //metodo de busqueda por string
                                return;
                            }
                        }
                    }else { //Cuando es un '<' o '>' se compara a un valor entero

                        if(isNumber(iniCommands[7])){
                            if (iniCommands[3].equals("countries")) {
                                //Metodo de busqueda por numero
                                return;
                            } else if (iniCommands[3].equals("cities")) {
                                //Metodo de busqueda por numero
                                return;
                            }
                        }

                    }
                }
            }else if(iniCommands.length==4){
                //Que muestretodo de un pais o ciudad
                if(iniCommands[3].equals("countries")){
                    //metodo de busqueda por descarte xd
                    return;
                } else if (iniCommands[3].equals("cities")) {
                    //metodo de busqueda por descarte xd
                    return;
                }
            }
        }
        throw new WrongFormatParameterException();
    }
    public ArrayList<Location> searchingDate(int comparator, String searchBy, boolean isCountry, String ){
        ArrayList<Location> tem = new ArrayList<>();
        if(isCountry) {
            if(searchBy.equals("id")) {
                for (Country c: countries){
                    if(c.getId().equals("")){

                    }
                }
            }
        }
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
