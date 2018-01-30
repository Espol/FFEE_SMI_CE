IF OBJECT_ID('USP_FE_INS_COMPROBANTE') IS NOT NULL
    BEGIN
        DROP PROCEDURE USP_FE_INS_COMPROBANTE;
        PRINT '<<< DROPPED SP USP_FE_INS_COMPROBANTE >>>'	
    END
go

CREATE PROCEDURE USP_FE_INS_COMPROBANTE
	  @pruc					 VARCHAR(13),
      @pnro_sri				 VARCHAR(20),
      @pdoc_referencia		 VARCHAR(20),
      @pfecha_emision		 DATETIME,
      @ptipo_doc			 VARCHAR(2),
      @ptipo_doc_referencia	 VARCHAR(20),
      @pclave_acceso		 VARCHAR(100),
      @pnro_autorizacion	 VARCHAR(100),
      @pfecha_autorizacion	 VARCHAR(20),
      @pcontabilizado		 INT,
      @panulado				 INT,
      @pxml					 TEXT,
      @ppath_xml			 VARCHAR(500),
      @ppath_pdf			 VARCHAR(500),
      @pescenario			 INT,
      @pestado				 VARCHAR(30),
      @plog					 TEXT,
      @ppdf					 INT,
      @pemail				 INT,
      @precepcionado		 INT,
      @pautorizado			 INT,
      @pusuario				 VARCHAR(30),
      @pterminal			 VARCHAR(20),
      @pemail_destino		 VARCHAR(500),
      @ptipo_emision		 INT,
      @pobservacion			 VARCHAR(700),
      @pdocument_xml		 TEXT,
      @pmensaje				 TEXT,
      @pnombre_documento	 VARCHAR(300),
      @pnotificacion		 INT,
      @pmonto_total			 FLOAT,
      @pinterlocutor		 VARCHAR(30),
      @ptipo				 VARCHAR(50),
      @psociedad			 VARCHAR(50),
      @prazon_social		 VARCHAR(100)
AS	
    
	UPDATE COMPROBANTE WITH (ROWLOCK) SET ULTIMO = 0 
	 WHERE nro_sri = @pnro_sri
	   AND tipo_doc = @ptipo_doc
	   AND ruc = @pruc;
	    
	
	INSERT INTO [comprobante]
           ([ruc]
           ,[nro_sri]
           ,[doc_referencia]
           ,[fecha_emision]
           ,[tipo_doc]
           ,[tipo_doc_referencia]
           ,[clave_acceso]
           ,[nro_autorizacion]
           ,[fecha_autorizacion]
           ,[contabilizado]
           ,[anulado]
           ,[xml]
           ,[path_xml]
           ,[path_pdf]
           ,[escenario]
           ,[estado]
           ,[log]
           ,[pdf]
           ,[email]
           ,[recepcionado]
           ,[autorizado]
           ,[usuario]
           ,[terminal]
           ,[email_destino]
           ,[fecha_registro]
           ,[tipo_emision]
           ,[observacion]
           ,[document_xml]
           ,[mensaje]
           ,[nombre_documento]
           ,[notificacion]
           ,[monto_total]
           ,[interlocutor]
           ,[tipo]
           ,[sociedad]
           ,[razon_social]
		   ,[ultimo])
     VALUES
           (@pruc,
      @pnro_sri,
      @pdoc_referencia,
      @pfecha_emision,
      @ptipo_doc,
      @ptipo_doc_referencia,
      @pclave_acceso,
      @pnro_autorizacion,
      @pfecha_autorizacion,
      @pcontabilizado,
      @panulado,
      @pxml,
      @ppath_xml,
      @ppath_pdf,
      @pescenario,
      @pestado,
      @plog,
      @ppdf,
      @pemail,
      @precepcionado,
      @pautorizado,
      @pusuario,
      @pterminal,
      @pemail_destino,
      CURRENT_TIMESTAMP,
      @ptipo_emision,
      @pobservacion,
      @pdocument_xml,
      @pmensaje,
      @pnombre_documento,
      @pnotificacion,
      @pmonto_total,
      @pinterlocutor,
      @ptipo,
      @psociedad,
      @prazon_social,
	  1);