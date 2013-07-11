package org.jvnet.ogc.gml.v_3_2_1.jts;

import net.opengis.gml.v_3_2_1.AbstractGeometryType;

import com.vividsolutions.jts.geom.Geometry;

public interface JTSToGML321SRSReferenceGroupConverterInterface {

  public void convert(Geometry source, AbstractGeometryType target);
}
