
-- agregar columna cod_interlocutor 10 dig. - cod_cliente.

alter table facele.comprobante add column COD_INTERLOCUTOR varchar(20);

-- documento anulado desde el visor
alter table documento add column anulado integer;

-- frecuencia de notificacion de firma vencida
alter table sociedad add column interval_notif_firma integer;

-- observacion comprobante, que no se graba en el xml, pero sí en el pdf
alter table documento add column obs_comprobante varchar(700);

-- esquema de procesamiento del documento
alter table documento add column esquema_proc integer;

-- renombre a la columna offline por esquema_proc
ALTER TABLE sociedad RENAME COLUMN offline TO esquema_proc;

-- renombre a la columna offline por esquema_proc
ALTER TABLE facele.comprobante RENAME COLUMN offline TO esquema_proc;


-- envio de correo por tipo documento

-- se amplian los caracteres del campo
ALTER TABLE sociedad ALTER COLUMN mail_notificacion TYPE character varying(500);

-- envio de correo por tipo doc.
alter table sociedad add column mail_factura varchar(500);
alter table sociedad add column mail_retencion varchar(500);
alter table sociedad add column mail_credito varchar(500);
alter table sociedad add column mail_debito varchar(500);
alter table sociedad add column mail_guia varchar(500);


-- estado notificación rechazado, para cancelar el envío de correo de un rechazado.
ALTER TABLE documento ADD COLUMN est_notif_rech integer;

--tiempo en minutos del inicio de envio de correos cuando vence la firma.
ALTER TABLE sociedad ADD COLUMN ini_notif_firma integer;

--tiempo en ( horas +/- 1 ) del inicio de envio de correos para los rechazados del sri
ALTER TABLE sociedad ADD COLUMN ini_notif_rechazo integer;

-- url de la sociedad para el portal
alter table sociedad add column url varchar(300);

-- sociedad a la que se puede loguear el usuario.
alter table seguridad.usuario add column sociedad varchar(20);

-- el nombre de usuario es unico por sociedad
ALTER TABLE seguridad.usuario ADD CONSTRAINT uq_username_sociedad UNIQUE (user_name,sociedad);

-- seq seguridad usuario rol:
CREATE SEQUENCE seguridad.seq_usuario_rol
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE seguridad.seq_usuario_rol
  OWNER TO postgres;
  

-- seq seguridad usuario :
CREATE SEQUENCE seguridad.seq_usuario
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE seguridad.seq_usuario_rol
  OWNER TO postgres;
  
  
  -- opción configuración portal - 20151202 1723
  
  INSERT INTO seguridad.opcion(
            id_opcion, id_modulo, codigo, nombre, descripcion, orden, icono, 
            controlador, es_directorio, vista, activo)
    VALUES (NEXTVAL('seguridad.seq_opcion'), 2, 'CONFPORTAL', 'Configuración Portal Web', 
			'Configuración Portal Web', '16', '', '/view/config/portal/view/', 
			'0', '/view/config/portal/view/', '1');			

CREATE SEQUENCE seguridad.seq_accesso
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 99999999
  START 100
  CACHE 1;
ALTER TABLE seguridad.seq_accesso
  OWNER TO postgres;
			
INSERT INTO seguridad.acceso(
            id_acceso, id_permiso, id_opcion, id_rol)
    VALUES (NEXTVAL('seguridad.seq_accesso'), 1, (SELECT max(id_opcion) FROM seguridad.opcion), 1);
  
-- Integración portal, 20151209 1645
    
-- correo email portal
alter table documento add column mail_portal varchar(200);

-- cantidad de dias permitido para que el comprobante no esté con fecha extemporánea.
alter table sociedad add column DIAS_FECHA_EXTEMP integer;

-- ruc de cliente para el portal
ALTER TABLE facele.comprobante ADD COLUMN ruc_cliente character varying(13);

-- ruc de cliente para el portal
ALTER TABLE documento ADD COLUMN ruc_cliente character varying(13);

-- sociedad que implimentan el portal
ALTER TABLE sociedad ADD COLUMN port_impl integer;
ALTER TABLE sociedad ALTER COLUMN port_impl SET DEFAULT 0;