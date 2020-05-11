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
			// posso collegarli?
			if (c.getDistanza()>dist) {
			DefaultWeightedEdge e= this.grafo.getEdge(c.getPartenza(), c.getDestinazione());
			
			if(e==null) {
				//arco non esiste lo aggiungo
				Graphs.addEdge(this.grafo, c.getPartenza(), c.getDestinazione(), c.getDistanza()); 
	            }
			else {
				double peso= this.grafo.getEdgeWeight(e);
				double pesoNuovo= (peso+c.getDistanza())/2;//voglio la media come peso 
				grafo.setEdgeWeight(e, pesoNuovo);
			}
			}
			//altrimenti non aggiungo nessun arco fra i due Airport	
			}
		}
		
	
	public int nVertici(){
		return this.grafo.vertexSet().size(); 
	}
	

	public int nArchi(){
		return this.grafo.edgeSet().size(); 
	}

	//non dao Dao perche' qui filtro sulla base della distanza che mi chiede l'utente
	public List<CoppiaAirport> getCoppieAirport(){

	    List<CoppiaAirport> coppie = new ArrayList<>();

		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
         coppie.add(new CoppiaAirport(this.grafo.getEdgeSource(e), 
        		 this.grafo.getEdgeTarget(e), this.grafo.getEdgeWeight(e)));
         }

		return coppie;
	 
}
}
