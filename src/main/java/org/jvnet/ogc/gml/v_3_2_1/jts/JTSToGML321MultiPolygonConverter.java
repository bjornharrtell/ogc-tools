package org.jvnet.ogc.gml.v_3_2_1.jts;

import javax.xml.bind.JAXBElement;

import org.jvnet.ogc.gml.v_3_2_1.ObjectFactoryInterface;

import net.opengis.gml.v_3_2_1.MultiSurfacePropertyType;
import net.opengis.gml.v_3_2_1.MultiSurfaceType;
import net.opengis.gml.v_3_2_1.SurfacePropertyType;
import net.opengis.gml.v_3_2_1.PolygonType;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;

public class JTSToGML321MultiPolygonConverter
		extends
		AbstractJTSToGML321Converter<MultiSurfaceType, MultiSurfacePropertyType, MultiPolygon> {
	private final JTSToGML321ConverterInterface<PolygonType, SurfacePropertyType, Polygon> polygonConverter;

	public JTSToGML321MultiPolygonConverter(
			ObjectFactoryInterface objectFactory,
			JTSToGML321SRSReferenceGroupConverterInterface srsReferenceGroupConverter,
			JTSToGML321ConverterInterface<PolygonType, SurfacePropertyType, Polygon> polygonConverter) {
		super(objectFactory, srsReferenceGroupConverter);
		this.polygonConverter = polygonConverter;
	}

	@Override
	protected MultiSurfaceType doCreateGeometryType(MultiPolygon multiPolygon) {
		final MultiSurfaceType multiSurfaceType = getObjectFactory()
				.createMultiSurfaceType();
		for (int index = 0; index < multiPolygon.getNumGeometries(); index++) {
			final Polygon polygon = (Polygon) multiPolygon.getGeometryN(index);
			multiSurfaceType.getSurfaceMember().add(
					polygonConverter.createPropertyType(polygon));
		}

		return multiSurfaceType;
	}

	public MultiSurfacePropertyType createPropertyType(MultiPolygon multiPolygon) {
		final MultiSurfacePropertyType multiSurfacePropertyType = getObjectFactory()
				.createMultiSurfacePropertyType();
		multiSurfacePropertyType
				.setMultiSurface(createGeometryType(multiPolygon));
		return multiSurfacePropertyType;
	}

	public JAXBElement<MultiSurfaceType> createElement(MultiPolygon multiPolygon) {
		return getObjectFactory().createMultiPolygon(
				createGeometryType(multiPolygon));
	}

}
