package com.quickbase;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.tuple.Pair;

import com.quickbase.devint.DataAccessImpl;
import com.quickbase.devint.IDataAccess;

/**
 * The main method of the executable JAR generated from this repository. This is to let you
 * execute something from the command-line or IDE for the purposes of demonstration, but you can choose
 * to demonstrate in a different way (e.g. if you're using a framework)
 */
public class Main {
	
    public static void main( String args[] ) {
    	
    	IDataAccess data = new DataAccessImpl();
    	
    	//Scanner scan = new Scanner(System.in);
    	//String countryName = scan.next();
    	//scan.close();
    	//System.out.println(data.getPopulationBy(countryName));
    	
		Iterator<Pair<String, Integer>> it = data.GetCountryPopulations().iterator();
		while (it.hasNext()) {
			Pair<String, Integer> entry = it.next();
				System.out.println(entry.getKey() +"\t" +entry.getValue());
			}
    }
}



