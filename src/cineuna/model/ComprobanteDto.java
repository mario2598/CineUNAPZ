/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.model;

import cineuna.util.LocalDateAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ComprobanteDto implements Serializable {

    private static final long serialVersionUID = 1L;
    @XmlTransient
     public Long compId;
    @XmlTransient
     public StringProperty compCosto;
    @XmlTransient
     public StringProperty butId;
    @XmlTransient
     public StringProperty movieId;
    @XmlTransient
     public StringProperty salaId;
    @XmlTransient
     public StringProperty usuId;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate compDate;
    
    public ComprobanteDto() {  
      
        compCosto = new SimpleStringProperty();
        butId = new SimpleStringProperty();
        movieId = new SimpleStringProperty();
        salaId = new SimpleStringProperty();
        usuId = new SimpleStringProperty();
    }
    
     public void duplicateData(ComprobanteDto c){
        this.compId =c.getCompId();
        this.compCosto.set(c.getCompCosto());
        this.butId.set(c.getButId());
        this.movieId.set(c.getMovieId());
        this.salaId.set(c.getSalaId());
        this.usuId.set(c.getUsuId());
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }
     public String getCompCosto() {
        return compCosto.get();
    }

    public void setCompCosto(String compCosto) {
        this.compCosto.set(compCosto);
    }
    public String getButId() {
        return butId.get();
    }

    public void setButId(String butId) {
        this.butId.set(butId);
    }
     public String getMovieId() {
        return movieId.get();
    }

    public void setMovieId(String movieId) {
        this.movieId.set(movieId);
    }
    public String getSalaId() {
        return salaId.get();
    }

    public void setSalaId(String salaId) {
        this.salaId.set(salaId);
    }
    
    public String getUsuId() {
        return usuId.get();
    }

    public void setUsuId(String usuId) {
        this.usuId.set(usuId);
    }


    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getCompDate() {
        return compDate;
    }
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setCompDate(LocalDate compDate) {
        this.compDate = compDate;
    }


}
