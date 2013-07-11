package org.jvnet.ogc.gml.v_3_2_1.jts;

import org.jvnet.ogc.gml.v_3_2_1.ObjectFactoryInterface;

import net.opengis.gml.v_3_2_1.AbstractGeometryType;

import com.vividsolutions.jts.geom.Geometry;

public abstract class AbstractJTSToGML321Converter<G, P, J extends Geometry>
    implements
    JTSToGML321ConverterInterface<G, P, J> {

  private final ObjectFactoryInterface objectFactory;
  private final JTSToGML321SRSReferenceGroupConverterInterface srsReferenceGroupConverter;

  public AbstractJTSToGML321Converter(
		  ObjectFactoryInterface objectFactory,
      JTSToGML321SRSReferenceGroupConverterInterface srsReferenceGroupConverter) {
    this.objectFactory = objectFactory;
    this.srsReferenceGroupConverter = srsReferenceGroupConverter;
  }

  public ObjectFactoryInterface getObjectFactory() {
    return objectFactory;
  }

  public JTSToGML321SRSReferenceGroupConverterInterface getSrsReferenceGroupConverter() {
    return srsReferenceGroupConverter;
  }

  public final G createGeometryType(J source) {
    final G target = doCreateGeometryType(source);

    if (target instanceof AbstractGeometryType) {
    	getSrsReferenceGroupConverter().convert(source, (AbstractGeometryType)target);
    }
    
    return target;

  }

  protected abstract G doCreateGeometryType(J geometry);

}
