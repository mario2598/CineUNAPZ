/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.model.MovieDto;
import cineuna.model.UsuarioDto;
import cineuna.service.MovieService;
import cineuna.service.UsuarioService;
import cineuna.util.LocalDateAdapter;
import cineuna.util.Mensaje;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import static javafx.scene.control.Alert.AlertType.ERROR;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    @FXML
    private VBox vbDate;
    @FXML
    private VBox vbId;
    @FXML
    private JFXComboBox<MovieDto> cbList;
    @FXML
    private StackPane spBtn;
    MovieService mService = new MovieService();
    @FXML
    private HBox hbBtn;

    /* * Initializes the controller class.
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
            String date1 = DP1.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE );
            String date2 = DP2.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE );
        try {
            
            Respuesta respuesta = mService.getReport(date1,date2);
             if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Generar  reporte", getStage(), respuesta.getMensaje());
                } else {
            byte[] b = (byte[]) respuesta.getResultado("reporte");
            String r = convPdf(b,"src\\cineuna\\jasper\\moviesListReport.pdf");
            showFile(r);
           }
        } catch (Exception ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, "Error registrando reporte.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar reporte", getStage(), "Ocurrio un error registrando el reporte.");
        }
         }
    }
    
     private void showFile(String dir){
        try{ 
            File path = new File (dir);
            Desktop.getDesktop().open(path);
     
        }catch(IOException e){
            System.out.println("error ruta");
        }
    }
 
    String convPdf(byte[] b,String ruta) throws FileNotFoundException, IOException{
        String outPutFile = ruta;
         String stream =Base64.getEncoder().encodeToString(b); 
            byte[] bytes = Base64.getDecoder().decode(stream);
            File someFile = new File(outPutFile);
            FileOutputStream fos = new FileOutputStream(someFile);
            fos.write(bytes);
            fos.flush();
            fos.close();
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar reporte", getStage(), "Reporte creado correctamente.");
            return ruta;
    }

    @FXML
    private void genReporteMovie(ActionEvent event) throws IOException {
        if(cbList.getValue() == null){
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Pelicula Vacia ", getStage(), "Es necesario seleccionar una pelicula.");
        }
        else{
          Respuesta res = mService.getReport(cbList.getValue().getMovieId());
          if(!res.getEstado()){
               new Mensaje().showModal(Alert.AlertType.ERROR, "Generar reporte", getStage(), res.getMensaje());
          }
          else{
              byte[] b = (byte[]) res.getResultado("reporte");
             String r = convPdf(b,"src\\cineuna\\jasper\\MovieReport.pdf");
             new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar reporte", getStage(), "Reporte creado correctamente."); 
              showFile(r);
          }
        }
    }

    @FXML
    private void btnVBList(ActionEvent event) {
        vbDate.setVisible(true);
        hbBtn.setVisible(false);
        
    }

    @FXML
    private void btnVbId(ActionEvent event) {
        Boolean v = cargarCombo();
        if(v){
              vbId.setVisible(true);
              hbBtn.setVisible(false); 
        }
     
    }
       public Boolean cargarCombo(){ // actualiza el box con los elementos de la lista
           List<MovieDto> list = new ArrayList<>();
           Respuesta res = mService.getMovies();
           if(!res.getEstado()){
                new Mensaje().showModal(ERROR, "Error en peliculas.", getStage(), "No se cargaron peliculas correctamente.");
                return false;
           }
           else{
               list = (List<MovieDto>) res.getResultado("Movie");
              for(MovieDto u : list){
              cbList.getItems().add(u);
             
                } 
              return true;
           }   
    } 

    @FXML
    private void volverList(ActionEvent event) {
         vbId.setVisible(false);
        hbBtn.setVisible(true);
    }

    @FXML
    private void volverId(ActionEvent event) {
              vbDate.setVisible(false);        
              hbBtn.setVisible(true); 
    }
}
