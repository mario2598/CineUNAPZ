/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.model;

import cineuna.util.LocalDateAdapter;
import java.time.LocalDate;
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
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlTransient
    private LocalDate tandaHinicio;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlTransient
    private LocalDate tandaHfin;
    @XmlTransient
    private Long movieId;
    @XmlTransient
    private Long salaId;
    
    //Constructors
    public TandaDto() {
        
    }
    
    //Methods
    public void duplicateData(TandaDto t){
        this.tandaId = t.getTandaId();
        this.tandaCobro = t.getTandaCobro();
        this.tandaHinicio = t.getTandaHinicio();
        this.tandaHfin = t.getTandaHfin();
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
    
}
