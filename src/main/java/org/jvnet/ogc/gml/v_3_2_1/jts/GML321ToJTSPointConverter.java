package org.jvnet.ogc.gml.v_3_2_1.jts;

import net.opengis.gml.v_3_2_1.PointPropertyType;
import net.opengis.gml.v_3_2_1.PointType;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class GML321ToJTSPointConverter extends
		AbstractGML321ToJTSConverter<PointType, PointPropertyType, Point> {

	// + Point

	private final GML321ToJTSCoordinateConverter coordinateConverter;

	public GML321ToJTSPointConverter(GeometryFactory geometryFactory,
			GML321ToJTSSRIDConverterInterface sridConverter,
			GML321ToJTSCoordinateConverter coordinateConverter) {
		super(geometryFactory, sridConverter);
		this.coordinateConverter = coordinateConverter;
	}

	@Override
	protected Point doCreateGeometry(ObjectLocator locator, PointType pointType)
			throws ConversionFailedException {

		if (pointType.isSetPos()) {
			return getGeometryFactory()
					.createPoint(
							coordinateConverter.createCoordinate(
									locator.property("pos", pointType.getPos()), pointType.getPos())); //$NON-NLS-1$
		} else if (pointType.isSetCoordinates()) {
			final Coordinate[] coords = coordinateConverter
					.createCoordinates(
							locator.property(
									"coordinates", pointType.getCoordinates()), pointType.getCoordinates()); //$NON-NLS-1$
			if (coords.length != 1) {
				throw new ConversionFailedException(
						locator.property(
								"coordinates", pointType.getCoordinates()), "Expected exactly one coordinate."); //$NON-NLS-1$
			} else {
				return getGeometryFactory().createPoint(coords[0]);

			}

		} else {
			throw new ConversionFailedException(locator,
					"Either [pos], [coordinates] or [coord] elements are expected."); //$NON-NLS-1$
		}

	}

	public Point createGeometry(ObjectLocator locator,
			PointPropertyType pointPropertyType)
			throws ConversionFailedException {
		if (pointPropertyType.isSetPoint()) {
			return createGeometry(
					locator.property("point", pointPropertyType.getPoint()), pointPropertyType.getPoint()); //$NON-NLS-1$
		} else {
			throw new ConversionFailedException(locator,
					"Expected [Point] element."); //$NON-NLS-1$
		}
	}
}
