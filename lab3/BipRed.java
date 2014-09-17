
public class BipRed {
	Kattio io;

	int[][] flodesMatris;
	int oldx;
	int oldy;
	int horn = 0;

	void readBipartiteGraph() {
		// Las antal horn och kanter
		int x = io.getInt();
		oldx = x;
		int y = io.getInt();
		oldy = y;
		int e = io.getInt();

		flodesMatris = new int[x + y + e + 3][3];

		// Las in kanterna
		for (int i = 3; i < e + 3; ++i) {
			flodesMatris[i][0] = io.getInt();
			flodesMatris[i][1] = io.getInt();
			flodesMatris[i][2] = 1;
			// skriv varje kant till dess plats i flodesmatrisen
		}
		// forsta raden antal horn, +2 eftersom vi lagger till kalla och sanka
		flodesMatris[0][0] = x + y + 2;
		int s = x + y + 1;// andra raden setter s och t
		int t = s + 1;
		flodesMatris[1][0] = s;
		flodesMatris[1][1] = t;
		flodesMatris[2][0] = e + x + y;
		// skriv kanter
		int counter = 1; //Starta raknare so flodesmatrisen formateras rett
		int offset = e + 3;
		int sroof = offset + x;
		for (int i = offset; i < sroof; i++) {
			flodesMatris[i][0] = s;
			flodesMatris[i][1] = counter;
			flodesMatris[i][2] = 1;
			counter++;
		}
		int stop = sroof + y;
		for (int i = sroof; i < stop; i++) {
			flodesMatris[i][0] = counter;
			flodesMatris[i][1] = t;
			flodesMatris[i][2] = 1;
			counter++;
		}
	}

	void writeFlowGraph() {
		int v = flodesMatris[0][0], 
		e = flodesMatris[2][0], 
		s = flodesMatris[1][0], 
		t = flodesMatris[1][1];
		// Skriv ut antal horn och kanter samt kalla och sanka
		io.println(v);
		io.println(s + " " + t);
		io.println(e);
		for (int i = 3; i < e + 3; ++i) {
			int a = flodesMatris[i][0], b = flodesMatris[i][1], c = flodesMatris[i][2];
			io.println(a + " " + b + " " + c);
		}
		io.flush();
	}

	void readMaxFlowSolution() {
		// Las in antal horn, kanter, kalla, sanka, och totalt flode
		// (Antal horn, kalla och sanka borde vara samma som vi i grafen vi
		// skickade ivag)
		int v = io.getInt();
		int s = io.getInt();
		int t = io.getInt();
		int totflow = io.getInt();
		int e = io.getInt();
		horn = e;
		flodesMatris[1][2] = totflow;
		flodesMatris[0][0] = v;
		flodesMatris[1][0] = s;
		flodesMatris[1][1] = t;
		flodesMatris[2][0] = e;

		for (int i = 3; i < e + 3; ++i) {
			flodesMatris[i][0] = io.getInt();
			flodesMatris[i][1] = io.getInt();
			flodesMatris[i][2] = io.getInt();
		}
	}

	void writeBipMatchSolution() {
		int maxMatch = flodesMatris[1][2];
		int s = flodesMatris[1][0], t = flodesMatris[1][1], e = flodesMatris[2][0];
		
		io.println(oldx + " " + oldy);
		io.println(maxMatch);
		
		for (int i = 3; i < e + 3; i++) {
			if (flodesMatris[i][0] != s && flodesMatris[i][1] != t) {
				int a = flodesMatris[i][0], b = flodesMatris[i][1];
				// Kant ingar i losning
				io.println(a + " " + b);
			}
		}
		

	}

	BipRed() {
		io = new Kattio(System.in, System.out);

		readBipartiteGraph();

		writeFlowGraph();

		readMaxFlowSolution();

		writeBipMatchSolution();

		io.close();
	}

	public static void main(String args[]) {
		new BipRed();
	}
}