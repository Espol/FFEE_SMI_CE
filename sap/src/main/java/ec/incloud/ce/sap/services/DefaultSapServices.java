/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.sap.services;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author Joel Povis Ocaña
 */
class DefaultSapServices implements SapServices {

    private final Logger log = Logger.getLogger(SapServices.class);
    private static SapServices instance;
    private static final String FILE_CONF = "CONFIGURACION";
    private final String BY = "Generado por Incloud Services - Peru";
    private final String FUNCION_RFC = "ZFIFM_ACTUALIZA_SRI";//SMI
//    

    private String mandante;
    private String usuario;
    private String contrasena;
    private String idioma;
    private String ipServidor;
    private String numeroInstancia;
    private String idSistema;
    private String sapRouter;

    private DefaultSapServices() {
    }

    public static SapServices create() {
        synchronized (DefaultSapServices.class) {
            if (instance == null) {
                instance = new DefaultSapServices();
            }
            return instance;
        }
    }

    private void crearFileConfiguracion() {
        Properties prop = new Properties();
        prop.setProperty(DestinationDataProvider.JCO_ASHOST, this.ipServidor);
        prop.setProperty(DestinationDataProvider.JCO_R3NAME, this.idSistema);
        prop.setProperty(DestinationDataProvider.JCO_SYSNR, this.numeroInstancia);
        prop.setProperty(DestinationDataProvider.JCO_CLIENT, this.mandante);
        prop.setProperty(DestinationDataProvider.JCO_USER, this.usuario);
        prop.setProperty(DestinationDataProvider.JCO_PASSWD, this.contrasena);
        prop.setProperty(DestinationDataProvider.JCO_LANG, this.idioma);

        if (this.sapRouter != null && !this.sapRouter.isEmpty()) {
            prop.setProperty(DestinationDataProvider.JCO_SAPROUTER, this.sapRouter);
        }

        File f = new File(FILE_CONF + ".jcoDestination");
        try {
            FileOutputStream fos = new FileOutputStream(f, false);
            prop.store(fos, BY);
            fos.close();
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    @Override
    public RespuestaSap actualizarDocumento(boolean updConfig, String ruc,
            String tipoDocumento,
            String serieCorrelativo,
            String numeroSap,
            String estado,
            String numeroAutorizacion,
            String claveAcceso,
            String fechaAutorizacion,
            String usuarioSap,
            boolean generoPdf,
            boolean envioMail,
            MensajeSap mensajeSap) {
        if (updConfig) {
            this.crearFileConfiguracion();
        }

        log.debug(MessageFormat.format("Actualiza en SAP => ruc: {0}, tipo: {1}, numero: {2}, sap: {3}, estado: {4}, autorizacion: {5}, clave acceso: {6}, fecha autorizacion: {7}, usuario sap: {8}, pdf: {9}, mail: {10}, mensajeSap: {11}",
                ruc, tipoDocumento, serieCorrelativo, numeroSap, estado, numeroAutorizacion, claveAcceso, fechaAutorizacion, usuarioSap, generoPdf, envioMail, mensajeSap));

        try {
            JCoDestination destination = JCoDestinationManager.getDestination(FILE_CONF);
            JCoFunction function = destination.getRepository().getFunction(FUNCION_RFC);

            if (function == null) {
                return new RespuestaSap("ERROR", "Function RFC es null");
            }

            function.getImportParameterList().setValue("IV_STCD1", ruc);
            function.getImportParameterList().setValue("IV_TIPDO", tipoDocumento);
            function.getImportParameterList().setValue("IV_XBLNR", serieCorrelativo);
            function.getImportParameterList().setValue("IV_VBELN", numeroSap);
            function.getImportParameterList().setValue("IV_ESTAD", estado);
            function.getImportParameterList().setValue("IV_NASRI", numeroAutorizacion);
            function.getImportParameterList().setValue("IV_CASRI", claveAcceso);
//            function.getImportParameterList().setValue("I_FEC_AUT", fechaAutorizacion);
            function.getImportParameterList().setValue("IV_USER", usuarioSap);
            function.getImportParameterList().setValue("IV_PDF", (generoPdf) ? "1" : "0");
            function.getImportParameterList().setValue("IV_EMAIL", (envioMail) ? "1" : "0");

            JCoTable tblRpta = function.getTableParameterList().getTable("IT_RPTA");
            List<Mensaje> list = mensajeSap.getListMensaje();
            Mensaje x;

            for (int i = 0; i < list.size(); i++) {
                x = list.get(i);
                log.debug("Agregando Mensaje a JCoTable -> " + x);
                tblRpta.appendRow();
                tblRpta.setRow(i);
                tblRpta.setValue("ADICIONAL", x.getAdicional());
                tblRpta.setValue("DESCRIPCION", x.getDescripcion());
                tblRpta.setValue("IDENTIFICADOR", x.getIdentificador());
                tblRpta.setValue("TIPO", x.getTipo());
            }

            try {
                function.execute(destination);
            } catch (AbapException ex) {
                log.error(ex);
                return new RespuestaSap("ERROR", "Error al ejecutar RFC");
            }

            String tipo = function.getExportParameterList().getString("EV_SUBRC");
            JCoTable tblMsg = function.getTableParameterList().getTable("ET_MSG");
            StringBuilder observacion = new StringBuilder();
            if (tblMsg.getNumRows() > 0) {
                for (int i = 0; i < tblMsg.getNumRows(); i++) {
                    tblMsg.setRow(i);
                    observacion.append(tblMsg.getValue("MESSAGE"));
                    log.debug(MessageFormat.format("JCoTable => "
                            + "TYPE: {0}, "
                            + "CODE: {1}, "
                            + "MESSAGE: {2}, "
                            + "LOG_NO: {3}, "
                            + "LOG_MSG_NO: {4}, "
                            + "MESSAGE_V1: {5}, "
                            + "MESSAGE_V2: {6}, "
                            + "MESSAGE_V3: {7}, "
                            + "MESSAGE_V4: {8}, ",
                            tblMsg.getValue("TYPE"),
                            tblMsg.getValue("CODE"),
                            tblMsg.getValue("MESSAGE"),
                            tblMsg.getValue("LOG_NO"),
                            tblMsg.getValue("LOG_MSG_NO"),
                            tblMsg.getValue("MESSAGE_V1"),
                            tblMsg.getValue("MESSAGE_V2"),
                            tblMsg.getValue("MESSAGE_V3"),
                            tblMsg.getValue("MESSAGE_V4")));
                }
            }

            return new RespuestaSap((tipo.trim().equals("00"))?"AC":"ER", observacion.toString());
        } catch (Exception ex) {
            log.error(ex);
            return new RespuestaSap("ER", ex.toString());
        }
    }

    @Override
    public void setMandante(String mandante) {
        this.mandante = mandante;
    }

    @Override
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    @Override
    public void setIpServidor(String servidor) {
        this.ipServidor = servidor;
    }

    @Override
    public void setNumeroInstancia(String numeroInstancia) {
        this.numeroInstancia = numeroInstancia;
    }

    @Override
    public void setIdSistema(String idSistema) {
        this.idSistema = idSistema;
    }

    @Override
    public void setSapRouter(String sapRouter) {
        this.sapRouter = sapRouter;
    }
}
