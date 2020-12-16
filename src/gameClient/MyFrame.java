package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BaseMultiResolutionImage;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import api.*;
import Server.Game_Server_Ex2;

/**
 * This class represents a very simple GUI class to present a
 * game on a graph - you are welcome to use this class - yet keep in mind
 * that the code is not well written in order to force you improve the
 * code and not to take it "as is".
 *
 */
public class MyFrame extends JFrame{
	private int _ind;
	private Arena _ar;
	private gameClient.util.Range2Range _w2f;
	private Image im;
	private Graphics gr;
	static int pokIndex = 0;
	static int AgentIndex = 0;

	//private Image im ;
	//private Graphics gr;
	private BufferedImage AgentIm0 = null;
//	 try {
//		AgentIm1 = ImageIO.read(new File("C:\\Users\\hanan\\IdeaProjects\\ex2\\src\\gameClient\\Graphic\\agent.png"));
//	} catch (IOException ex) {
//		// handle exception...
//	};
	private BufferedImage AgentIm1 = ImageIO.read(new File("src\\gameClient\\Graphic\\agent.png"));
	private BufferedImage pokIm1 = ImageIO.read(new File("src\\gameClient\\Graphic\\pok2.png"));
	private BufferedImage pokIm0  = ImageIO.read(new File("C:\\Users\\hanan\\IdeaProjects\\ex2\\src\\gameClient\\Graphic\\pok1.png"));;
	private BufferedImage background=null;
	private BufferedImage NodeIm1 = ImageIO.read(new File("C:\\Users\\hanan\\IdeaProjects\\ex2\\src\\gameClient\\Graphic\\node.png"));
	private static int timer;


	MyFrame(String a) throws IOException {
		super(a);
		int _ind = 0;

	}

	public void update(Arena ar) {
		this._ar = ar;
		updateFrame();


	}


	private void updateFrame() {



		Range rx = new Range(20,this.getWidth()-20);
		Range ry = new Range(this.getHeight()-10,150);
		Range2D frame = new Range2D(rx,ry);
		directed_weighted_graph g = _ar.getGraph();
		_w2f = Arena.w2f(g,frame);
	}
//	public void paint(Graphics g) {
//		int w = this.getWidth();
//		int h = this.getHeight();
//		g.clearRect(0, 0, w, h);
//		updateFrame();
//		drawPokemons(g);
//		drawGraph(g);
//		drawAgants(g);
//		drawInfo(g);
//
//	}
	public void getTime(game_service game){
		timer=(int) (game.timeToEnd()/1000);
	}

