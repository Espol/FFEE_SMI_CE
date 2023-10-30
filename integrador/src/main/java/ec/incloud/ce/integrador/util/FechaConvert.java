/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

@SuppressWarnings("restriction")
public class FechaConvert implements Converter{
    
	public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext mc) {
    	
        if(o instanceof XMLGregorianCalendarImpl){
            XMLGregorianCalendarImpl xgc = (XMLGregorianCalendarImpl) o;
            Date date = xgc.toGregorianCalendar().getTime();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String fecha = formato.format(date);
            writer.setValue(fecha);
        }else{
            writer.setValue("");
        }
    }
    


    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext uc) {
        return null;
    }



	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return false;
	}
    
}
