package collections.graph;


import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Generic Type Graph datastructure with add node functionality.
 * Considering unique element of each node.
 * E must override equals() and hashcode()
 * @author devi
 *
 */

public class PopulateGraph<E> {
	/**
	 * Map with all node's value as key and node instance as value
	 */
	private Map<E, Node<E>> graph;

	public PopulateGraph() {
		if(this.graph == null) {
			this.graph = new HashMap<>();
		}
	}



	/**
	 * This method takes node value and its adjacent node(s) to the graph
	 * @param value Value of the node
	 * @param adjacent Adjacent nodes
	 */
	public void add(E value, List<E> adjacent) {
		
		if(this.graph.containsKey(value)) {
			Node<E> existingNode = this.graph.get(value);
			HashSet<Node<E>> adjacentForExistingNode = existingNode.adjacentNodes;

			for(E item : adjacent) {
				if (!adjacentForExistingNode.contains(item) && (!this.graph.containsKey(item))) {
					//Existing node doesn't have edge connecting adjacent-item and map also doesn't contain adjacent-item
					Node<E> itemNode = new Node<>(item, new HashSet<>());
					adjacentForExistingNode.add(itemNode);
					itemNode.adjacentNodes.add(existingNode);
					this.graph.put(item, itemNode);		
				} else if (!adjacentForExistingNode.contains(item) && this.graph.containsKey(item)) {
					//Existing node doesn't have edge connecting adjacent-item but adjacent-item exists in the map
					Node<E> node = this.graph.get(item);
					node.adjacentNodes.add(existingNode);
					existingNode.adjacentNodes.add(node);
				}
			}
		} else {
			Node<E> itemNode = new Node<>(value, new HashSet<>());
			//Node with value does not exist in the map
			this.graph.put(value, itemNode);
			
			for (E item : adjacent) {
				//Adding adjacent node(s) connecting with the new node
				if (!this.graph.containsKey(item)) {
					Node<E> adj = new Node<>(item, new HashSet<>());
					this.graph.put(item, adj);
					adj.adjacentNodes.add(itemNode);
					itemNode.adjacentNodes.add(adj);
				} else {
					Node<E> existingAdjNode = this.graph.get(item);
					itemNode.adjacentNodes.add(existingAdjNode);
					existingAdjNode.adjacentNodes.add(itemNode);				
				}				
			}		
		}
	}


	//show graph content
	public void display() {
		for (Entry<E, Node<E>> entry : this.graph.entrySet()) {
			Node<E> node = entry.getValue();		
			HashSet<Node<E>> adjacentNodes = node.adjacentNodes;
			System.out.println("Data of node = "+node.value + ",adjacents are: "+adjacentNodes);	
		}
	}


	/**
	 * Node of Graph
	 *
	 */

	private static class Node<E> {
		E value;
		HashSet<Node<E>> adjacentNodes;

		public Node(E value, HashSet<Node<E>> adjacentNodes) {
			super();
			this.value = value;
			this.adjacentNodes = adjacentNodes;
		}

		public String toString() {
			return value.toString();
		}
	}



	/**
	 * Driver code to test
	 * 
	 */
	public static void main(String[] args) {

		PopulateGraph<Object> graph = new PopulateGraph<>();		

		graph.add("1", Arrays.asList("2","3"));

		graph.add("3", Arrays.asList("1","4","5"));

		graph.add("4", Arrays.asList("6","3"));

		graph.add("5", Arrays.asList("6"));

		graph.add(1, Arrays.asList(2,"3"));

		graph.display();

	}


}
