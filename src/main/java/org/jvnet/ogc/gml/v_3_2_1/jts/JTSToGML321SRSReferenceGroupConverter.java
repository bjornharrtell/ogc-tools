package org.jvnet.ogc.gml.v_3_2_1.jts;

import java.text.MessageFormat;

import org.apache.commons.lang.Validate;

import net.opengis.gml.v_3_2_1.AbstractGeometryType;

import com.vividsolutions.jts.geom.Geometry;

public class JTSToGML321SRSReferenceGroupConverter
    implements
    JTSToGML321SRSReferenceGroupConverterInterface {

  private String sridPattern = JTSToGML321Constants.DEFAULT_SRID_FORMAT_PATTERN;

  public void convert(Geometry source, AbstractGeometryType target) {
    Validate.notNull(source);
    Validate.notNull(target);

    if (source.getUserData() instanceof String) {
      target.setSrsName((String) source.getUserData());
    }
    else if (source.getSRID() != 0) {
      target.setSrsName(MessageFormat.format(this.sridPattern, source.getSRID()));
    }
  }

}
