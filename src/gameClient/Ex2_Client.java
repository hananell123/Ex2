package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import myClasses.DWGraph_Algo;
import myClasses.edge;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Ex2_Client implements Runnable{
	private static MyFrame _win;
	private static Arena _ar;
	private HashMap<Integer,HashMap<Integer,List<node_data>>>allPath=new HashMap<>();
	private HashMap<Integer,HashMap<Integer,Double>>allPathDis=new HashMap<>();
	private List<CL_Agent>AllAgent=new LinkedList<>();
	DWGraph_Algo gg=new DWGraph_Algo();
	HashMap<Integer,edge_data>srcList=new HashMap<>();
	LinkedList<geo_location>locations=new LinkedList<>();


	public static void main(String[] a) {
		Thread client = new Thread(new Ex2_Client());
		client.start();
	}
	
	@Override
	public void run() {
		int scenario_num = 1;
		game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
	//	int id = 999;
	//	game.login(id);
		init(game);
		System.out.println();
		game.startGame();// start game!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		_win.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
		int ind=0;
		long dt=100;
		
		while(game.isRunning()) {
			moveAgents(game, gg.getGraph());
			try {
				if(ind%1==0) {_win.repaint();}
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
	//	private static void moveAgants(game_service game, directed_weighted_graph gg) {
//		String lg = game.move();
//		List<CL_Agent> log = Arena.getAgents(lg, gg);
//		_ar.setAgents(log);
//		//ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
//		String fs =  game.getPokemons();
//		List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
//		_ar.setPokemons(ffs);
//		for(int i=0;i<log.size();i++) {
//			CL_Agent ag = log.get(i);
//			int id = ag.getID();
//			int dest = ag.getNextNode();
//			int src = ag.getSrcNode();
//			double v = ag.getValue();
//			if(dest==-1) {
//				dest = nextNode(gg, src);
//				game.chooseNextEdge(ag.getID(), dest);
//				System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);
//			}
//		}
//	}
	public void moveAgents(game_service game, directed_weighted_graph gg) {
		String lg = game.move();
		List<CL_Agent> log = Arena.getAgents(lg, gg);
		_ar.setAgents(log);
		String fs =  game.getPokemons();
		List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
		_ar.setPokemons(ffs);
		for(CL_Pokemon p:ffs){
			Arena.updateEdge(p,gg);
		}
		for(CL_Agent agent:log){
			int dest;
			if(agent.get_curr_edge()==null){

				if(srcList.get(agent.getID()).getSrc()== agent.getSrcNode()){
					dest=srcList.get(agent.getID()).getDest();
				}
				else{
					dest=nextNode(gg,agent);
				}
				game.chooseNextEdge(agent.getID(),dest);
				//System.out.println("Agent "+agent.getID()+"go to "+dest);


			}
		}
	}
	private  int nextNode(directed_weighted_graph g, CL_Agent agent){
		double currentDistance=Integer.MAX_VALUE;
		int dest=-1;
		for(CL_Pokemon p: _ar.getPokemons()){
			double TempDistance=this.allPathDis.get(agent.getSrcNode()).get(p.get_edge().getSrc());

			if(TempDistance<currentDistance && TempDistance!=-1){
				currentDistance=TempDistance;
				if(currentDistance==0){
					return p.get_edge().getDest() ;
				}
				//dest=p.get_edge().getSrc();
				srcList.put(agent.getID(),p.get_edge());
			}

		}
		int src1=agent.getSrcNode();
		int dest1=srcList.get(agent.getID()).getSrc();


		LinkedList<node_data>answer=(LinkedList)allPath.get(agent.getSrcNode()).get(dest1);
		if(answer.size()>2) {
			LinkedList<node_data> temp = (LinkedList) allPath.get(answer.get(1).getKey()).get(dest1);
			if (temp.get(1).getKey() == answer.get(0).getKey()) {
				return temp.get(2).getKey();
			}
		}


		return allPath.get(src1).get(dest1).get(1).getKey();
	}



	private void init(game_service game) {

		String g = game.getGraph();
		loadGraph(g,gg);
		LinkedList<node_data>check=(LinkedList<node_data>) gg.shortestPath(20,11);
		setAllPath(gg);
		setAllPathDis(gg);
		nodeLocation(gg.getGraph());
		//String pks = game.getPokemons();
		String fs = game.getPokemons();

		_ar = new Arena();
		_ar.setGraph(gg.getGraph());
		_ar.setPokemons(Arena.json2Pokemons(fs));
		_win = new MyFrame("test Ex2");
		_win.setSize(1000, 700);
		_win.update(_ar);

		_win.show();
		String info = game.toString();
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject GameServerInfo = line.getJSONObject("GameServer");
			int AgentNum = GameServerInfo.getInt("agents");// number of
			int src_node = 0;  // arbitrary node, you should start at one of the pokemon
			ArrayList<CL_Pokemon> AllPokemones= Arena.json2Pokemons(game.getPokemons());//pokemon list
			for(int a = 0;a<AllPokemones.size();a++) { // pokemones size-->update edges
				Arena.updateEdge(AllPokemones.get(a),gg.getGraph());
			}
			int a=0;
			  // set agent to vertex
				for (CL_Pokemon p : AllPokemones) {//check connectivity!!!!!!!!!!!!!!!!!!!!!!!
					if(a<AgentNum) {
						game.addAgent(p.get_edge().getSrc());
						srcList.put(a,p.get_edge());
						//AllAgent.add();

						//LinkedList<node_data> l = new LinkedList<node_data>();
						//l.add(gg.getGraph().getNode(p.get_edge().getDest()));
						a++;
					}
				}
				for(node_data n:gg.getGraph().getV()){
					if(a<AgentNum){
						CL_Agent cl=new CL_Agent(gg.getGraph(),n.getKey());
						//LinkedList<node_data> l = new LinkedList<node_data>();
						//l.add(n);
						AllAgent.add(cl);
						game.addAgent(n.getKey());
						a++;
						continue;
					}
					break;
			}


		}
		catch (JSONException e) {e.printStackTrace();}
		AllAgent=Arena.getAgents(game.getAgents(), (directed_weighted_graph) gg.getGraph());
		_ar.setAgents(AllAgent);

	}

	private void loadGraph(String g , DWGraph_Algo graph){


		try {
			PrintWriter pw = new PrintWriter(new File("gameGraph.json"));
			pw.write(g);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}
		graph.load("C:\\Users\\hanan\\IdeaProjects\\ex2\\gameGraph.json");
	}
	/** 
	 * Moves each of the agents along the edge,
	 * in case the agent is on a node the next destination (next edge) is chosen (randomly).
	 * @param game
	 * @param gg
	 * @param
	 */

	/**
	 * a very simple random walk implementation!
	 * @param
	 * @param
	 * @return
	 */
//	private static int nextNode(directed_weighted_graph g, int src) {
//		int ans = -1;
//		Collection<edge_data> ee = g.getE(src);
//		Iterator<edge_data> itr = ee.iterator();
//		int s = ee.size();
//		int r = (int)(Math.random()*s);
//		int i=0;
//		while(i<r) {itr.next();i++;}
//		ans = itr.next().getDest();
//		return ans;
//	}


	public void setAllPath(dw_graph_algorithms graphToPath){
		for(node_data a:graphToPath.getGraph().getV()){
			allPath.put(a.getKey(),new HashMap<Integer, List<node_data>>());
			for(node_data b:graphToPath.getGraph().getV()){
				allPath.get(a.getKey()).put(b.getKey(),graphToPath.shortestPath(a.getKey(),b.getKey()));
			}
		}
	}
	public void setAllPathDis(dw_graph_algorithms graphToPath){
		for(node_data a:graphToPath.getGraph().getV()){
			allPathDis.put(a.getKey(),new HashMap<Integer, Double>());
			for(node_data b:graphToPath.getGraph().getV()){
				allPathDis.get(a.getKey()).put(b.getKey(),graphToPath.shortestPathDist(a.getKey(),b.getKey()));
			}
		}
	}
	public void nodeLocation(directed_weighted_graph g){
		for(node_data n:g.getV()){
			locations.add(n.getLocation());
		}

	}

}
