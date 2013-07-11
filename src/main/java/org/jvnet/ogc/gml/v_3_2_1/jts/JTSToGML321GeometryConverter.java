package org.jvnet.ogc.gml.v_3_2_1.jts;

import javax.xml.bind.JAXBElement;

import org.jvnet.ogc.gml.v_3_2_1.ObjectFactoryInterface;

import net.opengis.gml.v_3_2_1.AbstractGeometryType;
import net.opengis.gml.v_3_2_1.AbstractRingPropertyType;
import net.opengis.gml.v_3_2_1.GeometryPropertyType;
import net.opengis.gml.v_3_2_1.CurvePropertyType;
import net.opengis.gml.v_3_2_1.LineStringType;
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

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class JTSToGML321GeometryConverter
		extends
		AbstractJTSToGML321Converter<Object, GeometryPropertyType, Geometry> {

	private final JTSToGML321CoordinateConverter coordinateConverter;
	private final JTSToGML321ConverterInterface<PointType, PointPropertyType, Point> pointConverter;
	private final JTSToGML321ConverterInterface<LinearRingType, AbstractRingPropertyType, LinearRing> linearRingConverter;
	private final JTSToGML321ConverterInterface<LineStringType, CurvePropertyType, LineString> lineStringConverter;
	private final JTSToGML321ConverterInterface<PolygonType, SurfacePropertyType, Polygon> polygonConverter;
	private final JTSToGML321ConverterInterface<MultiPointType, MultiPointPropertyType, MultiPoint> multiPointConverter;
	private final JTSToGML321ConverterInterface<MultiCurveType, MultiCurvePropertyType, MultiLineString> multiLineStringConverter;
	private final JTSToGML321ConverterInterface<MultiSurfaceType, MultiSurfacePropertyType, MultiPolygon> multiPolygonConverter;
	private final JTSToGML321ConverterInterface<MultiGeometryType, MultiGeometryPropertyType, GeometryCollection> multiGeometryConverter;

	public JTSToGML321GeometryConverter() {
		this(JTSToGML321Constants.DEFAULT_OBJECT_FACTORY,
				JTSToGML321Constants.DEFAULT_SRS_REFERENCE_GROUP_CONVERTER);
	}

	public JTSToGML321GeometryConverter(
			ObjectFactoryInterface objectFactory,
			JTSToGML321SRSReferenceGroupConverterInterface srsReferenceGroupConverter) {
		super(objectFactory, srsReferenceGroupConverter);
		this.coordinateConverter = new JTSToGML321CoordinateConverter(
				objectFactory, srsReferenceGroupConverter);
		this.pointConverter = new JTSToGML321PointConverter(objectFactory,
				srsReferenceGroupConverter, this.coordinateConverter);
		this.linearRingConverter = new JTSToGML321LinearRingConverter(
				objectFactory, srsReferenceGroupConverter,
				this.coordinateConverter);
		this.lineStringConverter = new JTSToGML321LineStringConverter(
				objectFactory, srsReferenceGroupConverter,
				this.coordinateConverter);
		this.polygonConverter = new JTSToGML321PolygonConverter(objectFactory,
				srsReferenceGroupConverter, this.linearRingConverter);
		this.multiPointConverter = new JTSToGML321MultiPointConverter(
				objectFactory, srsReferenceGroupConverter, this.pointConverter);
		this.multiLineStringConverter = new JTSToGML321MultiLineStringConverter(
				objectFactory, srsReferenceGroupConverter,
				this.lineStringConverter);
		this.multiPolygonConverter = new JTSToGML321MultiPolygonConverter(
				objectFactory, srsReferenceGroupConverter,
				this.polygonConverter);
		this.multiGeometryConverter = new JTSToGML321MultiGeometryConverter(
				objectFactory, srsReferenceGroupConverter, this);
	}

	@Override
	protected Object doCreateGeometryType(Geometry geometry) {
		if (geometry instanceof Point) {
			return pointConverter.createGeometryType((Point) geometry);
		} else if (geometry instanceof LineString) {
			return lineStringConverter
					.createGeometryType((LineString) geometry);
		} else if (geometry instanceof LinearRing) {
			return linearRingConverter
					.createGeometryType((LinearRing) geometry);
		} else if (geometry instanceof Polygon) {
			return polygonConverter.createGeometryType((Polygon) geometry);
		} else if (geometry instanceof MultiPoint) {
			return multiPointConverter
					.createGeometryType((MultiPoint) geometry);
		} else if (geometry instanceof MultiLineString) {
			return multiLineStringConverter
					.createGeometryType((MultiLineString) geometry);
		} else if (geometry instanceof MultiPolygon) {
			return multiPolygonConverter
					.createGeometryType((MultiPolygon) geometry);
		} else if (geometry instanceof GeometryCollection) {
			return multiGeometryConverter
					.createGeometryType((GeometryCollection) geometry);
		} else {
			// TODO
			throw new IllegalArgumentException();
		}

	}

	public GeometryPropertyType createPropertyType(Geometry geometry) {
		final GeometryPropertyType geometryPropertyType = getObjectFactory()
				.createGeometryPropertyType();
		geometryPropertyType.setAbstractGeometry(createElement(geometry));
		return geometryPropertyType;
	}

	public JAXBElement<? extends AbstractGeometryType> createElement(
			Geometry geometry) {
		if (geometry instanceof Point) {
			return pointConverter.createElement((Point) geometry);
		} else if (geometry instanceof LineString) {
			return lineStringConverter.createElement((LineString) geometry);
		} /*else if (geometry instanceof LinearRing) {
			return linearRingConverter.createElement((LinearRing) geometry);
		}*/ else if (geometry instanceof Polygon) {
			return polygonConverter.createElement((Polygon) geometry);
		} else if (geometry instanceof MultiPoint) {
			return multiPointConverter.createElement((MultiPoint) geometry);
		} else if (geometry instanceof MultiLineString) {
			return multiLineStringConverter
					.createElement((MultiLineString) geometry);
		} else if (geometry instanceof MultiPolygon) {
			return multiPolygonConverter.createElement((MultiPolygon) geometry);
		} else if (geometry instanceof GeometryCollection) {
			return multiGeometryConverter
					.createElement((GeometryCollection) geometry);
		} else {
			// TODo
			throw new IllegalArgumentException();
		}

	}

}
