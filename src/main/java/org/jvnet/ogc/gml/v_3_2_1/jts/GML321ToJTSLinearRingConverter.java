package org.jvnet.ogc.gml.v_3_2_1.jts;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import net.opengis.gml.v_3_2_1.DirectPositionType;
import net.opengis.gml.v_3_2_1.LinearRingPropertyType;
import net.opengis.gml.v_3_2_1.LinearRingType;
import net.opengis.gml.v_3_2_1.PointPropertyType;
import net.opengis.gml.v_3_2_1.PointType;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;

public class GML321ToJTSLinearRingConverter
		extends
		AbstractGML321ToJTSConverter<LinearRingType, LinearRingPropertyType, LinearRing> {

	// + LinearRing

	private final GML321ToJTSCoordinateConverter coordinateConverter;
	private final GML321ToJTSConverterInterface<PointType, PointPropertyType, Point> pointConverter;

	public GML321ToJTSLinearRingConverter(
			GeometryFactory geometryFactory,
			GML321ToJTSSRIDConverterInterface sridConverter,
			GML321ToJTSCoordinateConverter coordinateConverter,
			GML321ToJTSConverterInterface<PointType, PointPropertyType, Point> pointConverter) {
		super(geometryFactory, sridConverter);
		this.coordinateConverter = new GML321ToJTSCoordinateConverter();
		this.pointConverter = pointConverter;
	}

	@Override
	protected LinearRing doCreateGeometry(ObjectLocator locator,
			LinearRingType linearRingType) throws ConversionFailedException {
		if (linearRingType.isSetPosOrPointPropertyOrPointRep()) {
			final ObjectLocator fieldLocator = locator
					.property(
							"posOrPointPropertyOrPointRep", linearRingType.getPosOrPointPropertyOrPointRep()); //$NON-NLS-1$

			final List<Coordinate> coordinates = new LinkedList<Coordinate>();
			for (int index = 0; index < linearRingType
					.getPosOrPointPropertyOrPointRep().size(); index++) {
				final JAXBElement<?> item = linearRingType
						.getPosOrPointPropertyOrPointRep().get(index);
				final ObjectLocator itemLocator = fieldLocator
						.item(index, item);
				final Object value = item.getValue();
				final ObjectLocator itemValueLocator = itemLocator.property(
						"value", value); //$NON-NLS-1$

				if (value instanceof DirectPositionType) {
					coordinates.add(coordinateConverter.createCoordinate(
							itemValueLocator, (DirectPositionType) value));
				} else if (value instanceof PointType) {
					coordinates.add(pointConverter.createGeometry(
							itemValueLocator, (PointType) value)
							.getCoordinate());
				} else if (value instanceof PointPropertyType) {
					coordinates.add(pointConverter.createGeometry(
							itemValueLocator, ((PointPropertyType) value).getPoint())
							.getCoordinate());
				} else {
					throw new ConversionFailedException(itemValueLocator,
							"Unexpected type."); //$NON-NLS-1$
				}
			}
			final Coordinate[] coordinatesArray = coordinates
					.toArray(new Coordinate[coordinates.size()]);
			return getGeometryFactory().createLinearRing(coordinatesArray);

		} else if (linearRingType.isSetPosList()) {

			final Coordinate[] coordinates = coordinateConverter
					.createCoordinates(
							locator.property(
									"posList", linearRingType.getPosList()), linearRingType //$NON-NLS-1$
									.getPosList());
			return getGeometryFactory().createLinearRing(coordinates);

		} else if (linearRingType.isSetCoordinates()) {
			final Coordinate[] coordinates = coordinateConverter
					.createCoordinates(locator.property(
							"coordinates", linearRingType.getCoordinates()), //$NON-NLS-1$
							linearRingType.getCoordinates());
			return getGeometryFactory().createLinearRing(coordinates);

		} else {
			throw new ConversionFailedException(locator);
		}
	}

	public LinearRing createGeometry(ObjectLocator locator,
			LinearRingPropertyType linearRingPropertyType)
			throws ConversionFailedException {
		if (linearRingPropertyType.isSetLinearRing()) {
			return createGeometry(
					locator.property(
							"linearRing", linearRingPropertyType.getLinearRing()), linearRingPropertyType.getLinearRing()); //$NON-NLS-1$
		} else {
			throw new ConversionFailedException(locator,
					"Expected [LinearRing] element."); //$NON-NLS-1$
		}
	}
}
