/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.model.UsuarioDto;
import cineuna.service.MovieService;
import cineuna.service.UsuarioService;
import cineuna.util.LocalDateAdapter;
import cineuna.util.Mensaje;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXDatePicker;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
        if(DP1.getValue() == null||DP2.getValue() == null){
            new Mensaje().showModal(Alert.AlertType.ERROR, "Datos vacios", getStage(), "Es necesario llenar los campos de la fecha");
        }
        else{
            
        
         try {
            MovieService mService = new MovieService();
            String f1 = DP1.getValue().toString();
            String f2 = DP2.getValue().toString();
            Respuesta respuesta = mService.getReport(f1,f2);
             if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Generar  reporte", getStage(), respuesta.getMensaje());
                } else {
            byte[] b = (byte[]) respuesta.getResultado("reporte");
            convPdf(b);
 
           }
        } catch (Exception ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, "Error registrando reporte.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar reporte", getStage(), "Ocurrio un error registrando el reporte.");
        }
         }
    }
 
    void convPdf(byte[] b) throws FileNotFoundException, IOException{
        String outPutFile = "src\\cineuna\\jasper\\moviesReport.pdf";
         String stream =Base64.getEncoder().encodeToString(b); 
            byte[] bytes = Base64.getDecoder().decode(stream);
            File someFile = new File(outPutFile);
            FileOutputStream fos = new FileOutputStream(someFile);
            fos.write(bytes);
            fos.flush();
            fos.close();
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar reporte", getStage(), "reporte creado correctamente.");
    }
}
