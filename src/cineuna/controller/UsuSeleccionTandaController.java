/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.cards.CampoButaca;
import cineuna.model.ButacaDto;
import cineuna.model.ReservaDto;
import cineuna.model.TandaDto;
import cineuna.model.UsuarioDto;
import cineuna.service.ButacaService;
import cineuna.service.ReservaService;
import cineuna.util.AppContext;
import cineuna.util.LangUtils;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class UsuSeleccionTandaController extends Controller implements Initializable {

    @FXML
    private JFXDialogLayout root;
    @FXML
    private Label lblCine;
    @FXML
    private Label lblSala;
    @FXML
    private Label lblAsientos;
    @FXML
    private Label lblCosto;
    @FXML
    private TilePane apReserva;
    @FXML
    private Label lblMsjAsientos;
    @FXML
    private Label lblMsjCosto;
    @FXML
    private JFXButton btnReservar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblPantalla;
    private ArrayList<CampoButaca> butacaList;
    private ArrayList<ButacaDto> butacasDtoList;
    private ArrayList<ReservaDto> reservasDtoList;
    Integer filas;
    Integer columnas;
    private Boolean butacasDistribuidas;
    @FXML
    private BorderPane bpButacas;
    @FXML
    private HBox hbCont;
    private static SimpleIntegerProperty asientos;
    private static SimpleIntegerProperty costoTotal;
    private Integer costoPorAsiento;
    @FXML
    private FlowPane fp;
    private UsuarioDto usuario;
    private Hilo hilo;
    private ArrayList<CampoButaca> listaButacas;
    TandaDto tanda;
    ReservaDto reserva;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        butacasDtoList=new ArrayList<>();
        butacasDistribuidas=false;
        butacaList = new ArrayList<>();
        asientos=new SimpleIntegerProperty(0);
        costoTotal=new SimpleIntegerProperty(0);
        AppContext.getInstance().set("asientos",asientos);
        redimensionado();
    }    

    @Override
    public void initialize() {
        this.usuario = AppContext.getInstance().getUsuario();
        apReserva.getChildren().clear();
        asientos.set(0);
        costoTotal.set(0);
        cargarIdioma();
        cargarInfoTanda();
        cargarDistribucion();
        cargarListaButacas();
        cargarCamposButacas();
        refrescarCamposButaca();
        hilo=new Hilo();
        hilo.start();
    }
    
    public class Hilo extends Thread{
        private Boolean activado;
        
        public Hilo(){
            this.activado=true;
        }
        
        public void detener(){
            this.activado=false;
        }
        
        @Override
        public void run() {
            while(activado){
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        //apReserva.getChildren().clear();
                        //cargarDistribucion();
                        //cargarListaButacas();
                        refrescarCamposButaca();
                    }
                });
                
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UsuSeleccionTandaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
    private void redimensionado(){
        lblPantalla.widthProperty().addListener((observable, oldValue, newValue)->{
            if(!butacaList.isEmpty() && newValue!=null){
                Double anchura = bpButacas.getWidth()*0.82;
                Integer dimButaca = ((anchura.intValue())/columnas);
                butacaList.stream().forEach(butaca -> {
                    butaca.cambiarDimension(dimButaca);
                });
            }
        });
        fp.prefHeightProperty().bind(hbCont.heightProperty());
        fp.prefWidthProperty().bind(hbCont.widthProperty());
        hbCont.prefWidthProperty().bind(root.widthProperty());
        hbCont.prefHeightProperty().bind(root.heightProperty());
        bpButacas.prefHeightProperty().bind(hbCont.heightProperty());
        bpButacas.prefWidthProperty().bind(hbCont.widthProperty());
    }
    
    private void cargarIdioma(){
        Integer idioma=Integer.valueOf(AppContext.getInstance().getUsuario().usuIdioma.getValue());
        if(idioma.equals(1))
            LangUtils.getInstance().setLang("es");
        else
            LangUtils.getInstance().setLang("eng");
        
        LangUtils.getInstance().loadLabelLang(lblMsjAsientos, "lblMsjAsientos");
        LangUtils.getInstance().loadLabelLang(lblMsjCosto, "lblMsjCosto");
        LangUtils.getInstance().loadButtonLang(btnReservar, "btnReservar");
        LangUtils.getInstance().loadButtonLang(btnCancelar, "btnCancelar");
        LangUtils.getInstance().loadLabelLang(lblPantalla, "lblPantalla");
    }
    
    private void cargarInfoTanda(){
        try{
            this.tanda=(TandaDto) AppContext.getInstance().get("tandaSeleccionada");
            costoPorAsiento=tanda.getTandaCobro().intValue();
            lblAsientos.setText(asientos.getValue().toString());
            lblTotal.setText(costoTotal.getValue().toString());
            this.lblSala.setText(tanda.getSalaId().getSalaNombre());
            this.lblCosto.setText(costoPorAsiento.toString());
            asientos.addListener(l->{
                lblAsientos.setText(asientos.getValue().toString());
                costoTotal.set(asientos.getValue()*costoPorAsiento);
            });
            costoTotal.addListener(l->{
                lblTotal.setText(costoTotal.getValue().toString());
            });
        }
        catch(NullPointerException e){
            //System.out.println("Ninguna tanda seleccionada");
        }
        
        //this.lblCine.setText(tanda.getC);
    }
    
    private void cargarDistribucion(){
        try{
        filas=tanda.getSalaId().getSalaFilas().intValue();
        columnas=tanda.getSalaId().getSalaCol().intValue();
        apReserva.setPrefColumns(columnas);
        apReserva.setPrefRows(filas);
        //apReserva.getChildren().clear();
        }
        catch(NullPointerException e){

        }
    }
    
    /**
     * carga el arrayList de butacas
     */
    private void cargarListaButacas(){
        ButacaService bs= new ButacaService();
        try{
        Respuesta res= bs.getListaButacasSala(tanda.getSalaId().getSalaId());
        if(res.getEstado()){
            butacasDtoList = (ArrayList<ButacaDto>) res.getResultado("ButacaList");
        }
        }
        catch(Exception e){
        }
    }
    
    /**
     * cargar lista de reservas para tanda
     */
    public void cargarListaReservas(){
        ReservaService rs=new ReservaService();
        Respuesta res = rs.getListReservas(tanda.getTandaId());
        if(res.getEstado())
            reservasDtoList = (ArrayList<ReservaDto>) res.getResultado("ReservasList");
        else System.out.println("no se pudo cargar la lista de reservas para esta tanda");        
    }
    
    /**
     * carga las butacas y su estado (usada cada vez que refresca)
     */
    private void cargarCamposButacas(){
        //butacasSeleccionadas.clear();//ver si sirve
        butacaList.clear();
        Double anchura = bpButacas.getWidth()*0.82;
        Integer dimButaca = ((anchura.intValue())/columnas);
        butacasDtoList.sort(ButacaDto.butFilCol);
        
        for(ButacaDto b:butacasDtoList){
            CampoButaca espacioB = new CampoButaca(dimButaca,b);
            butacaList.add(espacioB);
            apReserva.getChildren().add(espacioB);
        }
    }
    
    private void refrescarCamposButaca(){
        //System.out.println("refrescando campos butacas");
        //apReserva.getChildren().clear();
        for(CampoButaca b:butacaList){
            b.refrescaEstado();
            //apReserva.getChildren().add(b);
        }
    }
    
    private void reiniciarDatos(){
        costoTotal.set(0);
        asientos.set(0);
        cargarDistribucion();
        cargarListaButacas();
    }
  
    @FXML
    private void reservar(ActionEvent event) {
        usuario.guardaButacasSeleccionadas();
        reiniciarDatos();
        refrescarCamposButaca();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        usuario.desSeleccionaButacas();
        reiniciarDatos();
        refrescarCamposButaca();
    }
    
}
