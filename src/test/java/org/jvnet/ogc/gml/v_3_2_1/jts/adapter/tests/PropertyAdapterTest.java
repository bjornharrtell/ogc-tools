package org.jvnet.ogc.gml.v_3_2_1.jts.adapter.tests;

import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;

import org.junit.Assert;
import org.junit.Test;

public class PropertyAdapterTest {

	@Test
	public void testPropertyAdapter() throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(A.class.getPackage()
				.getName());

		@SuppressWarnings("unchecked")
		final JAXBElement<A> aElement = (JAXBElement<A>) context
				.createUnmarshaller().unmarshal(
						new StreamSource(getClass().getResource("A0.xml")
								.toString()));
		
		// TODO: need to migrate test data to GML 3.2.1
		//Assert.assertNotNull(aElement.getValue().getOne());
		// context.createMarshaller().marshal(aElement, System.out);

	}
}
