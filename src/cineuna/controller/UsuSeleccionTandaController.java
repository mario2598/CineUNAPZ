/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.cards.UserEspacioButaca;
import cineuna.model.ButacaDto;
import cineuna.model.ComprobanteDto;
import cineuna.model.MovieDto;
import cineuna.model.ReservaDto;
import cineuna.model.SalaDto;
import cineuna.model.TandaDto;
import cineuna.model.UsuarioDto;
import cineuna.service.ButacaService;
import cineuna.service.ComprobanteService;
import cineuna.service.ReservaService;
import cineuna.service.SalaService;
import cineuna.util.AppContext;
import cineuna.util.FlowController;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class UsuSeleccionTandaController extends Controller implements Initializable {

    //FXML Attributes
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
    @FXML
    private BorderPane bpButacas;
    
    //Attributes
    private final ReadOnlyDoubleProperty stageWidthProp = FlowController.getInstance().getStage().widthProperty();
    private final ReadOnlyDoubleProperty stageHeightProp = FlowController.getInstance().getStage().heightProperty();
    private final SimpleBooleanProperty huboCambios = new SimpleBooleanProperty();
    private final ButacaService butacaService = new ButacaService();
    private final ReservaService reservaService = new ReservaService();
    private Integer columnas;
    private Integer filas;
    private Hilo hilo;
    private ArrayList<UserEspacioButaca> espacioButacaList;
    private ArrayList<ComprobanteDto> compList;
    private ArrayList<ButacaDto> butacaList;
    private ArrayList<ReservaDto> reservaList;
    private Boolean procesando = false;
    //Entidades para el comprobante
    private UsuarioDto usuario;
    private SalaDto sala;
    private TandaDto tanda;
    private MovieDto movie;
    private LocalDate fecha;
    private String idioma;
    
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
                procesando = true;
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        cargarListaReservas();
                        System.out.print("csdczdc");
                        distribuirReservas();
                        eliminarReservasViejas();
                        refrescarCamposButaca();
                        refreshData();
                    }
                });
                try {
                   
                    procesando = false;
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UsuSeleccionTandaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bpButacas.widthProperty().addListener((observable, oldValue, newValue)->{
            if(!espacioButacaList.isEmpty() && newValue!=null){
                Double anchura = ((stageWidthProp.get()-350)*0.75)/columnas;
                Double altura = ((stageHeightProp.get()-100)*0.65)/filas;
                Integer dimButaca;
                if(anchura > altura)
                    dimButaca = altura.intValue();
                else
                    dimButaca = anchura.intValue();
                espacioButacaList.stream().forEach(butaca -> {
                    butaca.cambiarDimension(dimButaca);
                });
            }
        });
        huboCambios.addListener((observable, oldValue, newValue) -> {
            refreshData();
        });
        AppContext.getInstance().set("HuboCambiosSeleccionButacas", huboCambios);
        ArrayList<ReservaDto> currentReservas = new ArrayList<>();
        AppContext.getInstance().set("currentReservas", currentReservas);
        AppContext.getInstance().set("procesando", procesando);
    }    

    @Override
    public void initialize() {
        clearData();
        this.usuario = AppContext.getInstance().getUsuario();
        this.movie = (MovieDto) AppContext.getInstance().get("UserShowingMovie");
        this.tanda = (TandaDto) AppContext.getInstance().get("UserSelectedTanda");
        this.sala = ((SalaDto) ((Respuesta) (new SalaService()).getSala(tanda.getSalaId().getSalaId())).getResultado("Sala"));
        this.fecha = (LocalDate) AppContext.getInstance().get("UserSelectedDate");
        this.idioma = (String) AppContext.getInstance().get("UserSelectedIdioma");
        cargarListaButacas(sala.getSalaId());
        aplicarDistribucion();
        refreshData();
        hilo=new Hilo();
        hilo.start();
    }
    
    //Methods
    private void aplicarDistribucion() {
        columnas = sala.getSalaCol().intValue();
        filas = sala.getSalaFilas().intValue();
        apReserva.setPrefColumns(columnas);
        apReserva.setPrefRows(filas);
        apReserva.getChildren().clear();
        espacioButacaList.clear();
        Integer dimButaca;
        Double anchura = ((stageWidthProp.get()-350)*0.75)/columnas;
        Double altura = ((stageHeightProp.get()-100)*0.65)/filas;
        if(anchura > altura)
            dimButaca = altura.intValue();
        else
            dimButaca = anchura.intValue();
        if(dimButaca>0){
            butacaList.sort((b1, b2) -> {
                if(b1.getButFila()>b2.getButFila())
                    return 1;
                else if(b1.getButFila()<b2.getButFila())
                    return -1;
                else if(b1.getButColumna()>b2.getButColumna())
                    return 1;
                else if(b1.getButColumna()<b2.getButColumna())
                    return -1;
                else 
                    return 0;
            });
            butacaList.stream().forEach(butaca -> {
                UserEspacioButaca espacioB = new UserEspacioButaca(dimButaca);
                espacioB.setButaca(butaca);
                espacioB.refreshStatus();
                espacioButacaList.add(espacioB);
                apReserva.getChildren().add(espacioB);
            });
        } else {
            System.out.println("No se ha aplicado la distribucion porque dimButaca <= 0");
        }
    }
    
    private void cargarListaButacas(Long salaID){
        Respuesta resp = butacaService.getListaButacasSala(salaID);
        if(resp.getEstado()){
            this.butacaList = ((ArrayList<ButacaDto>) resp.getResultado("ButacaList"));
        } else {
            System.out.println("Error cargando la lista de butacas.");
        }
    }
    
    /**
     * cargar lista de reservas para tanda
     */
    public void cargarListaReservas(){
        ReservaService rs=new ReservaService();
        Respuesta res = rs.getListReservas(tanda.getTandaId());
        if(res.getEstado()){
            reservaList.addAll((ArrayList<ReservaDto>) res.getResultado("ReservasList"));
        }
        else System.out.println("no se pudo cargar la lista de reservas para esta tanda");        
    }
    
    private void distribuirReservas(){
        reservaList.stream().forEach(r->{
            espacioButacaList.stream().filter(b->{
                if(b.getButaca()!=null){
                    return b.getButaca().getButId().equals(r.getButId().getButId());
                }
                else return false;
            }).forEach(cb->cb.setReserva(r));
        });
    }
    
    private void eliminarReservasViejas(){
        espacioButacaList.stream().filter(cb->cb.getReserva()!=null).forEach(cbf->{
            if(!reservaList.contains(cbf.getReserva())){
                cbf.setReserva(null);
            }
        });
    }
    
    private void refrescarCamposButaca(){
        espacioButacaList.stream().forEach(eb -> eb.cambiarEstado());
    }
    
    private void refreshData(){
        lblCosto.setText(tanda.getTandaCobro().toString());
        Integer nAsientos = ((ArrayList<ReservaDto>) AppContext.getInstance().get("currentReservas")).size();
        lblAsientos.setText(nAsientos.toString());
        lblTotal.setText(String.valueOf(nAsientos * tanda.getTandaCobro()));
    }
    
    private void clearData(){
        lblCosto.setText("?");
        lblAsientos.setText("?");
        lblTotal.setText("?");
        espacioButacaList = new ArrayList<>();
        butacaList = new ArrayList<>();
        reservaList = new ArrayList<>();
    }
    
    private void generarReservasFinales(){
        compList = new ArrayList<>();
        try{
            ((ArrayList<ReservaDto>) AppContext.getInstance().get("currentReservas")).stream().forEach(reserva -> {
                ReservaService rs = new ReservaService();
                reserva.setResEstado("O");
                Respuesta res = rs.guardarReserva(reserva);//cambiar a reserva
                if(res.getEstado()){
                    ComprobanteDto c = new ComprobanteDto();
                    c.setCompCosto(tanda.getTandaCobro().toString());
                    c.setMovieId(movie.getMovieId().toString());
                    c.setSalaId(sala.getSalaId().toString());
                    c.setCompDate(LocalDate.now());
                    c.setUsuId(usuario.getUsuId().toString());
                    c.setButId(reserva.getButId().getButId().toString());
                    compList.add(c);
                }
            });
            
            ((ArrayList<ReservaDto>) AppContext.getInstance().get("currentReservas")).clear();
            refrescarCamposButaca();
            huboCambios.set(!huboCambios.get());
        }catch(Exception e){
            System.out.println("problema guardando estado de butaca.\nError: " + e);
        }
    }
    
    private void cancelarReservas(){
        try{
            ((ArrayList<ReservaDto>) AppContext.getInstance().get("currentReservas")).stream().forEach(reserva -> {
                reserva.setResEstado("D");
                ReservaService rs = new ReservaService();
                Respuesta res = rs.eliminarReserva(reserva);//cambiar a reserva
            });
        }
        catch(Exception e){
            System.out.println("problema eliminando reserva.\tError: " + e);
        }
    }
    private void comprobantesFinales(){
        
        Boolean veri = false;
        compList.stream().forEach(comp -> {
              ComprobanteService cs = new ComprobanteService();
              cs.guardarComp(comp);
            });
    }
    private void salir(){     
        hilo.activado = false;
       
        FlowController.getInstance().goView("UsuInicio");
    }

    @FXML
    private void reservar(ActionEvent event) {
        hilo.stop();
        hilo.suspend();
        hilo.detener();
        generarReservasFinales();
        comprobantesFinales();
        salir();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        cancelarReservas();
        salir();
    }
    
}
