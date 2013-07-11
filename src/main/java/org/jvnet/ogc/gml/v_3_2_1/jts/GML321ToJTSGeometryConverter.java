package org.jvnet.ogc.gml.v_3_2_1.jts;

import java.text.MessageFormat;

import net.opengis.gml.v_3_2_1.AbstractGeometricAggregateType;
import net.opengis.gml.v_3_2_1.AbstractGeometryType;
import net.opengis.gml.v_3_2_1.GeometryPropertyType;
import net.opengis.gml.v_3_2_1.CurvePropertyType;
import net.opengis.gml.v_3_2_1.LineStringType;
import net.opengis.gml.v_3_2_1.LinearRingPropertyType;
import net.opengis.gml.v_3_2_1.LinearRingType;
import net.opengis.gml.v_3_2_1.MultiGeometryPropertyType;
import net.opengis.gml.v_3_2_1.MultiGeometryType;
import net.opengis.gml.v_3_2_1.MultiCurvePropertyType;
import net.opengis.gml.v_3_2_1.MultiCurveType;
import net.opengis.gml.v_3_2_1.MultiPointPropertyType;
import net.opengis.gml.v_3_2_1.MultiPointType;
import net.opengis.gml.v_3_2_1.MultiSurfacePropertyType;
import net.opengis.gml.v_3_2_1.MultiSurfaceType;
import net.opengis.gml.v_3_2_1.PointPropertyType;
import net.opengis.gml.v_3_2_1.PointType;
import net.opengis.gml.v_3_2_1.SurfacePropertyType;
import net.opengis.gml.v_3_2_1.PolygonType;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class GML321ToJTSGeometryConverter implements
		GML321ToJTSConverterInterface<Object, Object, Geometry> {

	// + Geometry
	// + Point
	// + Polygon
	// + LineString
	// + LinearRing
	// + GeometryCollection
	// + MultiPoint
	// + MultiLineString
	// + MultiPolygon

	private final GML321ToJTSCoordinateConverter coordinateConverter;
	private final GML321ToJTSConverterInterface<PointType, PointPropertyType, Point> pointConverter;
	private final GML321ToJTSConverterInterface<LineStringType, CurvePropertyType, LineString> lineStringConverter;
	private final GML321ToJTSConverterInterface<LinearRingType, LinearRingPropertyType, LinearRing> linearRingConverter;
	private final GML321ToJTSConverterInterface<PolygonType, SurfacePropertyType, Polygon> polygonConverter;
	private final GML321ToJTSConverterInterface<MultiPointType, MultiPointPropertyType, MultiPoint> multiPointConverter;
	private final GML321ToJTSConverterInterface<MultiCurveType, MultiCurvePropertyType, MultiLineString> multiLineStringConverter;
	private final GML321ToJTSConverterInterface<MultiSurfaceType, MultiSurfacePropertyType, MultiPolygon> multiPolygonConverter;
	private final GML321ToJTSConverterInterface<MultiGeometryType, MultiGeometryPropertyType, GeometryCollection> multiGeometryConverter;
	private final GML321ToJTSConverterInterface<AbstractGeometricAggregateType, MultiGeometryPropertyType, GeometryCollection> geometryCollectionConverter;

	public GML321ToJTSGeometryConverter(GeometryFactory geometryFactory,
			GML321ToJTSSRIDConverterInterface sridConverter) {
		this.coordinateConverter = new GML321ToJTSCoordinateConverter();
		this.pointConverter = new GML321ToJTSPointConverter(geometryFactory,
				sridConverter, this.coordinateConverter);
		this.lineStringConverter = new GML321ToJTSLineStringConverter(
				geometryFactory, sridConverter, this.coordinateConverter,
				this.pointConverter);
		this.linearRingConverter = new GML321ToJTSLinearRingConverter(
				geometryFactory, sridConverter, this.coordinateConverter,
				this.pointConverter);
		this.polygonConverter = new GML321ToJTSPolygonConverter(
				geometryFactory, sridConverter, this.linearRingConverter);
		this.multiPointConverter = new GML321ToJTSMultiPointConverter(
				geometryFactory, sridConverter, this.pointConverter);
		this.multiLineStringConverter = new GML321ToJTSMultiLineStringConverter(
				geometryFactory, sridConverter, this.lineStringConverter);
		this.multiPolygonConverter = new GML321ToJTSMultiPolygonConverter(
				geometryFactory, sridConverter, this.polygonConverter);
		this.multiGeometryConverter = new GML321ToJTSMultiGeometryConverter(
				geometryFactory, sridConverter, this);
		this.geometryCollectionConverter = new GML321ToJTSGeometryCollectionConverter(
				this.multiPointConverter, this.multiLineStringConverter,
				this.multiPolygonConverter,
				this.multiGeometryConverter);
	}

	public GML321ToJTSGeometryConverter() {
		this(GML321ToJTSConstants.DEFAULT_GEOMETRY_FACTORY,
				GML321ToJTSConstants.DEFAULT_SRID_CONVERTER);
	}

	/*public Geometry createGeometry(ObjectLocator locator,
			AbstractGeometryType abstractGeometryType)
			throws ConversionFailedException {
		if (abstractGeometryType instanceof PointType) {
			return pointConverter.createGeometry(locator,
					(PointType) abstractGeometryType);
		} else if (abstractGeometryType instanceof PolygonType) {
			return polygonConverter.createGeometry(locator,
					(PolygonType) abstractGeometryType);
		} else if (abstractGeometryType instanceof LineStringType) {
			return lineStringConverter.createGeometry(locator,
					(LineStringType) abstractGeometryType);
		} else if (abstractGeometryType instanceof LinearRingType) {
			return linearRingConverter.createGeometry(locator,
					(LinearRingType) abstractGeometryType);
		} else if (abstractGeometryType instanceof MultiPointType) {
			return multiPointConverter.createGeometry(locator,
					(MultiPointType) abstractGeometryType);
		} else if (abstractGeometryType instanceof MultiCurveType) {
			return multiLineStringConverter.createGeometry(locator,
					(MultiCurveType) abstractGeometryType);
		} else if (abstractGeometryType instanceof MultiSurfaceType) {
			return multiPolygonConverter.createGeometry(locator,
					(MultiSurfaceType) abstractGeometryType);
		} else if (abstractGeometryType instanceof AbstractGeometricAggregateType) {
			return geometryCollectionConverter.createGeometry(locator,
					(AbstractGeometricAggregateType) abstractGeometryType);
		} else {
			throw new ConversionFailedException(locator, "Unexpected type."); //$NON-NLS-1$
		}

	}*/

	public Geometry createGeometry(ObjectLocator locator,
			Object abstractGeometryType) throws ConversionFailedException {
		if (abstractGeometryType == null) {
			return null;
		} else if (abstractGeometryType instanceof PointType) {
			return pointConverter.createGeometry(locator,
					(PointType) abstractGeometryType);
		} else if (abstractGeometryType instanceof PointPropertyType) {
			return pointConverter.createGeometry(locator,
					((PointPropertyType) abstractGeometryType).getPoint());
		} else if (abstractGeometryType instanceof PolygonType) {
			return polygonConverter.createGeometry(locator,
					(PolygonType) abstractGeometryType);
		} else if (abstractGeometryType instanceof SurfacePropertyType) {
			return polygonConverter.createGeometry(locator,
					(PolygonType) ((SurfacePropertyType) abstractGeometryType).getAbstractSurface().getValue());
		} else if (abstractGeometryType instanceof LineStringType) {
			return lineStringConverter.createGeometry(locator,
					(LineStringType) abstractGeometryType);
		} else if (abstractGeometryType instanceof CurvePropertyType) {
			return lineStringConverter.createGeometry(locator,
					(LineStringType) ((CurvePropertyType) abstractGeometryType).getAbstractCurve().getValue());
		} else if (abstractGeometryType instanceof LinearRingType) {
			return linearRingConverter.createGeometry(locator,
					(LinearRingType) abstractGeometryType);
		} else if (abstractGeometryType instanceof LinearRingPropertyType) {
			return linearRingConverter.createGeometry(locator,
					((LinearRingPropertyType) abstractGeometryType).getLinearRing());
		} else if (abstractGeometryType instanceof MultiPointType) {
			return multiPointConverter.createGeometry(locator,
					(MultiPointType) abstractGeometryType);
		} else if (abstractGeometryType instanceof MultiPointPropertyType) {
			return multiPointConverter.createGeometry(locator,
					((MultiPointPropertyType) abstractGeometryType).getMultiPoint());
		} else if (abstractGeometryType instanceof MultiCurveType) {
			return multiLineStringConverter.createGeometry(locator,
					(MultiCurveType) abstractGeometryType);
		} else if (abstractGeometryType instanceof MultiCurvePropertyType) {
			return multiLineStringConverter.createGeometry(locator,
					((MultiCurvePropertyType) abstractGeometryType).getMultiCurve());
		} else if (abstractGeometryType instanceof MultiSurfaceType) {
			return multiPolygonConverter.createGeometry(locator,
					(MultiSurfaceType) abstractGeometryType);
		} else if (abstractGeometryType instanceof MultiSurfacePropertyType) {
			return multiPolygonConverter.createGeometry(locator,
					((MultiSurfacePropertyType) abstractGeometryType).getMultiSurface());
		} else if (abstractGeometryType instanceof AbstractGeometricAggregateType) {
			return geometryCollectionConverter.createGeometry(locator,
					(AbstractGeometricAggregateType) abstractGeometryType);
		} else if (abstractGeometryType instanceof MultiGeometryPropertyType) {
			return geometryCollectionConverter.createGeometry(locator,
					((MultiGeometryPropertyType) abstractGeometryType).getAbstractGeometricAggregate().getValue());
		} else if (abstractGeometryType instanceof GeometryPropertyType) {
			final GeometryPropertyType geometryPropertyType = (GeometryPropertyType) abstractGeometryType;
			if (geometryPropertyType.isSetAbstractGeometry()) {
				return createGeometry(
						locator
								.property(
										"geometry", geometryPropertyType.getAbstractGeometry()).property("value", geometryPropertyType.getAbstractGeometry().getValue()), geometryPropertyType.getAbstractGeometry().getValue()); //$NON-NLS-1$
			} else {
				throw new ConversionFailedException(locator,
						"Expected [Polygon] element."); //$NON-NLS-1$
			}

		} else

		{
			throw new ConversionFailedException(
					locator,
					MessageFormat
							.format(
									"Unexpected type [{0}].", abstractGeometryType.getClass().getName())); //$NON-NLS-1$
		}

	}
}
