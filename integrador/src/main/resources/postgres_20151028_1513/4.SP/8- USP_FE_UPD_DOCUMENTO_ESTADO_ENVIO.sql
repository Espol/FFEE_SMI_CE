CREATE OR REPLACE FUNCTION USP_FE_UPD_DOCUMENTO_ESTADO_ENVIO(
	pId_periodo      DOCUMENTO.id_periodo%TYPE,
	pId_sociedad     DOCUMENTO.id_sociedad%TYPE,
	pSecuencia     DOCUMENTO.secuencia%TYPE,
	pDestino     ENVIO_DOCUMENTO.destino%TYPE
 )
 RETURNS INTEGER AS $$
BEGIN
    
    UPDATE ENVIO_DOCUMENTO SET estado = 'TE'
    WHERE id_periodo    = pId_periodo
        AND id_sociedad = pId_sociedad
        AND secuencia   = pSecuencia
        AND destino     = pDestino;

    RETURN 1;
END;
$$ LANGUAGE plpgsql;