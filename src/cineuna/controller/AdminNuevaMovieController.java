/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class AdminNuevaMovieController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private ImageView imgPoster;
    @FXML
    private HBox hbPoster;
    @FXML
    private JFXTextField txtEspNombre, txtEngNombre, txtEspTrailer, txtEngTrailer;
    @FXML
    private JFXTextArea txtEspSinopsis, txtEngSinopsis;
    @FXML
    private JFXCheckBox chkBoxEsp1;
    @FXML
    private JFXCheckBox chkBoxEng1;
    @FXML
    private JFXDatePicker datePickEstreno;
    @FXML
    private JFXComboBox<String> cmboBoxEstado, cmboBoxTipo;

    //Initializers
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        imgPoster.setImage(new Image("cineuna/resources/images/VenomPoster.jpg"));
        imgPoster.setPreserveRatio(true);
        hbPoster.widthProperty().addListener((observable, oldValue, newValue) -> {
            Double width = newValue.doubleValue();
            imgPoster.setFitWidth(width * 0.92);
            System.out.println("Redimensionando:\n newValue: " + newValue + "\n imgPosterValue: " + imgPoster.getFitWidth());
        });
        hbPoster.heightProperty().addListener((observable, oldValue, newValue) -> {
            Double width = newValue.doubleValue();
            imgPoster.setFitHeight(width * 0.92);
            System.out.println("Redimensionando:\n newValue: " + newValue + "\n imgPosterValue: " + imgPoster.getFitHeight());
        });
        cmboBoxTipo.getItems().addAll(
            "2D", "3D"
        );
        cmboBoxEstado.getItems().addAll(
           "Próximamente", "En Cartelera", "Inactiva"
        );
    }    

    @Override
    public void initialize() {
        cmboBoxTipo.getSelectionModel().selectFirst();
        cmboBoxEstado.getSelectionModel().selectFirst();
    }

    private void imgPosterAction(MouseEvent event) {
        System.out.println("Cargar poster");
    }
    
}
