/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

import cineuna.model.ButacaDto;
import cineuna.service.ButacaService;
import cineuna.util.AppContext;
import cineuna.util.Respuesta;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextBoundsType;

/**
 *
 * @author Chris
 */
public class CampoButaca extends Label{
    
    private ButacaDto butaca;
    private Boolean status;
    private SimpleBooleanProperty disponible;
    private MaterialDesignIconView icon;
    private IntegerProperty dimension;
    private SimpleBooleanProperty seleccionada;
    private SimpleIntegerProperty asientos;
    private ArrayList<ButacaDto> butacasSeleccionadas;

    public CampoButaca(Integer dim,Boolean disponible,Boolean activa,Boolean seleccionada,Boolean propia) {
        inicializaVariables(dim,disponible,activa,seleccionada);

        this.setPrefSize(dim, dim);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setAlignment(Pos.CENTER);
        
        this.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(this.disponible.get()||propia)
                    this.seleccionada.set(!this.seleccionada.get());
            }
        });
        
        this.disponible.set(disponible);
        this.seleccionada.set(seleccionada);
        this.status = false;
        
        
        inicializaIcono(dim,disponible,activa,seleccionada,propia);
        seleccionaEstadoInicial(seleccionada,propia);
        iniciarListenerButaca(propia);
    }
    
    private void inicializaVariables(Integer dim,Boolean disponible,Boolean activa,Boolean seleccionada){
        this.icon=new MaterialDesignIconView();
        butacasSeleccionadas = (ArrayList<ButacaDto>) AppContext.getInstance().get("butacasSeleccionadas");
        asientos=(SimpleIntegerProperty) AppContext.getInstance().get("asientos");
        dimension = new SimpleIntegerProperty(dim);
        this.disponible = new SimpleBooleanProperty(true);
        this.seleccionada=new SimpleBooleanProperty(false);
    }
    
    private void inicializaIcono(Integer dim,Boolean disponible,Boolean activa,Boolean seleccionada,Boolean propia){
        if(activa){
            MaterialDesignIconView iconButaca = new MaterialDesignIconView(MaterialDesignIcon.READABILITY);
            iconButaca.setBoundsType(TextBoundsType.LOGICAL_VERTICAL_CENTER);
            //iconButaca.setSize(String.valueOf(dim*1.1));
            //iconButaca.setFill(Paint.valueOf("#000000"));
            this.icon = iconButaca;
            this.getStylesheets().add("cineuna/cards/StyleCards.css");
            icon.getStyleClass().clear();
            seleccionaEstiloInicial(disponible,seleccionada,propia);
               
            this.setGraphic(icon);
        
        }
    }
    
    private void seleccionaEstiloInicial(Boolean disponible,Boolean seleccionada,Boolean propia){
        if(disponible)
            icon.getStyleClass().add("campo-butaca");
        else if(seleccionada){
            if(propia)
              icon.getStyleClass().add("campo-butaca-sel");//seleccionada propia
            else
              icon.getStyleClass().add("campo-butaca-sel-otro");//seleccionada otro  
        }
        else
           icon.getStyleClass().add("campo-butaca-ocupada");//reservada    
    }
    
    private void iniciarListenerButaca(Boolean propia){
        seleccionada.addListener(e->{
            if(butaca.getButActiva().equalsIgnoreCase("A")){//&& seleccionador==AppContext.getInstance().getUsuario();
            icon.getStyleClass().clear();
            icon.setSize(String.valueOf(dimension.get()*1.1));
            cambiarDimension(dimension.get());//revisar esto
            if(seleccionada.get()){
               seleccionaButaca();
            }
            else{
               desSeleccionaButaca();
            }
            guardarButaca();
            }
        });
        
        disponible.addListener(l->{
            if(disponible.get()){
                icon.getStyleClass().add("campo-butaca");
            }
            else{
               icon.getStyleClass().add("campo-butaca-ocupada"); 
            }
        });
    }
    
    private void seleccionaButaca(){
        icon.getStyleClass().add("campo-butaca-sel");
        asientos.set(asientos.get()+1);
        butaca.setButEstado("S");
        butacasSeleccionadas.add(butaca);
        // System.out.println("Butaca añadida:"+butacasSeleccionadas.size());
    }
    
    private void desSeleccionaButaca(){
        icon.getStyleClass().add("campo-butaca");
        asientos.set(asientos.get()-1);
        butaca.setButEstado("D");
        butacasSeleccionadas.remove(butaca);
        //System.out.println("Butaca eliminada:"+butacasSeleccionadas.size());
    }
    
    private void seleccionaEstadoInicial(Boolean seleccionada,Boolean propia){
        if(seleccionada&&propia){
            seleccionaButaca();
        }
    }
    
    private void guardarButaca(){
            ButacaService bs = new ButacaService();
            Respuesta res = new Respuesta();
            res = bs.guardarButaca(butaca);
            if(res.getEstado()){
                //System.out.println("actulización en butaca");
            }
            else{
                //butaca.setButEstado("D");
                //System.out.println("fallo actulización en butaca");
            }
    }
    
    public void cambiarDimension(Integer dim){
        this.setPrefSize(dim, dim);
        //icon.setSize(String.valueOf(dim*1.1));
    }

    //Getters and Setters
    public ButacaDto getButaca() {
        return butaca;
    }

    public void setButaca(ButacaDto butaca) {
        this.butaca = butaca;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public MaterialDesignIconView getIcon() {
        return icon;
    }

    public void setIcon(MaterialDesignIconView icon) {
        this.icon = icon;
    }

    public IntegerProperty getDimension() {
        return dimension;
    }

    public void setDimension(IntegerProperty dimension) {
        this.dimension = dimension;
    }
 
}
