package org.jvnet.ogc.gml.v_3_2_1.jts;

import net.opengis.gml.v_3_2_1.AbstractGeometryType;
import net.opengis.gml.v_3_2_1.AbstractRingType;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

import com.vividsolutions.jts.geom.Geometry;

public interface GML321ToJTSConverterInterface<G, P, J extends Geometry> {
  J createGeometry(ObjectLocator locator, G geometryType) throws ConversionFailedException;
}
