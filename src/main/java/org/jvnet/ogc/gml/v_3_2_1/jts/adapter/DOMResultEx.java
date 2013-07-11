package org.jvnet.ogc.gml.v_3_2_1.jts.adapter;

import javax.xml.bind.ValidationEventHandler;
import javax.xml.transform.dom.DOMResult;

import org.apache.commons.lang.Validate;

public class DOMResultEx extends DOMResult {

	private final ValidationEventHandler validationEventHandler;

	public DOMResultEx(ValidationEventHandler validationEventHandler) {
		super();
		Validate.notNull(validationEventHandler);
		this.validationEventHandler = validationEventHandler;
	}

	public ValidationEventHandler getValidationEventHandler() {
		return validationEventHandler;
	}

}
