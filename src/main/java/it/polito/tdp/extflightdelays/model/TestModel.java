package it.polito.tdp.extflightdelays.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		//quanti vertici mi stampa = 322
		//System.out.println(model.analizza(1000));
		
		
		model.analizza(0);
		System.out.println("Grafo creato : "+ model.nVertici()+" vertici e "+model.nArchi()+" archi\n"); 
		

	}

}
