package exceptions;

public class CountryNotFoundException extends Exception{
    public CountryNotFoundException() {
        super("The ID of the country entered wasn't found");
    }
}
