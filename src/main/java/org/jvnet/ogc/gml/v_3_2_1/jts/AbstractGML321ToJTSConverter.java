package org.jvnet.ogc.gml.v_3_2_1.jts;

import net.opengis.gml.v_3_2_1.AbstractGeometryType;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

public abstract class AbstractGML321ToJTSConverter<G, P, J extends Geometry>
		implements GML321ToJTSConverterInterface<G, P, J> {
	private final GeometryFactory geometryFactory;
	private final GML321ToJTSSRIDConverterInterface sridConverter;

	public AbstractGML321ToJTSConverter(GeometryFactory geometryFactory,
			GML321ToJTSSRIDConverterInterface sridConverter) {
		super();
		this.geometryFactory = geometryFactory;
		this.sridConverter = sridConverter;
	}

	public GeometryFactory getGeometryFactory() {
		return geometryFactory;
	}

	public GML321ToJTSSRIDConverterInterface getSridConverter() {
		return sridConverter;
	}

	public J createGeometry(ObjectLocator locator, G geometryType)
			throws ConversionFailedException {

		final J geometry = doCreateGeometry(locator, geometryType);
		if (geometryType instanceof AbstractGeometryType) {
			getSridConverter().convert(locator, (AbstractGeometryType) geometryType, geometry);
		}
		return geometry;
	}

	protected abstract J doCreateGeometry(ObjectLocator locator, G geometryType)
			throws ConversionFailedException;

}
