/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

import cineuna.model.TandaDto;
import cineuna.util.DateUtil;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Chris
 */
public class AdminTandaCard extends Card{
    //Attributes
    private TandaDto tanda;

    //Constructors
    public AdminTandaCard(TandaDto tanda) {
        this.tanda = tanda;
    }
    
    //Methods
    @Override
    public void initCard() {
        if(!this.isInitialized()){
            this.setPrefSize(70, 50);
            this.getChildren().add(initTimeLbl());
            this.setInitialized(true);
        }
    }
    
    private Label initTimeLbl(){
        Label timeLbl = new Label();
        //Styling se quita cuando haya css
        timeLbl.setStyle("-fx-text-fill: #bcbcbc; -font-size: 12px;");
        timeLbl.setPrefSize(70, 50);
        timeLbl.setTextAlignment(TextAlignment.CENTER);
        timeLbl.setAlignment(Pos.CENTER);
        timeLbl.setText(castTime());
        return timeLbl;
    }

    //Setters and Getters
    public TandaDto getTanda() {
        return tanda;
    }

    public void setTanda(TandaDto tanda) {
        this.tanda = tanda;
    }
    
    private String castTime(){
        Integer hh = tanda.getTandaInihh().intValue();
        Integer mm = tanda.getTandaInimm().intValue();
        String stringTime = "";
        String ampm;
        if(hh>=12){
            hh -= 12;
            ampm = " pm";
        } else {
            ampm = " am";
        }
        if(hh<10)
            stringTime += "0";
        stringTime += String.valueOf(hh);
        stringTime += ":";
        if(mm<10){
            stringTime += "0";
        }
        stringTime += String.valueOf(mm);
        stringTime += ampm;
        return stringTime;
    }
    
}
