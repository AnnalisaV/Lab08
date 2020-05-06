package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	
	private Graph<Airport, DefaultWeightedEdge> grafo; 
	private Map<Integer, Airport> idMapAirport; 
	private List<CoppiaAirport> coppie; 
	
	
	public Model() {
		this.idMapAirport= new HashMap<>(); 
		this.coppie= new ArrayList<>(new ExtFlightDelaysDAO().PartenzaDestinazione(idMapAirport)); 
	}
	
	/**
	 * Creazione del grafo 
	 * @param dist
	 */
	public void analizza(int dist) {
	
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class); 
		
		ExtFlightDelaysDAO dao= new ExtFlightDelaysDAO(); 
		dao.loadAllAirports(this.idMapAirport);// popolo la idMap
		Graphs.addAllVertices(this.grafo, this.idMapAirport.values()); //aggiunta dei vertici al grafo
		
		//Aggiungo gli archi 
		for(CoppiaAirport c : dao.PartenzaDestinazione(this.idMapAirport)) {
			int distanza= dao.distanzaMedia(c.getPartenza(), c.getDestinazione()); 
			if (distanza > dist) {
				Graphs.addEdge(this.grafo, c.getPartenza(),c.getDestinazione(), distanza); 
			}
		}
		
		
		
	}
	
	public int nVertici(){
		return this.grafo.vertexSet().size(); 
	}
	

	public int nArchi(){
		return this.grafo.edgeSet().size(); 
	}

	public String elencoArchi() {
		String s=""; 
		
		for (DefaultWeightedEdge edge : this.grafo.edgeSet()) {
		// non so come prendere il peso dell'arco
	}
		return s;
	}
	 
}
