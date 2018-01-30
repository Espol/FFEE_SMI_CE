
-- ok


CREATE OR REPLACE FUNCTION USP_FE_UPD_DOCUMENTO_FECHA_NO_AUTORIZADO(
	pRuc SOCIEDAD.ruc%TYPE,
  pId_periodo      DOCUMENTO.id_periodo%TYPE,
  pId_sociedad     DOCUMENTO.id_sociedad%TYPE,
  pSecuencia     DOCUMENTO.secuencia%TYPE
)
RETURNS INTEGER AS $$
DECLARE
  vIntervaloNotificacion    SOCIEDAD.intervalo_notificacion%TYPE;
BEGIN
	
   SELECT s.invervalo_notificacion INTO vIntervaloNotificacion FROM SOCIEDAD s WHERE s.ruc = pRuc_emisor;
	
  UPDATE DOCUMENTO 
  	SET fecha_no_autorizado = fecha_no_autorizado + (vIntervaloNotificacion ||' minutes')::interval
  WHERE 
    ID_PERIODO      = pId_periodo
    AND ID_SOCIEDAD = pId_sociedad
    AND SECUENCIA   = pSecuencia    
    AND ULTIMO      = TRUE
    AND ESTADO_SRI= 'RE'
    AND FECHA_NO_AUTORIZADO IS NOT NULL
    ;

  RETURN 1;

END;
$$ LANGUAGE plpgsql;