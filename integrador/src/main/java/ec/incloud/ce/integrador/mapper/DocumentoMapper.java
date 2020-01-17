/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import ec.incloud.ce.integrador.bean.ComprobantePortal;
import ec.incloud.ce.integrador.bean.Documento;
import ec.incloud.ce.integrador.bean.Usuario;

/**
 *
 * @author Incloud Services S.A.C.
 */
public interface DocumentoMapper {

    @Insert("SELECT USP_FE_INS_DOCUMENTO( "
            + "#{doc.sociedad.ruc},"
            + "#{doc.tipoDocumento},"
            + "#{doc.establecimiento},"
            + "#{doc.puntoEmision},"
            + "#{doc.numero},"
            + "#{doc.fechaEmision},"
            + "#{doc.fechaReferencia},"
            + "#{doc.codigoCliente},"
            + "#{doc.claveAcceso},"
            + "#{doc.xml},"
            + "#{doc.pdf},"
            + "#{doc.numeroSap},"
            + "#{doc.usuarioSap},"
            + "#{doc.terminal},"
            + "#{doc.mailDestino},"
            + "#{doc.importeTotal},"
            + "#{doc.claseDocumento},"
            + "#{doc.nombreCliente},"
            + "#{doc.subtipoDoc},"
            + "#{doc.obsComprobante},"
            + "#{doc.emailPortal},"
            + "#{doc.rucCliente}, "
            + "#{doc.pathXml} "
            + ")")
    @Options(statementType = StatementType.CALLABLE)
    public void insertarDocumento(@Param("doc") Documento documento);
    	
        @Insert("SELECT USP_FE_INS_DOCUMENTO_INCONSISTENTE( "
            + "#{doc.sociedad.ruc},"
            + "#{doc.tipoDocumento},"
            + "#{doc.establecimiento},"
            + "#{doc.puntoEmision},"
            + "#{doc.numero},"
            + "#{doc.fechaEmision},"
            + "#{doc.fechaReferencia},"
            + "#{doc.codigoCliente},"
            + "#{doc.numeroSap},"
            + "#{doc.usuarioSap},"
            + "#{doc.terminal},"
            + "#{doc.mailDestino},"
            + "#{doc.importeTotal},"
            + "#{doc.claseDocumento},"
            + "#{doc.nombreCliente},"
            + "#{doc.mensaje},"
            + "#{doc.subtipoDoc},"
            + "#{doc.xml}"
            + ")")
    @Options(statementType = StatementType.CALLABLE)
    public void insertarDocumentoInconsistencia(@Param("doc") Documento documento);
    
    @Results({
        @Result(property = "periodo.idPeriodo", column = "ID_PERIODO"),
        @Result(property = "sociedad.idSociedad", column = "ID_SOCIEDAD"),
        @Result(property = "secuencia", column = "SECUENCIA"),
        @Result(property = "fechaEmision", column = "FECHA_EMISION"),
        @Result(property = "tipoDocumento", column = "TIPO_DOCUMENTO"),
        @Result(property = "establecimiento", column = "ESTABLECIMIENTO"),
        @Result(property = "puntoEmision", column = "PUNTO_EMISION"),
        @Result(property = "numero", column = "NUMERO"),
        @Result(property = "estadoSri", column = "ESTADO"),
        @Result(property = "serieCorrelativo", column = "SERIE_CORRELATIVO"),
        @Result(property = "anulado", column = "ANULADO"),
        @Result(property = "numeroSap", column = "NUMERO_SAP"),
    })
    @Select("SELECT * FROM USP_FE_GET_DOCUMENTO( #{rucEmisor}, #{tipoDoc}, #{establecimiento}, #{puntoEmision}, #{numero} )")
    @Options(statementType = StatementType.CALLABLE)
    public Documento getDocumento(@Param("rucEmisor") String rucEmisor,
            @Param("tipoDoc") String tipoDoc,
            @Param("establecimiento") String establecimiento,
            @Param("puntoEmision") String puntoEmision,
            @Param("numero") String numero);
    
