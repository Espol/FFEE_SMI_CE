-- Function: usp_fe_ins_persona_portal(character varying, character varying, character varying, character varying, character varying)

-- DROP FUNCTION usp_fe_ins_persona_portal(character varying, character varying, character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION usp_fe_ins_persona_portal(
    pruc_cliente character varying,
    ptipo_doc_cliente character varying,
    prazon_social_cliente character varying,
    pemail_portal character varying,
    pusuario_sap character varying)
  RETURNS integer AS
$BODY$
DECLARE
	vId_rol_tmp portal.ROL.id_rol%type;
	vMd5_clave portal.persona.clave%type;
	vCount_persona integer;
BEGIN

	SELECT COUNT(*) INTO vCount_persona
	FROM portal.persona where nro_documento = pruc_cliente;

	IF vCount_persona = 0 THEN

		SELECT id_rol INTO vId_rol_tmp
		FROM portal.rol where rol = 'ROLE_USER';

		SELECT MD5(pusuario_sap) INTO vMd5_clave;

		INSERT INTO portal.persona(
			id_persona,
			nro_documento,
			tipo_documento,
			nombres,
			apellido_paterno,
			apellido_materno,
			fecha_nacimiento,
			telefono,
			correo,
			direccion,
			eliminado,
			clave,
			clave_inicial,
			activo,
			id_rol,
			usuario_sap)
		VALUES(
			NEXTVAL('portal.seq_id_persona'),--id_persona
			pruc_cliente,--nro_documento
			ptipo_doc_cliente,--tipo_documento
			prazon_social_cliente,--nombres
			prazon_social_cliente,--apellido_paterno
			prazon_social_cliente,-- apellido_materno
			null,--fecha_nacimiento
			'', --telefono
			pemail_portal, -- correo
			'', --direccion
			'1', --eliminado
			vMd5_clave, --clave
			vMd5_clave, --clave_inicial
			'1', --activo
			vId_rol_tmp,--id_rol
			pusuario_sap --usuario_sap
		);

		return 1;
	ELSE
		RETURN 0;
	END IF;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION usp_fe_ins_persona_portal(character varying, character varying, character varying, character varying, character varying)
  OWNER TO postgres;
