package org.jvnet.ogc.gml.v_3_2_1.jts;

import java.util.ArrayList;
import java.util.List;

import net.opengis.gml.v_3_2_1.MultiSurfacePropertyType;
import net.opengis.gml.v_3_2_1.MultiSurfaceType;
import net.opengis.gml.v_3_2_1.SurfacePropertyType;
import net.opengis.gml.v_3_2_1.PolygonType;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;

public class GML321ToJTSMultiPolygonConverter
		extends
		AbstractGML321ToJTSConverter<MultiSurfaceType, MultiSurfacePropertyType, MultiPolygon> {

	// + MultiPolygon

	private final GML321ToJTSConverterInterface<PolygonType, SurfacePropertyType, Polygon> polygonConverter;

	public GML321ToJTSMultiPolygonConverter(
			GeometryFactory geometryFactory,
			GML321ToJTSSRIDConverterInterface sridConverter,
			GML321ToJTSConverterInterface<PolygonType, SurfacePropertyType, Polygon> polygonConverter) {
		super(geometryFactory, sridConverter);
		this.polygonConverter = polygonConverter;
	}

	@Override
	protected MultiPolygon doCreateGeometry(ObjectLocator locator,
			MultiSurfaceType multiSurfaceType) throws ConversionFailedException {
		final List<SurfacePropertyType> polygonMembers = multiSurfaceType
				.getSurfaceMember();
		final List<Polygon> polygons = new ArrayList<Polygon>(
				polygonMembers.size());
		for (int index = 0; index < polygonMembers.size(); index++) {
			final SurfacePropertyType surfacePropertyType = polygonMembers
					.get(index);
			final PolygonType polygonType = (PolygonType) surfacePropertyType.getAbstractSurface().getValue();
			polygons.add(polygonConverter
					.createGeometry(
							locator.property("polygonMember", polygonMembers).item(index, surfacePropertyType).property("polygon", polygonType), //$NON-NLS-1$ //$NON-NLS-2$
							polygonType));

		}
		return getGeometryFactory().createMultiPolygon(
				polygons.toArray(new Polygon[polygons.size()]));
	}

	public MultiPolygon createGeometry(ObjectLocator locator,
			MultiSurfacePropertyType multiSurfacePropertyType)
			throws ConversionFailedException {
		if (multiSurfacePropertyType.isSetMultiSurface()) {
			return createGeometry(
					locator.property("multiPolygon",
							multiSurfacePropertyType.getMultiSurface()),
					multiSurfacePropertyType.getMultiSurface());
		} else {
			throw new ConversionFailedException(locator,
					"Expected [MultiPolygon] element."); //$NON-NLS-1$
		}
	}
}
