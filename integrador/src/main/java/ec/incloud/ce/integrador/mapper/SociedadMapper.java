/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import ec.incloud.ce.integrador.bean.Sociedad;

/**
 *
 * @author Incloud Services S.A.C.
 */
public interface SociedadMapper {
	
    @Update("SELECT USP_FE_UPD_ESTADO_PROCESO_FIRMA( "
            + "#{sociedad.idSociedad}::integer,"
            + "#{estadoProcesoCaducidad}::integer"
            + ")")
    @Options(statementType = StatementType.CALLABLE)
    public void updateEstadoProcesoFirma(@Param("sociedad") Sociedad sociedad, @Param("estadoProcesoCaducidad") int estadoProcesoCaducidad);
	
    @Results({
        @Result(property = "idSociedad", column = "ID_SOCIEDAD"),
        @Result(property = "ruc", column = "RUC"),
        @Result(property = "razonSocial", column = "RAZON_SOCIAL"),
        @Result(property = "pathRoot", column = "PATH_ROOT"),
        @Result(property = "pathCertificado", column = "PATH_CERTIFICADO"),
        @Result(property = "claveCertificado", column = "CLAVE_CERTIFICADO"),
        @Result(property = "sapSettings", column = "CONF_SAP"),
        @Result(property = "mailSettings", column = "CONF_MAIL"),
        @Result(property = "mailNotificacion", column = "MAIL_NOTIFICACION"),
        @Result(property = "intervalNotifDocRechazado", column = "INTERVALO_NOTIFICACION"),
        
        @Result(property = "mailFactura", column = "MAIL_FACTURA"),
        @Result(property = "mailRetencion", column = "MAIL_RETENCION"),
        @Result(property = "mailCredito", column = "MAIL_CREDITO"),
        @Result(property = "mailDebito", column = "MAIL_DEBITO"),
        @Result(property = "mailGuia", column = "MAIL_GUIA"),
        @Result(property = "mailLiquidacionCompra", column = "MAIL_LIQUIDACIONCOMPRA"),
        
        @Result(property = "esquema", column = "ESQUEMA_PROC"),
        
        @Result(property = "diasFechaExtemporanea", column = "DIAS_FECHA_EXTEMP"),
        
        @Result(property = "portalImpl", column = "PORT_IMPL"),//PORTAL IMPLEMATACION
        
        @Result(property = "textoRide", column = "TEXTO_RIDE")
    })
    @Select("SELECT * FROM USP_FE_GET_SOCIEDAD( #{rucEmisor} )")
    @Options(statementType = StatementType.CALLABLE)
    public Sociedad getSociedad(@Param("rucEmisor") String ruc);
    
    @Results({
        @Result(property = "idSociedad", column = "ID_SOCIEDAD"),
        @Result(property = "ruc", column = "RUC"),
        @Result(property = "razonSocial", column = "RAZON_SOCIAL"),
        @Result(property = "pathRoot", column = "PATH_ROOT"),
        @Result(property = "pathCertificado", column = "PATH_CERTIFICADO"),
        @Result(property = "claveCertificado", column = "CLAVE_CERTIFICADO"),
        @Result(property = "sapSettings", column = "CONF_SAP"),
        @Result(property = "mailSettings", column = "CONF_MAIL"),
        @Result(property = "mailNotificacion", column = "MAIL_NOTIFICACION"),
        @Result(property = "vencFirma", column = "VENC_FIRMA"),
        @Result(property = "intervalNotifDocRechazado", column = "INTERVALO_NOTIFICACION"),
        @Result(property = "intervalNotifFirma", column = "INTERVAL_NOTIF_FIRMA"),
        @Result(property = "procVencFirma", column = "PROC_VENC_FIRMA"),
        @Result(property = "iniNotifFirma", column = "INI_NOTIF_FIRMA")
    })
    @Select("SELECT * FROM USP_FE_GET_SOCIEDADES_FIRMA_CADUCADA()")
    @Options(statementType = StatementType.CALLABLE)
    public List<Sociedad> getSociedadesByFirmaCaducada();
    
    @Results({
        @Result(property = "idSociedad", column = "ID_SOCIEDAD"),
        @Result(property = "ruc", column = "RUC"),
        @Result(property = "razonSocial", column = "RAZON_SOCIAL"),
        @Result(property = "pathRoot", column = "PATH_ROOT"),
        @Result(property = "pathCertificado", column = "PATH_CERTIFICADO"),
        @Result(property = "claveCertificado", column = "CLAVE_CERTIFICADO"),
        @Result(property = "sapSettings", column = "CONF_SAP"),
        @Result(property = "mailSettings", column = "CONF_MAIL"),
        @Result(property = "mailNotificacion", column = "MAIL_NOTIFICACION"),
        @Result(property = "vencFirma", column = "VENC_FIRMA"),
        @Result(property = "intervalNotifDocRechazado", column = "INTERVALO_NOTIFICACION"),
        @Result(property = "intervalNotifFirma", column = "INTERVAL_NOTIF_FIRMA"),
        @Result(property = "procVencFirma", column = "PROC_VENC_FIRMA"),
        @Result(property = "iniNotifFirma", column = "INI_NOTIF_FIRMA")
    })
    @Select("SELECT * FROM USP_FE_GET_SOCIEDAD_FIRMA_CADUCADA( #{sociedad.idSociedad} )")
    @Options(statementType = StatementType.CALLABLE)
    public Sociedad getSociedadByFirmaCaducada(@Param("sociedad") Sociedad sociedad);

}
