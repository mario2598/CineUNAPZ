/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.cards.AdminMovieCard;
import cineuna.cards.AdminTandaCard;
import cineuna.model.MovieDto;
import cineuna.model.SalaDto;
import cineuna.model.TandaDto;
import cineuna.service.TandaService;
import cineuna.util.AppContext;
import cineuna.util.FlowController;
import cineuna.util.LangUtils;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class AdminInfoSalaController extends Controller implements Initializable {
    //FXML Attributes
    @FXML
    private BorderPane root;
    @FXML
    private FlowPane spMovie;
    @FXML
    private Label lblNombre, lblTipo;
    @FXML
    private JFXToggleButton toggleBtnHabilitada;
    @FXML
    private JFXListView<AdminTandaCard> listViewTandas;
    @FXML
    private JFXButton btnGuardar, btnEliminarTanda, btnEditarTanda;
    @FXML
    private GridPane gpTandaInfo;
    @FXML
    private Label lblTandaHoraIni, lblTandaHoraFin, lblTandaCosto;
    
    //Attributes
    private final TandaService tandaService = new TandaService();
    private final ReadOnlyDoubleProperty heightProp = FlowController.getInstance().getStage().heightProperty();
    private final ReadOnlyDoubleProperty widthProp = FlowController.getInstance().getStage().widthProperty();
    private SalaDto sala;
    private AdminMovieCard card;
    @FXML
    private Label lblDistribucion;
    @FXML
    private JFXButton lblVer;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private Label lblTandas;
    
    //Initializers
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        heightProp.addListener((observable, oldValue, newValue) -> {
            if(card!=null){
                Double alto = heightProp.get()*0.38;
                Double ancho = widthProp.get()*0.40;
                card.cambiarTamanho(ancho, alto);
            }
        });
        listViewTandas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                gpTandaInfo.setVisible(true);
                btnEditarTanda.setDisable(false);
                btnEliminarTanda.setDisable(false);
                mostrarInfoTanda(newValue.getTanda());
            } else {
                gpTandaInfo.setVisible(false);
                btnEditarTanda.setDisable(true);
                btnEliminarTanda.setDisable(true);
            }
        });
        toggleBtnHabilitada.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(sala!=null){
                if(!newValue == this.sala.getSalaEstado().equalsIgnoreCase("A")){
                    toggleBtnHabilitada.setSelected(this.sala.getSalaEstado().equalsIgnoreCase("A"));
                }
            }
        });
        //Este boolean es para actualizar la lista de tandas cada vez que se cree una nueva
        SimpleBooleanProperty newTandaProp = new SimpleBooleanProperty();
        newTandaProp.set(false);
        newTandaProp.addListener((observable, oldValue, newValue) -> {
            if(newValue && sala!=null){
                listViewTandas.getItems().clear();
                cargarTandas();
            }
        });
        AppContext.getInstance().set("AdminNewTandaProperty", newTandaProp);
    }
    
    @Override
    public void initialize() {
        spMovie.getChildren().clear();
        sala = (SalaDto) AppContext.getInstance().get("manteSala");
        //this.txtPrueba.setText("Mostrando informacion de la sala: " + sala.getSalaNombre());
        mostrarInfoSala();
        cargarTandas();
        cargarIdioma();
    }
    
    //Methods
    private void mostrarInfoSala(){
        this.lblNombre.setText(sala.getSalaNombre());
        this.lblTipo.setText(this.sala.getSalaTipo());
        this.toggleBtnHabilitada.setSelected(this.sala.getSalaEstado().equalsIgnoreCase("A"));
    }
    
    private void cargarTandas(){
        listViewTandas.getItems().clear();
        try{
            Respuesta resp = tandaService.getTandasS(sala.getSalaId());
            if(resp.getEstado()){
                ArrayList<TandaDto> tandaList = (ArrayList<TandaDto>) resp.getResultado("TandaListS");
                if(!tandaList.isEmpty()){
                    tandaList.stream().forEach(tanda -> {
                        AdminTandaCard newCard = new AdminTandaCard(tanda);
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
    
    public AdminMovieCard generarMovieCard(MovieDto movie){
        Double alto = heightProp.get()*0.38;
        Double ancho = widthProp.get()*0.40;
        AdminMovieCard newCard = new AdminMovieCard(movie, ancho, alto);
        newCard.initCard();
        this.card = newCard;
        return newCard;
    }
    
    private void mostrarInfoTanda(TandaDto tanda){
        spMovie.getChildren().clear();
        spMovie.getChildren().add(generarMovieCard(tanda.getMovieId()));
        lblTandaCosto.setText("Precio: ₡" + String.valueOf(tanda.getTandaCobro()));
    }

    //FXML Methods
    @FXML
    private void btnNuevaTandaAction(ActionEvent event) {
        ((SimpleBooleanProperty) AppContext.getInstance().get("AdminNewTandaProperty")).set(false);
        FlowController.getInstance().goViewOnDialog("AdminNuevaTanda", FlowController.getInstance().getDialogsPane());
        
    }

    @FXML
    private void btnVerButacasAction(ActionEvent event) {
        //TODO
    }

    @FXML
    private void brnEliminarSalaAction(ActionEvent event) {
        //TODO
    }

    @FXML
    private void btnEditarSalaAction(ActionEvent event) {
        AppContext.getInstance().set("AdminEditingSala", sala);
        FlowController.getInstance().goView("AdminNuevaSala");
    }

    @FXML
    private void btnGuardarAction(ActionEvent event) {
        //TODO
    }

    @FXML
    private void btnEliminarTandaAction(ActionEvent event) {
        //TODO
    }

    @FXML
    private void btnEditarTandaAction(ActionEvent event) {
        AppContext.getInstance().set("AdminEditingTanda", listViewTandas.getSelectionModel().getSelectedItem().getTanda());
        FlowController.getInstance().goView("AdminNuevaTanda");
    }
    
    private void cargarIdioma(){
        LangUtils.getInstance().loadLabelLang(lblDistribucion, "lblDistribucion");
        LangUtils.getInstance().loadButtonLang(lblVer, "lblVer");
        LangUtils.getInstance().loadLabelLang(lblTandas, "lblTandas");
        LangUtils.getInstance().loadButtonLang(btnEliminar, "btnEliminar");
        LangUtils.getInstance().loadButtonLang(btnEditar, "btnEditar");
        LangUtils.getInstance().loadButtonLang(btnGuardar, "btnGuardar");
        LangUtils.getInstance().loadButtonLang(btnEliminarTanda, "btnEliminarTanda");
        LangUtils.getInstance().loadButtonLang(btnEditarTanda, "btnEditarTanda");
        
    }
        
    
}
