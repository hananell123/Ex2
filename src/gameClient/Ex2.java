package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import myClasses.DWGraph_Algo;
import myClasses.DWGraph_DS;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * this is the main class of the game, in this class the game start running and the data updates
 */
class Ex2_Client implements Runnable {
	private static MyFrame _win;
	private static Arena _ar;
	private HashMap<Integer,HashMap<Integer,List<node_data>>>allPath=new HashMap<>();// all the shortest pathes in the game
	private  HashMap<Integer,HashMap<Integer,Double>>allPathDis=new HashMap<>();//
	private List<CL_Agent>AllAgent=new LinkedList<>();
	private DWGraph_Algo gg=new DWGraph_Algo();
	private HashMap<Integer,edge_data>srcList=new HashMap<>();
	private HashMap<Integer,geo_location>srcListLoc=new HashMap<>();
	private LinkedList<geo_location>locations=new LinkedList<>();
	private boolean[]GoingToEat;
	private int AvrgSpeed=1;
	private static int dt=140;
	private final double destFromPok=0.30;
	private GUILogIn myLoginFrame = new GUILogIn();
	private static String[]enter=new String[2];


	/**
	 * start of the game, can take data from the login screen or from the jar
	 * @param a
	 */
	public static void main(String[] a) {

            enter=a;
			Thread client = new Thread(new Ex2_Client());
			client.start();

	}


	/**
	 * this is the method that run the game thread
	 * using other methods to set the thread sleep time between moves
	 * and make the agent their next destination node
	 */
	@Override
	public void run() {
		int scenario_num=-1;
		int id=-1;


// log in from the jar(cmd)/ login screen
        if(enter.length>0){
        	scenario_num=Integer.parseInt(enter[1]);
        	id=Integer.parseInt(enter[0]);
		}
        else {
			myLoginFrame.run();
			 //id = myLoginFrame.getIDText();
			while (scenario_num == -1) {
				scenario_num = myLoginFrame.getGameLevel();
				id = myLoginFrame.getIDText();
			}
			myLoginFrame.closeFrame();
		}

		   game_service game = Game_Server_Ex2.getServer(scenario_num);
			game.login(id);

			try {
				init(game);
			} catch (IOException e) {
				e.printStackTrace();
			}
		System.out.println();
			game.startGame();
			_win.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString());
			int ind = 1;
			//dt = 120;
			int LastTime = 0;



			while (game.isRunning()) {
				_win.getTime(game);



				moveAgents(game, gg.getGraph());
				try {
					//if(ind%1==0) {
					_win.repaint();


					if(AvrgSpeed<2){
						dt=145;
					}
					else {
						int ReadyToEat = GoingToEat();
						if (ReadyToEat > 0) {
							//dt=900;
							dt = setDt();
							System.out.println("DT is: "+dt);
						}
						else {
							dt = 110;
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
	/**
	 * this is the inisilaize of the game, take the jason data from the server and create the graph
	 * update every pokemon his located edge
	 * set the agents next the pokemon with the high value
	 * set the data to the Arena vriable
	 *
	 * @param game
	 * @throws IOException
	 */
	private void init(game_service game) throws IOException {

		String g = game.getGraph();
		loadGraph(g,gg);
		setAllPath(gg);
		setAllPathDis(gg);
		nodeLocation(gg.getGraph());
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
			int gameLevel=GameServerInfo.getInt("game_level");
			_win.getLevel(gameLevel);
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
			//check if the graph connected or not if not put agent on random nodes
			if(gg.isConnected()) {
				for (int i = 0; i < AgentNum; i++) {
					game.addAgent(MaxPokValue[i].get_edge().getSrc());
					srcList.put(i, MaxPokValue[i].get_edge());
					srcListLoc.put(i, MaxPokValue[i].getLocation());
				}
			}
			else{
				while(a<AgentNum){
					int temp= (int) (Math.random()*20);
					if(gg.getGraph().getNode(temp)!=null){
						game.addAgent(temp);
						srcList.put(a,null);
						srcListLoc.put(a,null);
						a++;
					}

				}

			}
//			for(node_data n:gg.getGraph().getV()){
//				if(a<AgentNum){
//					CL_Agent cl=new CL_Agent(gg.getGraph(),n.getKey());
//					//LinkedList<node_data> l = new LinkedList<node_data>();
//					//l.add(n);
//					AllAgent.add(cl);
//					game.addAgent(n.getKey());
//					a++;
//					continue;
//				}
//				break;
//			}


		}
		catch (JSONException e) {e.printStackTrace();}
		AllAgent=Arena.getAgents(game.getAgents(), (directed_weighted_graph) gg.getGraph());
		_ar.setAgents(AllAgent);

	}


	/**
	 * set the next node for all the agent that located on node (not on edge).
	 * if agent stend on src node of pokemon the method set the next node to be the pokemon dest node
	 * else its call NextNode method
	 * @param game
	 * @param gg
	 */
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
				//if(agent.getLocation().distance(srcList.ge))
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


			}
		}
		AvrgSpeed=newSpeed/_ar._agents.size();
	}

