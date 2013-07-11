package org.jvnet.ogc.gml.v_3_2_1.jts.tests;

import junit.framework.TestCase;
import net.opengis.gml.v_3_2_1.PointType;

import org.jvnet.jaxb2_commons.locator.DefaultRootObjectLocator;
import org.jvnet.ogc.gml.v_3_2_1.jts.ConversionFailedException;
import org.jvnet.ogc.gml.v_3_2_1.jts.GML321ToJTSConstants;
import org.jvnet.ogc.gml.v_3_2_1.jts.GML321ToJTSSRIDConverterInterface;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

public class GML321ToJTSSRIDConverterTest extends TestCase {

	String[][] examples = { {
			"http://www.opengis.net/gml/srs/epsg.xml#63266405", "63266405" } };

	String[] counterexamples = {

	};

	private GeometryFactory geometryFactory;
	private GML321ToJTSSRIDConverterInterface converter;

	@Override
	protected void setUp() throws Exception {
		geometryFactory = GML321ToJTSConstants.DEFAULT_GEOMETRY_FACTORY;
		converter = GML321ToJTSConstants.DEFAULT_SRID_CONVERTER;

	}

	private void check(String srsName, int srid, Object userData)
			throws ConversionFailedException {
		final PointType source = new PointType();
		source.setSrsName(srsName);
		Geometry target = geometryFactory.createPoint(new Coordinate(0, 0));
		converter.convert(new DefaultRootObjectLocator(source), source, target);
		assertEquals(target.getSRID(), srid);
		assertEquals(target.getUserData(), userData);
	}

	public void testConvert() throws ConversionFailedException {
		check("http://www.opengis.net/gml/srs/epsg.xml#4326", 4326,
				"http://www.opengis.net/gml/srs/epsg.xml#4326");
		check("EPSG:4326", 4326, "EPSG:4326");
		check("urn:ogc:def:crs:EPSG::4326", 4326, "urn:ogc:def:crs:EPSG::4326");
		check("urn:ogc:def:crs:EPSG:6.3:4326", 4326,
				"urn:ogc:def:crs:EPSG:6.3:4326");
		check("urn:x-ogc:def:crs:EPSG::4326", 4326,
				"urn:x-ogc:def:crs:EPSG::4326");
		check("urn:x-ogc:def:crs:EPSG:6.3:4326", 4326,
				"urn:x-ogc:def:crs:EPSG:6.3:4326");
	}

	public void testConvertWithCorrectPoint() throws ConversionFailedException {

		Geometry point = geometryFactory.createPoint(new Coordinate(0, 0));

		final PointType source = new PointType();
		int srid = 63266405;

		source.setSrsName("http://www.opengis.net/gml/srs/epsg.xml#63266405");

		converter.convert(new DefaultRootObjectLocator(source), source, point);
		assertEquals(srid, point.getSRID());

	}

	public void testConvertWithWrongFormatAndTargetUserDataNull()
			throws ConversionFailedException {
		Geometry point = geometryFactory.createPoint(new Coordinate(0, 0));

		final PointType source = new PointType();

		source.setSrsName("foo");

		converter.convert(new DefaultRootObjectLocator(source), source, point);
		assertEquals("foo", point.getUserData());

	}

	public void testConvertWithWrongFormatAndTargetUserDataNotNull()
			throws ConversionFailedException {
		Geometry point = geometryFactory.createPoint(new Coordinate(0, 0));
		point.setUserData("");
		final PointType source = new PointType();

		source.setSrsName("foo");

		try {
			converter.convert(new DefaultRootObjectLocator(source), source,
					point);
			fail();
		} catch (ConversionFailedException e) {
			assertTrue(true);
		}

	}

}
