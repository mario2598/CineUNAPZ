/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

import cineuna.model.ButacaDto;
import cineuna.util.AppContext;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
public class CampoButaca extends Label{
    
    private ButacaDto butaca;
    private Boolean status;
    private SimpleBooleanProperty ocupada;
    private MaterialDesignIconView icon;
    private IntegerProperty dimension;
    private SimpleBooleanProperty seleccionada;
    private SimpleIntegerProperty asientos;

    public CampoButaca(Integer dim,Boolean ocupada,Boolean activa) {
        inicializaVariables(dim,ocupada,activa);

        this.setPrefSize(dim, dim);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setAlignment(Pos.CENTER);
        
        
        
        this.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(!this.ocupada.get())
                this.seleccionada.set(!this.seleccionada.get());
            }
        });
        
        this.ocupada.set(ocupada);
        this.status = false;
        
        
        inicializaIcono(dim,ocupada,activa);
        iniciarListenerButaca();
    }
    
    private void inicializaVariables(Integer dim,Boolean ocupada,Boolean activa){
        asientos=(SimpleIntegerProperty) AppContext.getInstance().get("asientos");
        dimension = new SimpleIntegerProperty(dim);
        dimension = new SimpleIntegerProperty(dim);
        this.ocupada = new SimpleBooleanProperty(ocupada);
        this.seleccionada=new SimpleBooleanProperty();
    }
    
    private void inicializaIcono(Integer dim,Boolean disponible,Boolean activa){
        if(activa){
            MaterialDesignIconView iconButaca = new MaterialDesignIconView(MaterialDesignIcon.READABILITY);
            iconButaca.setBoundsType(TextBoundsType.LOGICAL_VERTICAL_CENTER);
            iconButaca.setSize(String.valueOf(dim*1.1));
            iconButaca.setFill(Paint.valueOf("#000000"));
            this.icon = iconButaca;
            this.getStylesheets().add("cineuna/cards/StyleCards.css");
            if(disponible)
                icon.getStyleClass().add("campo-butaca"); 
            else icon.getStyleClass().add("campo-butaca"); 
                
            this.setGraphic(icon);
        
        }
    }
    
    private void iniciarListenerButaca(){
        seleccionada.addListener(e->{
            icon.getStyleClass().clear();
            icon.setSize(String.valueOf(dimension.get()*1.1));
            //cambiarDimension(dimension.get());
            if(seleccionada.get()){
               icon.getStyleClass().add("campo-butaca-sel");
                //System.out.println("asientos campo butaca: "+asientos);
               asientos.set(asientos.get()+1);
            }
            else{
               icon.getStyleClass().add("campo-butaca");
               asientos.set(asientos.get()-1);
            }
        });
        ocupada.addListener(l->{
            if(ocupada.get()){
                icon.getStyleClass().add("campo-butaca-ocupada");
            }
            else{
               icon.getStyleClass().add("campo-butaca"); 
            }
        });
    }
    
    public void cambiarDimension(Integer dim){
        this.setPrefSize(dim, dim);
        icon.setSize(String.valueOf(dim*1.1));
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
