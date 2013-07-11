package org.jvnet.ogc.gml.v_3_2_1.jts.adapter;

import java.text.MessageFormat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.DomHandler;
import javax.xml.transform.dom.DOMResult;

import net.opengis.gml.v_3_2_1.AbstractGeometryType;

import org.apache.commons.lang.Validate;
import org.jvnet.jaxb2_commons.locator.DefaultRootObjectLocator;
import org.jvnet.ogc.gml.v_3_2_1.ObjectFactoryInterface;
import org.jvnet.ogc.gml.v_3_2_1.jts.ConversionFailedException;
import org.jvnet.ogc.gml.v_3_2_1.jts.GML321ToJTSConstants;
import org.jvnet.ogc.gml.v_3_2_1.jts.GML321ToJTSConverterInterface;
import org.jvnet.ogc.gml.v_3_2_1.jts.GML321ToJTSSRIDConverterInterface;
import org.jvnet.ogc.gml.v_3_2_1.jts.JAXBContextFactory;
import org.jvnet.ogc.gml.v_3_2_1.jts.JTSToGML321Constants;
import org.jvnet.ogc.gml.v_3_2_1.jts.JTSToGML321ConverterInterface;
import org.jvnet.ogc.gml.v_3_2_1.jts.JTSToGML321SRSReferenceGroupConverterInterface;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

