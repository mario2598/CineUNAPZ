/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.cards.AdminTandaCard;
import cineuna.model.MovieDto;
import cineuna.model.TandaDto;
import cineuna.service.TandaService;
import cineuna.util.AppContext;
import cineuna.util.FlowController;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class UserInfoCompraController extends Controller implements Initializable {

    @FXML
    private Label lblTandas;
    @FXML
    private JFXListView<AdminTandaCard> listViewTandas;
    @FXML
    private Label lblFecha;
    @FXML
    private JFXDatePicker datePickerFecha;
    @FXML
    private Label lblIdioma;
    @FXML
    private JFXComboBox<String> cmboBoxIdiomas;
    @FXML
    private JFXButton bntAceptar;
    
    //Attributes
    private final TandaService tandaService = new TandaService();
    private TandaDto tanda;
    private MovieDto movie;
    private ArrayList<TandaDto> tandaList;
    private String msgError;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        listViewTandas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null)
                tanda = newValue.getTanda();
        });
        tandaList = new ArrayList<>();
    }    

    @Override
    public void initialize() {
        this.movie = (MovieDto) AppContext.getInstance().get("UserShowingMovie");
        tandaList = new ArrayList<>();
        loadData();
        cargarTandas();
    }
    
    //Methods
    private void cargarTandas(){
        listViewTandas.getItems().clear();
        tandaList.clear();
        try{
            Respuesta resp = tandaService.getTandasM(movie.getMovieId());
            if(resp.getEstado()){
                if(tandaList!=null)
                    tandaList.addAll((List<TandaDto>) resp.getResultado("tandasM"));        
                if(!tandaList.isEmpty()){
                    tandaList.stream().forEach(t -> {
                        AdminTandaCard newCard = new AdminTandaCard(t);
                        newCard.initCard();
                        listViewTandas.getItems().add(newCard);
                    });
                }
            } else {
                System.out.println("Se ha producido un error cargando la lista de tandas.\nError: " + resp.getMensaje());
            }
        }catch(Exception ex){
            System.out.println("Se ha producido un error cargando la lista de tandas.\nError: " + ex);
        }
    }
    
    private void loadData(){
        cmboBoxIdiomas.getItems().clear();
        datePickerFecha.setValue(null);
        switch(movie.getMovieIdioma().intValue()){
            case 1: case 0:
                cmboBoxIdiomas.getItems().addAll("Español");
                break;
            case 2:
                cmboBoxIdiomas.getItems().addAll("Inglés");
                break;
            case 3:
                cmboBoxIdiomas.getItems().addAll("Español", "Inglés");
                break;
        }
    }
    
    private void cleanData(){
        
    }
    
    private Boolean validadDatosNecesarios(){
        msgError = "Se ha producido un error, debes ingresar los siguientes datos correctamente:";
        Boolean hayError = false;
        if(datePickerFecha.getValue()==null){
            hayError = true;
            msgError += "\n\tDebes ingresar la fecha para tus entradas.";
        }
        if(cmboBoxIdiomas.getValue()==null){
            hayError = true;
            msgError += "\n\tDebes seleccionar el idioma.";
        }
        if(this.tanda==null){
            hayError = true;
            msgError += "\n\tDebes ingresar la fecha para tus entradas.";
        }
        if(!hayError)
            msgError = null;
        return !hayError;
    }

    @FXML
    private void bntAceptarAction(ActionEvent event) {
        if(validadDatosNecesarios()){
            FlowController.getInstance().closeDialog();
            AppContext.getInstance().set("UserSelectedTanda", tanda);
            AppContext.getInstance().set("UserSelectedDate", datePickerFecha.getValue());
            AppContext.getInstance().set("UserSelectedIdioma", cmboBoxIdiomas.getValue());
            FlowController.getInstance().goView("UsuSeleccionTanda");
        } else {
            System.out.println("Faltaron datos necesarios.");
        }
    }
    
}
