package com.quickbase;

import com.quickbase.devint.ConcreteStatService;
import com.quickbase.devint.DBManager;
import com.quickbase.devint.DBManagerImpl;
import com.quickbase.devint.IStatService;

/**
 * The main method of the executable JAR generated from this repository. This is to let you
 * execute something from the command-line or IDE for the purposes of demonstration, but you can choose
 * to demonstrate in a different way (e.g. if you're using a framework)
 */
public class Main {
	
	public enum Country { USA, Canada, United_Kingdom, Guernsey, India, South_Korea, 
		PERU, NEW_ZEALAND, MOLDOVA, EGYPT, BAHRAIN, NORTH_KOREA, LAOS, UKRAINE, 
		CHILE, MALI, GREECE, ARMENIA, SLOVENIA, ST_VINCENT__GRANADINES, BHUTAN, 
		ARUBA, MALDIVES, MAYOTTE, VIETNAM, GERMANY, BOSTWANA, TOGO, LUXEMBOURG, 
		US_VIRGIN_ISLANDS, BELARUS, MYANMAR, MAURITANIA, MALAYSIA, DOMINICAN_REPUBLIC, 
		NEW_CALEDONIA_FRANCE, Slovakia, KYRGYZSTAN, LITUANIA };	
	
    public static void main( String args[] ) {
    	
        DBManager dao = new DBManagerImpl();
        IStatService service = new ConcreteStatService();
        
        String countryName = Country.Slovakia.toString();
        // returns 0 if the country is not found in the DB
        int dbPops = dao.getPopulationBy(countryName);
        int population = (dbPops == 0) ? service.getPopulationBy(countryName) : dbPops;
        
        System.out.println("Population of " + countryName +" : "+population);
    }

}