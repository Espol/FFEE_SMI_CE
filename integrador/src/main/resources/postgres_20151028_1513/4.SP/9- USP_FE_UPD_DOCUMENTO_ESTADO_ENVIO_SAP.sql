
-- ok
 
CREATE OR REPLACE FUNCTION USP_FE_UPD_DOCUMENTO_ESTADO_ENVIO_SAP(
  pId_periodo      DOCUMENTO.id_periodo%TYPE,
  pId_sociedad     DOCUMENTO.id_sociedad%TYPE,
  pSecuencia     DOCUMENTO.secuencia%TYPE,
  pEstadoSap	 DOCUMENTO.estado_sap%TYPE,
  pObservacionSap DOCUMENTO.observacion_sap%TYPE
)
RETURNS INTEGER AS $$
BEGIN

  UPDATE ENVIO_DOCUMENTO SET estado = 'TE'
  WHERE 
    id_periodo      = pId_periodo
    AND id_sociedad = pId_sociedad
    AND secuencia   = pSecuencia
    AND destino     = 'SA';
    
  UPDATE DOCUMENTO SET 
	   estado_sap = pEstadoSap	,
	   observacion_sap = pObservacionSap
    WHERE id_periodo = pId_periodo 
	 AND  id_sociedad = pId_sociedad
	 AND  secuencia = pSecuencia ;


  RETURN 1;
END;
$$ LANGUAGE plpgsql;