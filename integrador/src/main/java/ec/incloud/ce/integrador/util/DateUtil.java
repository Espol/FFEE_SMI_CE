/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.integrador.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Joel Povis Ocaña
 */
public class DateUtil {
    public static Date getDateFromString(String fecha){
        try{
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            return df.parse(fecha);
        }catch(ParseException ex){
            return null;
        }
    }
    
    public static String getStringFromDate(Date date){
        return getStringFromDate(date, "dd/MM/yyyy");
    }
    
    public static String getStringFromDate(Date date, String formato){
        DateFormat df = new SimpleDateFormat(formato);
        return df.format(date);
    }
}
