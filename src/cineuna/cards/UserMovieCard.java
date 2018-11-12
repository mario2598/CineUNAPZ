/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

import cineuna.model.MovieDto;
import cineuna.util.AppContext;
import cineuna.util.DateUtil;
import cineuna.util.FlowController;
import com.jfoenix.controls.JFXRippler;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Chris
 */
public class UserMovieCard extends Card{
    //Attributes
    private MovieDto movie;
    private ObjectProperty<LocalDate> dateProp;
    //Size Standar
    private final Integer width = 26*6;
    private final Integer height = 36*6;
    //Graphic Components
    private ImageView ImgView;
    private Label nameLbl, dateLbl, ripplerLbl;
    
    //Constructors
    public UserMovieCard() {
        
    }
    
    public UserMovieCard(MovieDto movie){
        this.movie = movie;
    }
    
    //Methods
    @Override
    public void initCard() {
        if(!this.isInitialized()){
            this.setPrefSize(width, height);
            this.getChildren().add(initPoster());
            this.getChildren().add(initNameLbl());
            this.getChildren().add(initDateLbl());
            this.getChildren().add(initRipplerEffect());
            this.setInitialized(true);
        }
    }
    
    private ImageView initPoster(){
        try {
            String imgPath = "src\\cineuna\\resources\\images\\" + movie.getMovieNombre() +".jpg";
            File file = new File(imgPath);
            movie.crearImagenDesdeByte();
            if(file.exists())
                ImgView = new ImageView(movie.abrirImagen());
            else
                ImgView = new ImageView(new Image("cineuna/resources/images/VenomPoster.jpg"));
            ImgView.setPreserveRatio(false);
            ImgView.setFitWidth(width);
            ImgView.setFitHeight(height);
            return ImgView;
        } catch (IOException ex) {
            Logger.getLogger(AdminMovieCard.class.getName()).log(Level.SEVERE, null, ex);
            ImgView = new ImageView();
            return ImgView;
        }
    }
    
    private Label initNameLbl(){
        nameLbl = new Label();
        //Styling se quita cuando haya css
        nameLbl.setStyle("-fx-text-fill: white; -font-size: 20px;");
        nameLbl.setPrefSize(width, 40);
        nameLbl.setLayoutY(height);
        nameLbl.setTextAlignment(TextAlignment.CENTER);
        nameLbl.setAlignment(Pos.CENTER);
        nameLbl.textProperty().bind(movie.movieNombre);
        return nameLbl;
    }
    
    private Label initDateLbl(){
        this.dateProp = movie.movieDate;
        dateLbl = new Label();
        //Styling se quita cuando haya css
        dateLbl.setStyle("-fx-text-fill: white; -font-size: 20px;");
        dateLbl.setPrefSize(width, 40);
        dateLbl.setLayoutY(height + 40);
        dateLbl.setTextAlignment(TextAlignment.CENTER);
        dateLbl.setAlignment(Pos.CENTER);
        dateLbl.setText(DateUtil.LocalDate2String(movie.getMovieDate()));
        this.dateProp.addListener((observable, oldValue, newValue) -> dateLbl.setText(DateUtil.LocalDate2String((LocalDate) newValue)));
        return dateLbl;
    }
    
    private JFXRippler initRipplerEffect(){
        ripplerLbl = new Label();
        ripplerLbl.setPrefSize(width, height + 80);
        ripplerLbl.setOnMouseClicked((t) -> {
            if(t.getButton().equals(MouseButton.PRIMARY)){
                AppContext.getInstance().set("UserShowingMovie", this.movie);
                FlowController.getInstance().goView("UsuInfoPelicula");
            }
            t.consume();
        });
        JFXRippler rip = new JFXRippler(ripplerLbl);
        return rip;
    }
    
    public void enableRipplerEffect(Boolean bool){
        ripplerLbl.setVisible(bool);
    }

    public MovieDto getMovie() {
        return movie;
    }

    public void setMovie(MovieDto movie) {
        this.movie = movie;
    }
}
