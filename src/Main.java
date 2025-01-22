import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		
		//graphArraySample();
		graphNodesSample();

	}
	
	private static void graphNodesSample() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		GraphNodes graph1 = new GraphNodes();
		graph1.readBGFromScanner(sc);
		graph1.print();
		
		/*GraphNodes graph2 = new GraphNodes();
		graph2.readDGFromScanner(sc);
		graph2.printD();*/
		
		/*GraphNodes graph3 = new GraphNodes();
		graph3.readBGLFromScanner(sc);
		graph3.print();*/
		
		/*if (graph1.isConnected()) {
			System.out.println("The graph is connected");
		} else {
			System.out.println("The graph is not conected");
		}*/
		
		/*if (graph1.isTree()) {
			System.out.println("The graph is a tree");
		} else {
			System.out.println("The graph is not a tree");
		}*/
		
		Node node1 = graph1.nodes.get(0);
		Node node2 = graph1.nodes.get(graph1.nodes.size()-1);
		
		/*if (twoNodesAreConnectedDFS(node1,node2)) {
			System.out.println("DFS: Node 0 and Node "+(graph1.nodes.size()-1) +" are connected.");
		} else {
			System.out.println("DFS: Node 0 and Node "+(graph1.nodes.size()-1) +" are not connected.");
		}*/
		
		if (twoNodesAreConnectedBFS(node1, node2)) {
			System.out.println("BFS: Node 0 and Node "+(graph1.nodes.size()-1) +" are connected.");
		} else {
			System.out.println("BFS: Node 0 and Node "+(graph1.nodes.size()-1) +" are not connected.");
		}
		sc.close();
	}

	private static boolean twoNodesAreConnectedBFS(Node node1, Node node2) {
		if (node1 == node2) return true;
		ArrayList<Node> passedNodes = new ArrayList<>();
		ArrayList<Node> tobeProcessed = new ArrayList<>();
		tobeProcessed.add(node1);
		
		while (!tobeProcessed.isEmpty()) {
			Node current = tobeProcessed.get(0);
			tobeProcessed.remove(0);
			
			for (Node n:current.connectedNodes) {
				if (n == node2) return true;
				if (passedNodes.contains(n)) continue;
				if (tobeProcessed.contains(n)) continue;
				tobeProcessed.add(n);
			}
			
			passedNodes.add(current);
		}
		return false;
	}

	private static boolean twoNodesAreConnectedDFS(Node node1, Node node2) {
		// TODO Auto-generated method stub
		ArrayList<Node> passedNodes = new ArrayList<>();
		return isConnectedTo(null, node1, node2, passedNodes);
	}

	private static boolean isConnectedTo(Node from, Node node1, Node node2, ArrayList<Node> passedNodes) {
		if (node1 == node2) return true;
		passedNodes.add(node1);
		for (Node n:node1.connectedNodes) {
			if (n==from) continue;
			if (passedNodes.contains(n)) continue;
			if (isConnectedTo(node1, n, node2, passedNodes)) 
				return true;
		}
		return false;
	}

	public static void graphArraySample() {
		Scanner sc = new Scanner(System.in);
		GraphArray graph1 = new GraphArray();
		graph1.readBGFromScanner(sc);
		//graph1.print();
		
		/*GraphArray graph2 = new GraphArray();
		graph2.readDGFromScanner(sc);
		graph2.print();*/
		
		/*GraphArray graph3 = new GraphArray();
		graph3.readBGLFromScanner(sc);
		graph3.print();|*/
		
		if (graph1.isConnected()) {
			System.out.println("The graph is connected");
		} else {
			System.out.println("The graph is not conected");
		}
		
		if (graph1.isTree()) {
			System.out.println("The graph is a tree");
		} else {
			System.out.println("The graph is not a tree");
		}
		sc.close();
	}

}

class GraphArray {
	int[][] map = null;
	int nodes = 0;
	int paths = 0;
	boolean direction = false;
	int connected = -1; // -1 for unknown, 0 for disconnected, 1 for connected.

	public void readBGFromScanner(Scanner sc) { // BG: bidirection
		nodes = sc.nextInt();
		paths = sc.nextInt();
		map = new int[nodes][nodes];
		for (int i=0;i<nodes;i++) { // initialization
			for (int j=0;j<nodes;j++) {
				map[i][j] = 0;
			}
		}
		
		for (int i=0;i<paths;i++) {
			int n1 = sc.nextInt();
			int n2 = sc.nextInt();
			this.map[n1][n2] = 1;
			this.map[n2][n1] = 1;
		}
		
	}

