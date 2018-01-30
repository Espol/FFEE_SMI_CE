/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.sap.services;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class MensajeSap {
    
    private final List<Mensaje> listMensaje;
    
    public MensajeSap() {
        listMensaje = new ArrayList<>();
    }
    
    public void setMensaje(int identificador, String tipo, String adicional, String descripcion){
        listMensaje.add(new Mensaje(identificador, tipo, adicional, descripcion));
    }
    
    public List<Mensaje> getListMensaje(){
        return this.listMensaje;
    }
}
