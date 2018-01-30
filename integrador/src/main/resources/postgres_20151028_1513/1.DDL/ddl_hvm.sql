/*

 * ER/Studio 8.0 SQL Code Generation

 * Company :      Usuario

 * Project :      modelo-componente-md.dm1

 * Author :       Usuario

 *

 * Date Created : Thursday, October 01, 2015 15:28:05

 * Target DBMS : Microsoft SQL Server 2008

 */



/* 

 * TABLE: DOCUMENTO 

 */



CREATE TABLE DOCUMENTO(

    ID_PERIODO            integer               NOT NULL,

    ID_SOCIEDAD           integer               NOT NULL,

    SECUENCIA             bigint            NOT NULL,

    TIPO_DOCUMENTO        char(2)           NOT NULL,

    ESTABLECIMIENTO       char(3)           NULL,

    PUNTO_EMISION         char(3)           NULL,

    NUMERO                char(9)           NULL,

    SERIE_CORRELATIVO     varchar(20)       NULL,

    FECHA_REGISTRO        timestamp          NULL,

    FECHA_EMISION         date              NOT NULL,

    FECHA_REFERENCIA      varchar(50)       NULL,

    CODIGO_CLIENTE        varchar(20)       NULL,

    CLAVE_ACCESO          char(49)          NULL,

    XML                   varchar(200)      NOT NULL,

    PDF                   varchar(200)      NULL,

    ULTIMO               boolean              NOT NULL,

    ESTADO                char(2)           NULL,

    ESTADO_SRI            char(2)           NULL,

    ESTADO_SAP            char(2)           NULL,

    OBSERVACION_SAP       varchar(100)      NULL,

    ACK_SRI               text              NULL,

    NUMERO_SAP            varchar(20)       NULL,

    USUARIO_SAP           varchar(30)       NULL,

    TERMINAL              varchar(20)       NULL,

    MAIL_DESTINO          varchar(40)       NULL,

    IMPORTE_TOTAL         decimal(10, 3)    NULL,

    CLASE_DOCUMENTO       varchar(20)       NULL,

    NRO_AUTORIZACION      varchar(100)      NULL,

    FECHA_AUTORIZACION    varchar(20)       NULL,

    ESCENARIO             integer               NULL,

    ENVIO_MAIL           integer              NULL,

    MENSAJE               text              NULL,

    NOMBRE_CLIENTE        varchar(100)      NULL,
    
    FECHA_NO_AUTORIZADO        timestamp          NULL,

    CONSTRAINT PK_DOCUMENTO_ID_SECUENCIA PRIMARY KEY (ID_PERIODO, ID_SOCIEDAD, SECUENCIA)

);

--go






/*
IF OBJECT_ID('DOCUMENTO') IS NOT NULL

    PRINT '<<< CREATED TABLE DOCUMENTO >>>'

ELSE

    PRINT '<<< FAILED CREATING TABLE DOCUMENTO >>>'

go
*/


/* 

 * TABLE: ENVIO_DOCUMENTO 

 */



CREATE TABLE ENVIO_DOCUMENTO(

    ID_ENVIO       integer         NOT NULL,

    ID_PERIODO     integer         NOT NULL,

    ID_SOCIEDAD    integer         NOT NULL,

    SECUENCIA      bigint      NOT NULL,

    DESTINO        char(2)     NULL,

    INTENTO        integer         NULL,

    ESTADO         char(2)     NOT NULL,

    FECHA_ENVIO    timestamp    NULL,

    CONSTRAINT PK_ENVIO_ID_ENVIO_DOCUMENTO PRIMARY KEY (ID_ENVIO, ID_PERIODO, ID_SOCIEDAD, SECUENCIA)

);

--go






/*
IF OBJECT_ID('ENVIO_DOCUMENTO') IS NOT NULL

    PRINT '<<< CREATED TABLE ENVIO_DOCUMENTO >>>'

ELSE

    PRINT '<<< FAILED CREATING TABLE ENVIO_DOCUMENTO >>>'

go
*/


/* 

 * TABLE: PERIODO 

 */



CREATE TABLE PERIODO(

    ID_PERIODO        integer         NOT NULL,

    ID_SOCIEDAD       integer         NOT NULL,

    FECHA_CREACION    timestamp    NULL,

    ESTADO           boolean        NOT NULL,

    CONSTRAINT PK_PERIODO_ID PRIMARY KEY (ID_PERIODO, ID_SOCIEDAD)

);

-- go






/*
IF OBJECT_ID('PERIODO') IS NOT NULL

    PRINT '<<< CREATED TABLE PERIODO >>>'

ELSE

    PRINT '<<< FAILED CREATING TABLE PERIODO >>>'

go
*/


/* 

 * TABLE: SOCIEDAD 

 */



CREATE TABLE SOCIEDAD(

    ID_SOCIEDAD          integer             NOT NULL,

    RUC                  varchar(13)     NOT NULL,

    RAZON_SOCIAL         varchar(100)    NOT NULL,

    PATH_ROOT            varchar(100)    NOT NULL,

    PATH_CERTIFICADO     varchar(200)    NOT NULL,

    CLAVE_CERTIFICADO    varchar(30)     NOT NULL,

    CONF_SAP             text            NOT NULL,

    CONF_MAIL            text            NULL,

    MAIL_NOTIFICACION    varchar(150)    NULL,
    
    INTERVALO_NOTIFICACION	integer          NULL,

    CONSTRAINT PK_SOCIEDAD_ID_SOCIEDAD PRIMARY KEY (ID_SOCIEDAD)

);

--go






/*
IF OBJECT_ID('SOCIEDAD') IS NOT NULL

    PRINT '<<< CREATED TABLE SOCIEDAD >>>'

ELSE

    PRINT '<<< FAILED CREATING TABLE SOCIEDAD >>>'

go
*/


/* 

 * TABLE: DOCUMENTO 

 */



ALTER TABLE DOCUMENTO ADD CONSTRAINT RefPERIODO1 

    FOREIGN KEY (ID_PERIODO, ID_SOCIEDAD)

    REFERENCES PERIODO(ID_PERIODO, ID_SOCIEDAD);

--go





/* 

 * TABLE: ENVIO_DOCUMENTO 

 */



ALTER TABLE ENVIO_DOCUMENTO ADD CONSTRAINT RefDOCUMENTO6 

    FOREIGN KEY (ID_PERIODO, ID_SOCIEDAD, SECUENCIA)

    REFERENCES DOCUMENTO(ID_PERIODO, ID_SOCIEDAD, SECUENCIA);

--go





/* 

 * TABLE: PERIODO 

 */



ALTER TABLE PERIODO ADD CONSTRAINT RefSOCIEDAD5 

    FOREIGN KEY (ID_SOCIEDAD)

    REFERENCES SOCIEDAD(ID_SOCIEDAD);

-- go





