/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.model.MovieDto;
import cineuna.model.TandaDto;
import cineuna.service.TandaService;
import cineuna.util.AppContext;
import cineuna.util.FlowController;
import cineuna.util.LangUtils;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
    private JFXListView<JFXButton> listaTandas;
    private Boolean disponible;
    @FXML
    private VBox vbRoot;
    @FXML
    private Label lblMsjFecha;
    private MovieDto pelicula;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @Override
    public void initialize() {
        disponible = (Boolean) AppContext.getInstance().get("peliDisponible");
        pelicula=(MovieDto) AppContext.getInstance().get("peliculaSel");
        cargarTandas();
        cargarIdioma();
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
        lblFecha.setText(pelicula.getMovieDate().toString());
        lblNombre.setText(pelicula.getMovieNombre());
        lblResenna.setText(pelicula.getMovieResena());
    }
    
    private void cargarTandas(){
        listaTandas.getItems().clear();
        if(disponible){
            /*for (int i = 0; i < 3; i++) {
                JFXButton btnTanda = new JFXButton("16:30");
                btnTanda.setOnAction(c->{  
                //AppContext.getInstance().set("tandaSeleccionada",tanda);
                 FlowController.getInstance().goView("UsuSeleccionTanda");
                });
                listaTandas.getItems().add(btnTanda);
            }*/
            List<TandaDto> listaDto=new ArrayList<>();
            TandaService ts=new TandaService();
            System.out.println("buscar por id: "+pelicula.getMovieId());
            Respuesta r = ts.getTandasM(pelicula.getMovieId());
            if(r.getEstado()){
                System.out.println("true");
                listaDto=(List<TandaDto>) r.getResultado("tandasM");
                System.out.println("lista tandas size:"+ listaDto.size());
            for(TandaDto t: listaDto){
                JFXButton btnTanda = new JFXButton(t.getTandaHinicio().toString());
                btnTanda.setOnAction(c->{  
                AppContext.getInstance().set("tandaSeleccionada",t);
                 FlowController.getInstance().goView("UsuSeleccionTanda");
                });
                listaTandas.getItems().add(btnTanda);
            }
            }
            else{
                System.out.println("false");
            }
            
            //List<TandaDto> tandas = (List<TandaDto>) ts.getTandasM(pelicula.getMovieId());
            
        }
    }
    
    private void cargarIdioma(){
        Integer idioma=Integer.valueOf(AppContext.getInstance().getUsuario().usuIdioma.getValue());
        if(idioma.equals(1))
            LangUtils.getInstance().setLang("es");
        else
            LangUtils.getInstance().setLang("eng");
        
        LangUtils.getInstance().loadLabelLang(lblMsjFecha, "lblMsjFecha");
    }
    
    private void buscarTandas(){
        //TandaService ts= new TandaService();
        
    }
}