public abstract class AbstractGeometryDomHandler<G extends AbstractGeometryType, P, J extends Geometry>
		implements DomHandler<J, DOMResultEx> {

	private static JAXBContext context;
	private final Marshaller marshaller;
	private final JTSToGML321ConverterInterface<G, P, J> marshallerConverter;
	private final Unmarshaller unmarshaller;
	private final GML321ToJTSConverterInterface<G, P, J> unmarshallerConverter;

	public AbstractGeometryDomHandler() throws JAXBException {
		Marshaller m = null;
		Unmarshaller u = null;
		JTSToGML321ConverterInterface<G, P, J> mc;
		GML321ToJTSConverterInterface<G, P, J> uc;
		if (context == null) {
			context = JAXBContext
					.newInstance(JAXBContextFactory.DEFAULT_CONTEXT_PATH);
		}
		// m = createMarshaller(context.createMarshaller());
		// u = createUnmarshaller(context.createUnmarshaller());
		m = context.createMarshaller();
		u = context.createUnmarshaller();
		mc = this.createMarshallerConverter(m);
		uc = this.createUnmarshallerConverter(u);
		this.marshaller = m;
		this.marshallerConverter = mc;
		this.unmarshaller = u;
		this.unmarshallerConverter = uc;
	}

	public AbstractGeometryDomHandler(Unmarshaller unmarshaller)
			throws JAXBException {
		Validate.notNull(unmarshaller);
		this.marshaller = null;
		// this.unmarshaller = createUnmarshaller(unmarshaller);
		this.marshallerConverter = null;
		this.unmarshaller = unmarshaller;
		this.unmarshallerConverter = createUnmarshallerConverter(this.unmarshaller);
	}

	public AbstractGeometryDomHandler(Marshaller marshaller)
			throws JAXBException {
		Validate.notNull(marshaller);
		// this.marshaller = createMarshaller(marshaller);
		this.marshaller = marshaller;
		this.marshallerConverter = createMarshallerConverter(this.marshaller);
		this.unmarshaller = null;
		this.unmarshallerConverter = null;
	}

	public AbstractGeometryDomHandler(Marshaller marshaller,
			Unmarshaller unmarshaller) throws JAXBException {
		Validate.notNull(marshaller);
		Validate.notNull(unmarshaller);
		// this.marshaller = createMarshaller(marshaller);
		// this.unmarshaller = createUnmarshaller(unmarshaller);
		this.marshaller = marshaller;
		this.marshallerConverter = createMarshallerConverter(this.marshaller);
		this.unmarshaller = unmarshaller;
		this.unmarshallerConverter = createUnmarshallerConverter(this.unmarshaller);
	}

	protected JTSToGML321ConverterInterface<G, P, J> createMarshallerConverter(
			Marshaller marshaller) throws JAXBException {
		ObjectFactoryInterface objectFactory = JTSToGML321Constants.DEFAULT_OBJECT_FACTORY;

		try {
			if (marshaller
					.getProperty(JAXBContextFactory.PROPERTY_NAME_OBJECT_FACTORY) != null) {
				objectFactory = (ObjectFactoryInterface) marshaller
						.getProperty(JAXBContextFactory.PROPERTY_NAME_OBJECT_FACTORY);
			}
		} catch (PropertyException ignored) {
		}

		JTSToGML321SRSReferenceGroupConverterInterface srsReferenceGroupConverter = JTSToGML321Constants.DEFAULT_SRS_REFERENCE_GROUP_CONVERTER;

		try {
			if (marshaller
					.getProperty(JAXBContextFactory.PROPERTY_NAME_SRS_REFERENCE_GROUP_CONVERTER) != null) {
				srsReferenceGroupConverter = (JTSToGML321SRSReferenceGroupConverterInterface) marshaller
						.getProperty(JAXBContextFactory.PROPERTY_NAME_SRS_REFERENCE_GROUP_CONVERTER);

			}
		} catch (PropertyException ignroed) {

		}
		return createMarshallerConverter(objectFactory,
				srsReferenceGroupConverter);
	}

	protected abstract JTSToGML321ConverterInterface<G, P, J> createMarshallerConverter(
			ObjectFactoryInterface objectFactory,
			JTSToGML321SRSReferenceGroupConverterInterface srsReferenceGroupConverter);

	protected GML321ToJTSConverterInterface<G, P, J> createUnmarshallerConverter(
			Unmarshaller unmarshaller) throws JAXBException {
		GeometryFactory geometryFactory = GML321ToJTSConstants.DEFAULT_GEOMETRY_FACTORY;
		try {
			if (unmarshaller
					.getProperty(JAXBContextFactory.PROPERTY_NAME_GEOMETRY_FACTORY) != null) {
				geometryFactory = (GeometryFactory) unmarshaller
						.getProperty(JAXBContextFactory.PROPERTY_NAME_GEOMETRY_FACTORY);
			}
		} catch (PropertyException ignored) {
		}

		GML321ToJTSSRIDConverterInterface sridConverter = GML321ToJTSConstants.DEFAULT_SRID_CONVERTER;
		try {
			if (unmarshaller
					.getProperty(JAXBContextFactory.PROPERTY_NAME_SRID_CONVERTER) != null) {
				sridConverter = (GML321ToJTSSRIDConverterInterface) unmarshaller
						.getProperty(JAXBContextFactory.PROPERTY_NAME_SRID_CONVERTER);

			}
		} catch (PropertyException ignored) {

		}
		return createUnmarshallerConverter(geometryFactory, sridConverter);
	}

	protected abstract GML321ToJTSConverterInterface<G, P, J> createUnmarshallerConverter(
			GeometryFactory geometryFactory,
			GML321ToJTSSRIDConverterInterface sridConverter);

	protected abstract Class<G> getGeometryType();

	protected abstract Class<P> getPropertyType();

	protected abstract Class<J> getGeometryClass();

	public J unmarshal(Element v) throws Exception {
		if (this.unmarshaller == null) {
			final Object g = this.unmarshaller.unmarshal(v);
			if (g == null) {
				return null;
			} else if (getGeometryClass().isAssignableFrom(getGeometryClass())) {
				throw new ClassCastException(
						MessageFormat
								.format("Unexpected geometry type [{0}], expected geometry type [{1}].",
										g.getClass().getName(),
										getGeometryClass().getName()));
			} else {
				@SuppressWarnings("unchecked")
				final J result = (J) g;
				return result;
			}
		} else {
			throw new UnsupportedOperationException(
					"Unmarshaller was not provided, unmarshalling is not supported.");
		}
	}

	public Element marshal(J v) throws Exception {
		if (this.marshaller == null) {
			final DOMResult result = new DOMResult();
			this.marshaller.marshal(v, result);
			final Node node = result.getNode();
			if (node instanceof Document) {
				return ((Document) node).getDocumentElement();
			} else if (node instanceof Element) {
				return (Element) node;
			} else if (node instanceof DocumentFragment) {
				return (Element) node.getChildNodes().item(0);
			} else {
				throw new JAXBException(
						"Could not marshal the geometry, intermediate result does not contain a DOM element.");
			}
		} else {
			throw new UnsupportedOperationException(
					"Marshaller was not provided, marshalling is not supported.");
		}
	}

	public DOMResultEx createUnmarshaller(ValidationEventHandler errorHandler) {
		Validate.notNull(errorHandler);
		return new DOMResultEx(errorHandler);
	}

	public J getElement(DOMResultEx result) {
		return unmarshalGeometryType(result);
	}

	protected J unmarshalGeometryType(DOMResultEx result) {
		if (this.unmarshaller == null) {
			// TODO Validation event
			return null;

		} else {
			try {
				final JAXBElement<G> geometryElement = this.unmarshaller
						.unmarshal(result.getNode(), getGeometryType());
				final G geometry = geometryElement.getValue();
				if (geometry == null) {
					// TODO Validation event
					return null;
				} else {
					try {
						return this.unmarshallerConverter.createGeometry(
								new DefaultRootObjectLocator(geometryElement),
								geometry);
					} catch (ConversionFailedException cfex) {
						// TODO Validation event
						return null;
					}
				}
			} catch (JAXBException jaxbex) {
				// TODO Validation event
				return null;
			}
		}
	}

	public javax.xml.transform.Source marshal(J geometry,
			ValidationEventHandler errorHandler) {
		return null;
	}

}
