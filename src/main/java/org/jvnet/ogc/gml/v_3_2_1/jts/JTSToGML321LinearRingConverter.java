package org.jvnet.ogc.gml.v_3_2_1.jts;

import javax.xml.bind.JAXBElement;

import org.jvnet.ogc.gml.v_3_2_1.ObjectFactoryInterface;

import net.opengis.gml.v_3_2_1.AbstractRingPropertyType;
import net.opengis.gml.v_3_2_1.DirectPositionType;
import net.opengis.gml.v_3_2_1.LinearRingType;

import com.vividsolutions.jts.geom.LinearRing;

public class JTSToGML321LinearRingConverter
		extends
		AbstractJTSToGML321Converter<LinearRingType, AbstractRingPropertyType, LinearRing> {
	private final JTSToGML321CoordinateConverter coordinateConverter;

	public JTSToGML321LinearRingConverter(
			ObjectFactoryInterface objectFactory,
			JTSToGML321SRSReferenceGroupConverterInterface srsReferenceGroupConverter,
			JTSToGML321CoordinateConverter coordinateConverter) {
		super(objectFactory, srsReferenceGroupConverter);
		this.coordinateConverter = coordinateConverter;
	}

	@Override
	protected LinearRingType doCreateGeometryType(LinearRing linearRing) {
		final LinearRingType resultLinearRing = getObjectFactory()
				.createLinearRingType();

		for (DirectPositionType directPosition : coordinateConverter
				.convertCoordinates(linearRing.getCoordinates())) {
			final JAXBElement<DirectPositionType> pos = getObjectFactory()
					.createPos(directPosition);
			resultLinearRing.getPosOrPointPropertyOrPointRep().add(pos);

		}
		return resultLinearRing;
	}

	public AbstractRingPropertyType createPropertyType(final LinearRing ring) {
		final AbstractRingPropertyType abstractRingProperty = getObjectFactory()
				.createAbstractRingPropertyType();

		abstractRingProperty.setAbstractRing(getObjectFactory().createLinearRing(
				createGeometryType(ring)));
		return abstractRingProperty;
	}

	public JAXBElement<LinearRingType> createElement(LinearRing linearRing) {
		return getObjectFactory().createLinearRing(
				createGeometryType(linearRing));
	}
}
