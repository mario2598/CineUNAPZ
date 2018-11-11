/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
<<<<<<< HEAD
 * @author mario
 */
    @XmlRootElement(name = "ReservaDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ReservaDto{
=======
 * @author robri
 */
@XmlRootElement(name = "ButacaDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ReservaDto {
>>>>>>> 1eef5da5cadf226cf5e8502dd4eea7bea7dc7cbb
    @XmlTransient
    private String resEstado;
    @XmlTransient
    private TandaDto tandaId;
    @XmlTransient
    private Long resId;
    @XmlTransient
    private ButacaDto butId;

    public ReservaDto() {
<<<<<<< HEAD
    
    }
    
=======
    }
    
    //Methods
    public void duplicateData(ReservaDto r){
        this.butId=r.getButId();
        this.resEstado=r.getResEstado();
        this.resId=r.getResId();
        this.tandaId=r.getTandaId();
    }

>>>>>>> 1eef5da5cadf226cf5e8502dd4eea7bea7dc7cbb
    public String getResEstado() {
        return resEstado;
    }

    public void setResEstado(String resEstado) {
        this.resEstado = resEstado;
    }

    public TandaDto getTandaId() {
        return tandaId;
    }

    public void setTandaId(TandaDto tandaId) {
        this.tandaId = tandaId;
    }

    public Long getResId() {
        return resId;
    }

    public void setResId(Long resId) {
        this.resId = resId;
    }

    public ButacaDto getButId() {
        return butId;
    }

    public void setButId(ButacaDto butId) {
        this.butId = butId;
    }
<<<<<<< HEAD
      
=======
    
    
>>>>>>> 1eef5da5cadf226cf5e8502dd4eea7bea7dc7cbb
}
