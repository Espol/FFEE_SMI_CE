-- Function: usp_fe_upd_documento_estado_envio_sri_aut(integer, integer, bigint, text, character, character varying, integer, integer, character varying, character varying, text)

-- DROP FUNCTION usp_fe_upd_documento_estado_envio_sri_aut(integer, integer, bigint, text, character, character varying, integer, integer, character varying, character varying, text);

CREATE OR REPLACE FUNCTION usp_fe_upd_documento_estado_envio_sri_aut(pid_periodo integer, pid_sociedad integer, psecuencia bigint, pack_autorizacion text, pestado_sri character, ppdf character varying, pescenario integer, penvio_mail integer, pnroautorizacion character varying, pfechaautorizacion character varying, pmensaje text)
  RETURNS integer AS
$BODY$
BEGIN
 
    -- ACTUALIZA LA SEGUNDA RESPUESTA DE SRI
    UPDATE DOCUMENTO SET 
        ack_sri     = pAck_autorizacion,
        estado_sri  = pEstado_sri,
        pdf		= pPdf,
        estado		= 'TE',
  escenario  = pEscenario,
  envio_mail = pEnvio_mail,
  nro_autorizacion = pNroAutorizacion,
  fecha_autorizacion = pFechaAutorizacion,
  mensaje    = pMensaje
    WHERE id_periodo    = pId_periodo 
        AND id_sociedad = pId_sociedad 
        AND secuencia   = pSecuencia
        AND ultimo      = TRUE;

  -- FINALIZA EL SEGUNDO ENVIO: DESTINO: 'ST'
  UPDATE ENVIO_DOCUMENTO SET 
       estado = 'TE'
  WHERE id_periodo    = pId_periodo
      AND id_sociedad = pId_sociedad
      AND secuencia   = pSecuencia
      AND destino     = 'ST';

	-- INSERTA EL ENVIO A SAP: DESTINO: 'SA'
        INSERT INTO ENVIO_DOCUMENTO(ID_ENVIO, ID_PERIODO, ID_SOCIEDAD, SECUENCIA, DESTINO, ESTADO)
        VALUES ( NEXTVAL('SQ_FE_ENVIO_DOCUMENTO'), pId_periodo, pId_sociedad, pSecuencia, 'SA', 'PE');

	-- AUTORIZADO O RECHAZADO
      INSERT INTO ENVIO_DOCUMENTO(ID_ENVIO, ID_PERIODO, ID_SOCIEDAD, SECUENCIA, DESTINO, ESTADO) 
      VALUES ( NEXTVAL('SQ_FE_ENVIO_DOCUMENTO'), pId_periodo, pId_sociedad, pSecuencia, 'MO', 'PE');

  RETURN 1;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION usp_fe_upd_documento_estado_envio_sri_aut(integer, integer, bigint, text, character, character varying, integer, integer, character varying, character varying, text)
  OWNER TO postgres;
