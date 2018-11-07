/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.model.UsuarioDto;
import cineuna.service.MovieService;
import cineuna.service.UsuarioService;
import cineuna.util.Mensaje;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class AdminReportesController extends Controller implements Initializable {

    @FXML
    private JFXDatePicker DP1;
    @FXML
    private JFXDatePicker DP2;

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
        
    }

    @FXML
    private void genReporte(ActionEvent event) {
         try {
            MovieService mService = new MovieService();
            Respuesta respuesta = mService.getReport(Date.from(DP1.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),Date.from(DP2.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
             if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Generar  reporte", getStage(), respuesta.getMensaje());
                } else {
                   
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar reporte", getStage(), "reporte creado correctamente.");
                }
            
        } catch (Exception ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, "Error registrando reporte.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar reporte", getStage(), "Ocurrio un error registrando el reporte.");
        }
    }
    
}
