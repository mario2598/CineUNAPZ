/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.cards.AdminMovieCard;
import cineuna.model.MovieDto;
import cineuna.model.SalaDto;
import cineuna.util.AppContext;
import cineuna.util.FlowController;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class AdminInfoSalaController extends Controller implements Initializable {
    //FXML Attributes
    @FXML
    private BorderPane root;
    @FXML
    private Label txtNombre;
    @FXML
    private FlowPane spMovie;
    //Attributes
    private final ReadOnlyDoubleProperty heightProp = FlowController.getInstance().getStage().heightProperty();
    private SalaDto sala;
    private AdminMovieCard card;
    
    //Initializers
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        heightProp.addListener((observable, oldValue, newValue) -> {
            if(card!=null){
                Double h = heightProp.getValue()*0.38;
                Integer intH = h.intValue()/36;
                card.cambiarTamanho(intH);
            }
        });
    }
    
    @Override
    public void initialize() {
        spMovie.getChildren().clear();
        sala = (SalaDto) AppContext.getInstance().get("manteSala");
        this.txtNombre.setText(sala.getSalaNombre());
        btnNuevaTandaAction(null);
    }
    
    //Methods

    @FXML
    private void btnNuevaTandaAction(ActionEvent event) {
        MovieDto movie = new MovieDto();
        movie.setMovieNombre("Movie de Prueba");
        movie.setMovieDate(LocalDate.now());
        Double alto = heightProp.get()*0.38;
        Integer times = (int) (alto/36);
        System.out.println("Altura del stackPane: " + alto
                + "\nDimension de la movieCard: " + times);
        AdminMovieCard newCard = new AdminMovieCard(movie, times);
        newCard.initCard();
        this.card = newCard;
        spMovie.getChildren().add(newCard);
    }
    
    
}
