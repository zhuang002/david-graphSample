import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		/*GraphArray graph1 = new GraphArray();
		graph1.readBGFromScanner(sc);
		graph1.print();*/
		
		GraphArray graph2 = new GraphArray();
		graph2.readDGFromScanner(sc);
		graph2.print();

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
				if (map[i][j]==1) {
					System.out.println(i+" "+j);
					paths++;
				}
			}
		}
		
		
		
	}
	
}
