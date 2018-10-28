/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.Pane;

/**
 *
 * @author robri
 */
public abstract class Card extends Pane{
    //Attributes
    private DoubleProperty widthProperty;
    private DoubleProperty heigthProperty;
    private boolean initialized;
    private boolean selected;
    private String posterUrl;
    
    //Constructors
    public Card() {
        super();
        this.initialized = false;
        this.selected = false;
        this.posterUrl = null;
    }

    //Methods
    public abstract void initCard();
    
    public void toggleSelected(){
        this.selected = !this.selected;
        if(selected){
            setStyle("-fx-border-color: #00ff3f;"
                   + "-fx-border-radius: 5px;"
                   + "-fx-border-width: 5px;"
                   + "-fx-border-insets: -5px;");
        } else {
            setStyle("");
        }
    }
    
    public void selectedStatus(boolean status){
        this.selected = !status;
        toggleSelected();
    }

    //Getters and Setters
    public DoubleProperty getWidthProperty() {
        return widthProperty;
    }

    public void setWidthProperty(DoubleProperty widthProperty) {
        this.widthProperty = widthProperty;
    }

    public DoubleProperty getHeigthProperty() {
        return heigthProperty;
    }

    public void setHeigthProperty(DoubleProperty heigthProperty) {
        this.heigthProperty = heigthProperty;
    }
    
    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
}
}
