/*-
 * #%L
 * libtiled
 * %%
 * Copyright (C) 2004 - 2018 Thorbj?rn Lindeijer <thorbjorn@lindeijer.nl>
 * %%
 * Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice,
   this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci?n de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perder?n si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.04.16 a las 09:35:28 PM CEST 
//


package org.mapeditor.core;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * All `tileset` tags shall occur before the first `layer` tag so<br>
 * that parsers may rely on having the tilesets before needing to<br>
 * resolve tiles.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TileLayer", propOrder = {
    "data"
})
@Generated(value = "com.sun.tools.xjc.Driver", date = "2018-04-16T09:35:28+02:00", comments = "JAXB RI v2.2.11")
public class TileLayerData
    extends MapLayer
{

    /**
     * 
     */
    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2018-04-16T09:35:28+02:00", comments = "JAXB RI v2.2.11")
    protected Data data;

    /**
     * 
     * @return
     *     possible object is
     *     {@link Data }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2018-04-16T09:35:28+02:00", comments = "JAXB RI v2.2.11")
    public Data getData() {
        return data;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link Data }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2018-04-16T09:35:28+02:00", comments = "JAXB RI v2.2.11")
    public void setData(Data value) {
        this.data = value;
    }

}
