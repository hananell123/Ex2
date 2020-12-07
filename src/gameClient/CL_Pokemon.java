package gameClient;

import api.edge_data;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gameClient.util.Point3D;

import java.io.FileReader;
import java.util.ArrayList;

public class CL_Pokemon {
	private edge_data _edge;
	private double _value;
	private int _type;
	private Point3D _pos;
	private double min_dist;
	private int min_ro;
	static public ArrayList<CL_Pokemon> arr;
	
	public CL_Pokemon(Point3D p, int t, double v, double s, edge_data e) {
		_type = t;
	//	_speed = s;
		_value = v;
		set_edge(e);
		_pos = p;
		min_dist = -1;
		min_ro = -1;
	}

//	public CL_Pokemon(){
//
//		_type = 0;
//		//	_speed = s;
//		_value = 0;
//		set_edge(null);
//		//_pos = 0;
//		min_dist = -1;
//		min_ro = -1;
//
//	}
	public static CL_Pokemon init_from_json(String json) {
		CL_Pokemon ans = null;
		try {
			GsonBuilder builder = new GsonBuilder();
			//builder.registerTypeAdapter(new ArrayList<CL_Pokemon> ,PokJasonDes );
			Gson gson = builder.create();

			FileReader reader = new FileReader(json);
			//arr = gson.fromJson(reader, CL_Pokemon);

		} catch (Exception e) {
			e.printStackTrace();

		}



		return ans;
	}
	public String toString() {return "F:{v="+_value+", t="+_type+"}";}
	public edge_data get_edge() {

		return _edge;
	}

	public void set_edge(edge_data _edge) {
		this._edge = _edge;

	}

	public Point3D getLocation() {
		return _pos;
	}
	public int getType() {return _type;}
//	public double getSpeed() {return _speed;}
	public double getValue() {return _value;}

	public double getMin_dist() {
		return min_dist;
	}

	public void setMin_dist(double mid_dist) {
		this.min_dist = mid_dist;
	}

	public int getMin_ro() {
		return min_ro;
	}

	public void setMin_ro(int min_ro) {
		this.min_ro = min_ro;
	}
}
