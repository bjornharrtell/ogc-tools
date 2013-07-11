package org.jvnet.ogc.gml.v_3_2_1.jts;

import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import net.opengis.gml.v_3_2_1.AbstractGeometryType;
import net.opengis.gml.v_3_2_1.GeometryPropertyType;

import org.jvnet.ogc.gml.v_3_2_1.ObjectFactoryInterface;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

public class JAXBContextFactory {

	public static final String PROPERTY_NAME_SRID_CONVERTER = GML321ToJTSSRIDConverterInterface.class
			.getName();
	public static final String PROPERTY_NAME_SRS_REFERENCE_GROUP_CONVERTER = JTSToGML321SRSReferenceGroupConverterInterface.class
			.getName();
	public static final String PROPERTY_NAME_OBJECT_FACTORY = ObjectFactoryInterface.class
			.getName();
	public static final String PROPERTY_NAME_GEOMETRY_FACTORY = GeometryFactory.class
			.getName();

	public static final String PROPERTY_NAME_CONTEXT_PATH = JAXBContextFactory.class
			.getName() + ".CONTEXT_PATH";

	public static final String DEFAULT_CONTEXT_PATH = net.opengis.gml.v_3_2_1.ObjectFactory.class
			.getPackage().getName();

	public static JAXBContext createContext(String contextPath,
			ClassLoader classLoader, Map<String, Object> properties)
			throws JAXBException {
		final String innerContextPath;

		if (properties.containsKey(PROPERTY_NAME_CONTEXT_PATH)) {
			innerContextPath = (String) properties.get(DEFAULT_CONTEXT_PATH);
		} else {
			innerContextPath = DEFAULT_CONTEXT_PATH;
		}
		final JAXBContext context = JAXBContext.newInstance(innerContextPath,
				classLoader, properties);

		final ObjectFactoryInterface objectFactory;
		if (properties.containsKey(PROPERTY_NAME_OBJECT_FACTORY)) {
			objectFactory = (ObjectFactoryInterface) properties
					.get(PROPERTY_NAME_OBJECT_FACTORY);
		} else {
			objectFactory = JTSToGML321Constants.DEFAULT_OBJECT_FACTORY;
		}

		final JTSToGML321SRSReferenceGroupConverterInterface srsReferenceGroupConverter;

		if (properties.containsKey(PROPERTY_NAME_SRS_REFERENCE_GROUP_CONVERTER)) {
			srsReferenceGroupConverter = (JTSToGML321SRSReferenceGroupConverterInterface) properties
					.get(PROPERTY_NAME_SRS_REFERENCE_GROUP_CONVERTER);

		} else {
			srsReferenceGroupConverter = JTSToGML321Constants.DEFAULT_SRS_REFERENCE_GROUP_CONVERTER;
		}
		final JTSToGML321ConverterInterface<Object, GeometryPropertyType, Geometry> marshallerConverter = new JTSToGML321GeometryConverter(
				objectFactory, srsReferenceGroupConverter);

		final GeometryFactory geometryFactory;
		if (properties.containsKey(PROPERTY_NAME_GEOMETRY_FACTORY)) {
			geometryFactory = (GeometryFactory) properties
					.get(PROPERTY_NAME_GEOMETRY_FACTORY);
		} else {
			geometryFactory = GML321ToJTSConstants.DEFAULT_GEOMETRY_FACTORY;
		}

		final GML321ToJTSSRIDConverterInterface sridConverter;
		if (properties.containsKey(PROPERTY_NAME_SRID_CONVERTER)) {
			sridConverter = (GML321ToJTSSRIDConverterInterface) properties
					.get(PROPERTY_NAME_SRID_CONVERTER);

		} else {
			sridConverter = GML321ToJTSConstants.DEFAULT_SRID_CONVERTER;
		}
		final GML321ToJTSConverterInterface<Object, Object, Geometry> unmarshallerConverter = new GML321ToJTSGeometryConverter(
				geometryFactory, sridConverter);
		return new JAXBContextImpl(context, marshallerConverter,
				unmarshallerConverter);
	}
}
