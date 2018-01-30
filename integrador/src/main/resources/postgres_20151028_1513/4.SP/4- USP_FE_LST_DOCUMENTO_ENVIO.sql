-- Function: usp_fe_lst_documento_envio(character, character, integer)

-- DROP FUNCTION usp_fe_lst_documento_envio(character, character, integer);

CREATE OR REPLACE FUNCTION usp_fe_lst_documento_envio(IN pdestino character, IN pestado character, IN pmaxresult integer)
  RETURNS TABLE(id_periodo integer, id_sociedad integer, secuencia bigint, ruc character varying, razon_social character varying, conf_sap text, conf_mail text, mail_destino character varying, mail_notificacion character varying, intervalo_notificacion integer, ini_notif_rechazo integer, mail_factura character varying, mail_credito character varying, mail_debito character varying, mail_guia character varying, mail_retencion character varying, url character varying, port_impl integer, texto_ride character varying, tipo_documento character, establecimiento character, punto_emision character, numero character, xml character varying, pdf character varying, clave_acceso character, numero_sap character varying, ack_sri text, usuario_sap character varying, estado_sri character, serie_correlativo character varying, clase_documento character varying, subtipo_doc integer, obs_comprobante character varying, esquema_proc integer, mail_portal character varying, codigo_cliente character varying, ruc_cliente character varying) AS
$BODY$
DECLARE 

BEGIN 

	IF pMaxResult > 0 THEN		
		RETURN	QUERY
			SELECT              
                doc.id_periodo,
                doc.id_sociedad, 
                doc.secuencia, 
                soc.ruc, 
                soc.razon_social,
				
                soc.conf_sap,
                soc.conf_mail,
                doc.mail_destino,
                soc.mail_notificacion,
                soc.intervalo_notificacion,
                
                soc.ini_notif_rechazo,
                
                soc.mail_factura,
                soc.mail_credito,
                soc.mail_debito,
                soc.mail_guia,
                soc.mail_retencion,
                
                soc.url,
                
                soc.port_impl,--portal
                soc.texto_ride,

                doc.tipo_documento, 
                doc.establecimiento, 
                doc.punto_emision, 
                doc.numero, 
                doc.xml,

                doc.pdf,
                
                doc.clave_acceso,
                doc.numero_sap,
                doc.ack_sri,
                doc.usuario_sap,
                doc.estado_sri,
                
                doc.serie_correlativo,
                doc.clase_documento,
                
                doc.subtipo_doc,
                
                doc.obs_comprobante,
                
                doc.esquema_proc,
                
                doc.mail_portal,
                doc.codigo_cliente,
                doc.ruc_cliente
        FROM PERIODO per
        INNER JOIN DOCUMENTO doc ON per.id_periodo = doc.id_periodo 
                AND per.id_sociedad =  doc.id_sociedad
                AND doc.ultimo = TRUE		
        INNER JOIN ENVIO_DOCUMENTO envio ON doc.id_periodo = envio.id_periodo
                AND doc.id_sociedad = envio.id_sociedad
                AND doc.secuencia   = envio.secuencia
                AND envio.destino   = pDestino 
                AND envio.estado    = pEstado
        INNER JOIN SOCIEDAD soc ON doc.id_sociedad = soc.id_sociedad
        WHERE per.estado = TRUE
        ORDER BY doc.id_periodo, doc.fecha_registro
		LIMIT pMaxResult
		;
	END IF;

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION usp_fe_lst_documento_envio(character, character, integer)
  OWNER TO postgres;
