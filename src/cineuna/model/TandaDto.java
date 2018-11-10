/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.model;

import cineuna.util.LocalDateAdapter;
import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
    @XmlTransient
    private MovieDto movieId;
    @XmlTransient
    private SalaDto salaId;
    private Integer horaTanda;
    @XmlTransient
    private Long tandaInihh;
    @XmlTransient
    private Long tandaInimm;
    @XmlTransient
    private Long tandaFinhh;
    @XmlTransient
    private Long tandaFinmm;
    //@XmlTransient
    //@XmlJavaTypeAdapter(LocalDateAdapter.class)
    //private ObjectProperty<LocalDate> tandaHinicio;
    //@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    //private LocalDateTime tandaHfin;
    //@XmlTransient
    //private ObjectProperty<LocalDate> tandaHfin;
     
    public TandaDto() {
        //this.tandaHinicio=new SimpleObjectProperty<>();
        //this.tandaHfin=new SimpleObjectProperty<>();
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
/*
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
    public void setTandaHfin(LocalDate tandaHinicio) {
        this.tandaHfin.set(tandaHinicio);
    }
    */
/*
    public LocalDateTime getTandaHfin() {
        return tandaHfin;
    }

    public void setTandaHfin(LocalDateTime tandaHfin) {
        this.tandaHfin = tandaHfin;
    }
*/
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

    public Integer getHoraTanda() {
        return horaTanda;
    }

    public void setHoraTanda(Integer horaTanda) {
        this.horaTanda = horaTanda;
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
      
}
