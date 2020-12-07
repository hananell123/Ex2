package gameClient;

import Server.Agent_Graph_Algo;
import Server.Game_Server_Ex2;
import api.game_service;
import api.edge_data;
import api.directed_weighted_graph;
//import Server.DWGraph;
import myClasses.DWGraph_Algo;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Ex2_Client implements Runnable{
	private static MyFrame _win;
	private static Arena _ar;
	public static void main(String[] a) {
		Thread client = new Thread(new Ex2_Client());
		client.start();
	}
	
	@Override
	public void run() {

		int scenario_num = 1;
		//int id = 999;

		game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
		//game.login(id);
		String g = game.getGraph();
		String pks = game.getPokemons();
		//System.out.println(pks);
		List<CL_Pokemon> pks1 = Agent_Graph_Algo.json2Pokemons(pks);

		DWGraph_Algo graph = new DWGraph_Algo();
		loadGraph(g , graph);
		// ? loadPks(pks , graph);

		init(game);
		String agents = game.getAgents();
		System.out.println(agents);
		game.startGame();
		_win.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
		int ind=0;
		long dt=100;
		
		while(game.isRunning()) {
			moveAgents(game, (directed_weighted_graph) graph);
			try {
				if(ind%1==0)
				{_win.repaint();}
				Thread.sleep(dt);
				ind++;
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		String res = game.toString();

		System.out.println(res);
		System.exit(0);
	}
	/** 
	 * Moves each of the robots along the edge, 
	 * in case the robot is on a node the next destination (next edge) is chosen (randomly).
	 * @param game
	 * @param gg
	 * @param
	 */
	private static void moveAgents(game_service game, directed_weighted_graph gg) {
		String lg = game.move();
		List<CL_Agent> log = Agent_Graph_Algo.getAgents(lg, gg);
		_ar.setAgents(log);
		//ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
		String fs = game.getPokemons();
		List<CL_Pokemon> ffs = Agent_Graph_Algo.json2Pokemons(fs);
		_ar.setPokemons(ffs);
		for(int i=0;i<log.size();i++) {
			CL_Agent robot = log.get(i);
			int id = robot.getID();
			int dest = robot.getNextNode();
			int src = robot.getSrcNode();
			double v = robot.getValue();
			if(dest==-1) {
				dest = nextNode(gg, src);
				game.chooseNextEdge(robot.getID(), dest);
				System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);
			}
		}
	}
	/**
	 * a very simple random walk implementation!
	 * @param g
	 * @param src
	 * @return
	 */

	private static int nextNode(directed_weighted_graph g, int src) {
		int ans = -1;
		Collection<edge_data> ee = g.getE(src);
		Iterator<edge_data> itr = ee.iterator();
		int s = ee.size();
		int r = (int)(Math.random()*s);

		//take a random edge
		int i=0;
		while(i<r) {itr.next();i++;}
		ans = itr.next().getDest();
		return ans;
	}
	private void init(game_service game) {
		
		String g = game.getGraph();
		String fs = game.getPokemons();

		//directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
		DWGraph_Algo myGraph = new DWGraph_Algo();
		loadGraph(g , myGraph);

		//DWGraph_Algo gg =  new DWGraph_Algo();
		//loadGraph(g ,gg);

		//gg.init(gg);

		_ar = new Arena();
		_ar.setGraph( myGraph.getGraph());
		_ar.setPokemons(Agent_Graph_Algo.json2Pokemons(fs));



		_win = new MyFrame("test Ex2");
		_win.update(_ar);
		_win.setSize(1000, 700);
	
		_win.show();
		String info = game.toString();
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("agents");
			System.out.println(info);
			System.out.println(game.getPokemons());
			int src_node = 0;  // arbitrary node, you should start at one of the fruits
			ArrayList<CL_Pokemon> cl_fs = Agent_Graph_Algo.json2Pokemons(game.getPokemons());
			for(int a = 0;a<cl_fs.size();a++) { Agent_Graph_Algo.updateEdge(cl_fs.get(a), myGraph.getGraph());}
			for(int a = 0;a<rs;a++) {
				int ind = a%cl_fs.size();
				CL_Pokemon c = cl_fs.get(ind);
				int nn = c.get_edge().getDest();
				if(c.getType()<0 ) {nn = c.get_edge().getSrc();}
				
				game.addAgent(nn);
			}
		}
		catch (JSONException e) {e.printStackTrace();}
		
	}

	private void loadGraph(String g , DWGraph_Algo graph){


		try {
			PrintWriter pw = new PrintWriter(new File("gameGraph.json"));
			pw.write(g);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}
		graph.load("C:\\Users\\Hodaya\\IdeaProjects\\ex2\\gameGraph.json");



	}

	private void loadPks(String Pks , DWGraph_Algo graph){
		try {
			PrintWriter pw = new PrintWriter(new File("gameGraph.json"));
			pw.write(Pks);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}
		graph.load("C:\\Users\\Hodaya\\IdeaProjects\\ex2\\gameGraph.json");




	}

}
