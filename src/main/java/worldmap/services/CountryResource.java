/**
 * Created by jason on 6/21/2016.
 */
package worldmap.services;

        import worldmap.model.Country;
        import worldmap.model.CountryService;
        import worldmap.model.ServiceProvider;

        import javax.json.Json;
        import javax.json.JsonArray;
        import javax.json.JsonArrayBuilder;
        import javax.json.JsonObjectBuilder;
        import javax.ws.rs.GET;
        import javax.ws.rs.Path;
        import javax.ws.rs.PathParam;
        import javax.ws.rs.Produces;
        import java.sql.SQLException;
        import java.util.logging.Level;
        import java.util.logging.Logger;

@Path("/countries")
public class CountryResource {
    //private static final Logger logger = Logger.getLogger(CountryResource.class.getName());

    /*
     * 1. Een lijst van alle landen waar informatie van is, TYPE: GET, URI:
     * http://localhost:8080/jouwapp/restservices/countries
     */
    @GET
    @Produces("application/json")
    public String getAllCountries() throws SQLException {
        CountryService service = ServiceProvider.getWorldService();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (Country c : service.getAllCountries()) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            job.add("code", c.getCode());
            job.add("continent", c.getContinent());
            job.add("government", c.getGovernment());
            job.add("name", c.getName());
            job.add("population", c.getPopulation());
            job.add("region", c.getRegion());
            job.add("surface", c.getSurface());
            jab.add(job);
        }
        JsonArray array = jab.build();
        return array.toString();
    }

    /**
     * Return country by landcode, otherwise it'll be an empty array
     * Example:
     * http://localhost:8080/jouwapp/restservices/countries/CHN
     *
     * @param id 3 letter land code: example: "CHN", "NLD", "GBR"
     * @return Array of 1 country Object, else an empty array.
     */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public String getCountry(@PathParam("id") String id) throws SQLException {
        CountryService service = ServiceProvider.getWorldService();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        Country c = service.getCountryByCode(id);
        JsonObjectBuilder job = Json.createObjectBuilder();

        try {
            job.add("code", c.getCode());
            job.add("continent", c.getContinent());
            job.add("government", c.getGovernment());
            job.add("name", c.getName());
            job.add("population", c.getPopulation());
            job.add("region", c.getRegion());
            job.add("surface", c.getSurface());
            jab.add(job);
        } catch (NullPointerException e) {
            System.out.println("Iets is leeg bij getCountry()");
            e.getMessage();
        }

        JsonArray array = jab.build();
        return array.toString();
    }

    /*
     * 3.Een lijst met de 10 grootste landen, gemeten naar oppervlakte, TYPE: GET, URI:
     * http://localhost:8080/jouwapp/restservices/countries/largestsurfaces
     */
    @GET
    @Path("/largestsurfaces")
    @Produces("application/json")
    public String get10BiggestCountries() throws SQLException {
        CountryService service = ServiceProvider.getWorldService();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (Country c : service.get10LargestSurfaces()) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            job.add("code", c.getCode());
            job.add("continent", c.getContinent());
            job.add("government", c.getGovernment());
            job.add("name", c.getName());
            job.add("population", c.getPopulation());
            job.add("region", c.getRegion());
            job.add("surface", c.getSurface());

            jab.add(job);
        }

        JsonArray array = jab.build();
        return array.toString();
    }

    /*
     * 4.Een lijst met de 10 grootste landen, gemeten naar inwoneraantal, TYPE: GET, URI:
     * http://localhost:8080/jouwapp/restservices/countries/largestpopulations
     */
    @GET
    @Path("/largestpopulations")
    @Produces("application/json")
    public String getCountriesWithLargestPopulation() throws SQLException {
        CountryService service = ServiceProvider.getWorldService();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (Country c : service.get10LargestPopulations()) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            job.add("code", c.getCode());
            job.add("continent", c.getContinent());
            job.add("government", c.getGovernment());
            job.add("name", c.getName());
            job.add("population", c.getPopulation());
            job.add("region", c.getRegion());
            job.add("surface", c.getSurface());
            jab.add(job);
        }

        JsonArray array = jab.build();
        return array.toString();
    }
}
