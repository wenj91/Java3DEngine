package object3D;

import java.util.ArrayList;

import math3D.Vector4D;

public class Object4D {
	public int id;
	public String name;
	public int state;
	public int attr;
	
	public int maxRadius;
	public int minRadius;
	
	public Vector4D pos;
	public Vector4D dir;                                                                                             
	
	public Vector4D ux, uy, uz;
	
	public int polyNum;
	public ArrayList<Poly4D> polyList = new ArrayList<Poly4D>();//组成object的面列表
	
	public int vertNums;
	public ArrayList<Vector4D> vertsLocal = new ArrayList<Vector4D>();//顶点列表
	public ArrayList<Vector4D> vertsTrans = new ArrayList<Vector4D>();//存储改变后的顶点
	

}
