/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

import cineuna.model.MovieDto;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Chris
 */
public class AdminSmallMovieCard extends Card {

    //Attributes
    private MovieDto movie;
    
    //Constructors
    public AdminSmallMovieCard(){
        
    }
    
    
    public AdminSmallMovieCard(MovieDto movie){
        this.movie = movie;
    }
    
    //Methods
    @Override
    public void initCard() {
        if(!this.isInitialized()){
            this.setPrefSize(180, 50);
            this.getChildren().add(initNameLbl());
            this.setInitialized(true);
        }
    }
    
    private Label initNameLbl(){
        Label timeLbl = new Label();
        //Styling se quita cuando haya css
        timeLbl.setStyle("-fx-text-fill: #bcbcbc; -font-size: 16px;");
        timeLbl.setPrefSize(180, 50);
        timeLbl.setTextAlignment(TextAlignment.CENTER);
        timeLbl.setAlignment(Pos.CENTER);
        timeLbl.setWrapText(true);
        timeLbl.setText(movie.getMovieNombre());
        return timeLbl;
    }

    public MovieDto getMovie() {
        return movie;
    }

    public void setMovie(MovieDto movie) {
        this.movie = movie;
    }
    
    
    
}
