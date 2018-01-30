


CREATE OR REPLACE FUNCTION USP_FE_GET_SOCIEDADES_FIRMA_CADUCADA()
  RETURNS TABLE(
	  id_sociedad integer,
	  ruc character varying,
	  razon_social character varying,
	  path_root character varying,
	  path_certificado character varying,
	  clave_certificado character varying,
	  conf_sap text,
	  conf_mail text,
	  mail_notificacion character varying,
	  interval_notif_firma integer,
	  esquema_proc integer,
	  venc_firma timestamp without time zone,
	  proc_venc_firma integer,	  
	  ini_notif_firma integer
  ) AS $$
DECLARE
 sociedad SOCIEDAD%ROWTYPE;
 vCount INTEGER;
BEGIN
  RETURN  QUERY
    SELECT soc.id_sociedad, 
    soc.ruc,
	soc.razon_social,
	soc.path_root,
	soc.path_certificado,
	soc.clave_certificado,
	soc.conf_sap,
	soc.conf_mail,
	soc.mail_notificacion,
	soc.interval_notif_firma,
	soc.esquema_proc,
	soc.venc_firma,
	soc.proc_venc_firma,
	soc.ini_notif_firma
    FROM SOCIEDAD soc
    -- ( 60 minutos * 24 ) -> 1 dia * 14 días = 20160 = soc.ini_notif_firma -> ( 2 semanas en minutos ) 
	where current_timestamp >= soc.venc_firma - (soc.ini_notif_firma * interval '1 minute') 
	and soc.proc_venc_firma = 0;
END
$$
LANGUAGE plpgsql;

