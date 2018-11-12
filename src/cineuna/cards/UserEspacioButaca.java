/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

import cineuna.model.ButacaDto;
import cineuna.model.ReservaDto;
import cineuna.model.TandaDto;
import cineuna.service.ReservaService;
import cineuna.util.AppContext;
import cineuna.util.Respuesta;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextBoundsType;

/**
 *
 * @author Chris
 */
public class UserEspacioButaca extends Label{
    
    private ButacaDto butaca;
    private ReservaDto reserva;
    private Boolean status;
    private MaterialDesignIconView icon;
    private IntegerProperty dimension;
    
    //Estados
    private Boolean activa;
    private Boolean seleccionada;
    private Boolean disponible;
    private Boolean ocupada;

    public UserEspacioButaca(Integer dim) {
        dimension = new SimpleIntegerProperty(dim);
        this.getStylesheets().add("cineuna/view/adminStyle.css");
        this.setPrefSize(dim, dim);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setAlignment(Pos.CENTER);
        MaterialDesignIconView iconButaca = new MaterialDesignIconView(MaterialDesignIcon.READABILITY);
        iconButaca.setBoundsType(TextBoundsType.LOGICAL_VERTICAL_CENTER);
        iconButaca.setSize(String.valueOf(dim*1.1));
        iconButaca.setFill(Paint.valueOf("#bcbcbc"));
        this.icon = iconButaca;
        this.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(disponible){
                    crearNuevaReserva();
                    guardarReserva();
                } else {
                   if(reserva!=null && ((ArrayList<ReservaDto>) AppContext.getInstance().get("currentReservas")).stream().anyMatch(r -> r.getResId().equals(reserva.getResId()))){
                       eliminarReserva();
                   } else {
                       System.out.println("La butaca esta reservada por otra persona.");
                   }
                }
            }
        });
    }
    
    private void toggleStatus(){
        this.status = !this.status;
        this.butaca.setButActiva(this.status ? "A":"I");
        if(status){
            this.setGraphic(icon);
        } else {
            this.setGraphic(null);
        }
    }
    
    public void refreshStatus(){
        this.status = butaca.getButActiva().equalsIgnoreCase("A");
        if(status){
            this.setGraphic(icon);
        } else {
            this.setGraphic(null);
        }
    }
    
    public void cambiarDimension(Integer dim){
        this.setPrefSize(dim, dim);
        icon.setSize(String.valueOf(dim*1.1));
        cambiarEstado();
    }
    
    public void cambiarEstado(){
        icon.getStyleClass().clear();
        if(butaca.getButActiva().equalsIgnoreCase("A")){
            activa=true;//estaba activa
            if(reserva!=null){
                switch(reserva.getResEstado()){
                    case "S":
                        disponible = false;
                        seleccionada = true;
                        ocupada = false;
                        if(((ArrayList<ReservaDto>) AppContext.getInstance().get("currentReservas")).stream().anyMatch(r -> r.getResId().equals(reserva.getResId()))){
                            icon.getStyleClass().add("campo-butaca-sel");
                        } else {
                            icon.getStyleClass().add("campo-butaca-sel-otro");
                        }
                        break;
                    case "O":
                        disponible = false;
                        seleccionada = false;
                        ocupada = true;
                        icon.getStyleClass().add("campo-butaca-ocupada");
                        break;
                    case "D":
                        disponible = true;
                        seleccionada = false;
                        ocupada = false;
                        icon.getStyleClass().add("campo-butaca");
                        break;
                }
            } else {//si reserva está nula es porque está disponible
                disponible = true;
                seleccionada = false;
                ocupada = false;
                icon.getStyleClass().add("campo-butaca"); 
            }
        } else {
            activa = false;
        }
    }
    
    public void guardarReserva(){
        try{
            ReservaService rs = new ReservaService();
            Respuesta res = rs.guardarReserva(reserva);//cambiar a reserva
            if(res.getEstado()){
               this.reserva = (ReservaDto) res.getResultado("Reserva");
                ((ArrayList<ReservaDto>) AppContext.getInstance().get("currentReservas")).add(reserva);
                cambiarEstado();
            }
            System.out.println("Ahora la lista de reservas del appContext tiene; "
                    + ((ArrayList<ReservaDto>) AppContext.getInstance().get("currentReservas")).size()
                    + " reservas.");
        }
        catch(Exception e){
            System.out.println("problema guardando estado de butaca");
        }
    }
    
    public void eliminarReserva(){
        try{
            try{
                ReservaDto reservaAux = ((ArrayList<ReservaDto>) AppContext.getInstance().get("currentReservas")).stream()
                        .filter(r -> r.getButId().equals(reserva.getButId()) && r.getTandaId().equals(reserva.getTandaId()))
                        .findFirst().get();
                if(reservaAux!=null){
                    ((ArrayList<ReservaDto>) AppContext.getInstance().get("currentReservas")).remove(reservaAux);
                }
            } catch(NullPointerException ex){
                
            }
            ReservaService rs = new ReservaService();
            Respuesta res = rs.eliminarReserva(reserva);//cambiar a reserva
            if(res.getEstado()){
                this.reserva = null;
                cambiarEstado();
            }
            System.out.println("Ahora la lista de reservas del appContext tiene; "
                    + ((ArrayList<ReservaDto>) AppContext.getInstance().get("currentReservas")).size()
                    + " reservas.");
        }
        catch(Exception e){
            System.out.println("problema eliminando reserva.\tError: " + e);
        }
    }
    
    public void crearNuevaReserva(){
        ReservaDto res = new ReservaDto();
        res.setButId(butaca);
        res.setTandaId((TandaDto) AppContext.getInstance().get("UserSelectedTanda"));
        res.setResEstado("S");
        this.reserva = res;
    }

    //Getters and Setters
    public ButacaDto getButaca() {
        return butaca;
    }

    public void setButaca(ButacaDto butaca) {
        this.butaca = butaca;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public ReservaDto getReserva() {
        return reserva;
    }

    public void setReserva(ReservaDto reserva) {
        this.reserva = reserva;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public Boolean getSeleccionada() {
        return seleccionada;
    }

    public void setSeleccionada(Boolean seleccionada) {
        this.seleccionada = seleccionada;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Boolean getOcupada() {
        return ocupada;
    }

    public void setOcupada(Boolean ocupada) {
        this.ocupada = ocupada;
    }
    
}
