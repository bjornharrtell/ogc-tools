package org.jvnet.ogc.gml.v_3_2_1.jts.tests;

import static org.junit.Assert.assertEquals;
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

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;

//@RunWith(Theories.class)
public class GeometryRoundtripTest {

	private static final double EPSILON = 0.1;
	private static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory(
			new PrecisionModel(), 4326);
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
	public static Object[][] RESOURCES = new Object[][] {

	{ "Point[4].xml", GEOMETRY_FACTORY.createPoint(new Coordinate(0, 1)) },
	{ "Point[5].xml", GEOMETRY_FACTORY.createPoint(new Coordinate(0, 1)) }

	};

	/*
	@Theory
	public void roundtrip(Object[] item) throws JAXBException,
			ConversionFailedException, ParseException {
		final URL url = getClass().getResource((String) item[0]);
		final Geometry alpha = (Geometry) JTS_CONTEXT.createUnmarshaller()
				.unmarshal(url);

		final JAXBElement<? extends AbstractGeometryType> jaxbElement = (JAXBElement<? extends AbstractGeometryType>) MARSHALLER_CONVERTER
				.createElement(alpha);
		final AbstractGeometryType geometryType = (AbstractGeometryType) MARSHALLER_CONVERTER
				.createGeometryType(alpha);
		final GeometryPropertyType propertyType = MARSHALLER_CONVERTER
				.createPropertyType(alpha);

		Geometry beta = UNMARSHALLER_CONVERTER.createGeometry(
				new DefaultRootObjectLocator(propertyType), propertyType);

		assertTrue(alpha.equalsExact(beta, EPSILON));
		assertEquals(alpha.getSRID(), beta.getSRID());

		Geometry gamma = UNMARSHALLER_CONVERTER.createGeometry(
				new DefaultRootObjectLocator(geometryType), geometryType);
		assertTrue(alpha.equalsExact(gamma, EPSILON));
		assertEquals(alpha.getSRID(), gamma.getSRID());

		final DOMResult result = new DOMResult();
		final Marshaller gmlMarshaller = GML_CONTEXT.createMarshaller();
		gmlMarshaller.marshal(jaxbElement, result);

		Geometry delta = (Geometry) JTS_CONTEXT.createUnmarshaller().unmarshal(
				new DOMSource(result.getNode()));

		assertTrue(alpha.equalsExact(delta, EPSILON));
		assertEquals(alpha.getSRID(), delta.getSRID());

		final Geometry omega = (Geometry) item[1];
		assertTrue(alpha.equalsExact(omega, EPSILON));
		assertEquals(alpha.getSRID(), omega.getSRID());
	}
	*/

}
