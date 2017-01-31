package com.quickbase.devint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class DataAccessImpl implements IDataAccess {
		
	private DBManager databaseData = new DBManagerImpl();
	private IStatService statData = new ConcreteStatService();

	@Override
	public Integer getPopulationBy(String country) {
		
        int dbData = databaseData.getPopulationBy(country);
        if (country.equals("U.S.A.")) {
        	country = "United States of America";
        }
        int population = (dbData == 0) ? statData.getPopulationBy(country) : 0;
		return population;
	}
	

	@Override
	public List<Pair<String, Integer>> GetCountryPopulations() {
		
		List<Pair<String, Integer>> countries = new ArrayList<Pair<String, Integer>>();
		boolean hasUSA = false;
		Iterator<Pair<String, Integer>> dbIter = databaseData.GetCountryPopulations().iterator();
		while (dbIter.hasNext()) {
			Pair<String, Integer> entry = dbIter.next();
			if (entry.getKey().equals("U.S.A."))
				hasUSA = true;
			countries.add(entry);
		}
		
		Iterator<Pair<String, Integer>> statIter = statData.GetCountryPopulations().iterator();
		while (statIter.hasNext()) {
			Pair<String, Integer> entry = statIter.next();
			if (entry.getKey().equals("United States of America") && hasUSA)
				continue;
			countries.add(entry);
		}
		
		return countries;
		
	}

}
