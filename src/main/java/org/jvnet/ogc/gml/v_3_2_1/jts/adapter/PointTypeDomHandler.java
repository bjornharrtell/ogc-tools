package org.jvnet.ogc.gml.v_3_2_1.jts.adapter;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.vividsolutions.jts.geom.Point;

public class PointTypeDomHandler extends AbstractPointDomHandler {

	public PointTypeDomHandler() throws JAXBException {
		super();
	}

	public PointTypeDomHandler(Marshaller marshaller, Unmarshaller unmarshaller)
			throws JAXBException {
		super(marshaller, unmarshaller);
	}

	public PointTypeDomHandler(Marshaller marshaller) throws JAXBException {
		super(marshaller);
	}

	public PointTypeDomHandler(Unmarshaller unmarshaller) throws JAXBException {
		super(unmarshaller);
	}
	
	@Override
	public Point getElement(DOMResultEx result) {
		return unmarshalGeometryType(result);
	}

}