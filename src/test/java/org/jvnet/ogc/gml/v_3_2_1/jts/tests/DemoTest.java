package org.jvnet.ogc.gml.v_3_2_1.jts.tests;

import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.junit.Test;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.WKTWriter;

public class DemoTest {

	@Test
	public void demonstrateContext() throws JAXBException, IOException
	{
		JAXBContext context = JAXBContext.newInstance("org.jvnet.ogc.gml.v_3_2_1.jts");
		WKTWriter wktWriter = new WKTWriter();
		
		// Unmarshal
		Point point = (Point) context.createUnmarshaller().unmarshal(getClass().getResource("Point[0].xml"));
		
		Polygon polygon = (Polygon) context.createUnmarshaller().unmarshal(getClass().getResource("Polygon[0].xml"));
		
		// Marshal
		context.createMarshaller().marshal(point, System.out);
//		
		System.out.println(wktWriter.write(point));
		
	}
}