    @Results({
        @Result(property = "periodo.idPeriodo", column = "ID_PERIODO"),
        @Result(property = "sociedad.idSociedad", column = "ID_SOCIEDAD"),
        @Result(property = "secuencia", column = "SECUENCIA"),
        @Result(property = "fechaEmision", column = "FECHA_EMISION"),
        @Result(property = "tipoDocumento", column = "TIPO_DOCUMENTO"),
        @Result(property = "establecimiento", column = "ESTABLECIMIENTO"),
        @Result(property = "puntoEmision", column = "PUNTO_EMISION"),
        @Result(property = "numero", column = "NUMERO"),
        @Result(property = "estadoSri", column = "ESTADO"),
        @Result(property = "serieCorrelativo", column = "SERIE_CORRELATIVO"),
        @Result(property = "anulado", column = "ANULADO"),
        @Result(property = "estadoNotifRechazado", column = "est_notif_rech")
    })
    @Select("SELECT * FROM USP_FE_GET_DOCUMENTO_BY_ID( #{rucEmisor}, #{tipoDoc}, #{establecimiento}, #{puntoEmision}, #{numero}, #{secuencial} )")
    @Options(statementType = StatementType.CALLABLE)
    public Documento getDocumentoById(@Param("rucEmisor") String rucEmisor,
            @Param("tipoDoc") String tipoDoc,
            @Param("establecimiento") String establecimiento,
            @Param("puntoEmision") String puntoEmision,
            @Param("numero") String numero,
            @Param("secuencial") int secuencial);

    @Results({
        @Result(property = "periodo.idPeriodo", column = "ID_PERIODO"),
        @Result(property = "sociedad.idSociedad", column = "ID_SOCIEDAD"),
        @Result(property = "secuencia", column = "SECUENCIA"),
        @Result(property = "sociedad.ruc", column = "RUC"),
        @Result(property = "sociedad.razonSocial", column = "RAZON_SOCIAL"),
        @Result(property = "sociedad.sapSettings", column = "CONF_SAP"),
        @Result(property = "sociedad.mailSettings", column = "CONF_MAIL"),
        @Result(property = "sociedad.mailNotificacion", column = "MAIL_NOTIFICACION"),
        @Result(property = "sociedad.intervalNotifDocRechazado", column = "intervalo_notificacion"),
        
        @Result(property = "sociedad.iniTimeAvailableCorrecion", column = "ini_notif_rechazo"),
        
        @Result(property = "sociedad.mailFactura", column = "MAIL_FACTURA"),
        @Result(property = "sociedad.mailRetencion", column = "MAIL_RETENCION"),
        @Result(property = "sociedad.mailCredito", column = "MAIL_CREDITO"),
        @Result(property = "sociedad.mailDebito", column = "MAIL_DEBITO"),
        @Result(property = "sociedad.mailGuia", column = "MAIL_GUIA"),
        @Result(property = "sociedad.mailLiquidacionCompra", column = "MAIL_LIQUIDACIONCOMPRA"),

        @Result(property = "sociedad.url", column = "url"),
        
        @Result(property = "sociedad.portalImpl", column = "PORT_IMPL"),//portal
        
        @Result(property = "sociedad.textoRide", column = "TEXTO_RIDE"),
        
        @Result(property = "mailDestino", column = "MAIL_DESTINO"),
        @Result(property = "tipoDocumento", column = "TIPO_DOCUMENTO"),
        @Result(property = "establecimiento", column = "ESTABLECIMIENTO"),
        @Result(property = "puntoEmision", column = "PUNTO_EMISION"),
        @Result(property = "numero", column = "NUMERO"),
        @Result(property = "xml", column = "XML"),
        @Result(property = "pdf", column = "PDF"),
        @Result(property = "claveAcceso", column = "CLAVE_ACCESO"),
        @Result(property = "numeroSap", column = "NUMERO_SAP"),
        @Result(property = "ackSri", column = "ACK_SRI"),
        @Result(property = "usuarioSap", column = "USUARIO_SAP"),
        @Result(property = "estadoSri", column = "ESTADO_SRI"),
        @Result(property = "claseDocumento", column = "CLASE_DOCUMENTO"),
        @Result(property = "serieCorrelativo", column = "SERIE_CORRELATIVO"),
        @Result(property = "subtipoDoc", column = "subtipo_doc"),
        @Result(property = "obsComprobante", column = "obs_comprobante"),
        @Result(property = "esquemaProc", column = "esquema_proc"),
        @Result(property = "emailPortal", column = "mail_portal"),
        @Result(property = "codigoCliente", column = "codigo_cliente"),
        @Result(property = "rucCliente", column = "ruc_cliente"),
        @Result(property = "pathXml", column = "path_xml")
    })
    @Select("SELECT * FROM USP_FE_LST_DOCUMENTO_ENVIO( #{destino}, #{estado}, #{max} )")
    @Options(statementType = StatementType.CALLABLE)
    public List<Documento> getlstDocumentoPorEnviar(@Param("destino") String destino, @Param("estado") String estado, @Param("max") int max);
    
