package org.jvnet.ogc.gml.v_3_2_1.jts;

import net.opengis.gml.v_3_2_1.AbstractGeometryType;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;


import com.vividsolutions.jts.geom.Geometry;

public interface GML321ToJTSSRIDConverterInterface {

  public void convert(ObjectLocator locator, AbstractGeometryType source, Geometry target)
      throws ConversionFailedException;
}
