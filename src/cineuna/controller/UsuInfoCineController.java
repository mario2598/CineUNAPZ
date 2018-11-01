/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class UsuInfoCineController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblNombreCine;
    @FXML
    private Hyperlink lblNumTel;
    @FXML
    private Hyperlink lblCorreo;
    @FXML
    private Label lblAbre;
    @FXML
    private Label lblCierra;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        llenaInfo();
    }
    
    private void llenaInfo(){
        lblNombreCine.setText("CineUNA");
        lblCorreo.setText("correoEjemplo@cineuna.com");
        lblNumTel.setText("27785664");
        lblAbre.setText("8:00");
        lblCierra.setText("21:00");
    }
}
