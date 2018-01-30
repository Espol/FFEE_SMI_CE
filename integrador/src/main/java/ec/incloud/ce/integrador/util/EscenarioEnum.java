/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.integrador.util;

/**
 *
 * @author Usuario
 */
public enum EscenarioEnum {
    AUTORIZADO(1), RECHAZADO(3), INCONSISTENTE(4);
    
    private int codigo;
    private EscenarioEnum(int codigo) {
        this.codigo = codigo;
    }
    
    public int getCodigo(){
        return this.codigo;
    }
}
