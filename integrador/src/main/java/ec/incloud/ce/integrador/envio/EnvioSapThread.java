/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.envio;

import ec.incloud.ce.integrador.bean.AckSRI;
import ec.incloud.ce.integrador.bean.Documento;
import ec.incloud.ce.integrador.bean.Mensaje;
import ec.incloud.ce.integrador.bean.SapSettings;
import ec.incloud.ce.integrador.exception.IntegradorException;
import ec.incloud.ce.integrador.services.DocumentoServices;
import ec.incloud.ce.integrador.util.AckSRIUtil;
import ec.incloud.ce.integrador.util.EstadoDocumentoEnum;
import ec.incloud.ce.sap.services.MensajeSap;
import ec.incloud.ce.sap.services.RespuestaSap;
import ec.incloud.ce.sap.services.SapFactory;
import ec.incloud.ce.sap.services.SapServices;
import org.apache.log4j.Logger;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class EnvioSapThread implements Runnable {

    private Documento documento;
    private SapSettings sap;
    private DocumentoServices documentoServices;
    private Logger log = Logger.getLogger(this.getClass());

    public EnvioSapThread(Documento documento, DocumentoServices documentoServices) {
        this.documento = documento;
        this.documentoServices = documentoServices;
    }

    @Override
    public void run() {
        boolean pdfStatus = false;

        try {
            AckSRI ackSRI = AckSRIUtil.getInstance().toObject(documento.getAckSri());
            MensajeSap msgSap = null;

            if (ackSRI.getMensaje() != null) {
                msgSap = new MensajeSap();
                for (Mensaje msg : ackSRI.getMensaje()) {
                    msgSap.setMensaje(msg.getIdentificador(),
                            msg.getTipo().equals("ERROR") ? "E" : "I",
                            msg.getAdicional(),
                            msg.getDescripcion());
                }
            }

            if (documento.getEstadoSri().equals(EstadoDocumentoEnum.AUTORIZADO.getCodigo())) {
                pdfStatus = true;
            }

            SapServices sapServices = SapFactory.createSapServices();
            sapServices.setContrasena(sap.getContrasena());
            sapServices.setIdSistema(sap.getIdSistema());
            sapServices.setIdioma(sap.getIdioma());
            sapServices.setIpServidor(sap.getIpServidor());
            sapServices.setMandante(sap.getMandante());
            sapServices.setNumeroInstancia(sap.getNumeroInstancia());
            sapServices.setSapRouter(sap.getSapRouter());
            sapServices.setUsuario(sap.getUsuario());

            RespuestaSap respuesta = sapServices.actualizarDocumento(true,
                    documento.getSociedad().getRuc(),
                    documento.getTipoDocumento(),
                    documento.getSerieCorrelativo(),
                    documento.getNumeroSap(),
                    documento.getEstadoSri().equals("NR") ? "RE" : documento.getEstadoSri(),
                    ackSRI.getNumeroAutorizacion(),
                    documento.getClaveAcceso(),
                    ackSRI.getFechaAutorizacion(),
                    documento.getUsuarioSap(),
                    pdfStatus,
                    true,
                    msgSap);
            
            documento.setEstadoSap(respuesta.getTipo());
            documento.setObservacionSap(respuesta.getDescripcion());
            
            documentoServices.actualizarEstadoEnvioSap(documento);
            log.info("Respuesta-SAP: " + respuesta);
        } catch (Exception ex) {
            log.error(ex);
            try {
                documento.setEstadoSap("ER");
                documento.setObservacionSap(ex.getMessage());
                documentoServices.actualizarEstadoEnvioErrorSap(documento);
            } catch (IntegradorException e) {
                log.error(e);
            }
        }
    }

    public SapSettings getSap() {
        return sap;
    }

    public void setSap(SapSettings sap) {
        this.sap = sap;
    }

}
