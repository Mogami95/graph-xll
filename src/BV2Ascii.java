
/*
 * By ChaTo <chato@chato.cl>
 * 
 * ==================================================================
 * DESCRIPTION: prints the links on a webgraph 
 * Argument1: a base, e.g.: /data/spam/uk/uk-2006-05
 * (this means that there is a /data/spam/uk/uk-2006-05.properties file)
 *  ==================================================================
 *
 * Originally: it.unimi.webgraph.examples.OutdegreeStats
 *             by Paolo Boldi and Sebastiano Vigna
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 */


import it.unimi.dsi.webgraph.ImmutableGraph;
import it.unimi.dsi.webgraph.NodeIterator;

import java.lang.reflect.InvocationTargetException;
import java.io.*;

/*
 * The main method of this class loads an arbitrary {@link it.unimi.dsi.webgraph.ImmutableGraph}
 * and performs a sequential scan to establish the minimum, maximum and average outdegree.
 */

public class BV2Ascii {

	private BV2Ascii() {}

	static public void main( String arg[] ) throws ClassNotFoundException, IllegalArgumentException, SecurityException, IllegalAccessException, IOException {
		if ( arg.length != 1 ) {
			System.err.println( "Usage: BV2Ascii BASENAME" );
			return;
		}
		
		final ImmutableGraph graph = it.unimi.dsi.webgraph.ImmutableGraph.loadOffline( arg[0] );
		
		NodeIterator nodeIterator = graph.nodeIterator();
		int curr, d;
		int[] suc;
		BufferedOutputStream outStreamB = new BufferedOutputStream(System.out, 4096);
		PrintStream outStream = new PrintStream( outStreamB );

		int max_node_id = 0;
		long num_arcs = 0;
		while( nodeIterator.hasNext() ) {
			curr = nodeIterator.nextInt();
			d = nodeIterator.outdegree();
			suc = nodeIterator.successorArray();
		if ( curr > max_node_id ) {
			max_node_id = curr;
		}
		for ( int j=0; j<d; j++ ) {
			outStream.println( curr + "\t" + (suc[j]) );
		num_arcs++;
		}
		}
		outStream.flush();

		FileWriter statWriter = new FileWriter( arg[0] + ".dat" );
		statWriter.write( "# Directed graph\n" );
		statWriter.write( "# Nodes: " + max_node_id + " Edges: " + num_arcs + "\n" );
		statWriter.write( "# FromNodeId    ToNodeId\n" );
		statWriter.close();
	}
}
