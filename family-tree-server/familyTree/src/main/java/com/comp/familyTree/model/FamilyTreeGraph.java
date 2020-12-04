package com.comp.familyTree.model;

import java.util.ArrayList;
import java.util.List;

public class FamilyTreeGraph {


	private List<Node> nodes= new ArrayList<Node>();

	private List<Link> links= new ArrayList<Link>();

	public void addNode(String id) {
		Node node = new Node(id);
		this.nodes.add(node);
	}

	public void addLink(String source, String target,String label) {
		Link link = new Link(source, target,label);
		this.links.add(link);
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	

}

class Node {
	private String id;

	Node(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

class Link {

	private String source;

	private String target;
	
	private String label;

	Link(String source, String target,String label) {
		this.source = source;
		this.target = target;
		this.label=label;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
