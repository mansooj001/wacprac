package worldmap.services;

import worldmap.services.CountryService;

public class ServiceProvider {
	private static CountryService worldService = new CountryService();

	public static CountryService getWorldService() {
		return worldService;
	}
}