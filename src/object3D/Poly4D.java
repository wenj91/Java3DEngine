package object3D;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import math3D.Vector4D;

public class Poly4D {//����ģ��,����С,���ǵ��ƶ�,��ת�Ȳ������ƻ���ģ��ԭ��������ϵͳ

	public int state;
	public Color color;
	public int attr;
	
	public List<Vector4D> vList = new ArrayList<Vector4D>(); //�洢����ı�
	public int[] index = new int[3]; //��ɶ���εĶ����ڱ��е����
}
