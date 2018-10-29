/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class UsuInfoPeliculaController extends Controller implements Initializable {

    @FXML
    private JFXDialogLayout root;
    @FXML
    private ImageView imgPoster;
    @FXML
    private AnchorPane apVideo;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblFecha;
    @FXML
    private JFXTextArea lblResenna;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
    }

    /**
     * usar el apVideo que está invisible para poner el reproductor de youtube
     * @param event 
     */
    @FXML
    private void verTrailer(MouseEvent event) {
    }
    
    private void cargarInfoPelicula(){
        //imgPoster.setImage(value);
        lblFecha.setText("28/10/18");
        lblNombre.setText("DeadPool 2");
        lblResenna.setText("Deadpool debe proteger a Russell, un adolescente mutante, de Cable un soldado del futuro genéticamente modificado. Deadpool se alía con otros superhéroes para poder derrotar al poderoso Cable.");
    }
    
}
