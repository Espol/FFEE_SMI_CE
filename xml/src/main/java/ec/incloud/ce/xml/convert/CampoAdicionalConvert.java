/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.xml.convert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import ec.incloud.ce.bean.common.CampoAdicional;

/**
 *
 * @author Joel Povis Ocaña 
 */
public class CampoAdicionalConvert implements Converter {
	
    private final Logger log = Logger.getLogger(this.getClass());

    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext mc) {
        List<CampoAdicional> list = (ArrayList<CampoAdicional>) o;
        if (list != null) {
            for (Iterator i$ = list.iterator(); i$.hasNext(); writer.endNode()) {
                CampoAdicional adicional = (CampoAdicional) i$.next();
                if(adicional!=null){
	                writer.startNode("campoAdicional");
	                writer.addAttribute("nombre", adicional.getNombre());
	                writer.setValue(adicional.getValue());
                }else{
                	log.warn("campo adicional es null");
                }
            }
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext uc) {
        List list = new ArrayList();
        for (; reader.hasMoreChildren(); reader.moveUp()) {
            reader.moveDown();
            CampoAdicional campo = new CampoAdicional();
            campo.setNombre(reader.getAttribute("nombre"));
            campo.setValue(reader.getValue());
            list.add(campo);
        }
        return list;
    }

    public boolean canConvert(Class type) {
        return ArrayList.class == type;
    }

}
