/*
 * $Id: EntityField.java 3466 2004-09-20 22:27:16Z jaz $
 *
 *  Copyright (c) 2001-2003 The Open For Business Project - www.ofbiz.org
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a
 *  copy of this software and associated documentation files (the "Software"),
 *  to deal in the Software without restriction, including without limitation
 *  the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *  and/or sell copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included
 *  in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 *  OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 *  CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 *  OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 *  THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.ofbiz.webapp.pseudotag;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.model.ModelEntity;
import org.ofbiz.entity.model.ModelField;

/**
 * Pseudo-Tag to Print Localized Entity Fields
 *
 * @author     <a href="mailto:jaz@ofbiz.org">Andy Zeneski</a>
 * @author     <a href="mailto:jonesde@ofbiz.org">David E. Jones</a>
 * @version    $Rev: 3466 $
 * @since      2.0
 */
public class EntityField {

    PageContext pageContextInternal = null;

    public EntityField(PageContext pageContextInternal) {
        this.pageContextInternal = pageContextInternal;
    }

    public void run(String attribute, String field)
        throws IOException, GenericEntityException {
        run(attribute, field, null, null, null, null, pageContextInternal);
    }

    public void run(String attribute, String field, String defaultStr)
        throws IOException, GenericEntityException {
        run(attribute, field, null, null, defaultStr, null, pageContextInternal);
    }

    public void run(String attribute, String field, String prefix, String suffix)
        throws IOException, GenericEntityException {
        run(attribute, field, prefix, suffix, null, null, pageContextInternal);
    }

    /** Run the EntityField Pseudo-Tag, all fields except attribute, and field can be null */
    public void run(String attribute, String field, String prefix, String suffix,
        String defaultStr, String type) throws IOException, GenericEntityException {
        run(attribute, field, prefix, suffix, defaultStr, type, pageContextInternal);
    }

    /* --- STATIC METHODS --- */

    public static void run(String attribute, String field,
        PageContext pageContext) throws IOException, GenericEntityException {
        run(attribute, field, null, null, null, null, pageContext);
    }

    public static void run(String attribute, String field, String defaultStr,
        PageContext pageContext) throws IOException, GenericEntityException {
        run(attribute, field, null, null, defaultStr, null, pageContext);
    }

    public static void run(String attribute, String field, String prefix, String suffix,
        PageContext pageContext) throws IOException, GenericEntityException {
        run(attribute, field, prefix, suffix, null, null, pageContext);
    }

