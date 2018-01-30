
-- ok

CREATE OR REPLACE FUNCTION USP_FE_UPD_INTENTO_ENVIO(
	pId_periodo      DOCUMENTO.id_periodo%TYPE,
	pId_sociedad     DOCUMENTO.id_sociedad%TYPE,
	pSecuencia     DOCUMENTO.secuencia%TYPE,
	pDestino     ENVIO_DOCUMENTO.destino%TYPE
 )
 RETURNS INTEGER AS $$
DECLARE
  vId_envio    ENVIO_DOCUMENTO.id_envio%TYPE;
  vIntento    ENVIO_DOCUMENTO.intento%TYPE;
BEGIN
    
    SELECT envio.id_envio, COALESCE(envio.intento, 0) + 1 INTO vId_envio, vIntento
    FROM ENVIO_DOCUMENTO envio 
    WHERE envio.id_periodo      = pId_periodo 
        AND envio.id_sociedad   = pId_sociedad
        AND envio.secuencia     = pSecuencia
        AND envio.destino       = pDestino;

    IF vId_envio IS NULL THEN
        RETURN 0;
    END IF;
    
    -- ACTUALIZA EL INTENTO Y ESTABLECE EL ESTADO EN 'EP' (EN PROCESO DE ENVIO)
    UPDATE ENVIO_DOCUMENTO SET 
        intento     = vIntento,
        estado      = 'EP',
        fecha_envio = CURRENT_TIMESTAMP
    WHERE id_envio      = vId_envio
        AND id_periodo  = pId_periodo
        AND id_sociedad = pId_sociedad
        AND secuencia   = pSecuencia;

    RETURN 1;
END;
$$ LANGUAGE plpgsql;