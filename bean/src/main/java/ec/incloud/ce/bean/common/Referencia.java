/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.incloud.ce.bean.common;

/**
 *
 * @author Usuario
 */
// DocumentoSAP en hivimar 
public class Referencia {
    private String docReferencia;
    private String tipoDocReferencia;
    private int anular;

    public void setDocReferencia(String docReferencia) {
        this.docReferencia = docReferencia;
    }

    public String getDocReferencia() {
        return docReferencia;
    }
 
    /**
     * @return the tipoDoc
     */
    public String getTipoDocReferencia() {
        return tipoDocReferencia;
    }

    

    /**
     * @param tipoDoc the tipoDoc to set
     */
    public void setTipoDocReferencia(String tipoDocReferencia) {
        this.tipoDocReferencia = tipoDocReferencia;
    }

   

    /**
     * @return the anular
     */
    public int getAnular() {
        return anular;
    }

    /**
     * @param anular the anular to set
     */
    public void setAnular(int anular) {
        this.anular = anular;
    }
    
}
