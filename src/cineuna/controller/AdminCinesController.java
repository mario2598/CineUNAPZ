/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.model.CineDto;
import cineuna.service.CineService;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class AdminCinesController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblNombreCine;
    @FXML
    private Label lblHorario;
    @FXML
    private Label lblMsjAbre;
    @FXML
    private Label lblMsjCierra;
    private CineDto cine;
    @FXML
    private JFXTextField txtNombreCine;
    @FXML
    private JFXTextField txtCorreoCine;
    @FXML
    private JFXTimePicker tpAbre;
    @FXML
    private JFXTimePicker tpCierra;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bindCine();
    }

    private void bindCine(){
        CineService cs = new CineService();
        Respuesta res = cs.getCine();
        if(res.getEstado()){
            CineDto cine = (CineDto) res.getResultado("Cine");
            txtNombreCine.textProperty().bindBidirectional(cine.cineNombre);
            txtCorreoCine.textProperty().bindBidirectional(cine.cineEmail); 
            tpAbre.valueProperty().addListener((e,e1,e2)->{
                cine.setCineAbre(LocalDate.now());
            });
            tpCierra.valueProperty().addListener((e,e1,e2)->{
                cine.setCineCierra(LocalDate.now());
            });
            //lblAbre.setText(cine.getCineAbre().toString());
            //lblCierra.setText(cine.getCineCierra().toString());
        }
        else{
            System.out.println("No se pudo obtener el cine para mostrar");
        }
        
    }

    private void unbind(){
        txtCorreoCine.textProperty().unbindBidirectional(cine.cineEmail);
        txtNombreCine.textProperty().unbindBidirectional(cine.cineNombre);
        tpAbre.valueProperty().set(LocalTime.MIN);
        tpCierra.valueProperty().set(LocalTime.MIN);
    }
    
    private void guardaCine(){
        CineService cs = new CineService();
        Respuesta res = cs.guardarCine(cine);
        if(!res.getEstado()){
            System.out.println("error guardando cine");
        }
    }
    
    /**
     * Metodo abstracto de la clase Controller
     */
    @Override
    public void initialize() {
        
    }
    
    private void binds(){
        
    }
    
    private void unbinds(){
        
    }
    
}
