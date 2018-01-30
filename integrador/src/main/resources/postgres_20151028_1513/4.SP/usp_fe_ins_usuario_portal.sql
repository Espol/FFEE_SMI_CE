-- Function: usp_fe_ins_usuario_portal(character varying, character varying, character varying, character varying, character varying)

-- DROP FUNCTION usp_fe_ins_usuario_portal(character varying, character varying, character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION usp_fe_ins_usuario_portal(
    pruc_cliente character varying,
    ptipo_doc_cliente character varying,
    prazon_social_cliente character varying,
    pemail_portal character varying,
    pusuario_sap character varying)
  RETURNS integer AS
$BODY$
DECLARE
	vid_rol_tmp portal.ROL.id_rol%type;
	vid_persona_tmp portal.persona.id_persona%type;
	vmd5_clave portal.usuario.clave%type;
	vcount_persona INTEGER;
BEGIN

	SELECT COUNT(*) INTO vcount_persona
	FROM portal.usuario where ruc_persona= pruc_cliente;

	IF vcount_persona = 0 THEN

		SELECT id_rol INTO vid_rol_tmp
		FROM portal.rol where rol = 'ROLE_USER';

		SELECT id_persona INTO vid_persona_tmp
		FROM portal.persona where nro_documento = pruc_cliente;

		SELECT MD5(pusuario_sap) INTO vmd5_clave;
		
		INSERT INTO portal.usuario (
			id_usuario,
			id_persona,
			id_rol,
			username,
			clave,
			observacion,
			activo,
			eliminado,
			correo,
			clave_inicial,
			usuario_sap,
			ruc_persona,
			flag_sap			
		)
		VALUES(
			NEXTVAL('portal.seq_id_usuario'),
			vid_persona_tmp,--id_persona
			vid_rol_tmp,--id_rol
			pruc_cliente,--username
			vmd5_clave,-- clave
			'',--observacion
			'1', --activo
			'0', -- eliminado
			pemail_portal, --correo
			vmd5_clave, --clave_inicial
			pusuario_sap, --usuario_sap
			pruc_cliente,--ruc persona
			'0' --flag_sap
		);
		return 1;
	ELSE
		return 0;
	END IF;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION usp_fe_ins_usuario_portal(character varying, character varying, character varying, character varying, character varying)
  OWNER TO postgres;