package com.again.gef.zest;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphContainer;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import com.again.gef.zest.model.Application;
import com.again.gef.zest.model.BusinessComponent;
import com.again.gef.zest.model.BusinessComponentPlugin;
import com.again.gef.zest.model.ModelType;
import com.again.gef.zest.model.UiComponent;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ApplicationGraph extends ApplicationWindow {

	public ApplicationGraph() {
		super("Application Dependencies Grapgh");
	}

	@Override
	protected void createPartControl(Composite parent) {

		// Graph will hold all other objects
		Graph graph = new Graph(parent, SWT.NONE);
		graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NONE), true);
		// Selection listener on graphConnect or GraphNode is not supported
		// see https://bugs.eclipse.org/bugs/show_bug.cgi?id=236528
		graph.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(e);
			}
		});

		createGraphControls(graph, initModels());
	}

	private List<Application> initModels() {
		Application app1 = new Application("Application1");
		app1.upList.add(new UiComponent("uiComponent1_1"));
		BusinessComponent bc1_1 = new BusinessComponent("businessComponent1_1");
		BusinessComponent bc1_2 = new BusinessComponent("businessComponent1_2");
		BusinessComponent bc1_3 = new BusinessComponent("businessComponent1_3");
		app1.bcList.addAll(Lists.newArrayList(bc1_1, bc1_2, bc1_3));
		BusinessComponentPlugin bcp1_2_1 = new BusinessComponentPlugin("businessComponentPlugin1_2_1", bc1_2.name);
		BusinessComponentPlugin bcp1_2_2 = new BusinessComponentPlugin("businessComponentPlugin1_2_2", bc1_2.name);
		app1.bcpList.addAll(Lists.newArrayList(bcp1_2_1, bcp1_2_2));
		bcp1_2_2.depBcpList.add(bcp1_2_1.name);

		Application app2 = new Application("Application2");
		app2.upList.add(new UiComponent("uiComponent2_1"));
		BusinessComponent bc2_1 = new BusinessComponent("businessComponent2_1");
		app2.bcList.add(bc2_1);
		BusinessComponentPlugin bcp2_1 = new BusinessComponentPlugin("businessComponentPlugin2_1", bc1_3.name);
		app2.bcpList.add(bcp2_1);
		app2.hostApplication = app1.name;

		return Lists.newArrayList(app2, app1);
	}

	private void createGraphControls(Graph graph, List<Application> apps) {
		Map<Pair<ModelType, String>, GraphNode> resolvedConenections = Maps.newHashMap();
		Map<Pair<ModelType, String>, List<GraphNode>> unResolvedConnections = Maps.newHashMap();

		for (Application app : apps) {
			processAppNode(graph, resolvedConenections, unResolvedConnections, app);
		}
	}

	private void processAppNode(Graph graph, Map<Pair<ModelType, String>, GraphNode> resolvedConenections,
			Map<Pair<ModelType, String>, List<GraphNode>> unResolvedConnections, Application app) {
		GraphContainer appNode = new GraphContainer(graph, SWT.NONE, app.name);
		appNode.setLayoutAlgorithm(new RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
		Pair<ModelType, String> appNodeKey = new ImmutablePair<>(ModelType.app, app.name);

		processUnresolvedConnections(unResolvedConnections, appNodeKey, appNode, ModelType.app);

		// hostApplication
		if (StringUtils.isNotBlank(app.hostApplication)) {
			Pair<ModelType, String> hostAppNodeKey = new ImmutablePair<>(ModelType.app, app.hostApplication);
			processResolvedConnections(resolvedConenections, unResolvedConnections, hostAppNodeKey, appNodeKey, appNode,
					ModelType.app);
		}

		for (BusinessComponent bc : app.bcList) {
			processBcNode(appNode, resolvedConenections, unResolvedConnections, bc);
		}
		for (BusinessComponentPlugin bcp : app.bcpList) {
			processBcpNode(appNode, resolvedConenections, unResolvedConnections, bcp);
		}

		resolvedConenections.put(appNodeKey, appNode);
		appNode.open(true);
		appNode.applyLayout();
	}

	private void processBcNode(GraphContainer appNode, Map<Pair<ModelType, String>, GraphNode> resolvedConenections,
			Map<Pair<ModelType, String>, List<GraphNode>> unResolvedConnections, BusinessComponent bc) {
		GraphNode bcNode = new GraphNode(appNode, SWT.NONE, bc.name);
		Pair<ModelType, String> bcNodeKey = new ImmutablePair<>(ModelType.bc, bc.name);
		resolvedConenections.put(bcNodeKey, bcNode);
		processUnresolvedConnections(unResolvedConnections, bcNodeKey, bcNode, ModelType.bc);
	}

	private void processBcpNode(GraphContainer appNode, Map<Pair<ModelType, String>, GraphNode> resolvedConenections,
			Map<Pair<ModelType, String>, List<GraphNode>> unResolvedConnections, BusinessComponentPlugin bcp) {
		GraphNode bcpNode = new GraphNode(appNode, SWT.NONE, bcp.name);
		Pair<ModelType, String> bcpNodeKey = new ImmutablePair<>(ModelType.bcp, bcp.name);
		processUnresolvedConnections(unResolvedConnections, bcpNodeKey, bcpNode, ModelType.bcp);
		// bc
		if (StringUtils.isNotBlank(bcp.bc)) {
			Pair<ModelType, String> bcNodeKey = new ImmutablePair<>(ModelType.bc, bcp.bc);
			processResolvedConnections(resolvedConenections, unResolvedConnections, bcNodeKey, bcpNodeKey, bcpNode,
					ModelType.bcp);
		}
		// dependentBcps
		if (bcp.depBcpList != null && bcp.depBcpList.isEmpty()) {
			for (String depBcp : bcp.depBcpList) {
				Pair<ModelType, String> depBcpNodeKey = new ImmutablePair<>(ModelType.bcp, depBcp);
				processResolvedConnections(resolvedConenections, unResolvedConnections, depBcpNodeKey, bcpNodeKey,
						bcpNode, ModelType.bcp);
			}
		}

		resolvedConenections.put(bcpNodeKey, bcpNode);
	}

	private static void processUnresolvedConnections(
			Map<Pair<ModelType, String>, List<GraphNode>> unResolvedConnections, Pair<ModelType, String> nodeKey,
			GraphNode thisNode, ModelType type) {
		List<GraphNode> graphNodes = unResolvedConnections.get(nodeKey);
		if (graphNodes != null && !graphNodes.isEmpty()) {
			for (GraphNode graphNode : graphNodes) {
				// other-->this
				createConnection(thisNode.getGraphModel(), graphNode, thisNode, type);
			}
		}
	}

	private static void processResolvedConnections(Map<Pair<ModelType, String>, GraphNode> resolvedConenections,
			Map<Pair<ModelType, String>, List<GraphNode>> unResolvedConnections, Pair<ModelType, String> otherNodeKey,
			Pair<ModelType, String> thisNodeKey, GraphNode thisNode, ModelType type) {

		GraphNode graphNode = resolvedConenections.get(otherNodeKey);
		if (graphNode != null) {
			// this-->other
			createConnection(thisNode.getGraphModel(), thisNode, graphNode, type);
		} else {
			addUnresolvedConnection(unResolvedConnections, otherNodeKey, thisNode);
		}
	}

	private static void addUnresolvedConnection(Map<Pair<ModelType, String>, List<GraphNode>> unResolvedConnections,
			Pair<ModelType, String> nodeKey, GraphNode thisNode) {
		List<GraphNode> graphNodes = unResolvedConnections.get(nodeKey);
		if (graphNodes == null) {
			graphNodes = Lists.newArrayList();
			unResolvedConnections.put(nodeKey, graphNodes);
		}
		graphNodes.add(thisNode);
	}

	private static GraphConnection createConnection(Graph graph, GraphNode source, GraphNode destination,
			ModelType type) {
		GraphConnection graphConnection = new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, source,
				destination);
		graphConnection.setLineWidth(3);
		switch (type) {
		case app:
			graphConnection.setLineColor(graph.getDisplay().getSystemColor(SWT.COLOR_RED));
			break;
		case bc:
		case bcp:
			graphConnection.setLineColor(graph.getDisplay().getSystemColor(SWT.COLOR_GREEN));
			break;
		default:
			break;
		}
		return graphConnection;
	}

	public static void main(String[] args) {
		new ApplicationGraph().open();
	}
}
