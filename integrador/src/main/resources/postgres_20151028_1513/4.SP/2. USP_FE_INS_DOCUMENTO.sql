-- Function: usp_fe_ins_documento(character varying, character, character, character, character, date, character varying, character varying, character, character varying, character varying, character varying, character varying, character varying, character varying, numeric, character varying, character varying, integer, character varying, character varying)

-- DROP FUNCTION usp_fe_ins_documento(character varying, character, character, character, character, date, character varying, character varying, character, character varying, character varying, character varying, character varying, character varying, character varying, numeric, character varying, character varying, integer, character varying, character varying,character varying);

CREATE OR REPLACE FUNCTION usp_fe_ins_documento(
    pruc_emisor character varying,
    ptipo_documento character,
    pestablecimiento character,
    ppunto_emision character,
    pnro_documento character,
    pfecha_emision date,
    pfecha_referencia character varying,
    pcodigo_cliente character varying,
    pclave_acceso character,
    pxml character varying,
    ppdf character varying,
    pnumero_sap character varying,
    pusuario_sap character varying,
    pterminal character varying,
    pcorreo_destino character varying,
    pimporte_total numeric,
    pclase_documento character varying,
    pnombre_cliente character varying,
    psubtipo_doc integer,
    pobs_comprobante character varying,
    pmail_portal character varying,
    pRuc_cliente character varying)
  RETURNS integer AS
$BODY$
DECLARE
  vId_sociedad     documento.id_sociedad%type;
  vId_periodo     documento.id_periodo%type;
  vSecuencia    documento.secuencia%type;
  estado_sri    DOCUMENTO.estado_sri%TYPE;
  vSerie_correlativo documento.serie_correlativo%type;
  vFechaRegistro timestamp := CURRENT_TIMESTAMP;
  vEsquema_proc    DOCUMENTO.esquema_proc%TYPE;
  
  vCount    INTEGER;
  vDocumento    DOCUMENTO%ROWTYPE;