    @Results({
        @Result(property = "periodo.idPeriodo", column = "ID_PERIODO"),
        @Result(property = "sociedad.idSociedad", column = "ID_SOCIEDAD"),
        @Result(property = "secuencia", column = "SECUENCIA"),
        @Result(property = "sociedad.ruc", column = "RUC"),
        @Result(property = "sociedad.sapSettings", column = "CONF_SAP"),
        @Result(property = "sociedad.mailSettings", column = "CONF_MAIL"),
        @Result(property = "sociedad.mailNotificacion", column = "MAIL_NOTIFICACION"),
        @Result(property = "mailDestino", column = "MAIL_DESTINO"),
        @Result(property = "tipoDocumento", column = "TIPO_DOCUMENTO"),
        @Result(property = "establecimiento", column = "ESTABLECIMIENTO"),
        @Result(property = "puntoEmision", column = "PUNTO_EMISION"),
        @Result(property = "numero", column = "NUMERO"),
        @Result(property = "xml", column = "XML"),
        @Result(property = "pdf", column = "PDF"),
        @Result(property = "claveAcceso", column = "CLAVE_ACCESO"),
        @Result(property = "numeroSap", column = "NUMERO_SAP"),
        @Result(property = "ackSri", column = "ACK_SRI"),
        @Result(property = "usuarioSap", column = "USUARIO_SAP"),
        @Result(property = "estadoSri", column = "ESTADO_SRI"),
        @Result(property = "claseDocumento", column = "CLASE_DOCUMENTO"),
        @Result(property = "serieCorrelativo", column = "SERIE_CORRELATIVO")
    })
    @Select("SELECT * FROM USP_FE_LST_DOCUMENTO_RECHAZADO( #{max} )")
    @Options(statementType = StatementType.CALLABLE)
    public List<Documento> getlstDocumentoRechazadoSriAutorizacion(@Param("max") int max);
    
    @Update("SELECT * FROM USP_FE_UPD_DOCUMENTO_ERROR_ENVIO( "
            + "#{doc.periodo.idPeriodo},"
            + "#{doc.sociedad.idSociedad},"
            + "#{doc.secuencia},"
            + "#{destino}"
            + ")")
    @Options(statementType = StatementType.CALLABLE)
    public void actualizarEstadoEnvioError(@Param("doc") Documento documento, @Param("destino") String destino);
    
    @Update("SELECT * FROM USP_FE_UPD_DOCUMENTO_FECHA_NO_AUTORIZADO( "
            + "#{doc.sociedad.ruc},"
            + "#{doc.periodo.idPeriodo},"
            + "#{doc.sociedad.idSociedad},"
            + "#{doc.secuencia}"
            + ")")
    @Options(statementType = StatementType.CALLABLE)
    public void actualizarFechaNoAutorizado(@Param("doc") Documento documento, @Param("destino") String destino);

    @Update("SELECT USP_FE_UPD_DOCUMENTO_ERROR_ENVIO_SRI_REC( "
            + "#{doc.periodo.idPeriodo},"
            + "#{doc.sociedad.idSociedad},"
            + "#{doc.secuencia}"
            + ")")
    @Options(statementType = StatementType.CALLABLE)
    public void actualizarEstadoEnvioErrorRecepcion(@Param("doc") Documento documento);

    @Update("SELECT USP_FE_UPD_DOCUMENTO_ERROR_ENVIO_SRI_AUT( "
            + "#{doc.periodo.idPeriodo},"
            + "#{doc.sociedad.idSociedad},"
            + "#{doc.secuencia}"
            + ")")
    @Options(statementType = StatementType.CALLABLE)
    public void actualizarEstadoEnvioErrorAutorizacion(@Param("doc") Documento documento);

    @Update("SELECT USP_FE_UPD_DOCUMENTO_ESTADO_ENVIO( "
            + "#{doc.periodo.idPeriodo},"
            + "#{doc.sociedad.idSociedad},"
            + "#{doc.secuencia},"
            + "#{destino}"
            + ")")
    @Options(statementType = StatementType.CALLABLE)
    public void actualizarEstadoEnvio(@Param("doc") Documento documento, @Param("destino") String destino);

    @Update("SELECT USP_FE_UPD_DOCUMENTO_ESTADO_ENVIO_SAP( "
            + "#{doc.periodo.idPeriodo},"
            + "#{doc.sociedad.idSociedad},"
            + "#{doc.secuencia},"
            + "#{doc.estadoSap},"
            + "#{doc.observacionSap}"
            + ")")
    @Options(statementType = StatementType.CALLABLE)
    public void actualizarEstadoEnvioSap(@Param("doc") Documento documento);

