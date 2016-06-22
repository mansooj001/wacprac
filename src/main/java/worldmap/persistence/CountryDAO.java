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
        try {
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
        } catch (SQLException e) {
            e.getMessage();
        }
        return null;
    }

    public ArrayList<Country> findAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        try {
            return resultSetToCountry(query);
        } catch (SQLException e) {
            System.out.println("Iets is misgegaan bij findAll()");
            e.getMessage();
        }
        System.out.println("findAll() ging mis");
        return null;
    }

    public Country findByCode(String countryCode) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE code = '" + countryCode + "'";
        try {
            return resultSetToCountry(query).get(0);
        } catch (SQLException e) {
            e.getMessage();
        }
        System.out.println("findByCode() ging mis");
        return null;
    }

    public ArrayList<Country> find10LargestPopulations() {
        final String query = "SELECT * FROM country " +
                "ORDER BY Population DESC " +
                "LIMIT 0, 10";
        try {
            return resultSetToCountry(query);
        } catch (SQLException e) {
            e.getMessage();
        }
        System.out.println("findlargestpopulation() ging mis");
        return null;
    }

    public ArrayList<Country> find10LargestSurfaces() {
        final String query = "SELECT * FROM country " +
                "ORDER BY SurfaceArea DESC " +
                "LIMIT 0, 10";
        try {
            return resultSetToCountry(query);
        } catch (SQLException e) {
            e.getMessage();
        }
        System.out.println("findlargestsurface() ging mis");
        return null;
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

    private ArrayList<Country> resultSetToCountry(String query) throws SQLException {
        ArrayList<Country> result = new ArrayList<>();
        try (Connection connection = getConnection()) {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            Country newCountry = null;
            while (rs.next()) {
                String code = rs.getString("code");
                String name = rs.getString("name");
                String continent = rs.getString("continent");
                double surfaceArea = rs.getDouble("surfacearea");
                String region = rs.getString("region");
                double indepYear = rs.getDouble("IndepYear");
                int population = rs.getInt("population");
                String governmentForm = rs.getString("GovernmentForm");
                if (indepYear > 0) {
                    newCountry = new Country(code, name, region, continent, surfaceArea, population, governmentForm);
                    result.add(newCountry);
                } else {
                    newCountry = new Country(code, name, region, continent, surfaceArea, indepYear, population, governmentForm);
                    result.add(newCountry);
                }
            }
            rs.close();
            return result;
        }
    }
}
