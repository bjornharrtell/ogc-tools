package org.jvnet.ogc.gml.v_3_2_1.jts.tests;

import static org.junit.Assert.assertTrue;

import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import net.opengis.gml.v_3_2_1.AbstractGeometryType;
import net.opengis.gml.v_3_2_1.GeometryPropertyType;
import net.opengis.gml.v_3_2_1.ObjectFactory;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.jvnet.jaxb2_commons.locator.DefaultRootObjectLocator;
import org.jvnet.ogc.gml.v_3_2_1.jts.ConversionFailedException;
import org.jvnet.ogc.gml.v_3_2_1.jts.GML321ToJTSConverterInterface;
import org.jvnet.ogc.gml.v_3_2_1.jts.GML321ToJTSGeometryConverter;
import org.jvnet.ogc.gml.v_3_2_1.jts.JAXBContextImpl;
import org.jvnet.ogc.gml.v_3_2_1.jts.JTSToGML321ConverterInterface;
import org.jvnet.ogc.gml.v_3_2_1.jts.JTSToGML321GeometryConverter;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

//@RunWith(Theories.class)
public class WktRoundtripTest {

	private static final double EPSILON = 0.1;
	private static final JAXBContext JTS_CONTEXT;
	private static final JAXBContext GML_CONTEXT;
	private static final GML321ToJTSConverterInterface<Object, Object, Geometry> UNMARSHALLER_CONVERTER;
	private static final JTSToGML321ConverterInterface<Object, GeometryPropertyType, Geometry> MARSHALLER_CONVERTER;

	static {
		try {
			JTS_CONTEXT = JAXBContext.newInstance(JAXBContextImpl.class
					.getPackage().getName());
			GML_CONTEXT = JAXBContext.newInstance(ObjectFactory.class);
		} catch (JAXBException jaxbex) {
			throw new ExceptionInInitializerError(jaxbex);
		}

		UNMARSHALLER_CONVERTER = new GML321ToJTSGeometryConverter();
		MARSHALLER_CONVERTER = new JTSToGML321GeometryConverter();
	}

	@DataPoints
	public static String[][] RESOURCES = new String[][] {

			{ "Point[0].xml", "POINT(0 1)" },

			{ "Point[1].xml", "POINT(0 1)" },

			{ "Point[2].xml", "POINT(0 1)" },

			{ "Point[3].xml", "POINT(0 1)" },

			{ "LineString[0].xml", "LINESTRING(0 0, 1 0, 1 1, 0 1)" },

			{ "LineString[1].xml", "LINESTRING(0 0, 1 0, 1 1, 0 1)" },

			{ "LineString[2].xml", "LINESTRING(0 0, 1 0, 1 1, 0 1)" },

			{ "LineString[3].xml", "LINESTRING(0 0, 1 0, 1 1, 0 1)" },

			{ "LinearRing[0].xml", "LINEARRING(0 0, 1 0, 1 1, 0 1, 0 0)" },

			{ "LinearRing[1].xml", "LINEARRING(0 0, 1 0, 1 1, 0 1, 0 0)" },

			{ "Polygon[0].xml", "POLYGON((0 0, 1 0, 1 1, 0 1, 0 0))" },

			{ "Polygon[1].xml", "POLYGON((0 0, 1 0, 1 1, 0 1, 0 0))" },

			{
					"Polygon[2].xml",
					"POLYGON((0 0, 100 0, 100 100, 0 100, 0 0), (10 10, 90 10, 90 90, 10 90, 10 10))" },

			{ "MultiPoint[0].xml", "MULTIPOINT(0 0, 1 0, 1 1, 0 1)" },

			{ "MultiLineString[0].xml", "MULTILINESTRING((0 0, 1 0, 1 1, 0 1))" },

			{ "MultiPolygon[0].xml",
					"MULTIPOLYGON(((0 0, 1 0, 1 1, 0 1, 0 0)))" },

			{ "MultiGeometry[0].xml",
					"GEOMETRYCOLLECTION(POINT(0 0), POINT(1 0), POINT(1 1), POINT(0 1))" },

	};

	/*
	@Theory
	public void roundtrip(String[] item) throws JAXBException,
			ConversionFailedException, ParseException {
		final URL url = getClass().getResource(item[0]);
		final Geometry alpha = (Geometry) JTS_CONTEXT.createUnmarshaller()
				.unmarshal(url);

		final JAXBElement<? extends Object> jaxbElement = MARSHALLER_CONVERTER
				.createElement(alpha);
		final Object geometryType = MARSHALLER_CONVERTER
				.createGeometryType(alpha);
		final GeometryPropertyType propertyType = MARSHALLER_CONVERTER
				.createPropertyType(alpha);

		Geometry beta = UNMARSHALLER_CONVERTER.createGeometry(
				new DefaultRootObjectLocator(propertyType), propertyType);

		assertTrue(alpha.equalsExact(beta, EPSILON));
		Geometry gamma = UNMARSHALLER_CONVERTER.createGeometry(
				new DefaultRootObjectLocator(geometryType), geometryType);
		assertTrue(alpha.equalsExact(gamma, EPSILON));

		final DOMResult result = new DOMResult();
		final Marshaller gmlMarshaller = GML_CONTEXT.createMarshaller();
		gmlMarshaller.marshal(jaxbElement, result);

		Geometry delta = (Geometry) JTS_CONTEXT.createUnmarshaller().unmarshal(
				new DOMSource(result.getNode()));

		assertTrue(alpha.equalsExact(delta, EPSILON));
		final Geometry omega = new WKTReader().read(item[1]);
		assertTrue(alpha.equalsExact(omega, EPSILON));
	}
	*/
}