    @Update("SELECT USP_FE_UPD_DOCUMENTO_ESTADO_ENVIO_SRI_REC( "
            + "#{doc.periodo.idPeriodo},"
            + "#{doc.sociedad.idSociedad},"
            + "#{doc.secuencia},"
            + "#{doc.ackSri},"
            + "#{doc.estadoSri},"
            + "#{doc.envioMail},"
            + "#{doc.escenario},"
            + "#{doc.mensaje}"
            + ")")
    @Options(statementType = StatementType.CALLABLE)
    public void actualizarAckRecepcion(@Param("doc") Documento documento);

    @Update("SELECT USP_FE_UPD_DOCUMENTO_ESTADO_ENVIO_SRI_AUT( "
            + "#{doc.periodo.idPeriodo},"
            + "#{doc.sociedad.idSociedad},"
            + "#{doc.secuencia},"
            + "#{doc.ackSri},"
            + "#{doc.estadoSri},"
            + "#{doc.pdf},"
            + "#{doc.escenario},"
            + "#{doc.envioMail},"
            + "#{doc.nroAutorizacion},"
            + "#{doc.fechaAutorizacion},"
            + "#{doc.mensaje}"
            + ")")
    @Options(statementType = StatementType.CALLABLE)
    public void actualizarAckAutorizacion(@Param("doc") Documento documento);

    @Update("SELECT USP_FE_UPD_INTENTO_ENVIO( "
            + "#{doc.periodo.idPeriodo},"
            + "#{doc.sociedad.idSociedad},"
            + "#{doc.secuencia},"
            + "#{destino}"
            + ")")
    @Options(statementType = StatementType.CALLABLE)
    public void actualizarIntentoEnvio(@Param("doc") Documento documento, @Param("destino") String destino);

    @Update("SELECT USP_FE_UPD_INTENTO_ENVIO_REC( "
            + "#{doc.periodo.idPeriodo},"
            + "#{doc.sociedad.idSociedad},"
            + "#{doc.secuencia}"
            + ")")
    @Options(statementType = StatementType.CALLABLE)
    public void actualizarIntentoEnvioRecepcion(@Param("doc") Documento documento);

    @Update("SELECT USP_FE_UPD_INTENTO_ENVIO_AUT( "
            + "#{doc.periodo.idPeriodo},"
            + "#{doc.sociedad.idSociedad},"
            + "#{doc.secuencia}"
            + ")")
    @Options(statementType = StatementType.CALLABLE)
    public void actualizarIntentoEnvioAutorizacion(@Param("doc") Documento documento);
    
    @Insert("SELECT USP_FE_INS_PERSONA_PORTAL( "
            + "#{port.rucCliente},"
            + "#{port.tipoDocCliente},"
            + "#{port.razonSocialsCliente},"
            + "#{doc.emailPortal},"
            + "#{doc.usuarioSap}"
            + ")")
    @Options(statementType = StatementType.CALLABLE)
    public void insertarPersonaPortal(@Param("doc") Documento documento, @Param("port") ComprobantePortal comprobantePortal);
    
    @Insert("SELECT USP_FE_INS_USUARIO_PORTAL( "
            + "#{port.rucCliente},"
            + "#{port.tipoDocCliente},"
            + "#{port.razonSocialsCliente},"
            + "#{doc.emailPortal},"
            + "#{doc.usuarioSap}"
            + ")")
    @Options(statementType = StatementType.CALLABLE)
    public void insertarUsuarioPortal(@Param("doc") Documento documento, @Param("port") ComprobantePortal comprobantePortal);
    
    @Results({
    	@Result(property = "idUsuario", column = "ID_USUARIO"),
        @Result(property = "idPersona", column = "ID_PERSONA"),
        @Result(property = "idRol", column = "ID_ROL"),
        @Result(property = "username", column = "USERNAME"),
        @Result(property = "clave", column = "CLAVE"),
        @Result(property = "observacion", column = "OBSERVACION"),
        @Result(property = "activo", column = "ACTIVO"),
        @Result(property = "eliminado", column = "ELIMINADO"),
        @Result(property = "correo", column = "CORREO"),
        @Result(property = "claveInicial", column = "CLAVE_INICIAL"),
        @Result(property = "usuarioSap", column = "USUARIO_SAP"),
        @Result(property = "rucPersona", column = "RUC_PERSONA"),
        @Result(property = "flagSap", column = "FLAG_SAP")
    })
    @Select("SELECT * FROM usp_fe_get_clave_portal( #{rucCliente} )")
    @Options(statementType = StatementType.CALLABLE)
    public Usuario getClavePortal(@Param("rucCliente") String rucCliente);
    
    

}
