package org.jvnet.ogc.gml.v_3_2_1.jts;

import javax.xml.bind.JAXBElement;

import net.opengis.gml.v_3_2_1.AbstractGeometryType;

import com.vividsolutions.jts.geom.Geometry;

public interface JTSToGML321ConverterInterface<G, P, J extends Geometry> {
  G createGeometryType(J geometry);

  P createPropertyType(J geometry);

  JAXBElement<? extends G> createElement(J geometry);

}
