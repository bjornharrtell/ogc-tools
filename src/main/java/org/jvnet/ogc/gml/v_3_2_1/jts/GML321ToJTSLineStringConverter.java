package org.jvnet.ogc.gml.v_3_2_1.jts;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import net.opengis.gml.v_3_2_1.AbstractCurveType;
import net.opengis.gml.v_3_2_1.DirectPositionType;
import net.opengis.gml.v_3_2_1.CurvePropertyType;
import net.opengis.gml.v_3_2_1.LineStringType;
import net.opengis.gml.v_3_2_1.PointPropertyType;
import net.opengis.gml.v_3_2_1.PointType;

import org.jvnet.jaxb2_commons.locator.ItemObjectLocator;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

public class GML321ToJTSLineStringConverter
		extends
		AbstractGML321ToJTSConverter<LineStringType, CurvePropertyType, LineString> {

	// + LineString

	private final GML321ToJTSCoordinateConverter coordinateConverter;
	private final GML321ToJTSConverterInterface<PointType, PointPropertyType, Point> pointConverter;

	public GML321ToJTSLineStringConverter(
			GeometryFactory geometryFactory,
			GML321ToJTSSRIDConverterInterface sridConverter,
			GML321ToJTSCoordinateConverter coordinateConverter,
			GML321ToJTSConverterInterface<PointType, PointPropertyType, Point> pointConverter) {
		super(geometryFactory, sridConverter);
		this.coordinateConverter = coordinateConverter;
		this.pointConverter = new GML321ToJTSPointConverter(geometryFactory,
				sridConverter, coordinateConverter);
	}

	@Override
	protected LineString doCreateGeometry(ObjectLocator locator,
			LineStringType lineStringType) throws ConversionFailedException {
		if (lineStringType.isSetPosOrPointPropertyOrPointRep()) {

			final List<Coordinate> coordinates = new LinkedList<Coordinate>();
			final ObjectLocator fieldLocator = locator
					.property(
							"PosOrPointPropertyOrPointRep", lineStringType.getPosOrPointPropertyOrPointRep()); //$NON-NLS-1$
			for (int index = 0; index < lineStringType
					.getPosOrPointPropertyOrPointRep().size(); index++) {
				final JAXBElement<?> item = lineStringType
						.getPosOrPointPropertyOrPointRep().get(index);
				final ItemObjectLocator itemLocator = fieldLocator.item(index,
						item);
				final Object value = item.getValue();
				final ObjectLocator itemValueLocator = itemLocator.property(
						"value", value); //$NON-NLS-1$

				if (value instanceof PointPropertyType) {
					coordinates.add(pointConverter.createGeometry(
							itemValueLocator, ((PointPropertyType) value).getPoint())
							.getCoordinate());
				} else if (value instanceof DirectPositionType) {
					coordinates.add(coordinateConverter.createCoordinate(
							itemValueLocator, (DirectPositionType) value));
				} else {
					throw new ConversionFailedException(itemLocator,
							"Unexpected type."); //$NON-NLS-1$
				}
			}
			final Coordinate[] coordinatesArray = coordinates
					.toArray(new Coordinate[coordinates.size()]);
			return getGeometryFactory().createLineString(coordinatesArray);

		} else if (lineStringType.isSetPosList()) {

			final Coordinate[] coordinates = coordinateConverter
					.createCoordinates(locator.property("posList",//$NON-NLS-1$
							lineStringType.getPosList()),
							lineStringType.getPosList());
			return getGeometryFactory().createLineString(coordinates);

		} else if (lineStringType.isSetCoordinates()) {
			final Coordinate[] coordinates = coordinateConverter
					.createCoordinates(locator.property(
							"coordinates", lineStringType.getCoordinates()), //$NON-NLS-1$
							lineStringType.getCoordinates());
			return getGeometryFactory().createLineString(coordinates);

		} else {
			throw new ConversionFailedException(locator);
		}
	}

	public LineString createGeometry(ObjectLocator locator,
			CurvePropertyType curvePropertyType)
			throws ConversionFailedException {
		
		if (curvePropertyType.isSetAbstractCurve()) {
			AbstractCurveType abstractCurve = curvePropertyType.getAbstractCurve().getValue();
			return createGeometry(
					locator.property(
							"lineString", abstractCurve), curvePropertyType); //$NON-NLS-1$
		} else {
			throw new ConversionFailedException(locator,
					"Expected [LineString] element."); //$NON-NLS-1$
		}
	}

}
