/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.bean.debito;


/**
 *
 * @author Rolando
 */
public class Motivo {

    protected String razon;
    protected String valor;

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon.trim();
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor.trim();
    }
}
