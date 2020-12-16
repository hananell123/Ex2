package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import myClasses.DWGraph_Algo;
import myClasses.edge;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Ex2_Client implements Runnable {
	private static MyFrame _win;
	private static Arena _ar;
	private HashMap<Integer,HashMap<Integer,List<node_data>>>allPath=new HashMap<>();
	private HashMap<Integer,HashMap<Integer,Double>>allPathDis=new HashMap<>();
	private List<CL_Agent>AllAgent=new LinkedList<>();
	DWGraph_Algo gg=new DWGraph_Algo();
	HashMap<Integer,edge_data>srcList=new HashMap<>();
	LinkedList<geo_location>locations=new LinkedList<>();
	private boolean[]GoingToEat;
	private int AvrgSpeed=1;
	private game_service game=null;





	public static void main(String[] a) {

			Thread client = new Thread(new Ex2_Client());
			client.start();

	}


	
	@Override
	public void run() {
			int scenario_num = 11;
			 game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
//				int id = 999;
//				game.login();


			try {
				init(game);
			} catch (IOException e) {
				e.printStackTrace();
			}
		System.out.println();
			game.startGame();// start game!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			_win.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString());
			int ind = 1;
			long dt = 100;
			int LastTime = 17;
			boolean t = false;


			while (game.isRunning()) {
				_win.getTime(game);



				moveAgents(game, gg.getGraph());
				try {
					//if(ind%1==0) {
					_win.repaint();


					if(AvrgSpeed<3){
						dt=90;
					}
					else {
						int ReadyToEat = GoingToEat();
//						if (ReadyToEat > GoingToEat.length / 2) {
//							dt = 90;
//						}
						if (ReadyToEat > 0) {
							dt = 200;
						} else {
							dt = 200;

						}
					}
					Thread.sleep(dt);
					ind++;
				} catch (Exception e) {
					e.printStackTrace();
				}
				LastTime = (int) game.timeToEnd();
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
		int newSpeed=0;
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
			newSpeed+=agent.getSpeed();
			int dest;
			if(agent.get_curr_edge()==null){
				if(agent.getSrcNode()==srcList.get(agent.getID()).getDest()){
					GoingToEat[agent.getID()]=false;
				}

				if(srcList.get(agent.getID()).getSrc()== agent.getSrcNode()){
					dest=srcList.get(agent.getID()).getDest();
					GoingToEat[agent.getID()]=true;
				}
				else{
					dest=nextNode(gg,agent);
				}
				game.chooseNextEdge(agent.getID(),dest);
				//System.out.println("[Agent: "+agent.getID()+",current node:"+agent.getSrcNode()+"]"+"go to edge "+srcList.get(agent.getID()).getSrc()+"----->"+srcList.get(agent.getID()).getDest()+" and the next node is: "+dest);


			}
		}
		AvrgSpeed=newSpeed/_ar._agents.size();
	}//check!!!
	private  int nextNode(directed_weighted_graph g, CL_Agent agent){

		double currentDistance=Integer.MAX_VALUE;
		int dest=-1;
		for(CL_Pokemon p: _ar.getPokemons()) {
			double TempDistance = this.allPathDis.get(agent.getSrcNode()).get(p.get_edge().getSrc());
			if (TempDistance < currentDistance && TempDistance != -1) {
				if (srcList.values().contains(p.get_edge()) && srcList.get(agent.getID()) != p.get_edge()) continue;


				currentDistance = TempDistance;
				if (currentDistance == 0) {
					return p.get_edge().getDest();
				}
				srcList.put(agent.getID(), p.get_edge());
			}
		}


		int src1=agent.getSrcNode();
		int dest1=srcList.get(agent.getID()).getSrc();


		LinkedList<node_data>answer=(LinkedList)allPath.get(src1).get(dest1);
		if(answer.size()>2) {
			LinkedList<node_data> temp = (LinkedList) allPath.get(answer.get(1).getKey()).get(dest1);
			if (temp.get(1).getKey() == answer.get(0).getKey()) {

				return temp.get(2).getKey();
			}
		}


		return allPath.get(src1).get(dest1).get(1).getKey();
	}



	private void init(game_service game) throws IOException {

		String g = game.getGraph();
		loadGraph(g,gg);
		//LinkedList<node_data>check=(LinkedList<node_data>) gg.shortestPath(20,11);
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
			GoingToEat=new boolean[AgentNum];
			int src_node = 0;  // arbitrary node, you should start at one of the pokemon
			ArrayList<CL_Pokemon> AllPokemones= Arena.json2Pokemons(game.getPokemons());//pokemon list
			CL_Pokemon []MaxPokValue=new CL_Pokemon[AgentNum];
			for(int a = 0;a<AllPokemones.size();a++) { // pokemones size-->update edges
				Arena.updateEdge(AllPokemones.get(a),gg.getGraph());
				HighValuePok(AllPokemones.get(a),MaxPokValue);
			}
			int a=0;
			  // set agent to vertex
			for(int i=0;i<AgentNum;i++){
				game.addAgent(MaxPokValue[i].get_edge().getSrc());
				srcList.put(i,MaxPokValue[i].get_edge());
			}

//				for (CL_Pokemon p : AllPokemones) {//check connectivity!!!!!!!!!!!!!!!!!!!!!!!
//					if(a<AgentNum) {
//						game.addAgent(p.get_edge().getSrc());
//						srcList.put(a,p.get_edge());
//						//AllAgent.add();
//
//						//LinkedList<node_data> l = new LinkedList<node_data>();
//						//l.add(gg.getGraph().getNode(p.get_edge().getDest()));
//						a++;
//					}
//				}
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
	public void HighValuePok(CL_Pokemon p,CL_Pokemon[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == null) {
				arr[i] = p;
				break;
			}
			if (arr[i].getValue() < p.getValue()) {
				CL_Pokemon temp1 = arr[i];
				arr[i] = p;
				for (int j = i + 1; j < arr.length; j++) {
					CL_Pokemon temp2 = arr[j];
					arr[j] = temp1;
					temp1 = temp2;
				}
				break;
			}

		}
	}
		public int GoingToEat(){
		int counter=0;
			for(int i=0;i<GoingToEat.length;i++){
				if(GoingToEat[i]==true) counter++;
			}
			return counter;

		}




}
