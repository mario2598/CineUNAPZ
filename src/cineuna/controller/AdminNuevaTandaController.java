/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.cards.AdminMovieCard;
import cineuna.cards.AdminSmallMovieCard;
import cineuna.model.MovieDto;
import cineuna.model.SalaDto;
import cineuna.model.TandaDto;
import cineuna.service.MovieService;
import cineuna.service.TandaService;
import cineuna.util.AppContext;
import cineuna.util.DateUtil;
import cineuna.util.FlowController;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class AdminNuevaTandaController extends Controller implements Initializable {
    //FXML Attributes
    @FXML
    private JFXTimePicker timePickerHoraIni;
    @FXML
    private Label lblHoraIni, lblHoraFin, lblHoraFinData, lblPrecio;
    @FXML
    private JFXTextField txtPrecioEntrada;
    @FXML
    private JFXButton btnCancelar, btnGuardar, btnSeleccionarMovie;
    @FXML
    private HBox hbMovieCard;
    @FXML
    private BorderPane baseBorderPane;
    
    //Attributes 
    private final MovieService movieService = new MovieService();
    private final TandaService tandaService = new TandaService();
    private TandaDto tanda;
    private AdminMovieCard card;
    private Boolean editando;
    private String mensajeAlerta;
    
    //Initializers
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timePickerHoraIni.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null && tanda!=null){
                Integer hh = timePickerHoraIni.getValue().getHour();
                Integer mm = timePickerHoraIni.getValue().getMinute();
                tanda.setTandaInihh(new Long(hh));
                tanda.setTandaInimm(new Long(mm));
                if(tanda.getMovieId()!=null)
                    calcularHoraFin(tanda.getMovieId().getMovieDuracion());
            }
        });
    }    

    @Override
    public void initialize() {
        baseBorderPane.setPrefSize(750, 400);
        baseBorderPane.setRight(null);
        if(tanda!=null)
            unbindTanda();
        cleanData();
        if(AppContext.getInstance().get("AdminEditingTanda")!=null){
            tanda = (TandaDto) AppContext.getInstance().get("AdminEditingTanda");
            this.editando = true;
            bindTanda();
            loadData();
        } else {
            tanda = new TandaDto();
            this.editando = false;
            bindTanda();
        }
    }
    
    //Methods
    private void checkAvailableMovies(){
        if(true){
            ScrollPane moviesScrollPane = new ScrollPane();
            moviesScrollPane.setFitToHeight(true);
            moviesScrollPane.setFitToWidth(true);
            moviesScrollPane.setPrefWidth(200);
            JFXListView<AdminSmallMovieCard> listView = new JFXListView<>();
            listView.getStyleClass().add("customListView");
            listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue!=null){
                    tanda.setMovieId(newValue.getMovie());
                    hbMovieCard.getChildren().add(generarMovieCard(newValue.getMovie()));
                    calcularHoraFin(newValue.getMovie().getMovieDuracion());
                }
            });
            moviesScrollPane.setContent(listView);
            loadMovies(listView);
            baseBorderPane.setPrefSize(950, 400);
            baseBorderPane.setRight(moviesScrollPane);
        } else {
            System.out.println("No hay ninguna película registrada hasta el momento.");
        }
    }
    
    private void loadMovies(JFXListView<AdminSmallMovieCard> listView){
        try{
            Respuesta resp = movieService.getMovies("P");
            if(resp.getEstado()){
                ArrayList<MovieDto> movieList = (ArrayList<MovieDto>) resp.getResultado("Movies");
                fillMoviesListView(listView, movieList);
            } else {
                System.out.println("Se ha producido un error cargando las peliculas (Proximamente) en la vista de nueva tanda.");
            }
        } catch(Exception ex){
            System.out.println("Se ha producido un error cargando las peliculas (Proximamente) en la vista de nueva tanda."
                    + "\nError: " + ex);
        }
        try{
            Respuesta resp = movieService.getMovies("I");
            if(resp.getEstado()){
                ArrayList<MovieDto> movieList = (ArrayList<MovieDto>) resp.getResultado("Movies");
                fillMoviesListView(listView, movieList);
            } else {
                System.out.println("Se ha producido un error cargando las peliculas (Inactivas) en la vista de nueva tanda.");
            }
        } catch(Exception ex){
            System.out.println("Se ha producido un error cargando las peliculas (Inactivas) en la vista de nueva tanda."
                    + "\nError: " + ex);
        }
    }
    
    private void fillMoviesListView(JFXListView<AdminSmallMovieCard> listView, ArrayList<MovieDto> movieList){
        movieList.stream().forEach(movie -> {
            AdminSmallMovieCard newCard = new AdminSmallMovieCard(movie);
            newCard.initCard();
            listView.getItems().add(newCard);
        });
    }

    private void bindTanda() {
        txtPrecioEntrada.textProperty().bindBidirectional(tanda.tandaCobro);
    }

    private void unbindTanda() {
        txtPrecioEntrada.textProperty().unbindBidirectional(tanda.tandaCobro);
    }

    private void loadData() {
        timePickerHoraIni.setValue(LocalTime.of(tanda.getTandaInihh().intValue(), tanda.getTandaInimm().intValue()));
        hbMovieCard.getChildren().add(generarMovieCard(tanda.getMovieId()));
    }

    private void cleanData() {
        txtPrecioEntrada.setText("");
        timePickerHoraIni.setValue(null);
        lblHoraFinData.setText("");
        hbMovieCard.getChildren().clear();
    }
    
    private AdminMovieCard generarMovieCard(MovieDto movie){
        AdminMovieCard newCard = new AdminMovieCard(movie, 150.0, 185.0);
        newCard.initCard();
        newCard.enableRipplerEffect(false);
        this.card = newCard;
        return newCard;
    }
    
    private void calcularHoraFin(Long movieDuracion){
        if(tanda.getTandaInihh()!=null){
            Integer hh = tanda.getTandaInihh().intValue();
            Integer hh2 = DateUtil.getHoras(movieDuracion);
            hh += hh2;
            Integer mm = tanda.getTandaInimm().intValue();
            Integer mm2 = DateUtil.getMinutos(movieDuracion);
            mm += mm2;
            if(mm>=60){
                mm-=60;
                hh++;
            }
            if(hh>=24)
                hh-=24;
            tanda.setTandaFinhh(new Long(hh));
            tanda.setTandaFinmm(new Long(mm));
            String ampm;
            if(hh>=12){
                hh -= 12;
                ampm = " pm";
            } else {
                ampm = " am";
            }
            String horaFin = "";
            if(hh<10)
                horaFin += "0";
            horaFin += String.valueOf(hh);
            horaFin += ":";
            if(mm<10){
                horaFin += "0";
            }
            horaFin += String.valueOf(mm);
            horaFin += ampm;
            lblHoraFinData.setText(horaFin);
        } else {
            System.out.println("entra al elseg jksndfgkjshdfg");
        }
    }
    
    private Boolean evaluarDatosRequeridos(){
        Boolean hayError = false;
        mensajeAlerta = "Se ha producido los siguientes errores:";
        if(txtPrecioEntrada.getText().isEmpty()){
            mensajeAlerta += "\n\tFalta introducir el precio de la entrada en esta sala.";
            hayError = true;
        } else {
            try{
                Integer precio = Integer.valueOf(txtPrecioEntrada.getText());
            } catch(NumberFormatException | ClassCastException ex){
                hayError = true;
                mensajeAlerta += "\n\tSe están ingresando datos alfabéticos como precio.";
            }
        }
        if(timePickerHoraIni.getValue()==null){
            hayError = true;
            mensajeAlerta += "\n\tFalta registrar la hora de ingreso a esta tanda.";
        }
        if(tanda.getMovieId()==null){
            hayError = true;
            mensajeAlerta += "\n\tFalta seleccionar la película que estará en cartelera durante esta tanda.";
        }
        if(!hayError)
            mensajeAlerta = null;
        return hayError;
    }
    
    private void salir(){
        unbindTanda();
        tanda = null;
        FlowController.getInstance().closeDialog();
    }

    //FXML Methods
    @FXML
    private void btnCancelarAction(ActionEvent event) {
        salir();
    }

    @FXML
    private void btnGuardarAction(ActionEvent event) {
        if(!evaluarDatosRequeridos()){
            try{
                tanda.setSalaId((SalaDto) AppContext.getInstance().get("AdminShowingSala"));
                Respuesta resp = tandaService.guardarTanda(tanda);
                if(!resp.getEstado()){
                    ((SimpleBooleanProperty) AppContext.getInstance().get("AdminNewTandaProperty")).set(true);
                    salir();
                } else {
                    System.out.println("Se ha producido un error guardando la nueva tanda.\nError: " + resp.getMensaje());
                }
            } catch(Exception ex){
                System.out.println("Se ha producido un error guardando la nueva tanda.\nError: " + ex);
            }
        } else {
            System.out.println("Hacen falta datos necesarios para poder crear la nueva tanda.\n" + mensajeAlerta);
        }
    }

    @FXML
    private void btnSeleccionarMovieAction(ActionEvent event) {
        checkAvailableMovies();
    }
    
}
