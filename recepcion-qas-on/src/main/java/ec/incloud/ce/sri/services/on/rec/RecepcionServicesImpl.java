/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.sri.services.on.rec;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import ec.gob.sri.comprobantes.ws.on.rec.RecepcionComprobantes;
import ec.gob.sri.comprobantes.ws.on.rec.RecepcionComprobantesService;
import ec.gob.sri.comprobantes.ws.on.rec.RespuestaSolicitud;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class RecepcionServicesImpl implements RecepcionServices {

    private static RecepcionServices instance = null;
    private final Logger logger = Logger.getLogger(this.getClass());
    private final RecepcionComprobantesService services = new RecepcionComprobantesService();
    private int second = 15;

    private RecepcionServicesImpl() {
//        Map<String, Object> requestContext = ((BindingProvider) services).getRequestContext();
//        requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, this.second * 60 * 1000);
    }

    public static RecepcionServices getInstance() {
        synchronized (RecepcionServicesImpl.class) {
            if (instance == null) {
                instance = new RecepcionServicesImpl();
            }
            return instance;
        }
    }

    @Override
    public void setSecondForTimeOut(int second) {
        this.second = second;
    }

    @Override
    public RespuestaSolicitud validarComprobante(String pathAbsoluteXML) throws RecepcionException {
        RespuestaSolicitud respuesta = null;
        try {
            byte[] data = Files.readAllBytes(Paths.get(pathAbsoluteXML));
            RecepcionComprobantes recepcionComprobantes = services.getRecepcionComprobantesPort();
            Map<String, Object> requestContext = ((BindingProvider) recepcionComprobantes).getRequestContext();
        	requestContext.put("com.sun.xml.internal.ws.connect.timeout", this.second * 1000);
        	requestContext.put("com.sun.xml.internal.ws.request.timeout", this.second * 1000);
        	requestContext.put("com.sun.xml.ws.connect.timeout", this.second * 1000);
        	requestContext.put("com.sun.xml.ws.request.timeout", this.second * 1000);
            respuesta = recepcionComprobantes.validarComprobante(data);
        } catch (Exception e) {
            logger.error(e);
            throw new RecepcionException(e.getMessage());
        }
        return respuesta;
    }

}
