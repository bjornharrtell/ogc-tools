package org.jvnet.ogc.gml.v_3_2_1.jts.tests;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.opengis.gml.v_3_2_1.AbstractGeometryType;

import org.jvnet.ogc.gml.v_3_2_1.jts.JTSToGML321GeometryConverter;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class JTSToGML321ConverterTest extends TestCase {

	private final GeometryFactory geometryFactory = new GeometryFactory();

	private JAXBContext context;

	private JTSToGML321GeometryConverter converter;

	@Override
	protected void setUp() throws Exception {
		// context =
		// JAXBContext.newInstance(ObjectFactory.class.getPackage().getName());
		converter = new JTSToGML321GeometryConverter(

		);
	}

	protected void marshal(Object object) throws JAXBException {
		final Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(object, new StreamResult(System.out));
	}

	public void testPoint0() throws Exception {

		final Point point = geometryFactory.createPoint(new Coordinate(10, 20));
		point.setSRID(4326);
		final JAXBElement<? extends AbstractGeometryType> element = converter
				.createElement(point);
		Assert.assertEquals(
				"urn:ogc:def:crs:EPSG::4326", element.getValue().getSrsName()); //$NON-NLS-1$
		// marshal(element);

	}

	public void testPoint1() throws Exception {

		final Point point = geometryFactory.createPoint(new Coordinate(10, 20));
		point.setUserData("urn:ogc:def:crs:EPSG::4326"); //$NON-NLS-1$
		final JAXBElement<? extends AbstractGeometryType> element = converter
				.createElement(point);
		Assert.assertEquals(
				"urn:ogc:def:crs:EPSG::4326", element.getValue().getSrsName()); //$NON-NLS-1$

	}

	public void testPoint2() throws Exception {

		final Point point = geometryFactory.createPoint(new Coordinate(10, 20));
		point.setUserData("urn:ogc:def:crs:OGC:1.3:CRS1"); //$NON-NLS-1$
		final JAXBElement<? extends AbstractGeometryType> element = converter
				.createElement(point);
		Assert.assertEquals(
				"urn:ogc:def:crs:OGC:1.3:CRS1", element.getValue().getSrsName()); //$NON-NLS-1$

	}

	public void testPoint3() throws Exception {

		final Point point = geometryFactory.createPoint(new Coordinate(10, 20));
		final JAXBElement<? extends AbstractGeometryType> element = converter
				.createElement(point);
		Assert.assertEquals(null, element.getValue().getSrsName());

	}
}
