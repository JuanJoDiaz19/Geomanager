package model;

public class Country extends Location {
    private Double population;
    private String countryCode;

    public Country(String id, String name, Double population, String countryCode) {
        super(id, name);
        this.population = population;
        this.countryCode = countryCode;
    }

    public Double getPopulation() {
        return population;
    }

    public void setPopulation(Double population) {
        this.population = population;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
