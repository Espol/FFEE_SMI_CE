
package ec.incloud.ce.integrador.test;

import ec.incloud.ce.integrador.util.TipoDocumentoEnum;

/**
 *
 * @author Joel Povis Ocaña
 */
public class TipoDocumentoEnumTest {
    public static void main(String... arg){
        String label = "";
        
        for(TipoDocumentoEnum x : TipoDocumentoEnum.values()){
            if(x.getCodigo().equals("01")){
                label = x.getDescripcion();
            }
        }
        System.out.println(label);
    }
}
