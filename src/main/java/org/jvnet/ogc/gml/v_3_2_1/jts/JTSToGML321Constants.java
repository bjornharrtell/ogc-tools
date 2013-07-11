package org.jvnet.ogc.gml.v_3_2_1.jts;

import org.jvnet.ogc.gml.v_3_2_1.ObjectFactory;
import org.jvnet.ogc.gml.v_3_2_1.ObjectFactoryInterface;

public class JTSToGML321Constants {

	private JTSToGML321Constants() {
	}

	public static final ObjectFactoryInterface DEFAULT_OBJECT_FACTORY = new ObjectFactory();

	public static final JTSToGML321SRSReferenceGroupConverterInterface DEFAULT_SRS_REFERENCE_GROUP_CONVERTER = new JTSToGML321SRSReferenceGroupConverter();

	public static final String DEFAULT_SRID_FORMAT_PATTERN = "urn:ogc:def:crs:EPSG::{0,number,#}"; //$NON-NLS-1$
}
