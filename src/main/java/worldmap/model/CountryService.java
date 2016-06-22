package worldmap.model;

import worldmap.persistence.CountryDAO;

import java.sql.SQLException;
import java.util.List;

public class CountryService {
    private CountryDAO countryDAO = new CountryDAO();

    CountryService(){
    }

    public List<Country> getAllCountries() {
        return countryDAO.findAll();
    }

    public List<Country> get10LargestPopulations() {
        return countryDAO.find10LargestPopulations();
    }

    public List<Country> get10LargestSurfaces() {
        return countryDAO.find10LargestSurfaces();
    }

    public Country getCountryByCode(String code) {
        return countryDAO.findByCode(code);
    }
}
