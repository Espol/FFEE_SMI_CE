
-- ok

CREATE OR REPLACE FUNCTION USP_FE_INS_DOCUMENTO_INCONSISTENTE(
  pRuc_emisor       SOCIEDAD.ruc%TYPE,
  pTipo_documento   documento.tipo_documento%TYPE,
  pEstablecimiento   documento.establecimiento%TYPE,
  pPunto_emision     documento.punto_emision%TYPE,
  pNro_documento     documento.numero%TYPE,
  pFecha_emision    documento.fecha_emision%TYPE,
  pFecha_referencia   documento.fecha_referencia%TYPE,
  pCodigo_cliente   documento.codigo_cliente%TYPE,
  
  pNumero_sap     documento.numero_sap%TYPE,
  pUsuario_sap    documento.usuario_sap%TYPE,
  pTerminal       documento.terminal%TYPE,
  pCorreo_destino documento.mail_destino%TYPE,
  pImporte_total  documento.importe_total%TYPE,
  pClase_documento documento.clase_documento%TYPE,
  pNombre_cliente  documento.nombre_cliente%TYPE,
  
  pmensaje documento.mensaje%TYPE,
  pSubtipo_doc  documento.subtipo_doc%TYPE,
  pXML  documento.xml%TYPE
)
  RETURNS INTEGER AS $$
DECLARE
 
  vId_envio     envio_documento.id_envio%type;
  vId_sociedad     documento.id_sociedad%type;
  vId_periodo     documento.id_periodo%type;
  vSecuencia    documento.secuencia%type;
  vSerie_correlativo documento.serie_correlativo%type;
  vEsquema_proc    DOCUMENTO.esquema_proc%TYPE;

  vCount    INTEGER;
  vDocumento    DOCUMENTO%ROWTYPE;

BEGIN
  
   SELECT s.id_sociedad, s.esquema_proc INTO vId_sociedad, vEsquema_proc FROM SOCIEDAD s WHERE s.ruc = pRuc_emisor;

   IF vId_sociedad IS NULL THEN
    RAISE EXCEPTION E'El RUC % no se encuentra configurado.\nSQL Nivel: 16', pRuc_emisor USING ERRCODE = '00001';
    RETURN 1;
   END IF;

   -- obtiene el periodo
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
      XML,
      ULTIMO, 
      ESTADO,
      NUMERO_SAP,
      USUARIO_SAP,
      TERMINAL,
      MAIL_DESTINO,
      IMPORTE_TOTAL,
      CLASE_DOCUMENTO,
      NOMBRE_CLIENTE,
      ESCENARIO,
      MENSAJE,
      SUBTIPO_DOC,
      
      esquema_proc
  )  VALUES (
      vId_periodo,
      vId_sociedad,
      vSecuencia,
      pTipo_documento,
      pEstablecimiento,
      pPunto_emision,
      pNro_documento,
      (pEstablecimiento || '-' || pPunto_emision || '-' || pNro_documento),
      CURRENT_TIMESTAMP,
      pFecha_emision,
      pFecha_referencia,
      pCodigo_cliente,
      pXML,
      TRUE,
      'TE',
      pNumero_sap,
      pUsuario_sap,
      pTerminal,
      pCorreo_destino,
      pImporte_total,
      pClase_documento,
      pNombre_cliente,
      4,
      pmensaje,
      pSubtipo_doc,
      
      vEsquema_proc
      
      );

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
      'MO',
      'PE');
    
   RETURN 1;
END;
$$ LANGUAGE plpgsql;






