import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
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

	}

}

class GraphArray {
	int[][] map = null;
	int nodes = 0;
	int paths = 0;
	boolean direction = false;

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

	public boolean isConnected() {
		ArrayList<Integer> nodes = goThoughConnectedNodes(0); // get all the nodes that can be passed from node 0.
		for (int i=0;i<this.nodes-1;i++) { // iterate all nodes.
			if (!nodes.contains(i)) { // if there is a node that is not in nodes list, then it is disconnected graph.
				return false;
			}
		}
		return true; // all nodes can be passed from node 0.
	}

	private ArrayList<Integer> goThoughConnectedNodes(int start) {
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
