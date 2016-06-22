package worldmap.persistence;

import worldmap.model.Country;
import worldmap.services.CountryResource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by jason on 6/22/2016.
 */
public class CountryDAO extends BaseDAO {
    private static final String TABLE_NAME = "wacprac.country";
    private static final String INSERT_INTO_COUNTRY = "INSERT INTO " + TABLE_NAME + " (code, name, continent, region, surface, population, government) ";
    private Connection connection;
    private Statement statement;

    public CountryDAO() {
        connection = getConnection();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("countryDAO constructor geeft een error");
            e.getMessage();
        }
    }

    public Country save(Country country) {
        CountryResource countryResource = new CountryResource();
        if (countryResource.getAllCountries().isEmpty() || countryResource.getAllCountries() == null) {
            String query = INSERT_INTO_COUNTRY +
                    "VALUES (" +
                    country.getCode() +
                    ", " +
                    country.getName() +
                    ", " +
                    country.getContinent() +
                    ", " +
                    country.getRegion() +
                    ", " +
                    country.getSurface() +
                    ", " +
                    country.getPopulation() +
                    ", " +
                    country.getGovernment() +
                    ")";
            commitQuery(query);
            return country;
        } else {
            update(country);
        }
        return country;
    }

    public ArrayList<Country> findAll() {
        ArrayList<Country> results = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        try {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String code = rs.getString("Code");
                String name = rs.getString("Name");
                String continent = rs.getString("Continent");
                String region = rs.getString("Region");
                double surfaceArea = rs.getDouble("SurfaceArea");
                double indepYear = rs.getDouble("IndepYear");
                int population = rs.getInt("Population");
                String governmentForm = rs.getString("GovernmentForm");
                if (indepYear > 0) {
                    Country c = new Country(code, name, region, continent, surfaceArea, population, governmentForm);
                    results.add(c);
                } else {
                    Country c = new Country(code, name, region, continent, surfaceArea, indepYear, population, governmentForm);
                    results.add(c);
                }
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Iets is misgegaan bij findAll()");
            e.getMessage();
        }
        return results;
    }

    public Country findByCode(String countryCode) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE code = '" + countryCode + "'";
        Country newCountry = null;
        try {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String code = rs.getString("code");
                String name = rs.getString("name");
                String continent = rs.getString("continent");
                double surfacearea = rs.getDouble("surfacearea");
                String region = rs.getString("region");
                int population = rs.getInt("population");
                String governmentform = rs.getString("governmentform");

                newCountry = new Country(code, name, region, continent, surfacearea, population, governmentform);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("er is iets mis gegaan bij findByCode()");
            e.getMessage();
        }
        return newCountry;
    }

    public ArrayList<Country> find10LargestPopulations() {

        final String query = "SELECT * FROM country " +
                "ORDER BY Population DESC " +
                "LIMIT 0, 10";

        ArrayList<Country> results = new ArrayList<>();

        try {
            resultSetToCountry(query);
        } catch (SQLException e) {
            System.out.println("er is iets mis gegaan bij find10LargestPopulations()");
            e.getMessage();
        }
        return results;
    }

    public ArrayList<Country> find10LargestSurfaces() {
        final String query = "SELECT * FROM country " +
                "ORDER BY SurfaceArea DESC " +
                "LIMIT 0, 10";

        ArrayList<Country> results = new ArrayList<>();

        try {
            resultSetToCountry(query);
        } catch (SQLException e) {
            System.out.println("er is iets mis gegaan bij find10LargestSurfaces()");
            e.getMessage();
        }
        return results;
    }

    private Country update(Country country) {
        String query = "UPDATE " +
                TABLE_NAME +
                " SET name = " +
                country.getName() +
                "AND continent = " +
                country.getContinent() +
                "AND region = " +
                country.getRegion() +
                "AND surface = " +
                country.getSurface() +
                "AND population = " +
                country.getPopulation() +
                "AND government = " +
                country.getGovernment() +
                " WHERE code = " +
                country.getCode();
        commitQuery(query);
        return country;
    }

    public boolean delete(Country country) {
        String query = "DELETE FROM " +
                TABLE_NAME +
                " WHERE code = " +
                country.getCode();

        try {
            statement.executeUpdate(query);
            commitQuery(query);
            return true;
        } catch (SQLException e) {
            System.out.println("er is iets mis gegaan bij delete()");
            e.getMessage();
        }
        return false;
    }

    private void commitQuery(String query) {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.close();
            statement.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    private Country resultSetToCountry(String query) throws SQLException {
        ResultSet rs = statement.executeQuery(query);

        while (rs.next()) {
            String code = rs.getString("code");
            String name = rs.getString("name");
            String continent = rs.getString("continent");
            double surfaceArea = rs.getDouble("surfacearea");
            String region = rs.getString("region");
            int population = rs.getInt("population");
            String governmentForm = rs.getString("GovernmentForm");

            Country c = new Country(code, name, region, continent, surfaceArea, population, governmentForm);
            rs.close();
            return c;
        }
        System.out.println("er is iets mis gegaan bij resultSetToCountry()");
        return null;
    }
}
