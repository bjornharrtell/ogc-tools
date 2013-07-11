package org.jvnet.ogc.gml.v_3_2_1.jts.adapter.tests;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

	public final static QName A_QNAME = new QName("a");

	@XmlElementDecl(name = "a")
	public JAXBElement<A> createPos(A value) {
		return new JAXBElement<A>(A_QNAME, A.class, null, value);
	}

	public A createA() {
		return new A();
	}

}
