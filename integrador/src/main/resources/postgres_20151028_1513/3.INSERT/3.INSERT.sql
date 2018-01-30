-- DEV
INSERT INTO SOCIEDAD (id_sociedad,
	ruc, 
	razon_social, 	
	path_root, 
	path_certificado, 
	clave_certificado,
        conf_sap,
        conf_mail,
        mail_notificacion) 
VALUES ( NEXTVAL('SQ_FE_SOCIEDAD'),
'0990344760001',
'QUIMPAC',
'D:\PruebasMirth\ComprobantesQuimpac',
'C:\Program Files\Incloud Middleware Service\custom-lib\certificate\quimpac_facturacion_electronica.p12',
'QEsa09413',
'<sapSetting><mandante>300</mandante><usuario>CSTI</usuario><contrasena>password</contrasena><idioma>ES</idioma><ipServidor>192.168.10.6</ipServidor><numeroInstancia>00</numeroInstancia><idSistema>DES</idSistema></sapSetting>',
'<mailSetting><host>smtp.gmail.com</host><port>587</port><user>facturacion.electronica@quimpac.com.ec</user><password>Q12w3e4r</password></mailSetting>',
'jchoquecota@inclouds.biz');

﻿-- DEV
INSERT INTO SOCIEDAD (id_sociedad,
	ruc, 
	razon_social, 	
	path_root, 
	path_certificado, 
	clave_certificado,
        conf_sap,
        conf_mail,
        mail_notificacion) 
VALUES ( NEXTVAL('SQ_FE_SOCIEDAD'),
'0990007241001',
'ECUASAL',
'D:\PruebasMirth\ComprobantesEcuasal',
'C:\Program Files\Incloud Middleware Service\custom-lib\certificate\quimpac_facturacion_electronica.p12',
'QEsa09413',
'<sapSetting><mandante>300</mandante><usuario>CSTI</usuario><contrasena>password</contrasena><idioma>ES</idioma><ipServidor>192.168.10.6</ipServidor><numeroInstancia>00</numeroInstancia><idSistema>DES</idSistema></sapSetting>',
'<mailSetting><host>smtp.gmail.com</host><port>587</port><user>facturacion.electronica@quimpac.com.ec</user><password>Q12w3e4r</password></mailSetting>',
'jchoquecota@inclouds.biz,mmoyano@csticorp.biz');



INSERT INTO SOCIEDAD (id_sociedad,
	ruc, 
	razon_social, 	
	path_root, 
	path_certificado, 
	clave_certificado,
        conf_sap,
        conf_mail,
        mail_notificacion) 
VALUES ( NEXTVAL('SQ_FE_SOCIEDAD'),
'0990218994001',
'QCTERMINALES',
'D:\PruebasMirth\QCTerminales',
'C:\Program Files\Incloud Middleware Service\custom-lib\certificate\quimpac_facturacion_electronica.p12',
'QEsa09413',
'<sapSetting><mandante>300</mandante><usuario>CSTI</usuario><contrasena>password</contrasena><idioma>ES</idioma><ipServidor>192.168.10.6</ipServidor><numeroInstancia>00</numeroInstancia><idSistema>DES</idSistema></sapSetting>',
'<mailSetting><host>smtp.gmail.com</host><port>587</port><user>facturacion.electronica@quimpac.com.ec</user><password>Q12w3e4r</password></mailSetting>',
'jchoquecota@inclouds.biz,mmoyano@csticorp.biz');



-- HIVIMAR QAS
INSERT INTO SOCIEDAD (id_sociedad,
	ruc, 
	razon_social, 	
	path_root, 
	path_certificado, 
	clave_certificado,
        conf_sap,
        conf_mail,
        mail_notificacion) 
VALUES ( NEXTVAL('SQ_FE_SOCIEDAD'),
'0990129185001',
'HIVIMAR',
'C:\PruebasMiddleware\comprobantes',
'C:\Program Files\Incloud Middleware Service\custom-lib\certificate\javier_vicente_echeverria_ampuero.p12',
'Nicolas06',
'<sapSetting><mandante>300</mandante><usuario>CSTI</usuario><contrasena>password</contrasena><idioma>ES</idioma><ipServidor>192.168.10.6</ipServidor><numeroInstancia>00</numeroInstancia><idSistema>DES</idSistema></sapSetting>',
'<mailSetting><host>mail.hivimar.com</host><port>587</port><user>hfe@hivimar.com</user><password>hivms33$</password></mailSetting>',
'jpovis@inclouds.biz, ileiva@inclouds.biz, avivas@csticorp.biz');