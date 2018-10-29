/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.util.AppContext;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
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
    @FXML
    private Label lblFechaFin;
    @FXML
    private JFXListView<JFXButton> listaTandas;
    private Boolean disponible;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @Override
    public void initialize() {
        disponible = (Boolean) AppContext.getInstance().get("peliDisponible");
        cargarTandas();
        cargarInfoPelicula();
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
    
    private void cargarTandas(){
        listaTandas.getItems().clear();
        if(disponible){
            for (int i = 0; i < 3; i++) {
                JFXButton btnTanda = new JFXButton("16:30");
                btnTanda.setOnMouseClicked(c->{
                
                });
                listaTandas.getItems().add(btnTanda);
            }
        }
    }
}