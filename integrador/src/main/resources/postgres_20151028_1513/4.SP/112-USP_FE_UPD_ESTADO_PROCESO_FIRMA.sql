-- Function: USP_FE_UPD_ESTADO_PROCESO_FIRMA()

-- DROP FUNCTION USP_FE_UPD_ESTADO_PROCESO_FIRMA();

CREATE OR REPLACE FUNCTION USP_FE_UPD_ESTADO_PROCESO_FIRMA(
	pId_sociedad     SOCIEDAD.id_sociedad%TYPE,
	pEstadoProcesoCaducidad     integer
)
  RETURNS void AS
$BODY$
BEGIN

	UPDATE SOCIEDAD
	SET proc_venc_firma = pEstadoProcesoCaducidad
	WHERE id_sociedad= pId_sociedad;	

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
	