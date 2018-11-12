/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

import cineuna.model.ButacaDto;
import cineuna.model.ReservaDto;
import cineuna.service.ReservaService;
import cineuna.util.Respuesta;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
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
        this.getStylesheets().add("cineuna/cards/AdminStyle.css");
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
                //Evento para seleccionar una butaca momentaneamente
            }
        });
        this.status = true;
        this.setGraphic(icon);
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
    }
    
    public void cambiarEstado(){
        icon.getStyleClass().clear();
        disponible = true;
        seleccionada = false;
        ocupada = false;
        activa = false;
        if(butaca.getButActiva().equalsIgnoreCase("A")){
            activa=true;//estaba activa
            if(reserva!=null){
                switch(reserva.getResEstado()){
                    case "S":
                        seleccionada=true;
                        icon.getStyleClass().add("campo-butaca-sel");
//                        icon.getStyleClass().add("campo-butaca-sel-otro");
                        break;
                    case "O":
                        ocupada = true;
                        icon.getStyleClass().add("campo-butaca-ocupada");
                        break;
                    case "D":
                        seleccionada = false;
                        icon.getStyleClass().add("campo-butaca");
                        break;
                }
            } else {//si reserva está nula es porque está disponible
                disponible = true;
                icon.getStyleClass().add("campo-butaca"); 
            }
        }
    }
    
    public void guardarReserva(){
        try{
            ReservaService rs = new ReservaService();
            Respuesta res = rs.guardarReserva(reserva);//cambiar a reserva
            if(res.getEstado()){
               this.reserva = (ReservaDto) res.getResultado("Reserva");

            }
                
        }
        catch(Exception e){
            System.out.println("problema guardando estado de butaca");
        }
    }
    
    public void eliminarReserva(){
        try{
            ReservaService rs = new ReservaService();
            Respuesta res = rs.eliminarReserva(reserva);//cambiar a reserva
            if(res.getEstado()){
                this.reserva = null;
                cambiarEstado();
            } 
        }
        catch(Exception e){
            System.out.println("problema eliminando reserva");
        }
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
    
}
