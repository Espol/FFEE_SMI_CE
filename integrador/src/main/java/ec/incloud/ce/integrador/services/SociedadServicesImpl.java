/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.services;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import ec.incloud.ce.integrador.bean.MailSetting;
//import com.microsoft.sqlserver.jdbc.SQLServerException;
import ec.incloud.ce.integrador.bean.Sociedad;
import ec.incloud.ce.integrador.exception.IntegradorException;
import ec.incloud.ce.integrador.mapper.SociedadMapper;
import ec.incloud.ce.integrador.util.MailSettingUtil;
import ec.incloud.ce.integrador.util.SqlConfig;
import ec.incloud.ce.notificacion.EnviarNotificacion;
import ec.incloud.ce.notificacion.MailProperties;
import ec.incloud.ce.notificacion.Notificacion;

/**
 *
 * @author Joel Povis Ocaña
 */
class SociedadServicesImpl implements SociedadServices {

    private final Logger log = Logger.getLogger(SociedadServices.class);
    private static SociedadServices instance;
    
    private SociedadServicesImpl() {
    }

    static SociedadServices create() {
        synchronized (SociedadServicesImpl.class) {
            if (instance == null) {
                instance = new SociedadServicesImpl();
            }
            return instance;
        }
    }
    
    public void updateEstadoProcesoFirma(Sociedad sociedad, int estadoProcesoCaducidad ){
        try {
            SqlConfig.getSqlSessionManager().getMapper(SociedadMapper.class).updateEstadoProcesoFirma(sociedad, estadoProcesoCaducidad);
        } catch (Exception e) {
            log.error(e);
        }
    }
    
    @Override
    public boolean notificaFirmaCaducada(Sociedad sociedad, String fechaCaducidadFirma) {
        Notificacion notifica = EnviarNotificacion.NOTIFICACION_FIRMA;
        MailSetting ms = MailSettingUtil.getInstance()
                        .toObject(sociedad.getMailSettings());
        
        String mailAdministrador = sociedad.getMailNotificacion();
        
        String paramSociedad [] = { sociedad.getRuc(), fechaCaducidadFirma };
        
        return notifica.enviarCorreo(
        							paramSociedad,
									this.getMailProperties(ms), 
                                    null, // tipo documento, no aplica para la notificación de firma caducada 
                                    null, //documento.getSerieCorrelativo()
                                    null, // documento.getMensaje(), -> mensaje adicional 
                                    mailAdministrador, // usuarios sap
                                    null,
                                    null,
                                    null);//adjunto
    }

    @Override
    public Sociedad getSociedadByFirmaCaducada(Sociedad sociedad){
        try {
            return SqlConfig.getSqlSessionManager().getMapper(SociedadMapper.class).getSociedadByFirmaCaducada(sociedad);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }
    
    @Override
    public List<Sociedad> getSociedadesByFirmaCaducada(){
        try {
            return SqlConfig.getSqlSessionManager().getMapper(SociedadMapper.class).getSociedadesByFirmaCaducada();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }
    
    @Override
    public Sociedad getSociedad(String ruc) throws IntegradorException {
        try {
            return SqlConfig.getSqlSessionManager().getMapper(SociedadMapper.class).getSociedad(ruc);
        } catch (Exception e) {
            log.error(e);
            throw this.getErrorCode(e);
        }
    }

    private IntegradorException getErrorCode(Exception e) {
        final Throwable cause = e.getCause();
        if (cause instanceof SQLException) {
        	SQLException ex = (SQLException) cause;
            return new IntegradorException(ex.getErrorCode() + "", ex.getMessage());
        }
        return new IntegradorException("99999", e.getMessage());
    }
    
    private MailProperties getMailProperties(final MailSetting ms) {
        return new MailProperties() {

            @Override
            public String getHost() {
                return ms.getHost();
            }

            @Override
            public String getPort() {
                return ms.getPort();
            }

            @Override
            public String getUsuario() {
                return ms.getUser();
            }

            @Override
            public String getPassword() {
                return ms.getPassword();
            }
        };
    }
}
