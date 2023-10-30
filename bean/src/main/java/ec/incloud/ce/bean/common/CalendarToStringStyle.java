/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.bean.common;

import java.util.Calendar;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * @author Incloud Services S.A.C.
 */
@SuppressWarnings("serial")
public class CalendarToStringStyle extends ToStringStyle {

    public CalendarToStringStyle() {
        super();
        this.setUseShortClassName(true);
        this.setUseIdentityHashCode(false);
    }

    @Override
    protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
        if (value instanceof Calendar) {
            value = String.format("%1$tY-%1$tm-%1$td", value);
        }
        buffer.append(value);
    }

    public static CalendarToStringStyle instance() {
        return new CalendarToStringStyle();
    }
}
