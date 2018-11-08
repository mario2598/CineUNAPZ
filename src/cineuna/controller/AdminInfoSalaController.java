/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.model.SalaDto;
import cineuna.util.AppContext;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class AdminInfoSalaController extends Controller implements Initializable {
    //FXML Attributes
    @FXML
    private Label txtPrueba;
    //Attributes
    private SalaDto sala;
    
    //Initializers
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @Override
    public void initialize() {
        sala = (SalaDto) AppContext.getInstance().get("manteSala");
        //this.txtPrueba.setText("Mostrando informacion de la sala: " + sala.getSalaNombre());
    }
    
    //Methods
    
    
}
