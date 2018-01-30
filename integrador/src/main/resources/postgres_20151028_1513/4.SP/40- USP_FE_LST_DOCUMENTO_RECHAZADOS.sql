
-- ok


-- DROP FUNCTION usp_fe_lst_documento_envio(character,character,integer);

--ALTER TABLE documento ADD COLUMN FECHA_NO_AUTORIZADO timestamp;



CREATE OR REPLACE FUNCTION USP_FE_LST_DOCUMENTO_RECHAZADO(
    pMaxResult INTEGER
)
 RETURNS TABLE(                 
    id_periodo documento.id_periodo%type ,
    id_sociedad documento.id_sociedad%type ,
    secuencia documento.secuencia%type ,
    ruc sociedad.ruc%type ,

    conf_sap sociedad.conf_sap%type,
    conf_mail sociedad.conf_mail%type,
    mail_destino documento.mail_destino%type,
    mail_notificacion sociedad.mail_notificacion%type,
    
    
    tipo_documento documento.tipo_documento%type ,
    establecimiento documento.establecimiento%type ,
    punto_emision documento.punto_emision%type ,
    numero documento.numero%type ,
    xml documento.xml%type ,

    pdf documento.pdf%type ,
    
    clave_acceso documento.clave_acceso%type ,
    numero_sap documento.numero_sap%type ,
    ack_sri documento.ack_sri%type ,
    usuario_sap documento.usuario_sap%type ,
    estado_sri documento.estado_sri%type ,
    
    serie_correlativo documento.serie_correlativo%type,
    
	clase_documento documento.clase_documento%type
    
)
AS $$
DECLARE 

BEGIN 

	IF pMaxResult > 0 THEN		
		RETURN	QUERY
			SELECT              
                doc.id_periodo,
                doc.id_sociedad, 
                doc.secuencia, 
                soc.ruc, 

                soc.conf_sap,
                soc.conf_mail,
                doc.mail_destino,
                soc.mail_notificacion,

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
                doc.clase_documento
        FROM PERIODO per
        INNER JOIN DOCUMENTO doc 
		ON per.id_periodo = doc.id_periodo 
                AND per.id_sociedad =  doc.id_sociedad
                AND doc.ultimo = TRUE
                AND doc.estado_sri = 'RE'
        INNER JOIN SOCIEDAD soc 
		ON doc.id_sociedad = soc.id_sociedad
        WHERE per.estado = TRUE
        ORDER BY doc.id_periodo, doc.fecha_registro
		LIMIT pMaxResult
		;
	END IF;

END;
$$ LANGUAGE plpgsql;