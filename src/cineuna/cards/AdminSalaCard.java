/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

import cineuna.model.SalaDto;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 *
 * @author Chris
 */
public class AdminSalaCard extends Card{

    //Attributes
    private SalaDto sala;
    
    @Override
    public void initCard() {
        this.setPrefSize(224, 70);
        this.getChildren().add(initNameLbl());
    }
    
    private Label initNameLbl(){
        Label lbl = new Label();
        lbl.setPrefSize(224, 70);
        lbl.setText(sala.getSalaNombre());
        lbl.setAlignment(Pos.CENTER);
        lbl.setFont(Font.font(30.0));
        return lbl;
    }
    
    //Getters and Setters
    public SalaDto getSala() {
        return sala;
    }

    public void setSala(SalaDto sala) {
        this.sala = sala;
    }
    
}
