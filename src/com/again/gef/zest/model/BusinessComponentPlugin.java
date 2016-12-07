package com.again.gef.zest.model;

import java.util.List;

import com.google.common.collect.Lists;

public class BusinessComponentPlugin {
	public final String name;
	public final String bc;

	public BusinessComponentPlugin(String name, String bc) {
		this.name = name;
		this.bc = bc;
	}

	public List<String> depBcpList = Lists.newArrayList();
}
