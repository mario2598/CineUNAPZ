/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.model;

import cineuna.util.LocalDateAdapter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author mario
 */
@XmlRootElement(name = "MovieDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class MovieDto {
    //Attributes
    @XmlTransient
    private Long movieId;
    @XmlTransient
    public StringProperty movieNombre;
    @XmlTransient
    public StringProperty movieResena;
    @XmlTransient
    public StringProperty movieUrlesp;
    @XmlTransient
    public StringProperty movieUrleng;
    @XmlTransient
    public SimpleStringProperty movieDuracion;
    @XmlTransient
    public SimpleStringProperty movieIdioma;
    @XmlTransient
    public SimpleStringProperty moviePortada;
    //@XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlTransient
    public ObjectProperty<LocalDate> movieDate;
    @XmlTransient
    private String movieEstado;
    @XmlTransient
    private String movieTipo;
    @XmlTransient
    private ArrayList<ComprobanteDto> comprobanteList = new ArrayList<>();
    @XmlTransient
    private ArrayList<TandaDto> tandaList = new ArrayList<>();
    @XmlTransient
    private ArrayList<ReviewDto> reviewList = new ArrayList<>();
    @XmlTransient
    public SimpleStringProperty movieNombreing;
    @XmlTransient
    public SimpleStringProperty movieResenaing;
    private String movieUrlimg;
    
     
    //Constructors     
    public MovieDto() {
        movieNombre = new SimpleStringProperty();
        movieNombreing = new SimpleStringProperty();
        movieResena = new SimpleStringProperty();
        movieResenaing = new SimpleStringProperty();
        movieUrlesp = new SimpleStringProperty();
        movieUrleng = new SimpleStringProperty();
        movieDate = new SimpleObjectProperty<>();
        moviePortada = new SimpleStringProperty();
        movieDuracion = new SimpleStringProperty();
        movieIdioma = new SimpleStringProperty();
        movieNombreing = new SimpleStringProperty();
        movieResenaing = new SimpleStringProperty();
    }
    
    //Methods
    public void duplicateData(MovieDto m){
        this.movieId = m.getMovieId();
        this.setMovieNombre(getMovieNombre());
        this.setMovieResena(m.getMovieResena());
        this.setMovieUrlesp(m.getMovieUrlesp());
        this.setMovieUrleng(m.getMovieUrleng());
        this.movieDate.set(m.getMovieDate());
        this.movieEstado = m.getMovieEstado();
        this.setMoviePortada(m.getMoviePortada());
        this.setMovieDuracion(m.getMovieDuracion());
        this.setMovieIdioma(m.getMovieIdioma());
        this.setMovieNombreing(m.getMovieNombreing());
        this.setMovieResenaing(m.getMovieResenaing());
        this.movieUrlimg = m.getMovieUrlimg();
    }
    
    public void crearImagenDesdeByte() throws FileNotFoundException, IOException{
        String outPutFile = "src\\cineuna\\resources\\images\\" + movieNombre.getValue()+".jpg";
        File someFile = new File(outPutFile);
        byte[] b = Base64.getDecoder().decode(movieUrlimg);
        try (FileOutputStream fos = new FileOutputStream(someFile)) {
            fos.write(b);
            fos.flush();
        }
        this.moviePortada.set(outPutFile);
    }

    
    public void guardarImagenByte(File file) throws FileNotFoundException{
        //File file = new File(path);
        try{
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            try {
                for (int readNum; (readNum = fis.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum);
                }
            } catch (IOException ex) {

            }
            byte[] by = bos.toByteArray();
            movieUrlimg =Base64.getEncoder().encodeToString(by); 
        //   System.out.println("imagen creada: "+Arrays.toString(movieUrlimg));
        }
        catch(NullPointerException e){
                System.out.println("Imagen seleccionada nula");
        }
    }
    
    public Image abrirImagen(){
        //System.out.println();
        return new Image("cineuna/resources/images/"+this.movieNombre.getValue()+".jpg");
    }
    
    //Setters and Getters
    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getMovieNombre() {
        return movieNombre.get();
    }

    public void setMovieNombre(String movieNombre) {
        this.movieNombre.set(movieNombre);
    }

    public String getMovieResena() {
        return movieResena.get();
    }

    public void setMovieResena(String movieResena) {
        this.movieResena.set(movieResena);
    }

    public String getMovieUrlesp() {
        return movieUrlesp.get();
    }

    public void setMovieUrlesp(String movieUrlesp) {
        this.movieUrlesp.set(movieUrlesp);
    }

    public String getMovieUrleng() {
        return movieUrleng.get();
    }

    public void setMovieUrleng(String movieUrleng) {
        this.movieUrleng.set(movieUrleng);
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getMovieDate() {
        return movieDate.get();
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setMovieDate(LocalDate movieDate) {
        this.movieDate.set(movieDate);
    }

//    @XmlJavaTypeAdapter(LocalDateAdapter.class)
//    public LocalDate getEmpFingreso() {
//        return empFingreso.get();
//    }
//
//    @XmlJavaTypeAdapter(LocalDateAdapter.class)
//    public void setEmpFingreso(LocalDate empFingreso) {
//        this.empFingreso.set(empFingreso);
//    }
    
    public String getMovieEstado() {
        return movieEstado;
    }

    public void setMovieEstado(String movieEstado) {
        this.movieEstado = movieEstado;
    }

    public String getMoviePortada() {
        return moviePortada.get();
    }

    public void setMoviePortada(String moviePortada) {
        this.moviePortada.set(moviePortada);
    }

    public Long getMovieDuracion() {
        if(movieDuracion.get()!=null)
            return Long.valueOf(movieDuracion.get());
        else
            return null;
    }

    public void setMovieDuracion(Long movieDuracion) {
        this.movieDuracion.set(movieDuracion.toString());
    }
    @XmlTransient
    public ArrayList<ComprobanteDto> getComprobanteList() {
        return comprobanteList;
    }

    public void setComprobanteList(ArrayList<ComprobanteDto> comprobanteList) {
        this.comprobanteList = comprobanteList;
    }
     @XmlTransient
    public ArrayList<TandaDto> getTandaList() {
        return tandaList;
    }

    public void setTandaList(ArrayList<TandaDto> tandaList) {
        this.tandaList = tandaList;
    }
     @XmlTransient
    public ArrayList<ReviewDto> getReviewList() {
        return reviewList;
    }

    public void setReviewList(ArrayList<ReviewDto> reviewList) {
        this.reviewList = reviewList;
    }
    
    public String getMovieTipo() {
        return movieTipo;
    }

    public void setMovieTipo(String movieTipo) {
        this.movieTipo = movieTipo;
    }

    public String getMovieNombreing() {
        return movieNombreing.get();
    }

    public void setMovieNombreing(String movieNombreing) {
        this.movieNombreing.set(movieNombreing);
    }

    public String getMovieResenaing() {
        return movieResenaing.get();
    }

    public void setMovieResenaing(String movieResenaing) {
        this.movieResenaing.set(movieResenaing);
    }
    
    public String getMovieUrlimg() {
        return movieUrlimg;
    }
    public void setMovieUrlimg(String movieUrlimg) {
        this.movieUrlimg = movieUrlimg;
    }
    
    public Long getMovieIdioma(){
        if(this.movieIdioma.get()!=null){
            return Long.valueOf(this.movieIdioma.get());
        } else {
            return null;
        }
    }
    
    public void setMovieIdioma(Long idioma){
        this.movieIdioma.set(idioma.toString());
    }
    
    public String toString(){
        return this.getMovieNombre();
    }
    
}
