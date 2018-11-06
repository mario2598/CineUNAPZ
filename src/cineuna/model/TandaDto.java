/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.model;

import cineuna.util.LocalDateAdapter;
import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author mario
 */
@XmlRootElement(name = "TandaDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class TandaDto {
    @XmlTransient
    private Long tandaId;
    @XmlTransient
    private Long tandaCobro;
    //@XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlTransient
    //private LocalDate tandaHinicio;
    private ObjectProperty<LocalDate> tandaHinicio;
    //@XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlTransient
    //private LocalDate tandaHfin;
    private ObjectProperty<LocalDate> tandaHfin;
    @XmlTransient
    private MovieDto movieId;
    @XmlTransient
    private SalaDto salaId;
    
    //Constructors
    public TandaDto() {
        
    }
    
    //Methods
    public void duplicateData(TandaDto t){
        this.tandaId = t.getTandaId();
        this.tandaCobro = t.getTandaCobro();
        //this.tandaHinicio = t.getTandaHinicio();
        //this.tandaHfin = t.getTandaHfin();
        this.tandaHfin.set(t.getTandaHfin());
        this.tandaHinicio.set(t.getTandaHinicio());
        //this.movieId = t.getMovieId();
        //this.salaId = t.getSalaId();
        this.movieId = t.getMovieId();
        this.salaId = t.getSalaId();
    }

    //Setters and Getters
    public Long getTandaId() {
        return tandaId;
    }

    public void setTandaId(Long tandaId) {
        this.tandaId = tandaId;
    }

    public Long getTandaCobro() {
        return tandaCobro;
    }

    public void setTandaCobro(Long tandaCobro) {
        this.tandaCobro = tandaCobro;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getTandaHinicio() {
        return tandaHinicio.get();
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setTandaHinicio(LocalDate tandaHinicio) {
        this.tandaHinicio.set(tandaHinicio);
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getTandaHfin() {
        return tandaHfin.get();
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setTandaHfin(LocalDate tandaHfin) {
        this.tandaHfin.set(tandaHfin);
    }

    public MovieDto getMovieId() {
        return movieId;
    }

    public void setMovieId(MovieDto movieId) {
        this.movieId = movieId;
    }

    public SalaDto getSalaId() {
        return salaId;
    }

    public void setSalaId(SalaDto salaId) {
        this.salaId = salaId;
    }
    
    /*
    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getSalaId() {
        return salaId;
    }

    public void setSalaId(Long salaId) {
        this.salaId = salaId;
    }
    */
    
}
