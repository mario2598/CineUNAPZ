/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.model.CineDto;
import cineuna.service.CineService;
import cineuna.util.AppContext;
import cineuna.util.Mensaje;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
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
    private JFXTimePicker tpAbre;
    private JFXTimePicker tpCierra;
    @FXML
    private JFXTextField txtAbre;
    @FXML
    private JFXTextField txtCierra;
    @FXML
    private JFXButton btnActualizar;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clear();
        CineService cs = new CineService();
        Respuesta res = cs.getCine();
        cine = (CineDto) res.getResultado("Cine");
        if(res.getEstado()){
            bindCine();
        }
    }

    private void bindCine(){
            lblNombreCine.textProperty().bindBidirectional(cine.cineNombre);
            txtNombreCine.textProperty().bindBidirectional(cine.cineTel);
            txtCorreoCine.textProperty().bindBidirectional(cine.cineEmail); 
            txtAbre.textProperty().bindBidirectional(cine.cineAbre); 
            txtCierra.textProperty().bindBidirectional(cine.cineCierra); 
        
    }

    private void unbind(){
        txtCorreoCine.textProperty().unbindBidirectional(cine.cineEmail);
        txtNombreCine.textProperty().unbindBidirectional(cine.cineNombre);
        txtAbre.textProperty().unbindBidirectional(cine.cineAbre);
        txtCierra.textProperty().unbindBidirectional(cine.cineCierra);
    }
    
    private Boolean guardaCine(){
        CineService cs = new CineService();
        Respuesta res = cs.guardarCine(cine);
        if(!res.getEstado()){
            System.out.println("error guardando cine");
            return false;
        }
        else{
            return true;
        }
    }
    void disable(){
        txtCorreoCine.setDisable(true);
        txtNombreCine.setDisable(true);
        txtAbre.setDisable(true);
        txtCierra.setDisable(true);
    }
    
        void able(){
        txtCorreoCine.setDisable(false);
        txtNombreCine.setDisable(false);
        txtAbre.setDisable(false);
        txtCierra.setDisable(false);
    }
        
    void clear(){
        txtCorreoCine.clear();
        txtNombreCine.clear();
        txtAbre.clear();
        txtCierra.clear();
    }
    /**
     * Metodo abstracto de la clase Controller
     */
    @Override
    public void initialize() {
        
    }

    @FXML
    private void btnModificar(ActionEvent event) {
        btnActualizar.setDisable(false);
        unbind();
        able();
    }

    @FXML
    private void btnAct(ActionEvent event) {
        
        if(txtCorreoCine.getText().isEmpty()||txtNombreCine.getText().isEmpty()||txtAbre.getText().isEmpty()||txtCierra.getText().isEmpty()){
            new Mensaje().showModal(ERROR, "Error en campos.", getStage(), "No se pueden dejar campos vacios!."); 
        }
        else{
            disable();
            cine.setCineEmail(txtCorreoCine.getText());
            cine.setCineAbre(Long.valueOf(txtAbre.getText()));
            cine.setCineCierra(Long.valueOf(txtCierra.getText()));
            cine.setCineTel(Long.valueOf(txtNombreCine.getText()));
            CineService cs = new CineService();
            Respuesta res = cs.guardarCine(cine);
            if(res.getEstado()){
            clear();
            cine = (CineDto) res.getResultado("Cine");
            bindCine();
            new Mensaje().showModal(INFORMATION, "Actualizado.", getStage(), "Se actualizaton tus datos!."); 
        }
            else{
                initialize();
                 new Mensaje().showModal(ERROR, "Error actualizando.", getStage(), "No se puedactualizar tus datos!."); 
            }
  
        }
    }
    
}
