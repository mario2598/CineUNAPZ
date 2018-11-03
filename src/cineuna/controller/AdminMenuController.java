/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.TilePane;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class AdminMenuController extends Controller implements Initializable {

    @FXML
    private TilePane tilepAdminMenu;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /**
     * Metodo abstracto de la clase Controller
     */
    @Override
    public void initialize() {
        FlowController.getInstance().btnVolverVisible(false);
    }

    @FXML
    private void btnCinesAction(ActionEvent event) {
        FlowController.getInstance().goView("AdminCines");
        FlowController.getInstance().btnVolverVisible(true);
    }

    @FXML
    private void btnSalasAction(ActionEvent event) {
        FlowController.getInstance().goView("AdminSalas");
        FlowController.getInstance().btnVolverVisible(true);
    }

    @FXML
    private void btnMoviesAction(ActionEvent event) {
        FlowController.getInstance().goView("AdminMovies");
        FlowController.getInstance().btnVolverVisible(true);
    }

    @FXML
    private void btnUsersAction(ActionEvent event) {
        FlowController.getInstance().goView("AdminUsers");
        FlowController.getInstance().btnVolverVisible(true);
    }

    @FXML
    private void btnReportsAction(ActionEvent event) {
        FlowController.getInstance().goView("AdminReportes");
        FlowController.getInstance().btnVolverVisible(true);
    }

    @FXML
    private void btnConfigAction(ActionEvent event) {
        //TODO
    }
    
}
