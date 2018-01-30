/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.envio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import ec.incloud.ce.integrador.bean.Documento;
import ec.incloud.ce.integrador.bean.SapSettings;
import ec.incloud.ce.integrador.bean.Sociedad;
import ec.incloud.ce.integrador.services.DocumentoServices;
import ec.incloud.ce.integrador.services.PropertiesServices;
import ec.incloud.ce.integrador.services.ServicesFactory;
import ec.incloud.ce.integrador.services.SociedadServices;
import ec.incloud.ce.integrador.util.SapSettingUtil;
import ec.incloud.ce.integrador.util.Util;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class EnvioImpl implements Envio {

    private final Logger log = Logger.getLogger(this.getClass());
    private static Envio instance = null;
    private final DocumentoServices documentoServices = ServicesFactory.getFactory().createDocumentoServices();
    private final SociedadServices sociedadServices = ServicesFactory.getFactory().createSociedadServices();
    private final PropertiesServices confServices = ServicesFactory.getFactory().createPropertiesServices();
    
    private EnvioImpl() {
    }

    public static Envio create() {
        synchronized (EnvioImpl.class) {
            if (instance == null) {
                instance = new EnvioImpl();
            }
            return instance;
        }
    }
    
    @Override
    public void enviarDocumentoRecepcion() {
        List<Documento> lstDocumento = documentoServices.getlstDocumentoPorEnviarSriRecepcion();
        
        if (lstDocumento != null) {
            try {
                ExecutorService manager = Executors.newFixedThreadPool(confServices.getPoolEnvioSri());
                
                for (Documento documento : lstDocumento) {
                    log.info("[Enviando-Recepcion] " + documento);
                    documentoServices.actualizarIntentoEnvioRecepcion(documento);
                    manager.execute(new EnvioRecepcionThread(documento, documentoServices));
                }
                manager.shutdown();
                manager.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            } catch (Exception ex) {
                log.error("Error en envio Pool enviarDocumentoRecepcion ", ex);
            }
        }
    }

    @Override
    public void reenviarDocumentoRecepcion() {
        List<Documento> lstDocumento = documentoServices.getlstDocumentoPorReenviarSriRecepcion();
        
        if (lstDocumento != null) {
            try {
                ExecutorService manager = Executors.newFixedThreadPool(confServices.getPoolEnvioSri());

                for (Documento documento : lstDocumento) {
                    log.info("[Reenvio-Recepcion] " + documento);
                    documentoServices.actualizarIntentoEnvioRecepcion(documento);
                    manager.execute(new EnvioRecepcionThread(documento, documentoServices));
                }
                manager.shutdown();
                manager.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            } catch (Exception ex) {
                log.error("Error en envio Pool reenviarDocumentoRecepcion ", ex);
            }
        }
    }

    @Override
    public void enviarDocumentoAutorizacion() {
        List<Documento> lstDocumento = documentoServices.getlstDocumentoPorEnviarSriAutorizacion();

        if (lstDocumento != null) {
            try {
                ExecutorService manager = Executors.newFixedThreadPool(confServices.getPoolEnvioSri());

                for (Documento documento : lstDocumento) {
                    log.info("[Envio-Autorizacion] " + documento);
                    documentoServices.actualizarIntentoEnvioAutorizacion(documento);
                    manager.execute(new EnvioAutorizacionThread(documento, documentoServices));
                }
                manager.shutdown();
                manager.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            } catch (Exception ex) {
                log.error("Error en envio Pool de enviarDocumentoAutorizacion " + ex);
            }
        }
    }

    @Override
    public void reenviarDocumentoAutorizacion() {
        List<Documento> lstDocumento = documentoServices.getlstDocumentoPorReenviarSriAutorizacion();

        if (lstDocumento != null) {
            try {
                ExecutorService manager = Executors.newFixedThreadPool(confServices.getPoolEnvioSri());

                for (Documento documento : lstDocumento) {
                    log.info("[Reenvio-Autorizacion] " + documento);
                    documentoServices.actualizarIntentoEnvioAutorizacion(documento);
                    manager.execute(new EnvioAutorizacionThread(documento, documentoServices));
                }
                manager.shutdown();
                manager.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            } catch (Exception ex) {
                log.error("Error en envio Pool de reenviarDocumentoAutorizacion " + ex);
            }
        }
    }
    
    @Override
    public void enviarDocumentoSap() {
        List<Documento> lstDocumento = documentoServices.getlstDocumentoPorEnviarSap();

        if (lstDocumento != null) {
            try {
                ExecutorService manager = Executors.newFixedThreadPool(confServices.getPoolEnvioSap());
                EnvioSapThread envioSap;
                Map<String, SapSettings> ss = new HashMap<>();

                final SapSettingUtil sapSettings = SapSettingUtil.getInstance();

                SapSettings sap;
                for (Documento documento : lstDocumento) {
                    if (ss.get(documento.getSociedad().getRuc()) == null) {
                        sap = sapSettings.toObject(documento.getSociedad().getSapSettings());
                        ss.put(documento.getSociedad().getRuc(), sap);
                    } else {
                        sap = ss.get(documento.getSociedad().getRuc());
                    }
                    
                    envioSap = new EnvioSapThread(documento, documentoServices);
                    envioSap.setSap(sap);

                    log.info("Envio-SAP: " + documento);
                    documentoServices.actualizarIntentoEnvioSap(documento);
                    manager.execute(envioSap);
                }
                manager.shutdown();
                manager.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            } catch (Exception ex) {
                log.error("Error en envio Pool de envio enviarDocumentoSap ", ex);
            }
        }
    }

    @Override
    public void notificarByFirmaCaducada() {
        List<Sociedad> sociedades = sociedadServices.getSociedadesByFirmaCaducada();
        
        if ( sociedades != null && !sociedades.isEmpty() ) {
            try {
                ExecutorService manager = Executors.newFixedThreadPool(6);
                
                for (Sociedad sociedad : sociedades) {
                    log.info("[Iniciando Notificación Firma Vencida] " + sociedad );
            		sociedadServices.updateEstadoProcesoFirma(sociedad, Util.PROCESO_CADUCIDAD_FIRMA_ON );
                    manager.execute( new NotificarCaducFirmaThread(sociedadServices, sociedad));
                }
                manager.shutdown();
                manager.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            } catch (Exception ex) {
                log.error("Error en envio Pool enviarDocumentoRecepcion ", ex);
            }
        }
    }

}
