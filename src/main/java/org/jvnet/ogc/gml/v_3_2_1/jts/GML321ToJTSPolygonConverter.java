package org.jvnet.ogc.gml.v_3_2_1.jts;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import net.opengis.gml.v_3_2_1.AbstractRingPropertyType;
import net.opengis.gml.v_3_2_1.AbstractRingType;
import net.opengis.gml.v_3_2_1.AbstractSurfaceType;
import net.opengis.gml.v_3_2_1.LinearRingPropertyType;
import net.opengis.gml.v_3_2_1.LinearRingType;
import net.opengis.gml.v_3_2_1.SurfacePropertyType;
import net.opengis.gml.v_3_2_1.PolygonType;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;

public class GML321ToJTSPolygonConverter extends
		AbstractGML321ToJTSConverter<PolygonType, SurfacePropertyType, Polygon> {

	// + Polygon

	private final GML321ToJTSConverterInterface<LinearRingType, LinearRingPropertyType, LinearRing> linearRingConverter;

	public GML321ToJTSPolygonConverter(
			GeometryFactory geometryFactory,
			GML321ToJTSSRIDConverterInterface sridConverter,
			GML321ToJTSConverterInterface<LinearRingType, LinearRingPropertyType, LinearRing> linearRingConverter) {
		super(geometryFactory, sridConverter);
		this.linearRingConverter = linearRingConverter;
	}

	@Override
	protected Polygon doCreateGeometry(ObjectLocator locator, PolygonType polygonType)
			throws ConversionFailedException {
		final LinearRing shell;
		if (polygonType.isSetExterior()) {
			final AbstractRingType abstractRingType = polygonType.getExterior().getAbstractRing().getValue();
			if (abstractRingType instanceof LinearRingType) {
				shell = linearRingConverter
						.createGeometry(
								locator.property("exterior",
										polygonType.getExterior())
										.property(
												"value",
												polygonType.getExterior().getAbstractRing().getValue())
										.property(
												"ring",
												polygonType.getExterior().getAbstractRing().getValue())
										.property("value", abstractRingType), (LinearRingType) abstractRingType); //$NON-NLS-1$
			} else {
				throw new ConversionFailedException(
						locator.property("exterior", polygonType.getExterior())
								.property("value",
										polygonType.getExterior().getAbstractRing().getValue())
								.property(
										"ring",
										polygonType.getExterior().getAbstractRing().getValue()),
						"Only linear rings are supported."); //$NON-NLS-1$
			}
		} else {
			shell = null;
		}

		final LinearRing[] holes;
		if (polygonType.isSetInterior()) {
			final ObjectLocator interiorObjectLocator = locator.property(
					"interior", polygonType.getInterior()); //$NON-NLS-1$
			final List<LinearRing> holesList = new ArrayList<LinearRing>(
					polygonType.getInterior().size());
			for (int index = 0; index < polygonType.getInterior().size(); index++) {
				final AbstractRingPropertyType ringElement = polygonType.getInterior().get(index);
				final ObjectLocator entryLocator = interiorObjectLocator.item(
						index, ringElement);

				final AbstractRingType abstractRingType = ringElement.getAbstractRing().getValue();
				if (abstractRingType instanceof LinearRingType) {
					holesList.add(linearRingConverter.createGeometry(
							entryLocator
									.property("value", ringElement)
									.property("ring",
											abstractRingType)
									.property(
											"value",
											abstractRingType),

							(LinearRingType) abstractRingType));
				} else {
					throw new ConversionFailedException(entryLocator,
							"Only linear rings are supported."); //$NON-NLS-1$
				}
			}

			holes = holesList.toArray(new LinearRing[holesList.size()]);
		} else {
			holes = null;
		}
		return getGeometryFactory().createPolygon(shell, holes);
	}

	public Polygon createGeometry(ObjectLocator locator,
			SurfacePropertyType surfacePropertyType)
			throws ConversionFailedException {
		if (surfacePropertyType.isSetAbstractSurface()) {
			AbstractSurfaceType abstractSurfaceType = surfacePropertyType.getAbstractSurface().getValue();
			return createGeometry(
					locator.property(
							"polygon", abstractSurfaceType), surfacePropertyType); //$NON-NLS-1$
		} else {
			throw new ConversionFailedException(locator,
					"Expected [Polygon] element."); //$NON-NLS-1$
		}
	}

}
