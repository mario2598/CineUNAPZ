/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.cards.MovieCard;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class UsuCinesController extends Controller implements Initializable {

    @FXML
    private HBox root;
    @FXML
    private Label lblNombreCine;
    @FXML
    private JFXListView<MovieCard> tpCartelera;
    @FXML
    private JFXListView<MovieCard> tpProximas;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @Override
    public void initialize() {
        llenarCartelera();
        llenarProximas();
    }
    
    private void llenarCartelera(){
        for (int i = 0; i < 10; i++) {
            MovieCard card = new MovieCard();
            card.initCard();
            tpCartelera.getItems().add(card);
        }
    }
    
    private void llenarProximas(){
        for (int i = 0; i < 10; i++) {
            MovieCard card = new MovieCard();
            card.initCard();
            tpProximas.getItems().add(card);
        }
    }
}
