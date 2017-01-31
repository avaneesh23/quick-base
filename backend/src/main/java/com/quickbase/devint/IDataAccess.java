package com.quickbase.devint;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public interface IDataAccess {
	
	public List<Pair<String, Integer>> GetCountryPopulations();
	
	public Integer getPopulationBy(String country);

}
