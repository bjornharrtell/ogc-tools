package org.jvnet.ogc.gml.v_3_2_1;

import javax.xml.bind.JAXBElement;

import net.opengis.gml.v_3_2_1.AbstractGeometryType;
import net.opengis.gml.v_3_2_1.AbstractRingPropertyType;
import net.opengis.gml.v_3_2_1.CoordinatesType;
import net.opengis.gml.v_3_2_1.DirectPositionListType;
import net.opengis.gml.v_3_2_1.DirectPositionType;
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

public interface ObjectFactoryInterface {

	public CoordinatesType createCoordinatesType();

	public DirectPositionType createDirectPositionType();

	public DirectPositionListType createDirectPositionListType();

	public JAXBElement<DirectPositionType> createPos(DirectPositionType value);

	public PointType createPointType();

	public JAXBElement<PointType> createPoint(PointType value);

	public PointPropertyType createPointPropertyType();

	public JAXBElement<PointPropertyType> createPointProperty(
			PointPropertyType value);

	public LineStringType createLineStringType();

	public JAXBElement<LineStringType> createLineString(LineStringType value);

	public CurvePropertyType createCurvePropertyType();

	public JAXBElement<CurvePropertyType> createLineStringProperty(
			CurvePropertyType value);

	public LinearRingType createLinearRingType();

	public JAXBElement<LinearRingType> createLinearRing(LinearRingType value);

	public LinearRingPropertyType createLinearRingPropertyType();
	
	public JAXBElement<LinearRingPropertyType> createLinearRingProperty(
			LinearRingPropertyType value);
	
	public PolygonType createPolygonType();

	public SurfacePropertyType createSurfacePropertyType();

	public JAXBElement<PolygonType> createPolygon(PolygonType value);

	public JAXBElement<SurfacePropertyType> createPolygonProperty(
			SurfacePropertyType value);

	public MultiPointType createMultiPointType();

	public JAXBElement<MultiPointType> createMultiPoint(MultiPointType value);

	public MultiPointPropertyType createMultiPointPropertyType();

	public JAXBElement<MultiPointPropertyType> createMultiPointProperty(
			MultiPointPropertyType value);

	public MultiCurveType createMultiCurveType();

	public JAXBElement<MultiCurveType> createMultiLineString(
			MultiCurveType value);

	public MultiCurvePropertyType createMultiCurvePropertyType();

	public JAXBElement<MultiCurvePropertyType> createMultiLineStringProperty(
			MultiCurvePropertyType value);

	public AbstractRingPropertyType createAbstractRingPropertyType();

	public JAXBElement<AbstractRingPropertyType> createExterior(
			AbstractRingPropertyType value);

	public JAXBElement<AbstractRingPropertyType> createInterior(
			AbstractRingPropertyType value);

	public MultiSurfaceType createMultiSurfaceType();

	public MultiSurfacePropertyType createMultiSurfacePropertyType();

	public JAXBElement<MultiSurfaceType> createMultiPolygon(
			MultiSurfaceType value);

	public JAXBElement<MultiSurfacePropertyType> createMultiPolygonProperty(
			MultiSurfacePropertyType value);

	public MultiGeometryType createMultiGeometryType();

	public JAXBElement<MultiGeometryType> createMultiGeometry(
			MultiGeometryType value);

	public MultiGeometryPropertyType createMultiGeometryPropertyType();

	public JAXBElement<MultiGeometryPropertyType> createMultiGeometryProperty(
			MultiGeometryPropertyType value);

	public GeometryPropertyType createGeometryPropertyType();

	public JAXBElement<AbstractGeometryType> createGeometry(
			AbstractGeometryType value);
}
