/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.sri.services.on.rec;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class DefaultRecepcionFactory extends RecepcionFactory {

    @Override
    public RecepcionServices getSriServices() {
        return RecepcionServicesImpl.getInstance();
    }

}
