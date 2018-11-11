/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.util.AppContext;
import cineuna.util.FlowController;
import cineuna.util.LangUtils;
import com.jfoenix.controls.JFXButton;
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
    @FXML
    private JFXButton btnCines;
    @FXML
    private JFXButton btnSalas;
    @FXML
    private JFXButton btnPeliculas;
    @FXML
    private JFXButton btnUsuarios;
    @FXML
    private JFXButton btnReportes;
    @FXML
    private JFXButton btnConfig;

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
        cargarIdioma();
    }

    @FXML
    private void btnCinesAction(ActionEvent event) {
        FlowController.getInstance().goView("AdminCines");
    }

    @FXML
    private void btnSalasAction(ActionEvent event) {
        FlowController.getInstance().goView("AdminSalas");
    }

    @FXML
    private void btnMoviesAction(ActionEvent event) {
        FlowController.getInstance().goView("AdminMovies");
    }

    @FXML
    private void btnUsersAction(ActionEvent event) {
        FlowController.getInstance().goView("AdminUsuarios");
    }

    @FXML
    private void btnReportsAction(ActionEvent event) {
        FlowController.getInstance().goView("AdminReportes");
    }

    @FXML
    private void btnConfigAction(ActionEvent event) {
        //TODO
    }
    
    private void cargarIdioma(){
        if(AppContext.getInstance().getUsuario().getUsuIdioma()==1)
            LangUtils.getInstance().setLang("es");
        
        LangUtils.getInstance().loadButtonLang(btnCines, "btnCines");
        LangUtils.getInstance().loadButtonLang(btnConfig, "btnConfig");
        LangUtils.getInstance().loadButtonLang(btnPeliculas, "btnPeliculas");
        LangUtils.getInstance().loadButtonLang(btnReportes, "btnReportes");
        LangUtils.getInstance().loadButtonLang(btnSalas, "btnSalas");
        LangUtils.getInstance().loadButtonLang(btnUsuarios, "btnUsuarios");
    }
    
}