	public boolean isTree() {
		if (!this.isConnected()) return false;
		
		ArrayList<Integer> passedNodes = new ArrayList<>(); // the return list contains all passed nodes;
		ArrayList<Integer> nextNodes = new ArrayList<>(); // the next connected nodes of a current node being processed.
		ArrayList<Integer> tobeProcessed = new ArrayList<>(); // all the nodes we have not yet processed.
		
		tobeProcessed.add(0); // initialize the tobeProcess with the starting node.
		
		while (!tobeProcessed.isEmpty()) { // now start the loop when there is/are node(s)  to be processed.
			int currentNode = tobeProcessed.get(0); // get the first node to be processed as current node.
			// get all next nodes of current;
			for (int i=0;i<this.nodes-1;i++) { // go through all nodes.
				if (i == currentNode) continue; //  if the i is current node, it is not a to be processed node.
				
				if (this.map[currentNode][i]!=0 && i!=currentNode) { // if there is a path between currentNode and i, then i is a next node.
					if (passedNodes.contains(i)) // if the i node has been passed previously, then there should be a ring.
						return false;
					if (tobeProcessed.contains(i)) // if the i node is in the tobe list, then there should be a ring.
						return false;
					nextNodes.add(i);
				}
			}
			passedNodes.add(currentNode); // the current node has been processed and is passed.
			tobeProcessed.remove(0); // remove the passed node from to be processed list.
			tobeProcessed.addAll(nextNodes); // add all next nodes of current node to the tobeProcessed.
			nextNodes.clear();
		}
		
		return true; // if never meet previous node, then there is no ring.
	}

	public boolean isConnected() {
		if (this.connected==1) return true;
		if (this.connected==0) return false;
		
		ArrayList<Integer> nodes = goThoughConnectedNodesBFS(0); // get all the nodes that can be passed from node 0.
		for (int i=0;i<this.nodes-1;i++) { // iterate all nodes.
			if (!nodes.contains(i)) { // if there is a node that is not in nodes list, then it is disconnected graph.
				this.connected = 0;
				return false;
			}
		}
		this.connected = 1;
		return true; // all nodes can be passed from node 0.
	}

	// this is a BFS algorithm.
	private ArrayList<Integer> goThoughConnectedNodesBFS(int start) {
		ArrayList<Integer> passedNodes = new ArrayList<>(); // the return list contains all passed nodes;
		ArrayList<Integer> nextNodes = new ArrayList<>(); // the next connected nodes of a current node being processed.
		ArrayList<Integer> tobeProcessed = new ArrayList<>(); // all the nodes we have not yet processed.
		
		tobeProcessed.add(start); // initialize the tobeProcess with the starting node.
		
		while (!tobeProcessed.isEmpty()) { // now start the loop when there is/are node(s)  to be processed.
			int currentNode = tobeProcessed.get(0); // get the first node to be processed as current node.
			// get all next nodes of current;
			for (int i=0;i<this.nodes-1;i++) { // go through all nodes.
				if (i == currentNode) continue; //  if the i is current node, it is not a to be processed node.
				
				if (passedNodes.contains(i)) continue; // if i is already passed, then it is not to be processed again.
				
				if (tobeProcessed.contains(i)) continue; // if i is already in tobe list, then we do nothing.
				
				if (this.map[currentNode][i]!=0) { // if there is a path between currentNode and i, then i is a next node.
					nextNodes.add(i);
				}
			}
			passedNodes.add(currentNode); // the current node has been processed and is passed.
			tobeProcessed.remove(0); // remove the passed node from to be processed list.
			tobeProcessed.addAll(nextNodes); // add all next nodes of current node to the tobeProcessed.
			nextNodes.clear();
		}
		
		return passedNodes; // return all passed nodes.
	}

	public void readBGLFromScanner(Scanner sc) {
		// TODO Auto-generated method stub
		nodes = sc.nextInt();
		paths = sc.nextInt();
		map = new int[nodes][nodes];
		for (int i=0;i<nodes;i++) { // initialization
			for (int j=0;j<nodes;j++) {
				map[i][j] = 0;
			}
		}
		
		for (int i=0;i<paths;i++) {
			int n1 = sc.nextInt();
			int n2 = sc.nextInt();
			int l = sc.nextInt();
			this.map[n1][n2] = l;
			this.map[n2][n1] = l;
		}
	}

	public void readDGFromScanner(Scanner sc) {
		nodes = sc.nextInt();
		paths = sc.nextInt();
		direction = true;
		map = new int[nodes][nodes];
		for (int i=0;i<nodes;i++) { // initialization
			for (int j=0;j<nodes;j++) {
				map[i][j] = 0;
			}
		}
		
		for (int i=0;i<paths;i++) {
			int n1 = sc.nextInt();
			int n2 = sc.nextInt();
			this.map[n1][n2] = 1;
		}
	}

	public void print() {
		System.out.println(nodes+" "+paths);
		for (int i=0;i<nodes;i++) {
			int jStart = i+1;
			if (direction) {
				jStart = 0;
			}
			for (int j=jStart;j<nodes;j++) {
				if (map[i][j]!=0) {
					System.out.println(i+" "+j+" "+map[i][j]);
					paths++;
				}
			}
		}
		
	}
	
}


class Node {
	int id; // the node id
	ArrayList<Node> connectedNodes = new ArrayList<>(); // all the nodes connected to this node.
	
	public Node(int id) {
		this.id = id;
	}

	// add a connected node(neighbour) to this node.
	public void addNeighbour(Node neighbour) {
		
		if (!containsNeighbour(neighbour))
			this.connectedNodes.add(neighbour);
	}

