package object3D;

import java.awt.Color;

import math3D.Vector4D;

public class PolyF4D {//������ģ��, �������Щ, ���ǵ����ƶ�,��ת�Ȳ��������ƻ���ģ��ԭ������ϵͳ
	public int state;
	public Color color;
	public int attr;
	
	public Vector4D[] vList = new Vector4D[3]; //���������
	public Vector4D[] tvList = new Vector4D[3]; //�任�󶥵������
	
	public PolyF4D(){
		vList[0] = new Vector4D();
		vList[1] = new Vector4D();
		vList[2] = new Vector4D();
		
		tvList[0] = new Vector4D();
		tvList[1] = new Vector4D();
		tvList[2] = new Vector4D();
	}
}
