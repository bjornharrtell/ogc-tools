package org.jvnet.ogc.gml.v_3_2_1.jts;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.text.MessageFormat;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.attachment.AttachmentMarshaller;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.validation.Schema;

import net.opengis.gml.v_3_2_1.AbstractGeometryType;
import net.opengis.gml.v_3_2_1.GeometryPropertyType;

import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;

import com.vividsolutions.jts.geom.Geometry;

public class MarshallerImpl implements javax.xml.bind.Marshaller {

	private final javax.xml.bind.Marshaller marshaller;
	private final JTSToGML321ConverterInterface<Object, GeometryPropertyType, Geometry> converter;

	public MarshallerImpl(
			javax.xml.bind.Marshaller marshaller,
			JTSToGML321ConverterInterface<Object, GeometryPropertyType, Geometry> converter) {
		super();
		this.marshaller = marshaller;
		this.converter = converter;
	}

	public javax.xml.bind.Marshaller getMarshaller() {
		return marshaller;
	}

	public JTSToGML321ConverterInterface<Object, GeometryPropertyType, Geometry> getConverter() {
		return converter;
	}

	protected JAXBElement<? extends AbstractGeometryType> convert(Object object)
			throws JAXBException {
		if (object == null) {
			return null;
		} else if (object instanceof Geometry) {
			return (JAXBElement<? extends AbstractGeometryType>) getConverter().createElement((Geometry) object);
			// try {
			// } catch (ConversionFailedException cfex) {
			// throw new JAXBException(
			// "Could not convert the geometry into a JAXB element.",
			// cfex);
			// }

		} else {
			throw new JAXBException(
					MessageFormat
							.format("This marshaller can only marshal instances of [{0}]. Class of this object [{1}] is not an instance of [{0}].",
									Geometry.class, object.getClass()));
		}
	}

	public void marshal(Object jaxbElement, Result result) throws JAXBException {
		getMarshaller().marshal(convert(jaxbElement), result);
	}

	public void marshal(Object jaxbElement, OutputStream os)
			throws JAXBException {
		getMarshaller().marshal(convert(jaxbElement), os);
	}

	public void marshal(Object jaxbElement, File output) throws JAXBException {
		getMarshaller().marshal(convert(jaxbElement), output);
	}

	public void marshal(Object jaxbElement, Writer writer) throws JAXBException {
		getMarshaller().marshal(convert(jaxbElement), writer);
	}

	public void marshal(Object jaxbElement, ContentHandler handler)
			throws JAXBException {
		getMarshaller().marshal(convert(jaxbElement), handler);
	}

	public void marshal(Object jaxbElement, Node node) throws JAXBException {
		getMarshaller().marshal(convert(jaxbElement), node);
	}

	public void marshal(Object jaxbElement, XMLStreamWriter writer)
			throws JAXBException {
		getMarshaller().marshal(convert(jaxbElement), writer);
	}

	public void marshal(Object jaxbElement, XMLEventWriter writer)
			throws JAXBException {
		getMarshaller().marshal(convert(jaxbElement), writer);

	}

	public Node getNode(Object contentTree) throws JAXBException {
		throw new UnsupportedOperationException();
	}

	public void setProperty(String name, Object value) throws PropertyException {
		getMarshaller().setProperty(name, value);

	}

	public Object getProperty(String name) throws PropertyException {
		return getMarshaller().getProperty(name);
	}

	public void setEventHandler(ValidationEventHandler handler)
			throws JAXBException {
		getMarshaller().setEventHandler(handler);

	}

	public ValidationEventHandler getEventHandler() throws JAXBException {
		return getMarshaller().getEventHandler();
	}

	public void setAdapter(@SuppressWarnings("rawtypes") XmlAdapter adapter) {
		getMarshaller().setAdapter(adapter);
	}

	@SuppressWarnings("rawtypes")
	public <A extends XmlAdapter> void setAdapter(Class<A> type, A adapter) {
		getMarshaller().setAdapter(type, adapter);

	}

	@SuppressWarnings("rawtypes")
	public <A extends XmlAdapter> A getAdapter(Class<A> type) {
		return getMarshaller().getAdapter(type);
	}

	public void setAttachmentMarshaller(AttachmentMarshaller am) {
		getMarshaller().setAttachmentMarshaller(am);
	}

	public AttachmentMarshaller getAttachmentMarshaller() {
		return getMarshaller().getAttachmentMarshaller();
	}

	public void setSchema(Schema schema) {
		getMarshaller().setSchema(schema);

	}

	public Schema getSchema() {
		return getMarshaller().getSchema();
	}

	public void setListener(Listener listener) {
		getMarshaller().setListener(listener);

	}

	public Listener getListener() {
		return getMarshaller().getListener();
	}

}
