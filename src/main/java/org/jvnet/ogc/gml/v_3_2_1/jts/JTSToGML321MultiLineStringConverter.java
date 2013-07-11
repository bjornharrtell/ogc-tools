package org.jvnet.ogc.gml.v_3_2_1.jts;

import javax.xml.bind.JAXBElement;

import org.jvnet.ogc.gml.v_3_2_1.ObjectFactoryInterface;

import net.opengis.gml.v_3_2_1.CurvePropertyType;
import net.opengis.gml.v_3_2_1.LineStringType;
import net.opengis.gml.v_3_2_1.MultiCurvePropertyType;
import net.opengis.gml.v_3_2_1.MultiCurveType;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;

public class JTSToGML321MultiLineStringConverter
		extends
		AbstractJTSToGML321Converter<MultiCurveType, MultiCurvePropertyType, MultiLineString> {
	private final JTSToGML321ConverterInterface<LineStringType, CurvePropertyType, LineString> lineStringConverter;

	public JTSToGML321MultiLineStringConverter(
			ObjectFactoryInterface objectFactory,
			JTSToGML321SRSReferenceGroupConverterInterface srsReferenceGroupConverter,
			JTSToGML321ConverterInterface<LineStringType, CurvePropertyType, LineString> lineStringConverter) {
		super(objectFactory, srsReferenceGroupConverter);
		this.lineStringConverter = lineStringConverter;
	}

	protected MultiCurveType doCreateGeometryType(
			MultiLineString multiLineString) {
		final MultiCurveType multiCurveType = getObjectFactory()
				.createMultiCurveType();
		for (int index = 0; index < multiLineString.getNumGeometries(); index++) {
			final LineString lineString = (LineString) multiLineString
					.getGeometryN(index);
			multiCurveType.getCurveMember().add(
					lineStringConverter.createPropertyType(lineString));
		}

		return multiCurveType;
	}

	public MultiCurvePropertyType createPropertyType(
			MultiLineString multiLineString) {
		final MultiCurvePropertyType multiCurvePropertyType = getObjectFactory()
				.createMultiCurvePropertyType();
		multiCurvePropertyType
				.setMultiCurve(createGeometryType(multiLineString));
		return multiCurvePropertyType;
	}

	public JAXBElement<MultiCurveType> createElement(
			MultiLineString multiLineString) {
		return getObjectFactory().createMultiLineString(
				createGeometryType(multiLineString));
	}
}