	// if the connected nodes already has a node as its neighbour.
	private boolean containsNeighbour(Node neighbour) {
		for (Node n:this.connectedNodes) {
			if (n.id == neighbour.id) {
				return true;
			}
		}
		return false;
	}
}


class GraphNodes {
	ArrayList<Node> nodes = new ArrayList<>(); // all the nodes in the graph.

	public void readBGFromScanner(Scanner sc) {
		int nOfNodes = sc.nextInt();
		int lines = sc.nextInt();
		
		for (int i=0;i<lines;i++) {
			// read in the line which contains 2 node ids.
			int id1 = sc.nextInt();
			int id2 = sc.nextInt();
			
			
			// create 2 nodes.
			Node node1 = new Node(id1); 
			Node node2 = new Node(id2);
			
			// if the created nodes are not included in the graph, then add them into the 
			// graph
			Node tmpNode = null;
			tmpNode = findNode(node1);
			if (tmpNode !=null ) { // we have alreay a node1
				node1 = tmpNode;
			} else { // we have never had node 1
				this.nodes.add(node1);
			}
			
			tmpNode = findNode(node2);
			if (tmpNode !=null ) {
				node2 = tmpNode;
			} else {
				this.nodes.add(node2);
			}
			
			// connect the 2 nodes;
			node1.addNeighbour(node2);
			node2.addNeighbour(node1);
		}
		
	}

	public boolean isTree() {
		if (!this.isConnected()) {
			return false;
		}
		
		ArrayList<Node> passedNodes = new ArrayList<>();
		
		Node startNode = this.nodes.get(0);
		
		return isTreeFrom(null, startNode, passedNodes);
	}

	private boolean isTreeFrom(Node from, Node startNode, ArrayList<Node> passedNodes) {
		// a dfs method to go through all nodes, using recursive method.
		passedNodes.add(startNode);
		for (Node n:startNode.connectedNodes) {
			if (n==from) continue;
			if (passedNodes.contains(n))
				return false;
			
			if (!isTreeFrom(startNode, n, passedNodes)) {
				return false;
			}
		}
		return true;
		
	}

	public boolean isConnected() {
		ArrayList<Node> passedNodes = new ArrayList<>();
		goFromNodeBFS(this.nodes.get(0),passedNodes);
		return passedNodes.size() == this.nodes.size();
	}

	private void goFromNodeBFS(Node node, ArrayList<Node> passedNodes) {
		ArrayList<Node> tobeProcessed = new ArrayList<>();
		tobeProcessed.add(node);
		while (!tobeProcessed.isEmpty()) {
			node = tobeProcessed.get(0);
			tobeProcessed.remove(0);
			for (Node n:node.connectedNodes) {
				if (n == node) continue;
				if (passedNodes.contains(n)) continue;
				if (tobeProcessed.contains(n)) continue;
				tobeProcessed.add(n);
			}
			passedNodes.add(node);
		}
		
	}

	public void printD() {
		int paths = getPathsD(); // get number of paths
		System.out.println(this.nodes.size()+" "+paths);
		for (Node n:this.nodes) {
			for (Node neighbour:n.connectedNodes) {
				System.out.println(n.id+" "+neighbour.id);
			}
		}
		
		
	}

	private int getPathsD() {
		// TODO Auto-generated method stub
		int paths = 0;
		for (Node n:this.nodes) {
			paths += n.connectedNodes.size();
		}
		return paths;
	}

	public void readDGFromScanner(Scanner sc) {
		int nOfNodes = sc.nextInt();
		int lines = sc.nextInt();
		
		for (int i=0;i<lines;i++) {
			// read in the line which contains 2 node ids.
			int id1 = sc.nextInt();
			int id2 = sc.nextInt();
			
			
			// create 2 nodes.
			Node node1 = new Node(id1); 
			Node node2 = new Node(id2);
			
			// if the created nodes are not included in the graph, then add them into the 
			// graph
			Node tmpNode = null;
			tmpNode = findNode(node1);
			if (tmpNode !=null ) { // we have alreay a node1
				node1 = tmpNode;
			} else { // we have never had node 1
				this.nodes.add(node1);
			}
			
			tmpNode = findNode(node2);
			if (tmpNode !=null ) {
				node2 = tmpNode;
			} else {
				this.nodes.add(node2);
			}
			
			// connect the 2 nodes;
			node1.addNeighbour(node2);
			
		}
		
	}

	// try to find the node in node list.
	private Node findNode(Node node) {
		for (Node n:this.nodes) {
			if (n.id == node.id) {
				return n;
			}
		}
		return null;
	}

	public void print() {
		int paths = getPaths(); // get number of paths
		System.out.println(this.nodes.size()+" "+paths);
		for (Node n:this.nodes) {
			for (Node neighbour:n.connectedNodes) {
				if (neighbour.id>n.id)
					System.out.println(n.id+" "+neighbour.id);
			}
		}
		
	}
	
	

	private int getPaths() {
		int paths = 0;
		for (Node n:this.nodes) {
			paths += n.connectedNodes.size();
		}
		return paths/2; // because the count is duplicated, so we need to get half.
	}

	
}




