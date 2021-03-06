CREATE OR REPLACE FUNCTION USP_FE_UPD_DOCUMENTO_ERROR_ENVIO_SRI_AUT(
	pId_periodo      DOCUMENTO.id_periodo%TYPE,
	pId_sociedad     DOCUMENTO.id_sociedad%TYPE,
	pSecuencia     DOCUMENTO.secuencia%TYPE
)
 RETURNS INTEGER
AS $$
DECLARE
	vId_envio    ENVIO_DOCUMENTO.id_envio%TYPE;
BEGIN

  SELECT envio.id_envio INTO vId_envio
  FROM ENVIO_DOCUMENTO envio
  WHERE envio.id_periodo      = pId_periodo 
      AND envio.id_sociedad   = pId_sociedad
      AND envio.secuencia     = pSecuencia
      AND envio.destino       = 'ST';


  IF vId_envio IS NULL THEN
      RETURN 0;
  END IF;
  
  UPDATE ENVIO_DOCUMENTO SET estado = 'EC'
  WHERE 
    id_envio            = vId_envio 
    AND id_periodo      = pId_periodo 
    AND id_sociedad     = pId_sociedad
    AND secuencia       = pSecuencia;
    
    
  UPDATE DOCUMENTO SET estado = 'TE' 
  WHERE 
    ID_PERIODO      = pId_periodo
    AND ID_SOCIEDAD = pId_sociedad
    AND SECUENCIA   = pSecuencia
    AND ultimo      = TRUE;
  
  RETURN 1;

END;
$$ LANGUAGE plpgsql;