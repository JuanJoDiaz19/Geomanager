package ui;

import exceptions.AlreadyExists;
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
        System.out.println("===============================" +
                "\n    WELCOME TO GEOMANAGER    " +
                "\n===============================");
        int option = 0;
        do {
            System.out.println("\nSelect one of the following options:\n" +
                    "1. Insert command\n" +
                    "2. Import data from .SQL file\n" +
                    "3. Save data.\n" +
                    "0. Leave\n");
            try {
                option = Integer.parseInt(sc.nextLine());
                executeOption(option);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        } while (option != 0);
    }

    public void executeOption(int option) {
        switch (option) {
            case 1:
                System.out.println("Enter the command: ");
                String s = sc.nextLine();
                try {
                    System.out.println(geograficControler.selectionOfCommand(s));
                } catch (CountryNotFoundException | AlreadyExists | WrongFormatParameterException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                System.out.println("Please enter the path of the file: ");
                String path = sc.nextLine();
                geograficControler.importSQLFile(path);
                break;
            case 3:
                geograficControler.saveData();
                System.out.println("The data has been saved.");
                break;
            case 0:
                System.out.println("Byeeee :)");
                break;
            default:
                System.out.println("Please chose an available option :)");
                break;
        }
    }


}
