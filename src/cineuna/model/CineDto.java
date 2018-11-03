/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.model;

import cineuna.util.LocalDateAdapter;
import java.time.LocalDate;
import java.util.ArrayList;
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
@XmlRootElement(name = "CineDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class CineDto { 
    //Attributes
    @XmlTransient
    private Long cineId;
    @XmlTransient
    public StringProperty cineNombre;
    @XmlTransient
    public StringProperty cineTel;
    @XmlTransient
    public StringProperty cineEmail;
    @XmlTransient
//    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate cineAbre;
    @XmlTransient
//    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate cineCierra;
    @XmlTransient
    private ArrayList<SalaDto> salaList;
    @XmlTransient
    private ArrayList<UsuarioDto> usuarioList;
    
    //Constructors
    public CineDto(){
        cineNombre = new SimpleStringProperty();
        cineTel = new SimpleStringProperty();
        cineEmail = new SimpleStringProperty();
    }
    
    //Methods
    public void duplicateData(CineDto c) {
        this.cineId = c.getCineId();
        this.setCineNombre(c.getCineNombre());
        this.setCineTel(c.getCineTel());
        this.setCineEmail(c.getCineEmail());
        this.cineAbre = c.getCineAbre();
        this.cineCierra = c.getCineCierra();
        this.salaList = c.getSalaList();
        this.usuarioList = c.getUsuarioList();
    }
    
    //Setters and Getters
    public Long getCineId() {
        return cineId;
    }

    public void setCineId(Long cineId) {
        this.cineId = cineId;
    }

    public String getCineNombre() {
        return cineNombre.get();
    }

    public void setCineNombre(String cineNombre) {
        this.cineNombre.set(cineNombre);
    }

    public Long getCineTel() {
        if(cineTel.get()!=null)
            return Long.valueOf(cineTel.get());
        else
            return null;
    }

    public void setCineTel(Long cineTel) {
        this.cineTel.set(cineTel.toString());
    }

    public String getCineEmail() {
        return cineEmail.get();
    }

    public void setCineEmail(String cineEmail) {
        this.cineEmail.set(cineEmail);
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getCineAbre() {
        return cineAbre;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setCineAbre(LocalDate cineAbre) {
        this.cineAbre = cineAbre;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getCineCierra() {
        return cineCierra;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setCineCierra(LocalDate cineCierra) {
        this.cineCierra = cineCierra;
    }

    public ArrayList<SalaDto> getSalaList() {
        return salaList;
    }

    public void setSalaList(ArrayList<SalaDto> salaList) {
        this.salaList = salaList;
    }

    public ArrayList<UsuarioDto> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(ArrayList<UsuarioDto> usuarioList) {
        this.usuarioList = usuarioList;
    }
    
}
