package com.again.gef.zest.model;

import java.util.List;

import com.google.common.collect.Lists;

public class Application {

	public final String name;

	public Application(String name) {
		this.name = name;
	}

	public String hostApplication;

	public List<BusinessComponent> bcList = Lists.newArrayList();

	public List<BusinessComponentPlugin> bcpList = Lists.newArrayList();

	public List<UiComponent> upList = Lists.newArrayList();

}
