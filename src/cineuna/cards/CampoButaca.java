/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

import cineuna.model.ButacaDto;
import cineuna.model.ReservaDto;
import cineuna.model.TandaDto;
import cineuna.model.UsuarioDto;
import cineuna.service.ButacaService;
import cineuna.service.ReservaService;
import cineuna.util.AppContext;
import cineuna.util.Respuesta;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.text.TextBoundsType;

/**
 *
 * @author Chris
 */
public class CampoButaca extends Label{
    
    private ButacaDto butaca;
    private ReservaDto reserva;
    private UsuarioDto usuario;
    private MaterialDesignIconView icon;
    private IntegerProperty dimension;
    private SimpleIntegerProperty asientos;//property sincronizada con la view de selección de butacas
    private Boolean seleccionada;
    private SimpleBooleanProperty disponible;
    private Boolean activa;
    private Boolean propia;
    private TandaDto tanda;

    public CampoButaca(Integer dim,ButacaDto butaca) {
        this.tanda=(TandaDto) AppContext.getInstance().get("tandaSeleccionada");
        inicializaVariables(dim,butaca);
        inicializaIcono(dim);
        this.setPrefSize(dim, dim);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setAlignment(Pos.CENTER);
        
        //cambia el estado de seleccionada si está disponible o es seleccionada y propia
        this.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(activa&&(this.disponible.get()||usuario.isSeleccionada(this))){
                    eventoClick();
                }
            }
            event.consume();
        });
        
        this.disponible.set(disponible.get());
    }
    
    private void inicializaVariables(Integer dim,ButacaDto butaca){
        this.icon=new MaterialDesignIconView(MaterialDesignIcon.READABILITY);
        this.icon.setBoundsType(TextBoundsType.LOGICAL_VERTICAL_CENTER);
        this.icon.getStyleClass().clear();
        this.butaca=butaca;
        asientos=(SimpleIntegerProperty) AppContext.getInstance().get("asientos");//la misma que maneja selección de butacas para calcular costo
        dimension = new SimpleIntegerProperty(dim);
        this.disponible = new SimpleBooleanProperty(false);
        this.seleccionada=false;
        if("A".equalsIgnoreCase(butaca.getButActiva()))
            activa=true;
        else
            activa=false;
        this.usuario=AppContext.getInstance().getUsuario();//usada para averiguar si el usuario la había seleccionado
    }
    
    public void refrescaEstado(){
            //refrescaButaca();
            reservaReserva();
            this.icon.getStyleClass().clear();
            this.disponible.set(false);
            this.seleccionada=false;
            //eventoClick();
            activa=false;
            propia=false;
        if(butaca.getButActiva().equalsIgnoreCase("A")){
            activa=true;//estaba activa
            if(reserva!=null){
                switch(reserva.getResEstado()){
                    case "S":
                        seleccionada=true;
                        if(usuario.isSeleccionada(this)){//pregunta si el usuario la tenía seleccionada
                            //seleccionada.set(true);//estaba seleccionada
                            propia = true;
                            icon.getStyleClass().add("campo-butaca-sel");
                        }
                        else{
                            //seleccionada.set(false);//estaba seleccionada
                            icon.getStyleClass().add("campo-butaca-sel-otro");
                        }
                        break;
                    case "O":
                        icon.getStyleClass().add("campo-butaca-ocupada");
                        break;
                    }
                }
            else{
                disponible.set(true);
                icon.getStyleClass().add("campo-butaca"); 
            }
            }
        else{
        }
        
        //System.out.println("\nestado:"+butaca.getButLetra()+"-->"+estado);
            
        //refrescaEstilo();
    }
    
    private void reservaReserva(){
        ReservaService rs = new ReservaService();
        //Respuesta res = rs.
    }
    
    private void inicializaIcono(Integer dim){
        if(butaca.getButActiva().equalsIgnoreCase("A")){
            //iconButaca.setSize(String.valueOf(dim*1.1));
            //iconButaca.setFill(Paint.valueOf("#000000"));
            this.getStylesheets().add("cineuna/cards/StyleCards.css");
            icon.getStyleClass().clear();
            this.setGraphic(icon);
        }
    }
    
    private void eventoClick(){
                this.seleccionada=!this.seleccionada;
                icon.getStyleClass().clear();
                icon.setSize(String.valueOf(dimension.get()*1.1));
                //cambiarDimension(dimension.get());
                if(seleccionada){
                    seleccionaButaca();
                }
                else{
                    desSeleccionaButaca();
                }
    }
    
    private void seleccionaButaca(){
        usuario.pushSeleccionada(this);
        icon.getStyleClass().add("campo-butaca-sel");
        asientos.set(asientos.get()+1);
        //butaca.setButEstado("S");//cambiar a reserva
        creaReserva();
        guardarReserva();
    }
    
    public void desSeleccionaButaca(){
        usuario.popSeleccionada(this);
        icon.getStyleClass().add("campo-butaca");
        asientos.set(asientos.get()-1);
        eliminarReserva();
    }
    
    public void guardarButaca(){
        try{
            ButacaService bs = new ButacaService();
            Respuesta res = new Respuesta();
            res = bs.guardarButaca(butaca);//cambiar a reserva
            if(res.getEstado()){
                //envía comprobante con butaca.getButID();
            }
                
        }
        catch(Exception e){
            System.out.println("problema guardando estado de butaca");
        }
    }
    
    public void guardarReserva(){
        try{
            ReservaService rs = new ReservaService();
            Respuesta res = new Respuesta();
            res = rs.guardarReserva(reserva);//cambiar a reserva
            if(res.getEstado()){
               this.reserva = (ReservaDto) res.getResultado("Reserva");
               System.out.println("guardar CampoButaca");
            }
                
        }
        catch(Exception e){
            System.out.println("problema guardando estado de butaca");
        }
    }
    
    public void eliminarReserva(){
        try{
            ReservaService rs = new ReservaService();
            Respuesta res = new Respuesta();
            res = rs.eliminarReserva(reserva);//cambiar a reserva
            if(res.getEstado()){
                System.out.println("reserva eliminada");
            } 
        }
        catch(Exception e){
            System.out.println("problema eliminando reserva");
        }
    }
    
    public void cambiarDimension(Integer dim){
        this.setPrefSize(dim, dim);
        //icon.setSize(String.valueOf(dim*1.1));
    }

    private void creaReserva(){
        this.reserva = new ReservaDto();
        this.reserva.setButId(this.butaca);
        this.reserva.setTandaId(this.tanda);
        this.reserva.setResEstado("S");
    }
    
    public void reservaButaca(){
        if(this.reserva!=null){
           this.reserva.setResEstado("O");
           guardarReserva();
        }
    }
    
    public void cancelaButaca(){
        icon.getStyleClass().add("campo-butaca");
        asientos.set(asientos.get()-1);
        eliminarReserva();
    }
    
    //Getters and Setters
    public ButacaDto getButaca() {
        return butaca;
    }

    public void setButaca(ButacaDto butaca) {
        this.butaca = butaca;
    }

    public MaterialDesignIconView getIcon() {
        return icon;
    }

    public void setIcon(MaterialDesignIconView icon) {
        this.icon = icon;
    }

    public IntegerProperty getDimension() {
        return dimension;
    }

    public void setDimension(IntegerProperty dimension) {
        this.dimension = dimension;
    }
 
    public Long getButacaId(){
        return this.getButacaId();
    }

    public ReservaDto getReserva() {
        return reserva;
    }

    public void setReserva(ReservaDto reserva) {
        this.reserva = reserva;
    }
  
}
