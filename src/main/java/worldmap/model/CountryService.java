package worldmap.model;

import worldmap.persistence.CountryDAO;

import java.sql.SQLException;
import java.util.List;

public class CountryService {
    private CountryDAO countryDAO = new CountryDAO();

    public List<Country> getAllCountries() throws SQLException {
        return countryDAO.findAll();
    }

    public List<Country> get10LargestPopulations() throws SQLException {
        return countryDAO.find10LargestPopulations();
    }

    public List<Country> get10LargestSurfaces() throws SQLException {
        return countryDAO.find10LargestSurfaces();
    }

    public Country getCountryByCode(String code) throws SQLException {
        return countryDAO.findByCode(code);
    }
}
