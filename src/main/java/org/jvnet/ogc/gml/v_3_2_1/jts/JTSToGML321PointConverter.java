package org.jvnet.ogc.gml.v_3_2_1.jts;

import javax.xml.bind.JAXBElement;

import org.jvnet.ogc.gml.v_3_2_1.ObjectFactoryInterface;

import net.opengis.gml.v_3_2_1.DirectPositionType;
import net.opengis.gml.v_3_2_1.PointPropertyType;
import net.opengis.gml.v_3_2_1.PointType;

import com.vividsolutions.jts.geom.Point;

public class JTSToGML321PointConverter extends
		AbstractJTSToGML321Converter<PointType, PointPropertyType, Point> {
	private final JTSToGML321CoordinateConverter coordinateConverter;

	public JTSToGML321PointConverter(
			ObjectFactoryInterface objectFactory,
			JTSToGML321SRSReferenceGroupConverterInterface srsReferenceGroupConverter,
			JTSToGML321CoordinateConverter coordinateConverter) {
		super(objectFactory, srsReferenceGroupConverter);
		this.coordinateConverter = coordinateConverter;
	}

	@Override
	protected PointType doCreateGeometryType(Point point) {

		final PointType resultPoint = getObjectFactory().createPointType();

		if (!point.isEmpty()) {
			final DirectPositionType directPosition = coordinateConverter
					.convertCoordinate(point.getCoordinate());
			resultPoint.setPos(directPosition);
		}
		return resultPoint;

	}

	public PointPropertyType createPropertyType(Point point) {
		final PointPropertyType pointPropertyType = getObjectFactory()
				.createPointPropertyType();

		pointPropertyType.setPoint(createGeometryType(point));
		return pointPropertyType;

	}

	public JAXBElement<PointType> createElement(Point point) {
		return getObjectFactory().createPoint(createGeometryType(point));
	}

}
