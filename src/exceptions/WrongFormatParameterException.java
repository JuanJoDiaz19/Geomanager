package exceptions;

public class WrongFormatParameterException extends Exception{
    public WrongFormatParameterException() {
        super("There's a wrong format on the parameter ");
    }
}
