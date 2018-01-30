-- Function: usp_fe_get_clave_portal(character varying)

-- DROP FUNCTION usp_fe_get_clave_portal(character varying);

CREATE OR REPLACE FUNCTION usp_fe_get_clave_portal(pruc_cliente character varying)
  RETURNS usuario AS
$BODY$
DECLARE
	usuario USUARIO%ROWTYPE;
	vCount INTEGER;
BEGIN

SELECT count(1) into vCount from portal.usuario s where s.username = pruc_cliente;
if vCount = 0 then
 return NULL;
end if;

 SELECT s.* INTO usuario from portal.usuario s where s.username = pruc_cliente;
 return usuario;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION usp_fe_get_clave_portal(character varying)
  OWNER TO postgres;