    /** Run the EntityField Pseudo-Tag, all fields except attribute, field, and pageContext can be null */
    public static void run(String attribute, String field, String prefix, String suffix,
        String defaultStr, String type, PageContext pageContext) throws IOException, GenericEntityException {
        if (attribute == null || pageContext == null) {
            throw new IllegalArgumentException("Required parameter (attribute or pageContext) missing");
        }

        if (defaultStr == null) defaultStr = "";
        String fieldObjectType = null;
        Object fieldObject = null;

        /* TYPE and FIELD should not be used together. TYPE defines the type of an object.
         *  When FIELD is defined, type is assumed to be a GenericValue or a Map.
         */

        // We should be a ValueObject
        if (type == null) {
            Object attrObject = pageContext.findAttribute(attribute);

            if (attrObject == null) {
                fieldObject = defaultStr;
                fieldObjectType = "comment"; // Default for NULL objects.
            } else {
                if (attrObject instanceof GenericValue) {
                    // Get the ValueObject from PageContext.
                    GenericValue valueObject = (GenericValue) attrObject;
                    ModelEntity entityModel = valueObject.getModelEntity();

                    fieldObject = valueObject.get(field);

                    // Get the Object Type.
                    if (fieldObject != null) {
                        ModelField fieldModel = entityModel.getField(field);

                        fieldObjectType = fieldModel.getType();
                    } else {
                        // Debug.logWarning("[EntityFieldTag] : Null ValueObject passed.", module);
                        fieldObject = defaultStr;
                        fieldObjectType = "comment"; // Default for NULL objects.
                    }
                } else if (attrObject instanceof Map) {
                    Map valueMap = (Map) attrObject;

                    fieldObject = valueMap.get(field);
                    fieldObjectType = "comment"; // Default for NULL objects.
                } else {
                    // handle non-composite types directly
                    fieldObject = attrObject;
                    fieldObjectType = "comment"; // Default for Strings.
                }
            }
        } else {
            // We should be either a 'currency' or a java type.
            fieldObject = pageContext.findAttribute(attribute);
            // javaType = type;
            // Set a default for NULL objects.
            if (fieldObject == null) {
                // Debug.logWarning("[EntityFieldTag] : Null Object passed.", module);
                fieldObject = defaultStr;
                // javaType = "java.lang.String";
            }
            if (type.equalsIgnoreCase("currency")) {
                // Convert the String to a Double for standard processing.
                if (fieldObject instanceof String) {
                    String objStr = (String) fieldObject;

                    try {
                        if (objStr.length() > 0) {
                            fieldObject = new Double(objStr);
                        }
                    } catch (NumberFormatException nfe) {
                        throw new IllegalStateException("String not a number for printing of type currency: " + objStr);
                    }
                }
                // The default type for currency is Double.
                // javaType = "java.lang.Double";
                fieldObjectType = "currency-amount";
            }
        }

        // Get the Locale from the Request object.
        Locale userLocale = null;

        if (false) {
            // disable this until we get i18n issues addressed
            userLocale = pageContext.getRequest().getLocale();
        }
        if (userLocale == null) {
            userLocale = Locale.getDefault();
        }

        // Format the Object based on its type.
        String fieldString = null;

        if (fieldObject instanceof java.lang.String) {
            fieldString = (String) fieldObject;
        } else if (fieldObject instanceof java.lang.Double) {
            Double doubleValue = (Double) fieldObject;
            NumberFormat nf = null;

            if ("currency-amount".equals(fieldObjectType)) {
                // TODO: convert currency to current Locale
                nf = NumberFormat.getCurrencyInstance(userLocale);
            } else {
                nf = NumberFormat.getNumberInstance(userLocale);
            }
            fieldString = nf.format(doubleValue);
        } else if (fieldObject instanceof java.lang.Float) {
            Float floatValue = (Float) fieldObject;
            NumberFormat nf = null;

            if ("currency-amount".equals(fieldObjectType)) {
                // TODO: convert currency to current Locale
                nf = NumberFormat.getCurrencyInstance(userLocale);
            } else {
                nf = NumberFormat.getNumberInstance(userLocale);
            }
            fieldString = nf.format(floatValue);
        } else if (fieldObject instanceof java.lang.Long) {
            Long longValue = (Long) fieldObject;
            NumberFormat nf = NumberFormat.getNumberInstance(userLocale);

            fieldString = nf.format(longValue);
        } else if (fieldObject instanceof java.lang.Integer) {
            Integer intValue = (Integer) fieldObject;
            NumberFormat nf = NumberFormat.getNumberInstance(userLocale);

            fieldString = nf.format(intValue);
        } else if (fieldObject instanceof java.lang.Boolean) {
            Boolean booleanValue = (Boolean) fieldObject;

            if (booleanValue.booleanValue()) {
                fieldString = "Yes";
            } else {
                fieldString = "No";
            }
        } else if (fieldObject instanceof java.sql.Timestamp) {
            Date dateValue = (Date) fieldObject;
            DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG,
                    DateFormat.FULL, userLocale);

            fieldString = df.format(dateValue);
        } else if (fieldObject instanceof java.sql.Time) {
            Date dateValue = (Date) fieldObject;
            DateFormat df = DateFormat.getTimeInstance(DateFormat.FULL, userLocale);

            fieldString = df.format(dateValue);
        } else if (fieldObject instanceof java.sql.Date) {
            Date dateValue = (Date) fieldObject;
            DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, userLocale);

            fieldString = df.format(dateValue);
        } else {
            if (fieldObject != null) {
                fieldString = fieldObject.toString();
            } else {
                fieldString = "";
            }
        }

        JspWriter out = pageContext.getOut();

        if (fieldString.length() > 0) {
            if (prefix != null)
                out.print(prefix);
            out.print(fieldString);
            if (suffix != null)
                out.print(suffix);
        }
    }
}
