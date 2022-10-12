package ui;

import exceptions.CountryNotFoundException;
import exceptions.WrongFormatParameterException;
import model.GeograficControler;

import java.util.Scanner;

public class Main {
    private Scanner sc;
    private GeograficControler geograficControler;

    public static void main(String[] args) {
        Main main = new Main();
        main.execute();
    }

    public void execute() {
        geograficControler = new GeograficControler();
        sc = new Scanner(System.in);
        System.out.println("==============================="+
                            "\n    WELCOME TO GEOMANAGER    "+
                            "\n===============================\n");
        int option = 0;
        do {
            System.out.println("Select one of the following options:\n"+
                    "1. Insert command\n" +
                    "2. Import data from .SQL file\n" +
                    "3. Leave\n");
            try {
                option = Integer.parseInt(sc.nextLine());
                executeOption(option);
            }catch (NumberFormatException ex){
                ex.printStackTrace();
            }
        } while (option != 3);
    }

    public void executeOption(int option) {
        switch (option){
            case 1:
                selectionOfCommand();
                break;
            case 2:
                geograficControler.importSQLFile();
                break;
            case 3:
                System.out.println("Bye :)");
                break;
            default:
                System.out.println("Please chose an available option :)");
                break;
        }
    }

    public void selectionOfCommand() {
        System.out.println("Enter the command: ");
        String s = sc.nextLine();
        String[] split = s.split(" ");
        if(split[0].equals("INSERT")){
            try {
                geograficControler.addData(s);
            } catch (WrongFormatParameterException | CountryNotFoundException ex) {
                ex.printStackTrace();
            }
        } else if (split[0].equals("SELECT")) {
            try {
                if (s.contains("ORDER BY")) {
                    geograficControler.orderData(s);
                } else {
                    geograficControler.searchData(s);
                }
            }catch (WrongFormatParameterException ex){ex.printStackTrace();}
        } else if(split[0].equals("DELETE")){
            geograficControler.deleteInformation(s);
        }else{
            System.out.println("Please enter a valid command");
        }
    }

}
