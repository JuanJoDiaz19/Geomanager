package exceptions;

public class AlreadyExists extends Exception{
    public AlreadyExists() {
        super("The location you are trying to add already exists");
    }
}
