package model;

public class City extends Location {
    private String countryID;
    private Double population;

    public City(String id, String name, String countryID, Double population) {
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

    public Double getPopulation() {
        return population;
    }

    public void setPopulation(Double population) {
        this.population = population;
    }
    @Override
    public String print(){
        return super.print()+"City ID:"+getId()+"\n City Name"+getName()+"\n Country ID"+countryID+"\n City population"+population;
    }
}
