/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

import canchaspz.util.DateUtil;
import cineuna.model.MovieDto;
import com.jfoenix.controls.JFXRippler;
import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
public class AdminMovieCard extends Card{
    //Attributes
    private MovieDto movie;
    private SimpleStringProperty posterUrlProp;
    private ObjectProperty<LocalDate> dateProp;
    //Size variables
    private final Integer anchoBase = 24;
    private final Integer altoBase = 36;
    private SimpleIntegerProperty widthProp = new SimpleIntegerProperty();
    private SimpleIntegerProperty heightProp = new SimpleIntegerProperty();
    //Graphic Components
    private ImageView ImgView;
    private Label nameLbl, dateLbl, ripplerLbl;
    
    //Constructors
    public AdminMovieCard() {
        
    }
    
    /**
     * Constructor que define tamaño de la card por defecto
     * Tamaño por defecto = 6 veces el tamaño estandar de un poster
     * Tamaño estandar = 24x36
     * @param m 
     */
    public AdminMovieCard(MovieDto m) {
        this();
        this.movie = m;
        this.widthProp.setValue(anchoBase*6);
        this.heightProp.setValue(altoBase*6);
    }

    /**
     * Constructor que define tamaño a la card
     * @param m
     * @param width
     * @param height
     */
    public AdminMovieCard(MovieDto m, Double width, Double height) {
        this();
        this.movie = m;
        preserveRatioSize(width, height);
    }
    
    //Methods
    @Override
    public void initCard() {
        if(!this.isInitialized()){
            this.setPrefSize(this.widthProp.get(), this.heightProp.get() + (this.heightProp.get()*0.14)*2);
            this.getChildren().add(initPoster());
            this.getChildren().add(initNameLbl());
            this.getChildren().add(initDateLbl());
            this.getChildren().add(initRipplerEffect());
            this.setInitialized(true);
        }
    }
    
    private ImageView initPoster(){
        posterUrlProp = movie.moviePortada;
        if(movie.getMoviePortada()!=null)
            ImgView = new ImageView(new Image("cineuna/resources/images/VenomPoster.jpg"));
        else
            ImgView = new ImageView(new Image("cineuna/resources/images/VenomPoster.jpg"));
        ImgView.setPreserveRatio(false);
        ImgView.setFitWidth(this.widthProp.get());
        ImgView.setFitHeight(this.heightProp.get());
        posterUrlProp.addListener(event -> {
            refreshPoster(ImgView);
        });
        return ImgView;
    }
    
    private void refreshPoster(ImageView IV){
        IV.setImage(new Image(posterUrlProp.getValue()));
    }
    
    private Label initNameLbl(){
        nameLbl = new Label();
        //Styling se quita cuando haya css
        nameLbl.setStyle("-fx-text-fill: white; -font-size: 20px;");
        nameLbl.setPrefSize(this.widthProp.get(), this.heightProp.get()*0.14);
        nameLbl.setLayoutY(this.heightProp.get());
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
        dateLbl.setPrefSize(this.widthProp.get(), this.heightProp.get()*0.14);
        dateLbl.setLayoutY(this.heightProp.get() + this.heightProp.get()*0.14);
        dateLbl.setTextAlignment(TextAlignment.CENTER);
        dateLbl.setAlignment(Pos.CENTER);
        dateLbl.setText(DateUtil.LocalDate2String(movie.getMovieDate()));
        this.dateProp.addListener((observable, oldValue, newValue) -> dateLbl.setText(DateUtil.LocalDate2String((LocalDate) newValue)));
        return dateLbl;
    }
    
    private JFXRippler initRipplerEffect(){
        ripplerLbl = new Label();
        ripplerLbl.setPrefSize(this.widthProp.get(), this.heightProp.get() + (this.heightProp.get()*0.14)*2);
        ripplerLbl.setOnMouseClicked((t) -> {
            if(t.getButton().equals(MouseButton.PRIMARY)){
                //Evento para mostrar informacion de la movie
            } else {
                //Evento para seleccionar la cancha
                this.toggleSelected();
            }
            t.consume();
        });
        JFXRippler rip = new JFXRippler(ripplerLbl);
        return rip;
    }
    
    public void cambiarTamanho(Double width, Double height){
        preserveRatioSize(width, height);
        this.setPrefSize(this.widthProp.get(), this.heightProp.get() + (this.heightProp.get()*0.14)*2);
        ImgView.setFitWidth(this.widthProp.getValue());
        ImgView.setFitHeight(this.heightProp.get());
        nameLbl.setPrefWidth(this.widthProp.getValue());
        nameLbl.setPrefSize(this.widthProp.get(), (this.heightProp.get()*0.14));
        nameLbl.setLayoutY(this.heightProp.get());
        dateLbl.setPrefWidth(this.widthProp.getValue());
        dateLbl.setPrefSize(this.widthProp.get(), (this.heightProp.get()*0.14));
        dateLbl.setLayoutY(this.heightProp.get() + (this.heightProp.get()*0.14));
        ripplerLbl.setPrefSize(this.widthProp.get(), this.heightProp.get() + (this.heightProp.get()*0.14)*2);
    }
    
    private void preserveRatioSize(Double width, Double height){
        Double finalWidth, finalHeight;
        Double ratio = height/altoBase;
        finalWidth = anchoBase * ratio;
        if(finalWidth <= width){
            this.widthProp.setValue(finalWidth);
            this.heightProp.setValue(height);
            System.out.println("Se queda con los valores 1");
        } else {
            ratio = width/anchoBase;
            finalHeight = altoBase * ratio;
            this.widthProp.setValue(width);
            this.heightProp.setValue(finalHeight);
            System.out.println("Se queda con los valores 2");
        }
    }

    public MovieDto getMovie() {
        return movie;
    }

    public void setMovie(MovieDto movie) {
        this.movie = movie;
    }

    public SimpleIntegerProperty getWidthProp() {
        return widthProp;
    }

    public void setWidthProp(SimpleIntegerProperty widthProp) {
        this.widthProp = widthProp;
    }

    public SimpleIntegerProperty getHeightProp() {
        return heightProp;
    }

    public void setHeightProp(SimpleIntegerProperty heightProp) {
        this.heightProp = heightProp;
    }
    
}
