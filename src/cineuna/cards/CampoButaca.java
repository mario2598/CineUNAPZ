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
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextBoundsType;

/**
 *
 * @author robri
 */
public class CampoButaca extends MaterialDesignIconView {
    private IntegerProperty dimension;
    private ButacaDto butaca;
    private Boolean status;
    
    public CampoButaca(Integer dim) {
        dimension = new SimpleIntegerProperty(dim);
        this.setBoundsType(TextBoundsType.LOGICAL_VERTICAL_CENTER);
        this.setSize(String.valueOf(dim*1.1));
        this.setFill(Paint.valueOf("#000000"));
        this.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                toggleStatus();
            }
        });
        //getStylesheets().add("cineuna/cards/StyleCards.css");
        this.getStyleClass().add("campo-butaca");
        this.status = true;
        this.setIcon(MaterialDesignIcon.READABILITY);
    }
    
    private void toggleStatus(){
        this.status = !this.status;
        this.butaca.setButActiva(this.status ? "A":"I");
        if(status){
            //this.setGraphic(icon);
            this.getStyleClass().add("campo-butaca-sel");
        } else {
            this.getStyleClass().add("campo-butaca");
        }
    }
    
    public void cambiarDimension(Integer dim){
        this.setSize(String.valueOf(dim*1.1));
    }
    
    public IntegerProperty getDimension() {
        return dimension;
    }

    public void setDimension(IntegerProperty dimension) {
        this.dimension = dimension;
    }

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
    
    
}
