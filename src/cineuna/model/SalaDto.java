/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.model;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mario
 */
@XmlRootElement(name = "SalaDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class SalaDto {
    //Attributes
    @XmlTransient
    private Long salaId;
    @XmlTransient
    private String salaEstado;
    @XmlTransient
    private String salaImgfondo;
    @XmlTransient
    public StringProperty salaTipo;
    @XmlTransient
    public StringProperty salaNombre;
    @XmlTransient
    private Long salaFilas;
    @XmlTransient
    private Long salaCol;
    @XmlTransient
    private Long cineId;
    @XmlTransient
    private ArrayList<TandaDto> tandaList = new ArrayList<>();
    @XmlTransient
    private ArrayList<ButacaDto> butacaList = new ArrayList<>();
    @XmlTransient
    private ArrayList<ComprobanteDto> comprobanteList = new ArrayList<>();
    
    //Constructors
    public SalaDto() {
        salaTipo = new SimpleStringProperty();
        salaNombre = new SimpleStringProperty();
    }

    //Methods
    public void duplicateData(SalaDto s){
        this.salaId = s.getSalaId();
        this.salaEstado = s.getSalaEstado();
        this.salaImgfondo = s.getSalaImgfondo();
        this.setSalaTipo(s.getSalaTipo());
        this.setSalaNombre(s.getSalaNombre());
        this.salaFilas = s.getSalaFilas();
        this.salaCol = s.getSalaCol();  
        this.cineId = s.getCineId();
    }
    
    //Setters and Getters
    public Long getSalaId() {
        return salaId;
    }

    public void setSalaId(Long salaId) {
        this.salaId = salaId;
    }

    public String getSalaEstado() {
        return salaEstado;
    }

    public void setSalaEstado(String salaEstado) {
        this.salaEstado = salaEstado;
    }

    public String getSalaImgfondo() {
        return salaImgfondo;
    }

    public void setSalaImgfondo(String salaImgfondo) {
        this.salaImgfondo = salaImgfondo;
    }

    public String getSalaTipo() {
        return salaTipo.get();
    }

    public void setSalaTipo(String salaTipo) {
        this.salaTipo.set(salaTipo);
    }

    public String getSalaNombre() {
        return salaNombre.get();
    }

    public void setSalaNombre(String salaNombre) {
        this.salaNombre.set(salaNombre);
    }

    public Long getSalaFilas() {
        return salaFilas;
    }

    public void setSalaFilas(Long salaFilas) {
        this.salaFilas = salaFilas;
    }

    public Long getSalaCol() {
        return salaCol;
    }

    public void setSalaCol(Long salaCol) {
        this.salaCol = salaCol;
    }

    public ArrayList<TandaDto> getTandaList() {
        return tandaList;
    }

    public void setTandaList(ArrayList<TandaDto> tandaList) {
        this.tandaList = tandaList;
    }

    public ArrayList<ButacaDto> getButacaList() {
        return butacaList;
    }

    public void setButacaList(ArrayList<ButacaDto> butacaList) {
        this.butacaList = butacaList;
    }

    public ArrayList<ComprobanteDto> getComprobanteList() {
        return comprobanteList;
    }

    public void setComprobanteList(ArrayList<ComprobanteDto> comprobanteList) {
        this.comprobanteList = comprobanteList;
    }

    public Long getCineId() {
        return cineId;
    }

    public void setCineId(Long cineId) {
        this.cineId = cineId;
    }
    
}
