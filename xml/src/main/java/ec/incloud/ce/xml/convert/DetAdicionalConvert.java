/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.xml.convert;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import ec.incloud.ce.bean.common.DetAdicional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 *
 * @author Joel Povis Ocaña
 */
public class DetAdicionalConvert implements Converter {

    @SuppressWarnings("unchecked")
	@Override
    public void marshal(Object o, HierarchicalStreamWriter w, MarshallingContext mc) {
        List<DetAdicional> list = (List<DetAdicional>) o;
        if (list != null) {
            for (Iterator<DetAdicional> it = list.iterator(); it.hasNext(); w.endNode()) {
                DetAdicional adicional = it.next();
                w.startNode("detAdicional");
                w.addAttribute("nombre", adicional.getNombre());
                w.addAttribute("valor", adicional.getValor());
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext uc) {
        List list = new ArrayList();
        for (; reader.hasMoreChildren(); reader.moveUp()) {
            reader.moveDown();
            DetAdicional campo = new DetAdicional();
            campo.setNombre(reader.getAttribute("nombre"));
            campo.setValor(reader.getAttribute("valor"));
            list.add(campo);
        }
        return list;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public boolean canConvert(Class type) {
        return ArrayList.class == type;
    }

}
