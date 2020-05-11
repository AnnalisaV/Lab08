package it.polito.tdp.extflightdelays.db;

import it.polito.tdp.extflightdelays.model.Airport;

public class TestDAO {

	public static void main(String[] args) {

		ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();

		System.out.println(dao.loadAllAirlines());
		//System.out.println(dao.loadAllAirports());
		System.out.println(dao.loadAllFlights().size());
		Airport part= new Airport(217);
		Airport arr= new Airport(269);
		//System.out.println(dao.PartenzaDestinazione(idMap)); 
	}

}
