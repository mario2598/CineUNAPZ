/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.view;

import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class PruebasCardsController implements Initializable {

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
    @FXML
    private JFXListView<?> tpCartelera;
    @FXML
    private JFXListView<?> tpProximas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
