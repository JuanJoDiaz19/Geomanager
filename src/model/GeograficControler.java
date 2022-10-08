package model;

import java.util.ArrayList;

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
            addData(split);
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

    public void addData(String[] command){
        //

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
}
