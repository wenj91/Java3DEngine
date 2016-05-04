package object3D;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import math3D.Vector4D;

public class Poly4D {//简单面模型,开销小,但是当移动,旋转等操作会破坏掉模型原来的坐标系统

	public int state;
	public Color color;
	public int attr;
	
	public List<Vector4D> vList = new ArrayList<Vector4D>(); //存储顶点的表
	public int[] index = new int[3]; //组成多边形的顶点在表中的序号
}
