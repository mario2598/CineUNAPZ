/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

import cineuna.model.ButacaDto;
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
public class AdminEspacioButaca extends Label{
    
    private ButacaDto butaca;
    private Boolean status;
    private MaterialDesignIconView icon;
    private IntegerProperty dimension;
    private Boolean editable = true;

    public AdminEspacioButaca(Integer dim) {
        dimension = new SimpleIntegerProperty(dim);
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
                if(editable)
                    toggleStatus();
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

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }
    
    
}
