/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.model;

import cineuna.util.LocalDateAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
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
@XmlRootElement(name = "ComprobanteDto")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComprobanteDto implements Serializable {

    private static final long serialVersionUID = 1L;
    @XmlTransient
    private Long compId;
    @XmlTransient
    private Long compCosto;
    @XmlTransient
    private Long butId;
    @XmlTransient
    private Long movieId;
    @XmlTransient
    private Long salaId;
    @XmlTransient
    private Long usuId;
   // @XmlTransient
  //  public ObjectProperty<LocalDate> compDate;
    
     public void duplicateData(ComprobanteDto c){
        this.compId = c.getCompId();
        this.compCosto = c.getCompCosto();
        this.butId = c.getButId();
        this.movieId = c.getMovieId();
        this.salaId = c.getSalaId();
        this.usuId = c.getUsuId();
    }

    public ComprobanteDto() {
     //   this.compDate = new SimpleObjectProperty<>();
    }


    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public Long getCompCosto() {
        return compCosto;
    }

    public void setCompCosto(Long compCosto) {
        this.compCosto = compCosto;
    }

    public Long getButId() {
        return butId;
    }

    public void setButId(Long butId) {
        this.butId = butId;
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

    public Long getUsuId() {
        return usuId;
    }

    public void setUsuId(Long usuId) {
        this.usuId = usuId;
    }
   // @XmlJavaTypeAdapter(LocalDateAdapter.class)
   // public LocalDate getCompDate() {
    //    return compDate.get();
  //  }
 //   @XmlJavaTypeAdapter(LocalDateAdapter.class)
  //  public void setCompDate(LocalDate compDate) {
      //  this.compDate.set(compDate); 
   // }   

}