	/**
	 * find for each agent the closest free pokemon and set the next node in this path to be 
	 * the agent next node
	 * @param g game graph
	 * @param agent given agent
	 * @return
	 */
	private  int nextNode(directed_weighted_graph g, CL_Agent agent){


		double currentDistance=Integer.MAX_VALUE;
		int dest=-1;
		for(CL_Pokemon p: _ar.getPokemons()) {

			double TempDistance = this.allPathDis.get(agent.getSrcNode()).get(p.get_edge().getSrc());
			if (TempDistance < currentDistance && TempDistance != -1) {
				if (srcList.values().contains(p.get_edge()) && srcList.get(agent.getID()) != p.get_edge()) continue;
				
				currentDistance = TempDistance;
				if (currentDistance == 0 ) {
					GoingToEat[agent.getID()]=true;
					srcList.put(agent.getID(),p.get_edge());
					srcListLoc.put(agent.getID(),p.getLocation());
					return p.get_edge().getDest();
				}
				srcList.put(agent.getID(), p.get_edge());
				srcListLoc.put(agent.getID(),p.getLocation());
			}
		}
		int src1=agent.getSrcNode();
		int dest1=srcList.get(agent.getID()).getSrc();
		LinkedList<node_data>answer=(LinkedList)allPath.get(src1).get(dest1);
		if(answer.size()>2) {
			LinkedList<node_data> temp = null;
			double minPath=Integer.MAX_VALUE;

			for(CL_Pokemon p: _ar.getPokemons()){
				if(allPathDis.get(answer.get(1).getKey()).get(p.get_edge().getSrc())<minPath){
					minPath=allPathDis.get(answer.get(1).getKey()).get(p.get_edge().getSrc());
					temp= (LinkedList<node_data>) allPath.get(answer.get(1).getKey()).get(p.get_edge().getSrc());
				}
			}
			if(temp.size()==1){
				System.out.println();
			}
			if(temp.size()>2) {
				if (temp.get(1).getKey() == answer.get(0).getKey()) {

					return temp.get(2).getKey();
				}
			}
		}
		return allPath.get(src1).get(dest1).get(1).getKey();
	}


	private void loadGraph(String g , DWGraph_Algo graph){


		try {
			PrintWriter pw = new PrintWriter(new File("gameGraph.json"));
			pw.write(g);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}
		graph.load("gameGraph.json");

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


	/**
	 * put in HashMap "allPath" all the pathes in the graph
	 * @param graphToPath
	 */
	public void setAllPath(dw_graph_algorithms graphToPath){
		for(node_data a:graphToPath.getGraph().getV()){
			allPath.put(a.getKey(),new HashMap<Integer, List<node_data>>());
			for(node_data b:graphToPath.getGraph().getV()){
				allPath.get(a.getKey()).put(b.getKey(),graphToPath.shortestPath(a.getKey(),b.getKey()));
			}
		}
	}
	/**
	 * put in HashMap "allPath" all the pathes size in the graph
	 * @param graphToPath
	 */
	public void setAllPathDis(dw_graph_algorithms graphToPath){
		for(node_data a:graphToPath.getGraph().getV()){
			allPathDis.put(a.getKey(),new HashMap<Integer, Double>());
			for(node_data b:graphToPath.getGraph().getV()){
				allPathDis.get(a.getKey()).put(b.getKey(),graphToPath.shortestPathDist(a.getKey(),b.getKey()));
			}
		}
	}

	/**
	 *
	 * @param g
	 */
	public void nodeLocation(directed_weighted_graph g){
		for(node_data n:g.getV()){
			locations.add(n.getLocation());
		}

	}

	/**
	 * find the high value poke in the start of the game
	 * @param p
	 * @param arr
	 */
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

	/**
	 * update if their is agent that on src edge of pokemon
	 * @return
	 */
		public int GoingToEat(){
		int counter=0;
			for(int i=0;i<GoingToEat.length;i++){
				if(GoingToEat[i]==true) counter++;
			}
			return counter;

		}

	/**
	 * set the best dt (thread sleep time) by the agents that "going to eat" distance from the pokemon
	 * @return
	 */
	public int setDt(){
		int answer=dt;
		double minDt=Integer.MAX_VALUE;
			for(CL_Agent agent: _ar._agents){
				if(srcList.get(agent.getID()).getSrc()==1){
					System.out.println();
				}
				if(GoingToEat[agent.getID()]==true){

					//double pokDis=srcListLoc.get(agent.getID()).distance(gg.getGraph().getNode(srcList.get(agent.getID()).getSrc()).getLocation());
					geo_location pokLocation= srcListLoc.get(agent.getID());
					//geo_location srcNodeLocation=gg.getGraph().getNode(srcList.get(agent.getID()).getSrc()).getLocation();
					geo_location srcNodeLocation= agent.getLocation();
					geo_location destNodeLocation=gg.getGraph().getNode(srcList.get(agent.getID()).getDest()).getLocation();

					double pokDis=pokLocation.distance(srcNodeLocation);
					int dis= (int) (pokDis/(agent.getSpeed()/(dt/1000)));
					System.out.println(((dis+1)*(agent.getSpeed()/(dt/1000)))-pokDis);
					if(agent.getLocation().distance(destNodeLocation)<pokLocation.distance(destNodeLocation)) continue;
					//if(agent.getLocation().distance(gg.getGraph().getNode(srcList.get(agent.getID()).getDest()).getLocation()))

					if(((dis+1)*(agent.getSpeed()/(dt/1000)))-pokDis>destFromPok) {
						for (int i = 80 ;i > 35; i--) {
							if (pokDis % (agent.getSpeed()/(dt/1000)) < destFromPok || (dis+1)*(agent.getSpeed()/(dt/1000))-pokDis < destFromPok) {
								if (answer > i) {
									answer = i;
									break;
								}
							}
						}
					}
				}
			}
			 return answer;


		}




}
