package org.jvnet.ogc.gml.v_3_2_1.jts;

import java.util.ArrayList;
import java.util.List;

import net.opengis.gml.v_3_2_1.MultiPointPropertyType;
import net.opengis.gml.v_3_2_1.MultiPointType;
import net.opengis.gml.v_3_2_1.PointPropertyType;
import net.opengis.gml.v_3_2_1.PointType;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.Point;

public class GML321ToJTSMultiPointConverter
		extends
		AbstractGML321ToJTSConverter<MultiPointType, MultiPointPropertyType, MultiPoint> {

	// + MultiPoint

	private final GML321ToJTSConverterInterface<PointType, PointPropertyType, Point> pointConverter;

	public GML321ToJTSMultiPointConverter(
			GeometryFactory geometryFactory,
			GML321ToJTSSRIDConverterInterface sridConverter,
			GML321ToJTSConverterInterface<PointType, PointPropertyType, Point> pointConverter) {
		super(geometryFactory, sridConverter);
		this.pointConverter = pointConverter;
	}

	@Override
	protected MultiPoint doCreateGeometry(ObjectLocator locator,
			MultiPointType multiPointType) throws ConversionFailedException {
		final List<Point> points = new ArrayList<Point>();
		if (multiPointType.isSetPointMember()) {
			final List<PointPropertyType> pointMembers = multiPointType
					.getPointMember();
			for (int index = 0; index < pointMembers.size(); index++) {
				final PointPropertyType pointPropertyType = pointMembers
						.get(index);
				points
						.add(pointConverter
								.createGeometry(
										locator
												.property(
														"pointMember", pointMembers).item(index, pointPropertyType), pointPropertyType.getPoint())); //$NON-NLS-1$
			}
		}
		if (multiPointType.isSetPointMembers()) {
			final List<PointType> pointMembers = multiPointType
					.getPointMembers().getPoint();
			for (int index = 0; index < pointMembers.size(); index++) {

				points
						.add(pointConverter
								.createGeometry(
										locator
												.property(
														"pointMembers", pointMembers).item(index, pointMembers.get(index)), pointMembers.get(index))); //$NON-NLS-1$
			}
		}
		if (multiPointType.isSetPointMember()
				|| multiPointType.isSetPointMembers()) {
			return getGeometryFactory().createMultiPoint(
					points.toArray(new Point[points.size()]));
		} else {
			throw new ConversionFailedException(locator,
					"Either [pointMember] or [pointMembers] elements are expected."); //$NON-NLS-1$
		}
	}

	public MultiPoint createGeometry(ObjectLocator locator,
			MultiPointPropertyType multiPointPropertyType)
			throws ConversionFailedException {
		if (multiPointPropertyType.isSetMultiPoint()) {
			return createGeometry(
					locator
							.property(
									"multiPoint", multiPointPropertyType.getMultiPoint()), multiPointPropertyType.getMultiPoint()); //$NON-NLS-1$
		} else {
			throw new ConversionFailedException(locator,
					"Expected [MultiPoint] element."); //$NON-NLS-1$
		}
	}
}
