/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.model;

import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
    public SimpleStringProperty tandaCobro;
    @XmlTransient
    private MovieDto movieId;
    @XmlTransient
    private SalaDto salaId;
    @XmlTransient
    private Long tandaInihh;
    @XmlTransient
    private Long tandaInimm;
    @XmlTransient
    private Long tandaFinhh;
    @XmlTransient
    private Long tandaFinmm;
    
//    private Integer horaTanda;
     
    public TandaDto() {
        this.tandaCobro = new SimpleStringProperty();
    }

    public Long getTandaId() {
        return tandaId;
    }

    public void setTandaId(Long tandaId) {
        this.tandaId = tandaId;
    }

    public Long getTandaCobro() {
        if(tandaCobro.get()!=null)
            return Long.valueOf(tandaCobro.get());
        else
            return null;
    }

    public void setTandaCobro(Long tandaCobro) {
        this.tandaCobro.set(tandaCobro.toString());
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

    public Long getTandaInihh() {
        return tandaInihh;
    }

    public void setTandaInihh(Long tandaInihh) {
        this.tandaInihh = tandaInihh;
    }

    public Long getTandaInimm() {
        return tandaInimm;
    }

    public void setTandaInimm(Long tandaInimm) {
        this.tandaInimm = tandaInimm;
    }

    public Long getTandaFinhh() {
        return tandaFinhh;
    }

    public void setTandaFinhh(Long tandaFinhh) {
        this.tandaFinhh = tandaFinhh;
    }

    public Long getTandaFinmm() {
        return tandaFinmm;
    }

    public void setTandaFinmm(Long tandaFinmm) {
        this.tandaFinmm = tandaFinmm;
    }

//    public Integer getHoraTanda() {
//        return horaTanda;
//    }
//
//    public void setHoraTanda(Integer horaTanda) {
//        this.horaTanda = horaTanda;
//    }
      
}
