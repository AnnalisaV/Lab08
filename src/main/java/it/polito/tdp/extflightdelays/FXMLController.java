/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.CoppiaAirport;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="distanzaMinima"
    private TextField distanzaMinima; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	txtResult.clear();
    	// controllo sull'input 
    	String distanza= distanzaMinima.getText();
    	if (distanza.length()==0) {
    		txtResult.appendText("Campo vuoto! Inserire una distanza minima!\n");
    		return; 
    	}
    	int dist=0; 
    	try {
    		dist= Integer.parseInt(distanza); 
    	}catch(Exception e ) {
    		txtResult.appendText("Inserire un valore numerico per la distanza minima!\n");
    		return; 
    	}
    	
    	this.model.analizza(dist);
    	txtResult.appendText("Grafo creato con "+this.model.nVertici()+" vertici e "+
    	this.model.nArchi()+"\n");
    	
    	txtResult.appendText("ELENCO ROTTE:\n");

    	for(CoppiaAirport c : this.model.getCoppieAirport()) {

    		txtResult.appendText(c.toString() + "\n");

    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
