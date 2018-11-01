/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.logic;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXScrollPane;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

/**
 *
 * @author robri
 */
public class PopUpButton extends JFXButton{
    private JFXPopup popUp;
    private VBox btnsContainer;
    private JFXScrollPane scroll;
    private AnchorPane root;
    private FontAwesomeIconView icon;
    public enum icon {FILTER,TEAMS};

    public PopUpButton( ) {
        super();
        this.popUp = new JFXPopup();
        this.btnsContainer = new VBox();
        this.scroll = new JFXScrollPane();
        this.root = new AnchorPane();
        this.icon = new FontAwesomeIconView();
    }

    /**
     * constructor del botón con texto
     * @param text texto a ingresar
     */
     
    public PopUpButton(String text) {
        super(text);
        this.popUp = new JFXPopup();
        this.btnsContainer = new VBox();
        this.scroll = new JFXScrollPane();
        this.root = new AnchorPane();
        this.icon = new FontAwesomeIconView();
     
    }
    
    /**
      * constructor del botón principal con ícono
      * @param type FILTER,TEAMS tipo de ícono
      */
     
    public PopUpButton(PopUpButton.icon type) {
        super();
        this.popUp = new JFXPopup();
        this.btnsContainer = new VBox();
        this.scroll = new JFXScrollPane();
        this.root = new AnchorPane();
        this.icon = new FontAwesomeIconView();
        switch(type){
            case FILTER:
                this.icon.setIcon(FontAwesomeIcon.FILTER);
                break;
            case TEAMS:
                this.icon.setIcon(FontAwesomeIcon.FUTBOL_ALT);
                break;
        }
        this.setGraphic(this.icon);
    }
    
     /**
      * constructor del botón con texto e ícono
      * @param text texto del botón principal
      * @param type FILTER,TEAMS tipo de ícono
      */
     
    public PopUpButton(String text,PopUpButton.icon type) {
        super(text);
        this.icon.setFill(Paint.valueOf("white"));
        this.popUp = new JFXPopup();
        this.btnsContainer = new VBox();
        this.scroll = new JFXScrollPane();
        this.root = new AnchorPane();
        this.icon = new FontAwesomeIconView();
        switch(type){
            case FILTER:
                this.icon.setIcon(FontAwesomeIcon.FILTER);
                break;
            case TEAMS:
                this.icon.setIcon(FontAwesomeIcon.FUTBOL_ALT);
                break;
        }
        this.setGraphic(this.icon);
    }
    
    /**
     * constructor del botón con texto e ícono definiendo color
     * @param text texto del botón principal
     * @param type FILTER,TEAMS tipo de ícono
     * @param color nombre del color ej:white,black,green...
     */ 
     
    public PopUpButton(String text,PopUpButton.icon type,String color) {
        super(text);
        this.icon.setFill(Paint.valueOf("white"));
        this.popUp = new JFXPopup();
        this.btnsContainer = new VBox();
        this.scroll = new JFXScrollPane();
        this.root = new AnchorPane();
        this.icon = new FontAwesomeIconView();
        switch(type){
            case FILTER:
                this.icon.setIcon(FontAwesomeIcon.FILTER);
                break;
            case TEAMS:
                this.icon.setIcon(FontAwesomeIcon.FUTBOL_ALT);
                break;
        }
        this.setGraphic(this.icon);
        this.icon.setFill(Paint.valueOf(color));
    }
    
    
    /**
     * inicializa el botón pricipal
     * @param node nodo usado por el Poppup que se mustra al darle click al botón principal
     */
    public void initPopupBtn(Node node){
        this.btnsContainer.setAlignment(Pos.CENTER);
        //this.scroll.setMaxHeight(300);
        //this.scroll.setMinWidth(80);
        
        this.setOnMouseClicked(event ->{
            this.popUp.show(node,JFXPopup.PopupVPosition.TOP,JFXPopup.PopupHPosition.LEFT,event.getX(),event.getY());
        });
        //this.root.getChildren().add(this.btnsContainer);
        //this.scroll.setContent(this.root);
        this.popUp.setPopupContent(this.btnsContainer);
    }
    
    /**
     * añade nuevos botones al popup
     * @param text texto del botón a añadir
     * @param btnAction mouse event del botón a añadir
     */
    public void addButton(String text, EventHandler<MouseEvent> btnAction){
        JFXButton newBtn = new JFXButton();
        newBtn.setText(text);
        newBtn.setOnMouseClicked(btnAction);
        newBtn.getStyleClass().add("popup-button");
        this.btnsContainer.getChildren().add(newBtn);
    }
     
}
