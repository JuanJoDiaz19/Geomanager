package ui;

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
        int option;
        do {
            System.out.println("Select one of the following options:\n"+
                    "1. Insert command\n" +
                    "2. Import data from .SQL file\n" +
                    "3. Leave\n");
            option = Integer.parseInt(sc.nextLine());
            executeOption(option);
        } while (option != 3);
    }

    public void executeOption(int option) {
        switch (option){
            case 1:
                System.out.println("Enter the command: ");
                String command = sc.nextLine();
                geograficControler.processCommand(command);
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

}
