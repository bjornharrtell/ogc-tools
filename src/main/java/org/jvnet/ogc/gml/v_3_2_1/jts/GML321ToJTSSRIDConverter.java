package org.jvnet.ogc.gml.v_3_2_1.jts;

import java.text.MessageFormat;
import java.text.ParseException;

import net.opengis.gml.v_3_2_1.AbstractGeometryType;

import org.apache.commons.lang.Validate;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;


import com.vividsolutions.jts.geom.Geometry;

public class GML321ToJTSSRIDConverter implements
		GML321ToJTSSRIDConverterInterface {

	private final String[] patterns;

	public GML321ToJTSSRIDConverter(String[] patterns) {
		this.patterns = patterns;
	}

	public void convert(ObjectLocator locator, AbstractGeometryType source,
			Geometry target) throws ConversionFailedException {
		Validate.notNull(source);
		Validate.notNull(target);

		String srsName = source.getSrsName();

		if (srsName != null) {
			for (String pattern : patterns) {
				try {
					final MessageFormat format = new MessageFormat(pattern);
					Object[] codearray = format.parse(srsName);
					if (codearray.length > 0) {
						target.setSRID(((Number) codearray[0]).intValue());
						if (target.getUserData() == null) {
							target.setUserData(srsName);
							return;
						}
					}
				} catch (ParseException e) {
					// this pattern was not correct
					continue;
				}
			}

			if (target.getUserData() != null) {
				throw new ConversionFailedException(locator,
						MessageFormat.format("Could not parse SRS name [{0}].",
								srsName));
			} else {
				target.setUserData(srsName);
			}
		}
	}

}
