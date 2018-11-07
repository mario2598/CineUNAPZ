/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.model;

import cineuna.util.LocalDateAdapter;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author mario
 */
@XmlRootElement(name = "TandaDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class TandaDto {
     private Long tandaId;
     private Long tandaCobro;
     @XmlJavaTypeAdapter(LocalDateAdapter.class)
     private LocalDate tandaHinicio;
     @XmlJavaTypeAdapter(LocalDateAdapter.class)
     private LocalDate tandaHfin;
     private MovieDto movieId;
     private SalaDto salaId;

    public TandaDto() {
    }
    
      

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

    public LocalDate getTandaHinicio() {
        return tandaHinicio;
    }

    public void setTandaHinicio(LocalDate tandaHinicio) {
        this.tandaHinicio = tandaHinicio;
    }

    public LocalDate getTandaHfin() {
        return tandaHfin;
    }

    public void setTandaHfin(LocalDate tandaHfin) {
        this.tandaHfin = tandaHfin;
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
    
    public void setMovieId(MovieDto movieId) {
        this.movieId = movieId.getMovieId();
    }

    public Long getSalaId() {
        return salaId;
    }

    public void setSalaId(SalaDto salaId) {
        this.salaId = salaId.getSalaId();
    }
    */
      
}
