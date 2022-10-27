package model;

import exceptions.AlreadyExists;
import exceptions.CountryNotFoundException;
import exceptions.WrongFormatParameterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeograficControlerTest {
    public GeograficControler geograficControler;

    public void setUp1() {
        geograficControler = new GeograficControler();
        try {
            geograficControler.selectionOfCommand("INSERT INTO countries(id, name, population, countryCode) VALUES ('6ec3e8ec-3dd0-11ed-b878-0242ac120002', 'Colombia', 50.2, '+57')");
            geograficControler.selectionOfCommand("INSERT INTO cities(id, name, countryID, population) VALUES ('edaa04t6-3dd0-11ed-b878-0242ac120002', 'Cali', '6ec3e8ec-3dd0-11ed-b878-0242ac120002', 2.2)");
        } catch (CountryNotFoundException | WrongFormatParameterException | AlreadyExists e) {
            e.printStackTrace();
        }
    }

    public void setUp2() {
        geograficControler = new GeograficControler();
        try {
            geograficControler.selectionOfCommand("INSERT INTO countries(id, name, population, countryCode) VALUES ('6ec3e8ec-3dd0-11ed-b878-0242ac120002', 'Colombia', 50.2, '+57')");
            geograficControler.selectionOfCommand("INSERT INTO countries(id, name, population, countryCode) VALUES ('71615790-3dd2-11ed-b878-0242ac120002', 'USA', 329.5, '+1')");
            geograficControler.selectionOfCommand("INSERT INTO countries(id, name, population, countryCode) VALUES ('83b3e642-3dd2-11ed-b878-0242ac120002', 'Mexico', 128.9, '+52')");
            geograficControler.selectionOfCommand("INSERT INTO cities(id, name, countryID, population) VALUES ('edaa04t6-3dd0-11ed-b878-0242ac120002', 'Cali', '6ec3e8ec-3dd0-11ed-b878-0242ac120002', 2.2)");
            geograficControler.selectionOfCommand("INSERT INTO cities(id, name, countryID, population) VALUES ('etra04t6-3dd0-11ed-b878-0242ac120002', 'Bogota', '6ec3e8ec-3dd0-11ed-b878-0242ac120002', 7.3)");
            geograficControler.selectionOfCommand("INSERT INTO cities(id, name, countryID, population) VALUES ('ernesto6-3dd0-11ed-b878-0242ac120002', 'Medellin', '6ec3e8ec-3dd0-11ed-b878-0242ac120002', 3.5)");

        } catch (CountryNotFoundException | WrongFormatParameterException | AlreadyExists e) {
            e.printStackTrace();
        }
    }

    public void setUp3() {
        geograficControler = new GeograficControler();
        try {
            geograficControler.selectionOfCommand("INSERT INTO countries(id, name, population, countryCode) VALUES ('6ec3y7yu-3dd0-11ed-b878-0242ac120002', 'Venezuela', 50.2, '+57')");
            geograficControler.selectionOfCommand("INSERT INTO countries(id, name, population, countryCode) VALUES ('6ec3e8ec-3dd0-11ed-b878-0242ac120002', 'Colombia', 50.2, '+57')");
            geograficControler.selectionOfCommand("INSERT INTO cities(id, name, countryID, population) VALUES ('edaa04t6-3dd0-11ed-b878-0242ac120002', 'Cali', '6ec3e8ec-3dd0-11ed-b878-0242ac120002', 2.2)");
            geograficControler.selectionOfCommand("INSERT INTO cities(id, name, countryID, population) VALUES ('etra04t6-3dd0-11ed-b878-0242ac120002', 'Bogota', '6ec3e8ec-3dd0-11ed-b878-0242ac120002', 7.3)");
            geograficControler.selectionOfCommand("INSERT INTO cities(id, name, countryID, population) VALUES ('ernesto6-3dd0-11ed-b878-0242ac120002', 'Medellin', '6ec3e8ec-3dd0-11ed-b878-0242ac120002', 3.5)");
        } catch (CountryNotFoundException | WrongFormatParameterException | AlreadyExists e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addCountryTest() {
        setUp1();
        assertEquals(1, geograficControler.getCountries().size());
        assertEquals("6ec3e8ec-3dd0-11ed-b878-0242ac120002", geograficControler.getCountries().get(0).getId());
        assertEquals("Colombia", geograficControler.getCountries().get(0).getName());
        assertEquals(50.2, geograficControler.getCountries().get(0).getPopulation());
        assertEquals("+57", geograficControler.getCountries().get(0).getCountryCode());

    }
    @Test
    public void addCityTest() {
        setUp1();
        assertEquals(1, geograficControler.getCities().size());
        assertEquals("edaa04t6-3dd0-11ed-b878-0242ac120002", geograficControler.getCities().get(0).getId());
        assertEquals("Cali", geograficControler.getCities().get(0).getName());
        assertEquals("6ec3e8ec-3dd0-11ed-b878-0242ac120002", geograficControler.getCities().get(0).getCountryID());
        assertEquals(2.2, geograficControler.getCities().get(0).getPopulation());
    }
    @Test
    public void repeatedCountryID() {
        setUp1();
        boolean alreadyExists = false;
        try {
            geograficControler.selectionOfCommand("INSERT INTO countries(id, name, population, countryCode) VALUES ('6ec3e8ec-3dd0-11ed-b878-0242ac120002', 'Francia', 67.5, '+33')");
        } catch (CountryNotFoundException e) {
            e.printStackTrace();
        } catch (WrongFormatParameterException e) {
            e.printStackTrace();
        } catch (AlreadyExists e) {
            alreadyExists = true;
            e.printStackTrace();
        }
        //We make sure that the program showed the exception WrongFormatException
        assertTrue(alreadyExists);
    }
    @Test
    public void countryIdNotFoundInCityTest() {
        setUp1();
        boolean countryIdNotFound = false;
        try {
            geograficControler.selectionOfCommand("INSERT INTO cities(id, name, countryID, population) VALUES ('91346eb8-3dd2-11ed-b878-0242ac120002', 'Barranquilla', '83b3e642-3dd2-11ed-b878-0242ac120002', 2.5)");
        } catch (CountryNotFoundException e) {
            countryIdNotFound = true;
            e.printStackTrace();
        } catch (WrongFormatParameterException e) {
            e.printStackTrace();
        } catch (AlreadyExists e) {
            e.printStackTrace();
        }
        //We make sure that the exception countryIdNotFound appeared.
        assertTrue(countryIdNotFound);
    }
    @Test
    public void showAllCitiesInCountry(){
        setUp2();
        try {
            String ans = "***** City *****\n" +
                    "City ID: edaa04t6-3dd0-11ed-b878-0242ac120002\n" +
                    "City Name: Cali\n" +
                    "Country ID: 6ec3e8ec-3dd0-11ed-b878-0242ac120002\n" +
                    "City population: 2.2\n" +
                    "***** City *****\n" +
                    "City ID: etra04t6-3dd0-11ed-b878-0242ac120002\n" +
                    "City Name: Bogota\n" +
                    "Country ID: 6ec3e8ec-3dd0-11ed-b878-0242ac120002\n" +
                    "City population: 7.3\n" +
                    "***** City *****\n" +
                    "City ID: ernesto6-3dd0-11ed-b878-0242ac120002\n" +
                    "City Name: Medellin\n" +
                    "Country ID: 6ec3e8ec-3dd0-11ed-b878-0242ac120002\n" +
                    "City population: 3.5\n";
            assertEquals( ans, geograficControler.selectionOfCommand("SELECT * FROM cities WHERE countryID = '6ec3e8ec-3dd0-11ed-b878-0242ac120002'") );
        } catch (CountryNotFoundException | WrongFormatParameterException | AlreadyExists e) {
            e.printStackTrace();
        }
    }
    @Test
    public void showAllCitiesInCountryOrderedByName() {
        setUp2();
        try {
            String s = "***** City *****\n" +
                    "City ID: etra04t6-3dd0-11ed-b878-0242ac120002\n" +
                    "City Name: Bogota\n" +
                    "Country ID: 6ec3e8ec-3dd0-11ed-b878-0242ac120002\n" +
                    "City population: 7.3\n" +
                    "***** City *****\n" +
                    "City ID: edaa04t6-3dd0-11ed-b878-0242ac120002\n" +
                    "City Name: Cali\n" +
                    "Country ID: 6ec3e8ec-3dd0-11ed-b878-0242ac120002\n" +
                    "City population: 2.2\n" +
                    "***** City *****\n" +
                    "City ID: ernesto6-3dd0-11ed-b878-0242ac120002\n" +
                    "City Name: Medellin\n" +
                    "Country ID: 6ec3e8ec-3dd0-11ed-b878-0242ac120002\n" +
                    "City population: 3.5\n";
            assertEquals(s, geograficControler.selectionOfCommand("SELECT * FROM cities WHERE countryID = '6ec3e8ec-3dd0-11ed-b878-0242ac120002' ORDER BY name"));
        } catch (CountryNotFoundException | WrongFormatParameterException | AlreadyExists e) {
            e.printStackTrace();
        }
    }
    @Test
    public void selectCountryByPopulation(){
        setUp2();
        try {
            String s = "***** Country *****\n" +
                    "Country ID: 71615790-3dd2-11ed-b878-0242ac120002\n" +
                    "Country Name: USA\n" +
                    "Country population: 329.5\n" +
                    "Country code: +1\n" +
                    "***** Country *****\n" +
                    "Country ID: 83b3e642-3dd2-11ed-b878-0242ac120002\n" +
                    "Country Name: Mexico\n" +
                    "Country population: 128.9\n" +
                    "Country code: +52\n";

            assertEquals(s, geograficControler.selectionOfCommand("SELECT * FROM countries WHERE population > 100"));
        } catch (CountryNotFoundException | WrongFormatParameterException | AlreadyExists e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void deleteByIdTest() {
        setUp3();
        try{
            geograficControler.selectionOfCommand("DELETE FROM countries WHERE id = '6ec3y7yu-3dd0-11ed-b878-0242ac120002'");
            assertEquals(1, geograficControler.getCountries().size());
            assertEquals("6ec3e8ec-3dd0-11ed-b878-0242ac120002", geograficControler.getCountries().get(0).getId());
            assertEquals("Colombia", geograficControler.getCountries().get(0).getName());
            assertEquals(50.2, geograficControler.getCountries().get(0).getPopulation());
            assertEquals("+57", geograficControler.getCountries().get(0).getCountryCode());
        }catch (CountryNotFoundException | WrongFormatParameterException | AlreadyExists e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void deleteCitiesByIdCountryTest(){
        setUp3();
        try {
            geograficControler.selectionOfCommand("DELETE FROM cities WHERE countryID = '6ec3e8ec-3dd0-11ed-b878-0242ac120002'");
            assertEquals(0, geograficControler.getCities().size());
        } catch (CountryNotFoundException | WrongFormatParameterException | AlreadyExists e) {
            throw new RuntimeException(e);
        }

    }

}