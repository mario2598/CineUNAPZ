/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.cards.CineCard;
import cineuna.cards.MovieCard2;
import com.jfoenix.controls.JFXDialogLayout;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.TilePane;
/**
 * FXML Controller class
 *
 * @author robri
 */
public class UsuSeleccionCinesController extends Controller implements Initializable {

    @FXML
    private JFXDialogLayout root;
    @FXML
    private TilePane tpCines;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        initCines();
    }
    
    private void initCines(){
        tpCines.getChildren().clear();
        for (int i = 0; i < 4; i++) {
            CineCard card=new CineCard();
            card.initCard();
            tpCines.getChildren().add(card);
        }
    }
    
}
