package org.jvnet.ogc.gml.v_3_2_1.jts;

import javax.xml.bind.JAXBElement;

import org.jvnet.ogc.gml.v_3_2_1.ObjectFactoryInterface;

import net.opengis.gml.v_3_2_1.AbstractRingPropertyType;
import net.opengis.gml.v_3_2_1.LinearRingType;
import net.opengis.gml.v_3_2_1.SurfacePropertyType;
import net.opengis.gml.v_3_2_1.PolygonType;

import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;

public class JTSToGML321PolygonConverter extends
		AbstractJTSToGML321Converter<PolygonType, SurfacePropertyType, Polygon> {
	private final JTSToGML321ConverterInterface<LinearRingType, AbstractRingPropertyType, LinearRing> linearRingConverter;

	public JTSToGML321PolygonConverter(
			ObjectFactoryInterface objectFactory,
			JTSToGML321SRSReferenceGroupConverterInterface srsReferenceGroupConverter,
			JTSToGML321ConverterInterface<LinearRingType, AbstractRingPropertyType, LinearRing> linearRingConverter) {
		super(objectFactory, srsReferenceGroupConverter);
		this.linearRingConverter = linearRingConverter;
	}

	@Override
	protected PolygonType doCreateGeometryType(Polygon polygon) {
		final PolygonType resultPolygon = getObjectFactory()
				.createPolygonType();
		{
			final LinearRing exteriorRing = (LinearRing) polygon
					.getExteriorRing();
			final AbstractRingPropertyType abstractRingProperty = linearRingConverter
					.createPropertyType(exteriorRing);
			resultPolygon.setExterior(getObjectFactory().createExterior(
					abstractRingProperty).getValue());
		}
		for (int index = 0; index < polygon.getNumInteriorRing(); index++) {
			final LinearRing interiorRing = (LinearRing) polygon
					.getInteriorRingN(index);
			resultPolygon.getInterior().add(
					getObjectFactory().createInterior(
							linearRingConverter
									.createPropertyType(interiorRing)).getValue());
		}
		return resultPolygon;

	}

	public SurfacePropertyType createPropertyType(Polygon polygon) {
		final SurfacePropertyType surfacePropertyType = getObjectFactory()
				.createSurfacePropertyType();
		surfacePropertyType.setAbstractSurface(createElement(polygon));
		return surfacePropertyType;
	}

	public JAXBElement<PolygonType> createElement(Polygon polygon) {
		return getObjectFactory().createPolygon(createGeometryType(polygon));
	}
}
