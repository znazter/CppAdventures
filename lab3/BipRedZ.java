/**
 * Exempel på in- och utdatahantering för maxflödeslabben i kursen ADK.
 * 
 * Använder Kattio.java för in- och utläsning. Se
 * http://kattis.csc.kth.se/doc/javaio
 * 
 * @author: Per Austrin
 */

public class BipRed
{
	Kattio io;

	int[][] flowMatrix; // the array that will hold
	int oldx;
	int oldy;
	// the flow representation
	// of the bipartite match.

	void readBipartiteGraph()
	{
		// Läs antal hörn och kanter
		int x = io.getInt();
		oldx = x;
		int y = io.getInt();
		oldy = y;
		int e = io.getInt();

		flowMatrix = new int[x + y + e + 3][3];

		// Läs in kanterna
		for (int i = 3; i < e + 3; ++i)
		{
			flowMatrix[i][0] = io.getInt();
			flowMatrix[i][1] = io.getInt();
			flowMatrix[i][2] = 1;
			// above code translates each edge into a corresponding spot in the
			// flow matrix, as well as the flow capacity for each edge (always
			// 1).
		}
		// first row = number of nodes, +2 since we're adding s and t
		flowMatrix[0][0] = x + y + 2;
		// second row = labeling of s and t
		int s = x + y + 1;
		int t = s + 1;
		flowMatrix[1][0] = s;
		flowMatrix[1][1] = t;
		flowMatrix[2][0] = e+x+y;
		// write the edges between all x and s and all y and t
		int offset = e + 3;
		int counter = 1;
		int sroof = offset + x;
		for (int i = offset; i < sroof; i++)
		{
			flowMatrix[i][0] = s;
			flowMatrix[i][1] = counter;
			flowMatrix[i][2] = 1;
			counter++;
		}
		int stop = sroof + y;
		for (int i = sroof; i < stop; i++)
		{
			flowMatrix[i][0] = counter;
			flowMatrix[i][1] = t;
			flowMatrix[i][2] = 1;
			counter++;
		}
	}

	void writeFlowGraph()
	{
		int v = flowMatrix[0][0], e = flowMatrix[2][0], s = flowMatrix[1][0], t = flowMatrix[1][1];
		// Skriv ut antal hörn och kanter samt källa och sänka
		io.println(v);
		io.println(s + " " + t);
		io.println(e);
		for (int i = 3; i < e+3; ++i)
		{
			int a = flowMatrix[i][0], b = flowMatrix[i][1], c = flowMatrix[i][2];
			// Kant från a till b med kapacitet c
			io.println(a + " " + b + " " + c);
		}
		// Var noggrann med att flusha utdata när flödesgrafen skrivits ut!
		io.flush();
		// Debugutskrift
		System.err.println("Skickade iväg flödesgrafen");
	}

	void readMaxFlowSolution()
	{
		// Läs in antal hörn, kanter, källa, sänka, och totalt flöde
		// (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
		// skickade iväg)
		int v = io.getInt();
		int s = io.getInt();
		int t = io.getInt();
		int totflow = io.getInt();
		int e = io.getInt();
		flowMatrix[1][2] = totflow;
		flowMatrix[0][0] = v;
		flowMatrix[1][0] = s;
		flowMatrix[1][1] = t;
		flowMatrix[2][0] = e;
		
		for (int i = 3; i < e+3; ++i)
		{
			// Flöde f från a till b
			flowMatrix[i][0] = io.getInt();
			flowMatrix[i][1] = io.getInt();
			flowMatrix[i][2] = io.getInt();
		}
	}
	
	void writeBipMatchSolution()
	{
		int maxMatch = flowMatrix[1][2];
		int s = flowMatrix[1][0], t = flowMatrix[1][1], e = flowMatrix[2][0];
		

		// Skriv ut antal hörn och storleken på matchningen
		//io.println(x + " " + y);
		io.println(oldx + " " + oldy);
		io.println(maxMatch);

		for (int i = 3; i < e+3; i++)
		{
			if (flowMatrix[i][0] != s && flowMatrix[i][1] != t)
			{
				int a = flowMatrix[i][0], b = flowMatrix[i][1];
				// Kant mellan a och b ingår i vår matchningslösning
				io.println(a + " " + b);
			}
		}

	}

	BipRed()
	{
		io = new Kattio(System.in, System.out);

		readBipartiteGraph();

		writeFlowGraph();

		readMaxFlowSolution();

		writeBipMatchSolution();

		// debugutskrift
		System.err.println("Bipred avslutar\n");

		// Kom ihåg att stänga ner Kattio-klassen
		io.close();
	}

	public static void main(String args[])
	{
		//new BipRed();
		MaxFlow m = new MaxFlow();
		m.readFlow();
		m.findMaxFlow();
		m.printFlow();
	}
}