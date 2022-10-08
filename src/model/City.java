package model;

public class City extends Location {
    private String countryID;
    private Integer population;

    public City(String id, String name, String countryID, Integer population) {
        super(id, name);
        this.countryID = countryID;
        this.population = population;
    }

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }
}
