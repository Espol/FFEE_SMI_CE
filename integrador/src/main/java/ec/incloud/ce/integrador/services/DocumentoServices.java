/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.services;

import java.util.List;

import ec.incloud.ce.integrador.bean.AckSRI;
import ec.incloud.ce.integrador.bean.ComprobantePortal;
import ec.incloud.ce.integrador.bean.Documento;
import ec.incloud.ce.integrador.bean.Usuario;
import ec.incloud.ce.integrador.exception.IntegradorException;

/**
 *
 * @author Incloud Services S.A.C.
 */
public interface DocumentoServices {
	
	public Usuario getClavePortal(String rucCliente) throws IntegradorException;
	
    public void guardar(Documento documento) throws IntegradorException;
    
    public void guardarInconsistencia(Documento documento) throws IntegradorException;
    
    public Documento getDocumentoById(String rucEmisor, String tipoDoc, String establecimiento, String puntoEmision, String numero, int secuencia);
    
    public Documento getDocumento(String rucEmisor, String tipoDoc, String establecimiento, String puntoEmision, String numero);

    public boolean generarPdf(Documento documento, AckSRI ackAutorizacion, Usuario usuario) throws IntegradorException;
    
//    public boolean generarPdf(Documento documento, AckSRI ackAutorizacion) throws IntegradorException;
    
    public boolean notificaNoAutorizadoCliente(Documento documento);
    
    public boolean renotificaRechazoAdministrador(Documento documento, String timeAvailableCorrecion);
    
//    public boolean notificaAutorizadoConfirmacionCliente(Documento documento);
////  
//  public boolean notificaAutorizadoCliente(Documento documento);
//  
//  public boolean notificaAutorizadoClienteOnline(Documento documento);
//    portal
    public boolean notificaAutorizadoConfirmacionCliente(Documento documento, Usuario usuario);
    
    public boolean notificaAutorizadoCliente(Documento documento, Usuario usuario);
    
    public boolean notificaAutorizadoClienteOnline(Documento documento, Usuario usuario);
//    portal
    
    public boolean notificaRechazoAdministrador(Documento documento);
    
    public boolean notificaInconsistenteAdministrador(Documento documento);
    
    public void actualizarFechaNoAutorizado(Documento documento) throws IntegradorException;
    
    public void actualizarIntentoEnvioRecepcion(Documento documento) throws IntegradorException;

    public void actualizarIntentoEnvioAutorizacion(Documento documento) throws IntegradorException;

    public void actualizarIntentoEnvioSap(Documento documento) throws IntegradorException;

    public void actualizarEstadoEnvioSriRecepcion(Documento documento) throws IntegradorException;

    public void actualizarEstadoEnvioSriAutorizacion(Documento documento) throws IntegradorException;

    public void actualizarEstadoEnvioSap(Documento documento) throws IntegradorException;

    public void actualizarEstadoEnvioErrorSriRecepcion(Documento documento) throws IntegradorException;

    public void actualizarEstadoEnvioErrorSriAutorizacion(Documento documento) throws IntegradorException;

    public void actualizarEstadoEnvioErrorSap(Documento documento) throws IntegradorException;

    public List<Documento> getlstDocumentoPorEnviarSriRecepcion();

    public List<Documento> getlstDocumentoPorReenviarSriRecepcion();

    public List<Documento> getlstDocumentoPorEnviarSriAutorizacion();

    public List<Documento> getlstDocumentoPorReenviarSriAutorizacion();

    public List<Documento> getlstDocumentoPorEnviarSap();

    public List<Documento> getlstDocumentoRechazadoSriAutorizacion();
    
    public void guardarPersonaPortal(Documento documento, ComprobantePortal comprobantePortal) throws IntegradorException;
    
    public void guardarUsuarioPortal(Documento documento, ComprobantePortal comprobantePortal) throws IntegradorException;
    

}
