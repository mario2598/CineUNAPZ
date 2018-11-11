/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

import cineuna.model.ButacaDto;
import cineuna.model.UsuarioDto;
import cineuna.service.ButacaService;
import cineuna.util.AppContext;
import cineuna.util.Respuesta;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.text.TextBoundsType;

/**
 *
 * @author Chris
 */
public class CampoButaca extends Label{
    
    private ButacaDto butaca;
    private UsuarioDto usuario;
    private MaterialDesignIconView icon;
    private IntegerProperty dimension;
    private SimpleIntegerProperty asientos;//property sincronizada con la view de selección de butacas
    private SimpleBooleanProperty seleccionada;
    private SimpleBooleanProperty disponible;
    private Boolean activa;
    private Boolean propia;

    public CampoButaca(Integer dim,ButacaDto butaca) {
        inicializaVariables(dim,butaca);
        inicializaIcono(dim);
        //estética
        this.setPrefSize(dim, dim);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setAlignment(Pos.CENTER);
        
        //cambia el estado de seleccionada si está disponible o es seleccionada y propia
        this.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(this.disponible.get()||propia)
                    this.seleccionada.set(!this.seleccionada.get());
            }
        });
        
        //refrescaEstado();
        
        //this.disponible.set(disponible);
        //this.seleccionada.set(seleccionada);

        //inicializaIcono(dim);
        //seleccionaEstadoInicial(seleccionada,propia);
        //iniciarListenerButaca();
    }
    
    private void inicializaVariables(Integer dim,ButacaDto butaca){
        this.icon=new MaterialDesignIconView(MaterialDesignIcon.READABILITY);
        this.icon.setBoundsType(TextBoundsType.LOGICAL_VERTICAL_CENTER);
        this.icon.getStyleClass().clear();
        this.butaca=butaca;
        asientos=(SimpleIntegerProperty) AppContext.getInstance().get("asientos");//la misma que maneja selección de butacas para calcular costo
        dimension = new SimpleIntegerProperty(dim);
        this.disponible = new SimpleBooleanProperty(true);
        this.seleccionada=new SimpleBooleanProperty(false);
        if(butaca.getButActiva()=="A")
            activa=true;
        else
            activa=false;
        this.usuario=AppContext.getInstance().getUsuario();//usada para averiguar si el usuario la había seleccionado
    }
    
    public void refrescaEstado(){
            refrescaButaca();
            this.icon.getStyleClass().clear();
            this.disponible.set(true);
            this.seleccionada.set(false);
            activa=false;
            propia=false;
            String estado="";
        if(butaca.getButActiva().equalsIgnoreCase("A")){
                activa=true;//estaba activa
                estado="A";
                if(butaca.getButEstado().equalsIgnoreCase("D")){
                    disponible.set(true);
                    icon.getStyleClass().add("campo-butaca");
                    estado="D";
                }else if(butaca.getButEstado().equalsIgnoreCase("S")){
                    seleccionada.set(true);//estaba seleccionada
                    estado="S";
                    if(usuario.isSeleccionada(this)){//pregunta si el usuario la tenía seleccionada
                        propia = true;
                        icon.getStyleClass().add("campo-butaca-sel");
                        estado="SP";
                    }
                    else{
                        icon.getStyleClass().add("campo-butaca-sel-otro");
                        estado="SO";
                    }
                }
                else{
                    icon.getStyleClass().add("campo-butaca-ocupada");
                    estado="O";
                }
            }
        else{
            estado="I";
        }
        
        //System.out.println("\nestado:"+butaca.getButLetra()+"-->"+estado);
            
        //refrescaEstilo();
    }
    
    private void refrescaButaca(){
        ButacaService bs=new ButacaService();
        Respuesta res= bs.getButaca(butaca.getButId());
        if(res.getEstado())
            butaca=(ButacaDto) res.getResultado("Butaca");
    }
    
    private void inicializaIcono(Integer dim){
        if(butaca.getButActiva().equalsIgnoreCase("A")){
            //iconButaca.setSize(String.valueOf(dim*1.1));
            //iconButaca.setFill(Paint.valueOf("#000000"));
            this.getStylesheets().add("cineuna/cards/StyleCards.css");
            icon.getStyleClass().clear();
            this.setGraphic(icon);
        }
    }
    
    private void iniciarListenerButaca(){
        seleccionada.addListener(e->{
            if(activa){//&& seleccionador==AppContext.getInstance().getUsuario();
                icon.getStyleClass().clear();
                icon.setSize(String.valueOf(dimension.get()*1.1));
                //cambiarDimension(dimension.get());
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
        usuario.pushSeleccionada(this);
        icon.getStyleClass().add("campo-butaca-sel");
        asientos.set(asientos.get()+1);
        butaca.setButEstado("S");//cambiar a reserva
    }
    
    public void desSeleccionaButaca(){
        usuario.popSeleccionada(this);
        icon.getStyleClass().add("campo-butaca");
        asientos.set(asientos.get()-1);
        butaca.setButEstado("D");//cambiar a reserva
    }
    
    public void guardarButaca(){
        try{
            ButacaService bs = new ButacaService();
            Respuesta res = new Respuesta();
            res = bs.guardarButaca(butaca);//cambiar a reserva
        }
        catch(Exception e){
            System.out.println("problema guardando estado de butaca");
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
 
    public Long getButacaId(){
        return this.getButacaId();
    }
}
