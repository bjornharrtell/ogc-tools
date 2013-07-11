package org.jvnet.ogc.gml.v_3_2_1.jts.adapter.tests;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jvnet.ogc.gml.v_3_2_1.jts.adapter.PointTypeDomHandler;

import com.vividsolutions.jts.geom.Point;

@XmlRootElement(name = "a")
public class A {

	private Point one;

	@XmlAnyElement(PointTypeDomHandler.class)
	public Point getOne() {
		return one;
	}

	public void setOne(Point one) {
		this.one = one;
	}

}
