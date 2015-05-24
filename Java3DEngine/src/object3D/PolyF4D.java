package object3D;

import java.awt.Color;

import math3D.Vector4D;

public class PolyF4D {//复杂面模型, 开销会大些, 但是当作移动,旋转等操作不会破坏掉模型原有坐标系统
	public int state;
	public Color color;
	public int attr;
	
	public Vector4D[] vList = new Vector4D[3]; //顶点的坐标
	public Vector4D[] tvList = new Vector4D[3]; //变换后顶点的坐标
	
	public PolyF4D(){
		vList[0] = new Vector4D();
		vList[1] = new Vector4D();
		vList[2] = new Vector4D();
		
		tvList[0] = new Vector4D();
		tvList[1] = new Vector4D();
		tvList[2] = new Vector4D();
	}
}