	public void paint(Graphics g){




		im = createImage(getWidth() , getHeight());
		gr = im.getGraphics();
		try {
			paintComponent(gr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(im , 0 , 0 , this);


	}

	public void paintComponent(Graphics g) throws IOException {


		int w = this.getWidth();
		int h = this.getHeight();
		g.clearRect(0, 0, w, h);
			updateFrame();
		drawPokemons(g);
		drawGraph(g);
		drawAgants(g);
		drawInfo(g);

	}
	private void drawInfo(Graphics g) {
		List<String> str = _ar.get_info();
		String dt = "none";
		for(int i=0;i<str.size();i++) {
			g.drawString(str.get(i)+" dt: "+dt,100,60+i*20);
		}
		
	}
	private void drawGraph(Graphics g) throws IOException {

//		try {
//			background = ImageIO.read(new File("C:\\Users\\hanan\\IdeaProjects\\ex2\\src\\gameClient\\Graphic\\backgroundLogo.png"));
//		} catch (IOException ex) {
//			// handle exception...
//		}

		directed_weighted_graph gg = _ar.getGraph();
		Iterator<node_data> iter = gg.getV().iterator();
		g.drawImage(background, 0, 0, null);

		while(iter.hasNext()) {
			node_data n = iter.next();
			g.setColor(Color.blue);
			drawNode(n,5,g);
			Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
			while(itr.hasNext()) {
				edge_data e = itr.next();
				g.setColor(Color.gray);
				drawEdge(e, g);
			}
		}
	}

	private void drawPokemons(Graphics g) {
		ImageObserver ob = new ImageIcon().getImageObserver();
		List<CL_Pokemon> fs = _ar.getPokemons();
		if(fs!=null) {
		Iterator<CL_Pokemon> itr = fs.iterator();
		int index=0;
		while(itr.hasNext()) {
			
			CL_Pokemon f = itr.next();
			Point3D c = f.getLocation();
			int r=10;
			g.setColor(Color.green);
			if(f.getType()<0) {g.setColor(Color.orange);}
			if(c!=null) {

				geo_location fp = this._w2f.world2frame(c);
				//g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
				//g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);

                if(f.getValue()<10) {
					g.drawImage(pokIm0, (int) fp.x() - r - 5, (int) fp.y() - r - 5, 2 * (r + 10), 2 * (r + 10), ob);
				}
                else {
					g.drawImage(pokIm1, (int) fp.x() - r - 20, (int) fp.y() - r - 20, 3 * (r + 10), 3 * (r + 10), ob);

				}
                if(f.get_edge()!=null)
					g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 25));
				g.drawString("distance from src "+f.get_edge().getSrc()+" is "+_ar.getGraph().getNode(f.get_edge().getSrc()).getLocation().distance(f.getLocation()),(int) fp.x() - r - 20, (int) fp.y() - r - 10);


			}
		}

		}
	}
	private void drawAgants(Graphics g) {
		g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, getWidth()/60));
		g.setColor(Color.BLUE);
		g.drawString("Score Board",40,getWidth()/20);
		g.setColor(Color.BLACK);

		//g.drawRect( 20,35,getWidth()/4,getHeight()/7);


		List<CL_Agent> fs = _ar.getAgents();
		g.drawImage(background, 0, 0, null);
		ImageObserver ob = new ImageIcon().getImageObserver();
		List<CL_Agent> rs = _ar.getAgents();
		int x=40;
		int y=getWidth()/20;
		if (rs != null) {
			for (CL_Agent Ag : rs) {
				g.setColor(Color.black);
				g.setFont(new Font("TimesRoman", Font.BOLD, getWidth()/80));
				g.drawString("Agent: "+Ag.getID()+" points: "+Ag.getValue()+" his speed is: "+Ag.getSpeed(), x,y+=25);
				g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, getWidth()/20));





				//Iterator<OOP_Point3D> itr = rs.iterator();
				g.setColor(Color.red);
				int i = 0;
				while (rs != null && i < rs.size()) {
					geo_location c = rs.get(i).getLocation();
					int r = 8;
					i++;
					if (c != null) {

						geo_location fp = this._w2f.world2frame(c);
						//g.fillOval((int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r);
						//AgentIm = pokImages[0];
						g.drawImage(AgentIm1,(int) fp.x() - r-20, (int) fp.y() - r-10, 3 * (r+10), 3 * (r+10) , ob );
						//g.drawString(""+(int)Ag.getValue(), (int) fp.x() - r, (int) fp.y() - r+2);



					}
				}
			}
		}

        if(timer>5) {
        	g.setColor(Color.black);
			g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, getWidth()/35));
			g.drawString("Remaining time "+timer , (int) (getWidth()/2.5),getWidth()/19);


		}
        else if(timer<=5&&timer!=0){
			g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, getWidth()/6));
			g.setColor(Color.red);
			g.drawString(""+timer, getWidth()/3, (int) (getHeight()/1.8));
		}
        else{
			g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, getWidth()/8));
			g.setColor(Color.red);
			g.drawString("GAME OVER", 20, (int) (getHeight()/1.5));
		}
	}
	private void drawNode(node_data n, int r, Graphics g) throws IOException {


		ImageObserver ob = new ImageIcon().getImageObserver();


		geo_location pos = n.getLocation();
		geo_location fp = this._w2f.world2frame(pos);
		//g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
		g.drawImage(NodeIm1,(int)fp.x()-r-10,(int)fp.y()-r-5,4*r+5,5*r,this);
		g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-4*r);
//		g.drawImage(NodeIm1,(int) fp.x() - r, (int) fp.y() - r, 2 * (r+10), 2 * (r+10) , ob );

	}
	private void drawEdge(edge_data e, Graphics g)  {
		directed_weighted_graph gg = _ar.getGraph();
		geo_location s = gg.getNode(e.getSrc()).getLocation();
		geo_location d = gg.getNode(e.getDest()).getLocation();
		geo_location s0 = this._w2f.world2frame(s);
		geo_location d0 = this._w2f.world2frame(d);
		g.drawLine((int)s0.x(), (int)s0.y(), (int)d0.x(), (int)d0.y());
		g.drawLine((int)s0.x()+1, (int)s0.y(), (int)d0.x()+1, (int)d0.y());
		g.drawLine((int)s0.x()+2, (int)s0.y(), (int)d0.x()+2, (int)d0.y());
		g.drawLine((int)s0.x()+3, (int)s0.y(), (int)d0.x()+3, (int)d0.y());


		//	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
	}
}
