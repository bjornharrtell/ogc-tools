package org.jvnet.ogc.gml.v_3_2_1.jts.tests;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.jvnet.ogc.gml.v_3_2_1.ObjectFactory;

public abstract class LightweightContextTest {

	// @Test
	public void createsContext() throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(ObjectFactory.class
				.getPackage().getName(), Thread.currentThread()
				.getContextClassLoader());
		Assert.assertNotNull(context);
	}
}
