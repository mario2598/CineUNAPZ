/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.model;

import cineuna.util.LocalDateAdapter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private String movieNombre;
    @XmlTransient
    private String movieResena;
    @XmlTransient
    private String movieUrlesp;
    @XmlTransient
    private String movieUrleng;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlTransient
    private LocalDate movieDate;
    @XmlTransient
    private String movieEstado;
    @XmlTransient
    private String moviePortada;
    @XmlTransient
    private Long movieDuracion;
    @XmlTransient
    private List<ComprobanteDto> comprobanteList = new ArrayList<>();
    @XmlTransient
    private List<TandaDto> tandaList = new ArrayList<>();
    @XmlTransient
    private List<ReviewDto> reviewList = new ArrayList<>();
     
    //Constructors     
    public MovieDto() {
        
    }
    
    //Methods
    public void duplicateData(MovieDto m){
        this.movieId = m.getMovieId();
        this.movieNombre = m.getMovieNombre();
        this.movieResena = m.getMovieResena();
        this.movieUrlesp = m.getMovieUrlesp();
        this.movieUrleng = m.getMovieUrleng();
        this.movieDate = m.getMovieDate();
        this.movieEstado = m.getMovieEstado();
        this.moviePortada = m.getMoviePortada();
        this.movieDuracion = m.getMovieDuracion();
    }
    
    //Setters and Getters
    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getMovieNombre() {
        return movieNombre;
    }

    public void setMovieNombre(String movieNombre) {
        this.movieNombre = movieNombre;
    }

    public String getMovieResena() {
        return movieResena;
    }

    public void setMovieResena(String movieResena) {
        this.movieResena = movieResena;
    }

    public String getMovieUrlesp() {
        return movieUrlesp;
    }

    public void setMovieUrlesp(String movieUrlesp) {
        this.movieUrlesp = movieUrlesp;
    }

    public String getMovieUrleng() {
        return movieUrleng;
    }

    public void setMovieUrleng(String movieUrleng) {
        this.movieUrleng = movieUrleng;
    }

    public LocalDate getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(LocalDate movieDate) {
        this.movieDate = movieDate;
    }

    public String getMovieEstado() {
        return movieEstado;
    }

    public void setMovieEstado(String movieEstado) {
        this.movieEstado = movieEstado;
    }

    public String getMoviePortada() {
        return moviePortada;
    }

    public void setMoviePortada(String moviePortada) {
        this.moviePortada = moviePortada;
    }

    public Long getMovieDuracion() {
        return movieDuracion;
    }

    public void setMovieDuracion(Long movieDuracion) {
        this.movieDuracion = movieDuracion;
    }

    public List<ComprobanteDto> getComprobanteList() {
        return comprobanteList;
    }

    public void setComprobanteList(List<ComprobanteDto> comprobanteList) {
        this.comprobanteList = comprobanteList;
    }

    public List<TandaDto> getTandaList() {
        return tandaList;
    }

    public void setTandaList(List<TandaDto> tandaList) {
        this.tandaList = tandaList;
    }

    public List<ReviewDto> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<ReviewDto> reviewList) {
        this.reviewList = reviewList;
    }
    
    
}
