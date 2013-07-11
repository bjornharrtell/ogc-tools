package org.jvnet.ogc.gml.v_3_2_1.jts;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

import net.opengis.gml.v_3_2_1.AbstractGeometryType;

import com.vividsolutions.jts.geom.Geometry;

public class NullGML321ToJTSSRIDConverter implements GML321ToJTSSRIDConverterInterface {

  public void convert(ObjectLocator locator, AbstractGeometryType source, Geometry target) {
  }

}
