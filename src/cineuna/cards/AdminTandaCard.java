/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

import cineuna.model.TandaDto;
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
        timeLbl.setText("6:30 pm");
        return timeLbl;
    }

    //Setters and Getters
    public TandaDto getTanda() {
        return tanda;
    }

    public void setTanda(TandaDto tanda) {
        this.tanda = tanda;
    }
    
    
    
}