BEGIN
  
   SELECT s.id_sociedad, s.esquema_proc INTO vId_sociedad, vEsquema_proc FROM SOCIEDAD s WHERE s.ruc = pRuc_emisor;
	
   IF vId_sociedad IS NULL THEN
    RAISE EXCEPTION E'El RUC % no se encuentra configurado.\nSQL Nivel: 16', pRuc_emisor USING ERRCODE = '00001';
   END IF;

   SELECT TO_NUMBER(TO_CHAR(pFecha_emision, 'yyyyMM'),'999999') INTO vId_periodo;
   
   SELECT COUNT(1) INTO vCount
     FROM PERIODO periodo 
    WHERE periodo.id_periodo  = vId_periodo 
      AND periodo.id_sociedad = vId_sociedad;      

   IF vCount = 0 THEN
      INSERT INTO PERIODO(ID_PERIODO , ID_SOCIEDAD, FECHA_CREACION, ESTADO) VALUES
       (vId_periodo, vId_sociedad, CURRENT_TIMESTAMP  , TRUE);
   ELSE
      vCount := 0;

      SELECT COUNT(1) INTO vCount 
        FROM PERIODO periodo 
       WHERE periodo.id_periodo  = vId_periodo 
         AND periodo.id_sociedad = vId_sociedad
         AND ( periodo.estado    = FALSE ); --OR periodo.estado IS NULL 
          
      IF vCount > 0 THEN
         RAISE EXCEPTION E'El periodo para % esta cerrado, no puede emitir el documento.\nSQL Nivel: 16', pFecha_emision USING ERRCODE = '00001';
      END IF;
   END IF;

    SELECT doc.id_periodo,
        doc.id_sociedad,
        doc.secuencia,
        doc.estado_sri,
        doc.serie_correlativo 
    INTO vDocumento.id_periodo,vDocumento.id_sociedad,vDocumento.secuencia,vDocumento.estado_sri,vDocumento.serie_correlativo
    FROM DOCUMENTO doc 
    WHERE doc.tipo_documento  = pTipo_documento 
        AND doc.establecimiento = pEstablecimiento 
        AND doc.punto_emision   = pPunto_emision 
        AND doc.numero          = pNro_documento 
        AND doc.id_sociedad     = vId_sociedad 
        AND doc.ultimo          = TRUE;
        
        
    IF FOUND THEN
      SELECT vDocumento.serie_correlativo INTO vSerie_correlativo;      

      IF vDocumento.estado_sri = 'AU' THEN
         RAISE EXCEPTION E'El comprobante %s se encuentra Autorizado por el SRI.\nSQL Nivel: 16', vSerie_correlativo USING ERRCODE = '00001';        
      END IF;
      
	
      UPDATE DOCUMENTO SET ultimo = FALSE
      WHERE id_periodo = vDocumento.id_periodo
          AND id_sociedad = vDocumento.id_sociedad 
          AND secuencia = vDocumento.secuencia;

    END IF;

  vSecuencia := NEXTVAL('SQ_FE_DOCUMENTO');
  
  -- INSERTA EL DOCUMENTO
  INSERT INTO DOCUMENTO(ID_PERIODO,
      ID_SOCIEDAD,
      SECUENCIA,
      TIPO_DOCUMENTO,
      ESTABLECIMIENTO,
      PUNTO_EMISION,
      NUMERO,
      SERIE_CORRELATIVO,
      FECHA_REGISTRO,
      FECHA_EMISION,
      FECHA_REFERENCIA,
      CODIGO_CLIENTE,
      CLAVE_ACCESO,
      XML,
      PDF,
      ULTIMO, 
      ESTADO,
      NUMERO_SAP,
      USUARIO_SAP,
      TERMINAL,
      MAIL_DESTINO,
      IMPORTE_TOTAL,
      CLASE_DOCUMENTO,
      NOMBRE_CLIENTE,
      SUBTIPO_DOC,
      
      OBS_COMPROBANTE,
      mail_portal,
      
      esquema_proc,
      ruc_cliente
  )  VALUES (
      vId_periodo,
      vId_sociedad,
      vSecuencia,
      pTipo_documento,
      pEstablecimiento,
      pPunto_emision,
      pNro_documento,
      (pEstablecimiento || '-' || pPunto_emision || '-' || pNro_documento),
      vFechaRegistro,
      pFecha_emision,
      pFecha_referencia,
      pCodigo_cliente,
      pClave_acceso,
      pXml,
      pPdf,
      TRUE,
      'PE',
      pNumero_sap,
      pUsuario_sap,
      pTerminal,
      pCorreo_destino,
      pImporte_total,
      pClase_documento,
      pNombre_cliente,
      pSubtipo_doc,
      
      pObs_comprobante,
      pMail_portal,
      
      vEsquema_proc,
      pRuc_cliente
      );
      
      
      --INSERTA EN EL MONITOR, estado pendiente
      
	UPDATE facele.COMPROBANTE SET ULTIMO = 0 
	 WHERE nro_sri =  (pEstablecimiento || '-' || pPunto_emision || '-' || pNro_documento)
	   AND tipo_doc =  pTipo_documento
	   AND ruc =  pRuc_emisor;
	   
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
		   ,ruc_cliente)
     VALUES
           ( 
	NEXTVAL('facele.sep_comprobante'),
       pRuc_emisor,
       (pEstablecimiento || '-' || pPunto_emision || '-' || pNro_documento),
       
       pNumero_sap
           ,pFecha_emision
           ,pTipo_documento
           ,pClase_documento
           ,pClave_acceso,
       
       null,
       null,
       1,-- contabilizadono importa para sap
       0,-- anulado
       '<xml></xml>',
       pXml,
       pPdf,
       22, -- escenario, 22 es pendiente
       'PENDIENTE',
       '',
       1,--pdf, se crea el pdf antes de guardar, y se muestra en la ventana pendiente.
       0,--email
       0,-- recepcionado
       0,-- autorizado
       pUsuario_sap,
       pTerminal,
       pCorreo_destino,
      vFechaRegistro,
       1, --emision
       '',-- observacion
       '<xml></xml>', --document_xml
       '', -- pmensaje
       '', -- nombre_documento
       0, -- notificacion
       pImporte_total, -- monto_total
       pCodigo_cliente, -- interlocutor
       -- ptipo,
       -- psociedad,
       pNombre_cliente,
       vEsquema_proc,
	  1,
	  pRuc_cliente);
      
      

  -- INSERTA EL PRIMER ENVIO A SRI: DESTINO = 'SR'
  INSERT INTO ENVIO_DOCUMENTO(ID_ENVIO,
      ID_PERIODO,
      ID_SOCIEDAD,
      SECUENCIA,
      DESTINO,      
      ESTADO)
  VALUES ( 
  NEXTVAL('SQ_FE_ENVIO_DOCUMENTO'),
      vId_periodo,
      vId_sociedad,
      vSecuencia,
      'SR',
      'PE');
    
   RETURN 1;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION usp_fe_ins_documento(character varying, character, character, character, character, date, character varying, character varying, character, character varying, character varying, character varying, character varying, character varying, character varying, numeric, character varying, character varying, integer, character varying, character varying,character varying)
  OWNER TO postgres;
