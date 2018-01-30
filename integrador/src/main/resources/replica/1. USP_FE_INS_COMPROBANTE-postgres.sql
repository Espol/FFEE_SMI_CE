-- Function: usp_fe_ins_comprobante(character varying, character varying, character varying, timestamp without time zone, character varying, character varying, character varying, character varying, character varying, integer, integer, text, character varying, character varying, integer, character varying, text, integer, integer, integer, integer, character varying, character varying, character varying, integer, character varying, text, text, character varying, integer, double precision, character varying, character varying, character varying, character varying)

-- DROP FUNCTION usp_fe_ins_comprobante(character varying, character varying, character varying, timestamp without time zone, character varying, character varying, character varying, character varying, character varying, integer, integer, text, character varying, character varying, integer, character varying, text, integer, integer, integer, integer, character varying, character varying, character varying, integer, character varying, text, text, character varying, integer, double precision, character varying, character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION usp_fe_ins_comprobante(
    pruc character varying,
    pnro_sri character varying,
    pdoc_referencia character varying,
    pfecha_emision timestamp without time zone,
    ptipo_doc character varying,
    ptipo_doc_referencia character varying,
    pclave_acceso character varying,
    pnro_autorizacion character varying,
    pfecha_autorizacion character varying,
    pcontabilizado integer,
    panulado integer,
    pxml text,
    ppath_xml character varying,
    ppath_pdf character varying,
    pescenario integer,
    pestado character varying,
    plog text,
    ppdf integer,
    pemail integer,
    precepcionado integer,
    pautorizado integer,
    pusuario character varying,
    pterminal character varying,
    pemail_destino character varying,
    ptipo_emision integer,
    pobservacion character varying,
    pdocument_xml text,
    pmensaje text,
    pnombre_documento character varying,
    pnotificacion integer,
    pmonto_total double precision,
    pinterlocutor character varying,
    ptipo character varying,
    psociedad character varying,
    prazon_social character varying,
    pEsquema_proc integer
    
    )
  RETURNS integer AS
$BODY$
DECLARE
	log_tmp character varying; -- log de inconsistencia, o rpta sri.
BEGIN
	
	UPDATE facele.COMPROBANTE SET ULTIMO = 0 
	 WHERE nro_sri =  pnro_sri
	   AND tipo_doc =  ptipo_doc
	   AND ruc =  pruc;

	IF pescenario = 4 THEN -- Escenario es inconsistente?
		SELECT pmensaje INTO log_tmp;
	ELSE
		SELECT plog INTO log_tmp;
	END IF;
	
	
	INSERT INTO facele.comprobante
	(
           id_comprobante
           ,ruc
           ,nro_sri
           ,doc_referencia
           ,fecha_emision
           ,tipo_doc
           ,tipo_doc_referencia
           ,clave_acceso
           ,nro_autorizacion
           ,fecha_autorizacion
           ,contabilizado
           ,anulado
           ,xml
           ,path_xml
           ,path_pdf
           ,escenario
           ,estado
           ,log
           ,pdf
           ,email
           ,recepcionado
           ,autorizado
           ,usuario
           ,terminal
           ,email_destino
           ,fecha_registro
           ,tipo_emision
           ,observacion
           ,document_xml
           ,mensaje
           ,nombre_documento
           ,notificacion
           ,importe_total -- monto_total
           ,COD_INTERLOCUTOR
           --,tipo     origen en hivimar: CASE WHEN documento.tipo_documento = '07' THEN 'P' ELSE 'C' END AS tipo,
           -- ,sociedad origen hivimar: '1000' AS sociedad,
           ,razon_social
           
           ,esquema_proc
           
		   ,ultimo
	)
	VALUES(
	NEXTVAL('facele.sep_comprobante'),
       pruc,
       pnro_sri,
       pdoc_referencia,
       pfecha_emision,
       ptipo_doc,
       ptipo_doc_referencia,
       pclave_acceso,
       pnro_autorizacion,
       pfecha_autorizacion,
       pcontabilizado,
       panulado,
       pxml,
       ppath_xml,
       ppath_pdf,
       pescenario,
       pestado,
       log_tmp,
       ppdf,
       pemail,
       precepcionado,
       pautorizado,
       pusuario,
       pterminal,
       pemail_destino,
      CURRENT_TIMESTAMP,
       ptipo_emision,
       pobservacion,
       pdocument_xml,
       pmensaje,
       pnombre_documento,
       pnotificacion,
       pmonto_total,
       pinterlocutor,
       -- ptipo,
       -- psociedad,
       prazon_social,
       
       pEsquema_proc,
       
	  1
	);
	  
	  RETURN 1;
	  
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
