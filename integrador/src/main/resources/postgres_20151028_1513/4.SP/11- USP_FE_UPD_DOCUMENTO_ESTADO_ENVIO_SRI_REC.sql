

CREATE OR REPLACE FUNCTION USP_FE_UPD_DOCUMENTO_ESTADO_ENVIO_SRI_REC(
  pId_periodo         DOCUMENTO.id_periodo%TYPE,
  pId_sociedad        DOCUMENTO.id_sociedad%TYPE,
  pSecuencia          DOCUMENTO.secuencia%TYPE,
  pAck_autorizacion   DOCUMENTO.ack_sri%TYPE,
  pEstado_sri         DOCUMENTO.estado_sri%TYPE,
  pEnvio_Mail         DOCUMENTO.envio_mail%TYPE,
  pEscenario          DOCUMENTO.escenario%TYPE,
  pMensaje            DOCUMENTO.mensaje%TYPE
)
RETURNS INTEGER AS $$
BEGIN

  -- ACTUALIZA LA SEGUNDA RESPUESTA DE SRI
  UPDATE DOCUMENTO SET 
    ack_sri     = pAck_autorizacion,
    estado_sri  = pEstado_sri,
    envio_mail  = pEnvio_Mail,
    escenario   = pEscenario,
    mensaje     = pMensaje,
    pdf			= SUBSTR(xml,0, length(xml) - 3) || '.pdf'
  WHERE id_periodo    = pId_periodo 
      AND id_sociedad = pId_sociedad 
      AND secuencia   = pSecuencia
    AND ultimo      = TRUE;

    -- SI EL DOCUMENTO NO FUE RECEPCIONADO (NR) INSERTA EL ENVIO A SAP (SA) Y ACTUALIZA EL ESTADO DEL DOCUMENTO (TE)
    IF pEstado_sri = 'NR' THEN
        INSERT INTO ENVIO_DOCUMENTO(ID_ENVIO, ID_PERIODO, ID_SOCIEDAD, SECUENCIA, DESTINO, ESTADO)
        VALUES ( NEXTVAL('SQ_FE_ENVIO_DOCUMENTO'), pId_periodo, pId_sociedad, pSecuencia, 'SA', 'PE');

        UPDATE DOCUMENTO SET 
         estado = 'TE' 
         WHERE id_periodo  = pId_periodo 
           AND id_sociedad = pId_sociedad 
           AND secuencia   = pSecuencia
           AND ultimo      = TRUE;
    END IF;

    -- SI EL DOCUMENTO FUE RECEPCIONADO (RC) INSERTA EL SEGUNDO ENVIO A SRI (ST)
    IF pEstado_sri = 'RC' THEN
        INSERT INTO ENVIO_DOCUMENTO(ID_ENVIO, ID_PERIODO, ID_SOCIEDAD, SECUENCIA, DESTINO, ESTADO) 
        VALUES ( NEXTVAL('SQ_FE_ENVIO_DOCUMENTO'), pId_periodo, pId_sociedad, pSecuencia, 'ST', 'PE');
    END IF;

-- SI EL DOCUMENTO NO FUE RECEPCIONADO, ESTE ES ENVIADO AL MONITOR PARA SER VISUALIZADO
    IF pEstado_sri = 'NR' THEN
        INSERT INTO ENVIO_DOCUMENTO(ID_ENVIO, ID_PERIODO, ID_SOCIEDAD, SECUENCIA, DESTINO, ESTADO) 
        VALUES ( NEXTVAL('SQ_FE_ENVIO_DOCUMENTO'), pId_periodo, pId_sociedad, pSecuencia, 'MO', 'PE');
    END IF;

-- FINALIZA EL PRIMER ENVIO
UPDATE ENVIO_DOCUMENTO SET 
     estado = 'TE'
WHERE id_periodo    = pId_periodo
    AND id_sociedad = pId_sociedad
    AND secuencia   = pSecuencia
    AND destino     = 'SR';

  RETURN 1;
END;
$$ LANGUAGE plpgsql;