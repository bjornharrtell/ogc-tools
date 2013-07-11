package org.jvnet.ogc.gml.v_3_2_1.jts;

import javax.xml.bind.JAXBElement;

import org.jvnet.ogc.gml.v_3_2_1.ObjectFactoryInterface;

import net.opengis.gml.v_3_2_1.AbstractGeometryType;
import net.opengis.gml.v_3_2_1.GeometryPropertyType;
import net.opengis.gml.v_3_2_1.MultiGeometryPropertyType;
import net.opengis.gml.v_3_2_1.MultiGeometryType;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;

public class JTSToGML321MultiGeometryConverter
		extends
		AbstractJTSToGML321Converter<MultiGeometryType, MultiGeometryPropertyType, GeometryCollection> {
	private final JTSToGML321ConverterInterface<Object, GeometryPropertyType, Geometry> geometryConverter;

	public JTSToGML321MultiGeometryConverter(
			ObjectFactoryInterface objectFactory,
			JTSToGML321SRSReferenceGroupConverterInterface srsReferenceGroupConverter,
			JTSToGML321ConverterInterface<Object, GeometryPropertyType, Geometry> geometryConverter) {
		super(objectFactory, srsReferenceGroupConverter);
		this.geometryConverter = geometryConverter;
	}

	@Override
	protected MultiGeometryType doCreateGeometryType(
			GeometryCollection multiGeometry) {
		final MultiGeometryType multiGeometryType = getObjectFactory()
				.createMultiGeometryType();

		for (int index = 0; index < multiGeometry.getNumGeometries(); index++) {
			final Geometry geometry = multiGeometry.getGeometryN(index);

			multiGeometryType.getGeometryMember().add(
					geometryConverter.createPropertyType(geometry));

		}
		return multiGeometryType;
	}

	public MultiGeometryPropertyType createPropertyType(
			GeometryCollection multiGeometry) {
		final MultiGeometryPropertyType multiGeometryPropertyType = getObjectFactory()
				.createMultiGeometryPropertyType();
		multiGeometryPropertyType
				.setAbstractGeometricAggregate(createElement(multiGeometry));
		return multiGeometryPropertyType;
	}

	public JAXBElement<MultiGeometryType> createElement(
			GeometryCollection geometryCollection) {
		return getObjectFactory().createMultiGeometry(
				createGeometryType(geometryCollection));
	}
}
