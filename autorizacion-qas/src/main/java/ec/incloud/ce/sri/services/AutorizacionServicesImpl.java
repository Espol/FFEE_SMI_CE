/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.sri.services;

import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import ec.gob.sri.comprobantes.ws.aut.AutorizacionComprobantesOffline;
import ec.gob.sri.comprobantes.ws.aut.AutorizacionComprobantesOfflineService;
import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class AutorizacionServicesImpl implements AutorizacionServices {

    private static AutorizacionServices instance = null;
    private final Logger logger = Logger.getLogger(this.getClass());
    private final AutorizacionComprobantesOfflineService services = new AutorizacionComprobantesOfflineService();
    private int second = 15;

    private AutorizacionServicesImpl() {
//        Map<String, Object> requestContext = ((BindingProvider) services).getRequestContext();
//        requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, this.second * 60 * 1000);
    }

    public static AutorizacionServices getInstance() {
        synchronized (AutorizacionServicesImpl.class) {
            if (instance == null) {
                instance = new AutorizacionServicesImpl();
            }
            return instance;
        }
    }

    @Override
    public void setSecondForTimeOut(int second) {
        this.second = second;
    }

    @Override
    public RespuestaComprobante autorizarComprobante(String claveAcceso) throws AutorizacionException {
        RespuestaComprobante respuesta = null;
        try {
        	AutorizacionComprobantesOffline autorizacionComprobantesOffline = services.getAutorizacionComprobantesOfflinePort();
        	Map<String, Object> requestContext = ((BindingProvider) autorizacionComprobantesOffline).getRequestContext();
        	requestContext.put("com.sun.xml.internal.ws.connect.timeout", this.second * 1000);
        	requestContext.put("com.sun.xml.internal.ws.request.timeout", this.second * 1000);
        	requestContext.put("com.sun.xml.ws.connect.timeout", this.second * 1000);
        	requestContext.put("com.sun.xml.ws.request.timeout", this.second * 1000);
        	
            respuesta = autorizacionComprobantesOffline.autorizacionComprobante(claveAcceso);
        } catch (Exception e) {
            logger.error(e);
            throw new AutorizacionException(e.getMessage());
        }
        return respuesta;
    }

}
