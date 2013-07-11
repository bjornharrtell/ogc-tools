package org.jvnet.ogc.gml.v_3_2_1.jts;

import java.util.ArrayList;
import java.util.List;

import net.opengis.gml.v_3_2_1.CurvePropertyType;
import net.opengis.gml.v_3_2_1.LineStringType;
import net.opengis.gml.v_3_2_1.MultiCurvePropertyType;
import net.opengis.gml.v_3_2_1.MultiCurveType;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;

public class GML321ToJTSMultiLineStringConverter
		extends
		AbstractGML321ToJTSConverter<MultiCurveType, MultiCurvePropertyType, MultiLineString> {

	// + MultiLineString

	private final GML321ToJTSConverterInterface<LineStringType, CurvePropertyType, LineString> lineStringConverter;

	public GML321ToJTSMultiLineStringConverter(
			GeometryFactory geometryFactory,
			GML321ToJTSSRIDConverterInterface sridConverter,
			GML321ToJTSConverterInterface<LineStringType, CurvePropertyType, LineString> lineStringConverter) {
		super(geometryFactory, sridConverter);
		this.lineStringConverter = lineStringConverter;
	}

	@Override
	protected MultiLineString doCreateGeometry(ObjectLocator locator,
			MultiCurveType multiCurveType)
			throws ConversionFailedException {
		final List<CurvePropertyType> lineStringMembers = multiCurveType
				.getCurveMember();
		final List<LineString> lineStrings = new ArrayList<LineString>(
				lineStringMembers.size());
		for (int index = 0; index < lineStringMembers.size(); index++) {
			final CurvePropertyType curvePropertyType = lineStringMembers
					.get(index);

			final LineStringType lineStringType = (LineStringType) curvePropertyType
					.getAbstractCurve().getValue();
			lineStrings
					.add(lineStringConverter
							.createGeometry(
									locator.property(
											"lineStringMember", lineStringMembers).item(index, curvePropertyType).property( //$NON-NLS-1$
													"lineString",
													lineStringType),
									lineStringType));
		}
		return getGeometryFactory().createMultiLineString(
				lineStrings.toArray(new LineString[lineStrings.size()]));
	}

	public MultiLineString createGeometry(ObjectLocator locator,
			MultiCurvePropertyType multiCurvePropertyType)
			throws ConversionFailedException {
		if (multiCurvePropertyType.isSetMultiCurve()) {
			return createGeometry(locator.property("multiLineString",
					multiCurvePropertyType.getMultiCurve()),
					multiCurvePropertyType.getMultiCurve());
		} else {
			throw new ConversionFailedException(locator,
					"Expected [MultiLineString] element."); //$NON-NLS-1$
		}
	}
}
