package org.jvnet.ogc.gml.v_3_2_1.jts;

import com.vividsolutions.jts.geom.GeometryFactory;

public class GML321ToJTSConstants {

	private GML321ToJTSConstants() {
	}

	public static final String[] DEFAULT_SRID_FORMAT_PATTERNS = {
			"EPSG:{0,number,integer}",
			"urn:ogc:def:crs:EPSG::{0,number,#}",
			"urn:ogc:def:crs:EPSG:{1}:{0,number,#}",
			"urn:x-ogc:def:crs:EPSG::{0,number,#}",
			"urn:x-ogc:def:crs:EPSG:{1}:{0,number,#}",
			"http://www.opengis.net/gml/srs/epsg.xml#{0,number,#}" };

	public static final GeometryFactory DEFAULT_GEOMETRY_FACTORY = new GeometryFactory();

	public static final GML321ToJTSSRIDConverterInterface DEFAULT_SRID_CONVERTER = new GML321ToJTSSRIDConverter(
			DEFAULT_SRID_FORMAT_PATTERNS);

}
