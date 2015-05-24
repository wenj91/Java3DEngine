package pipeline3D;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main3D.Const3D;
import math3D.Matrix4x4;
import math3D.Vector4D;
import object3D.Camera4D;
import object3D.Object4D;
import object3D.Poly4D;
import object3D.PolyF4D;
import object3D.RenderList4D;

public class Pipeline {

	public static void resetRenderList4D(RenderList4D rList) {
		rList.setPolyNum(0);
	}

	public static int insertPolyRenderList4D(RenderList4D rl, Poly4D poly) {

		int count = rl.getPolyNum();
		ArrayList<PolyF4D> pl = rl.getPolyList();

		if (count >= Const3D.RENDERLIST4DV1_MAX_POLYS) {
			System.out.println("It is over for RenderList!");
			return 0;
		}

		PolyF4D pv1 = new PolyF4D();
		pv1.attr = poly.attr;
		pv1.color = poly.color;
		pv1.state = poly.state;

		pv1.vList[0] = poly.vList.get(poly.index[0]);
		pv1.vList[1] = poly.vList.get(poly.index[1]);
		pv1.vList[2] = poly.vList.get(poly.index[2]);

		pl.add(pv1);
		rl.setPolyNum(count + 1);

		return 1;


	} // end Insert_POLY4DV1_RENDERLIST4DV1

	public static int insertPolyF4DRenderList4D(RenderList4D rl, PolyF4D poly) {
		int count = rl.getPolyNum();
		if (count >= Const3D.RENDERLIST4DV1_MAX_POLYS) {
			System.out.println("It is over for RenderList!");
			return 0;
		}

		ArrayList<PolyF4D> pl = rl.getPolyList();

		pl.add(poly);
		rl.setPolyNum(count + 1);

		return 1;

	}

	// 变换函数
	public static void transformRenderList4DV1(RenderList4D rList, Matrix4x4 m,
			int coorSelect) {
		ArrayList<PolyF4D> pl = rList.getPolyList();

		switch (coorSelect) {
		case Const3D.TRANSFORM_LOCAL_ONLY:
			for (int index = 0; index < rList.getPolyNum(); index++) {
				PolyF4D pv = pl.get(index);

				if ((null == pv)
						|| !(pv.state == Const3D.POLY4DV1_STATE_ACTIVE)
						|| (pv.state == Const3D.POLY4DV1_STATE_CLIPPED)
						|| (pv.state == Const3D.POLY4DV1_STATE_BACKFACE)) {
					continue;
				}

				for (int i = 0; i < 3; i++) {
					pv.vList[i] = pv.vList[i].vector4DMulMatrix4x4(m);
				}
			}
			break;

		case Const3D.TRANSFORM_LOCAL_TO_TRANS:

			for (int index = 0; index < rList.getPolyNum(); index++) {
				PolyF4D pv = pl.get(index);

				if ((null == pv)
						|| !(pv.state == Const3D.POLY4DV1_STATE_ACTIVE)
						|| (pv.state == Const3D.POLY4DV1_STATE_CLIPPED)
						|| (pv.state == Const3D.POLY4DV1_STATE_BACKFACE)) {
					continue;
				}

				for (int i = 0; i < 3; i++) {
					pv.tvList[i] = pv.vList[i].vector4DMulMatrix4x4(m);
				}
			}
			break;

		case Const3D.TRANSFORM_TRANS_ONLY:

			for (int index = 0; index < rList.getPolyNum(); index++) {
				PolyF4D pv = pl.get(index);

				if ((null == pv)
						|| !(pv.state == Const3D.POLY4DV1_STATE_ACTIVE)
						|| (pv.state == Const3D.POLY4DV1_STATE_CLIPPED)
						|| (pv.state == Const3D.POLY4DV1_STATE_BACKFACE)) {
					continue;
				}

				for (int i = 0; i < 3; i++) {
					pv.tvList[i] = pv.tvList[i].vector4DMulMatrix4x4(m);
				}
			}
			break;
		default:
			break;

		}

	}

	public static void transformObject4D(Object4D obj, Matrix4x4 transType,
			int coorSelect, int transformBasis) {

		switch (coorSelect) {
		case Const3D.TRANSFORM_LOCAL_ONLY:
			for (int i = 0; i < obj.vertNums; i++) {
				Vector4D v  = obj.vertsLocal.get(i).vector4DMulMatrix4x4(transType);
				obj.vertsLocal.set(i, v);
			}
			break;

		case Const3D.TRANSFORM_LOCAL_TO_TRANS:
			for (int i = 0; i < obj.vertNums; i++) {
					Vector4D v  = obj.vertsLocal.get(i).vector4DMulMatrix4x4(transType);
					obj.vertsTrans.set(i, v);
			}
			break;

		case Const3D.TRANSFORM_TRANS_ONLY:
			for (int i = 0; i < obj.vertNums; i++) {
				Vector4D v  = obj.vertsTrans.get(i).vector4DMulMatrix4x4(transType);
				obj.vertsTrans.set(i, v);
			}
			break;

		default:
			break;
		}

	}// transforBasis指定是否对朝向向量进行变换

	public static void modelToWorldObject4D(Object4D obj, int coorSelect) {

		if (coorSelect == Const3D.TRANSFORM_LOCAL_TO_TRANS) {
			for (int i = 0; i < obj.vertNums; i++) {
				Vector4D v  = obj.vertsLocal.get(i).vector4DAdd(obj.pos);
				obj.vertsTrans.set(i, v);
			}
		} else { // TRANSFORM_TRANS_ONLY
			for (int i = 0; i < obj.vertNums; i++) {
				Vector4D v  = obj.vertsTrans.get(i).vector4DAdd(obj.pos);
				obj.vertsTrans.set(i, v);
			}
		}

	}

	public static Matrix4x4 buildModelToWorldMatrix4x4(Vector4D vPos) {
		Matrix4x4 m = new Matrix4x4(
				1, 0, 0, 0, 
				0, 1, 0, 0,
				0, 0, 1, 0, 
				vPos.x,	vPos.y, vPos.z, 1);

		return m;
	}

	public static void modelToWorldRenderList4DV1(RenderList4D rList,
			Vector4D worldPos, int coorSelect) {
		ArrayList<PolyF4D> pl = rList.getPolyList();

		if (coorSelect == Const3D.TRANSFORM_LOCAL_TO_TRANS) {
			for (int index = 0; index < rList.getPolyNum(); index++) {
				PolyF4D pv = pl.get(index);

				if ((null == pv)
						|| !(pv.state == Const3D.POLY4DV1_STATE_ACTIVE)
						|| (pv.state == Const3D.POLY4DV1_STATE_CLIPPED)
						|| (pv.state == Const3D.POLY4DV1_STATE_BACKFACE)) {
					continue;
				}

				for (int i = 0; i < 3; i++) {
					pv.tvList[i] = pv.vList[i].vector4DAdd(worldPos);
				}
			}
		} else {
			for (int index = 0; index < rList.getPolyNum(); index++) {
				PolyF4D pv = pl.get(index);

				if ((null == pv)
						|| !(pv.state == Const3D.POLY4DV1_STATE_ACTIVE)
						|| (pv.state == Const3D.POLY4DV1_STATE_CLIPPED)
						|| (pv.state == Const3D.POLY4DV1_STATE_BACKFACE)) {
					continue;
				}

				for (int i = 0; i < 3; i++) {
					pv.tvList[i] = pv.tvList[i].vector4DAdd(worldPos);
				}
			}
		}
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void worldToCameraObject4D(Object4D obj, Camera4D c) {
		for (int i = 0; i < obj.vertNums; i++) {
			Vector4D v  = obj.vertsTrans.get(i).vector4DMulMatrix4x4(c.getMcam());
			obj.vertsTrans.set(i, v);
		}
	}

	public static void worldToCameraRenderList4D(RenderList4D rList,
			Camera4D c) {
		for (int index = 0; index < rList.getPolyNum(); index++) {
			PolyF4D p = rList.getPolyList().get(index);
/*
			if ((null == p) || (p.state != Const3D.POLY4DV1_STATE_ACTIVE)
					|| (p.state == Const3D.POLY4DV1_STATE_CLIPPED)
					|| (p.state == Const3D.POLY4DV1_STATE_BACKFACE)) {
				continue;
			}
			*/

			for (int i = 0; i < 3; i++) {
				p.tvList[i] = p.tvList[i].vector4DMulMatrix4x4(c.getMcam());
			}
		}
	}

	// 暂时不用这个内容
	int cullObject4D(Object4D o, Camera4D c, int cullFlags) { // 剔除物体

		return 0;
	}

	// 这个方法是用来内存管理的,在Java中不
	public static void resetObject4D(Object4D o) {

	}

	public static void removeBackfacesObject4D(Object4D o, Camera4D c) { // 物体背面消除
		// 有待添加
		if(null != o){
			List<Poly4D> pL = o.polyList;
			for(Iterator<Poly4D> it=pL.iterator(); it.hasNext(); ){
				Poly4D p = it.next();
				p.state = Const3D.POLY4DV1_STATE_ACTIVE;
				if (null == p || !(p.state == Const3D.POLY4DV1_STATE_ACTIVE)
						|| (p.state == Const3D.POLY4DV1_STATE_BACKFACE)
						|| (p.state == Const3D.POLY4DV1_STATE_CLIPPED)) {
					continue;
				}
					
				Vector4D vn = p.vList.get(p.index[0]).vector4DBulid(p.vList.get(p.index[1]));
				Vector4D vv = p.vList.get(p.index[0]).vector4DBulid(p.vList.get(p.index[2]));
				Vector4D vd = vn.vector4DCross(vv);//向量叉乘
				
				//创建由视点指向相机的向量
				Vector4D vc = p.vList.get(p.index[0]).vector4DBulid(c.getPos());
				
				c.getPos().vector4DPrint();
				p.vList.get(p.index[0]).vector4DPrint();
				p.vList.get(p.index[1]).vector4DPrint();
				p.vList.get(p.index[2]).vector4DPrint();
				
				float result = vc.vector4DDot(vd);
				if(result <= 0){
					p.state = Const3D.POLY4DV1_STATE_BACKFACE;
				}
			}
		}
	}

	public void removeBackefacesRenderList4DV1(RenderList4D rList, Camera4D c) {
		// 有待添加
	}

	// 相机到透视坐标的变换

	public Matrix4x4 buildCameraToPerspectiveMatrix4x4(Camera4D c) {
		Matrix4x4 m = new Matrix4x4(
				c.getViewDist(), 0, 0, 0, 
				0, c.getViewDist()* c.getAspectRatio(), 0, 0, 
				0, 0, 1, 1, 
				0, 0, 0, 0);

		return m;
	}

	public static void cameraFromHomogeneous4DObject4D(Object4D o) {
		
	}

	public static void cameraToPerspectiveRenderList4D(RenderList4D rList,
			Camera4D c) {

		for (int index = 0; index < rList.getPolyNum(); index++) {
			PolyF4D p = rList.getPolyList().get(index);
			if (null == p || !(p.state == Const3D.POLY4DV1_STATE_ACTIVE)
					|| (p.state == Const3D.POLY4DV1_STATE_BACKFACE)
					|| (p.state == Const3D.POLY4DV1_STATE_CLIPPED)) {

				continue;
			}

			for (int i = 0; i < 3; i++) {
				float z = p.tvList[i].z;
				p.tvList[i].x = (float) c.getViewDist() * p.tvList[i].x / z;
				p.tvList[i].y = (float) c.getViewDist() * p.tvList[i].y / z;
			}
		}
	}
	
	public static void cameraToPerspectiveObject4D(Object4D obj,
			Camera4D c) {

//		for (int i = 0; i < obj.polyNum; i++) {
//			Poly4D p = obj.polyList.get(i);
//			if (null == p || !(p.state == Const3D.POLY4DV1_STATE_ACTIVE)
//					|| (p.state == Const3D.POLY4DV1_STATE_BACKFACE)
//					|| (p.state == Const3D.POLY4DV1_STATE_CLIPPED)) {
//
//				continue;
//			}
//
//			for (int j = 0; j < 3; j++) {
//				int index = p.index[j];
//				
//				float z = (float)p.vList.get(index).z;
//				p.vList.get(index).x = (float) c.viewDist * p.vList.get(index).x / z;
//				p.vList.get(index).y = (float) c.viewDist * p.vList.get(index).y / z;
//			}
//		}
		
		for(int index=0; index<obj.vertNums; index++){
			float z = (float)obj.vertsTrans.get(index).z;
			
			obj.vertsTrans.get(index).x = (float)obj.vertsTrans.get(index).x*c.getViewDist()/z;
			obj.vertsTrans.get(index).y = (float)obj.vertsTrans.get(index).y*c.getViewDist()/z;
		}
	}

	// 待定---
	public static void convertFromHomogeneous4DRenderList4D(RenderList4D rList) {
	
	}

	// 透视坐标到屏幕坐标的变换
	public static void perspectiveToScreenObject4D(Object4D obj, Camera4D c) {
		float a = (0.5f * c.getViewportWidth() - 0.5f);
		float b = (0.5f * c.getViewportHeight() - 0.5f);
		
		for (int i = 0; i < obj.vertNums; i++) {
			Vector4D v  = obj.vertsTrans.get(i);
			v.x = a + a	* v.x;
			v.y = b - b * v.y;
			obj.vertsTrans.set(i, v);
		}
	}

	public static Matrix4x4 buildPerspectiveToScreen4DMatrix4x4(Camera4D c) {
		Matrix4x4 m;
		float a = (0.5f * c.getViewportWidth() - 0.5f);
		float b = (0.5f * c.getViewportHeight() - 0.5f);

		m = new Matrix4x4(a, 0, 0, 0, 0, -b, 0, 0, a, b, 1, 0, 0, 0, 0, 1);

		return m;
	}

	public static void perspectiveToScreenRenderList4D(RenderList4D rList,
			Camera4D c) {

		for (int index = 0; index < rList.getPolyNum(); index++) {
			PolyF4D p = rList.getPolyList().get(index);
			if (null == p || !(p.state == Const3D.POLY4DV1_STATE_ACTIVE)
					|| (p.state == Const3D.POLY4DV1_STATE_BACKFACE)
					|| (p.state == Const3D.POLY4DV1_STATE_CLIPPED)) {
				continue;
			}

			float a = (0.5f * c.getViewportWidth() - 0.5f);
			float b = (0.5f * c.getViewportHeight() - 0.5f);

			for (int i = 0; i < 3; i++) {
				p.tvList[i].x = a + a * p.tvList[i].x;
				p.tvList[i].y = b - b * p.tvList[i].y;
			}
		}

	}

	// 合并透视变换和屏幕变换/////待實現.........
	public static void cameraToPerspectiveScreenObject4D(Object4D obj,
			Camera4D c) {
//		float a = (float) (0.5f * c.viewportWidth - 0.5f);
//		float b = (float) (0.5f * c.viewportHeight - 0.5f);
//
//		for (int i = 0; i < obj.polyNum; i++) {
//			float z0 = obj.polyList.get(i).tvList[0].z;
//			float z1 = obj.polyList.get(i).tvList[1].z;
//			float z2 = obj.polyList.get(i).tvList[2].z;
//
//			obj.polyList.get(i).tvList[0].x = c.viewDist
//					* (obj.polyList.get(i).tvList[0]).x / z0;
//			obj.polyList.get(i).tvList[0].y = c.viewDist
//					* (obj.polyList.get(i).tvList[0]).y * c.aspectRatio / z0;
//
//			obj.polyList.get(i).tvList[1].x = c.viewDist
//					* (obj.polyList.get(i).tvList[1]).x / z1;
//			obj.polyList.get(i).tvList[1].y = c.viewDist
//					* (obj.polyList.get(i).tvList[1]).y * c.aspectRatio / z1;
//
//			obj.polyList.get(i).tvList[2].x = c.viewDist
//					* (obj.polyList.get(i).tvList[2]).x / z2;
//			obj.polyList.get(i).tvList[2].y = c.viewDist
//					* (obj.polyList.get(i).tvList[2]).y * c.aspectRatio / z2;
//
//			obj.polyList.get(i).tvList[0].x = obj.polyList.get(i).tvList[0].x
//					+ a;
//			obj.polyList.get(i).tvList[0].y = obj.polyList.get(i).tvList[0].y
//					+ b;
//
//			obj.polyList.get(i).tvList[1].x = obj.polyList.get(i).tvList[1].x
//					+ a;
//			obj.polyList.get(i).tvList[1].y = obj.polyList.get(i).tvList[1].y
//					+ b;
//
//			obj.polyList.get(i).tvList[2].x = obj.polyList.get(i).tvList[2].x
//					+ a;
//			obj.polyList.get(i).tvList[2].y = obj.polyList.get(i).tvList[2].y
//					+ b;
	//	}
	}

	public static void cameraToPerspectiveScreenRenderList4D(
			RenderList4D rList, Camera4D c) {
		
	}

	// 渲染几何体
	public static void drawObject4D(Object4D obj, Graphics g) {
		for (int i = 0; i < obj.polyNum; i++) {
			Poly4D p = obj.polyList.get(i);
			if (!(p.state == Const3D.POLY4DV1_STATE_ACTIVE)
					|| (p.state == Const3D.POLY4DV1_STATE_BACKFACE)
					|| (p.state == Const3D.POLY4DV1_STATE_CLIPPED)) {
				continue;
			}

			int index0 = p.index[0];
			int index1 = p.index[1];
			int index2 = p.index[2];
			Vector4D v0 = p.vList.get(index0);
			Vector4D v1 = p.vList.get(index1);
			Vector4D v2 = p.vList.get(index2);
			
			Color c = g.getColor();
			g.setColor(p.color);
			g.drawLine((int) v0.x, (int) v0.y, (int) v1.x, (int) v1.y);
			g.drawLine((int) v1.x, (int) v1.y, (int) v2.x, (int) v2.y);
			g.drawLine((int) v2.x, (int) v2.y, (int) v0.x, (int) v0.y);

			g.setColor(c);
		}
	}

	public static void drawRenderList4D(RenderList4D rList, Graphics g) {
		ArrayList<PolyF4D> pl = rList.getPolyList();
		for (int index = 0; index < rList.getPolyNum(); index++) {
			PolyF4D p = pl.get(index);
			if (!(p.state == Const3D.POLY4DV1_STATE_ACTIVE)
					|| (p.state == Const3D.POLY4DV1_STATE_BACKFACE)
					|| (p.state == Const3D.POLY4DV1_STATE_CLIPPED)) {
				continue;
			}

			Color c = g.getColor();
			g.setColor(p.color);
			g.drawLine((int) p.tvList[0].x, (int) p.tvList[0].y,
					(int) p.tvList[1].x, (int) p.tvList[1].y);
			g.drawLine((int) p.tvList[1].x, (int) p.tvList[1].y,
					(int) p.tvList[2].x, (int) p.tvList[2].y);
			g.drawLine((int) p.tvList[2].x, (int) p.tvList[2].y,
					(int) p.tvList[0].x, (int) p.tvList[0].y);

			g.setColor(c);

		}
	}

	public static Matrix4x4 buildXYZRotationMatrix4x4(float thetaX, // euler
																	// angles
			float thetaY, float thetaZ) {// output

		Matrix4x4 mRot = new Matrix4x4(), mX, mY, mZ;
		float sinTheta = 0.0f, cosTheta = 0.0f;
		int rotSeq = 0; // 1 for x axis, 2 for y, 4 for z;
		mRot.identity();

		if (Math.abs(thetaX) > Const3D.EPSILON_E5) {
			rotSeq |= 1;
		}
		if (Math.abs(thetaY) > Const3D.EPSILON_E5) {
			rotSeq |= 2;
		}
		if (Math.abs(thetaZ) > Const3D.EPSILON_E5) {
			rotSeq |= 4;
		}

		switch (rotSeq) {
		case 0:
			break;
		case 1:
			cosTheta = (float)Math.cos(Math.toRadians(thetaX));
			sinTheta = (float)Math.sin(Math.toRadians(thetaX));
			
			mX = new Matrix4x4(
					1, 0, 0, 0, 
					0, cosTheta, sinTheta, 0,
					0, -sinTheta, cosTheta, 0,
					0, 0, 0, 1);
			mRot = mRot.matrix4x4Mul(mX);
			break;
		case 2:
			cosTheta = (float)Math.cos(Math.toRadians(thetaY));
			sinTheta = (float)Math.sin(Math.toRadians(thetaY));
			
			mY = new Matrix4x4(
					cosTheta, 0, -sinTheta, 0,
					0, 1, 0, 0,
					sinTheta, 0, cosTheta, 0,
					0, 0, 0, 1);
			mRot = mRot.matrix4x4Mul(mY);
			break;
		case 3:
			cosTheta = (float)Math.cos(Math.toRadians(thetaX));
			sinTheta = (float)Math.sin(Math.toRadians(thetaX));
			
			mX = new Matrix4x4(
					1, 0, 0, 0, 
					0, cosTheta, sinTheta, 0,
					0, -sinTheta, cosTheta, 0,
					0, 0, 0, 1);
			mRot = mRot.matrix4x4Mul(mX);
			
			cosTheta = (float)Math.cos(Math.toRadians(thetaY));
			sinTheta = (float)Math.sin(Math.toRadians(thetaY));
			
			mY = new Matrix4x4(
					cosTheta, 0, -sinTheta, 0,
					0, 1, 0, 0,
					sinTheta, 0, cosTheta, 0,
					0, 0, 0, 1);
			mRot = mRot.matrix4x4Mul(mY);
			break;
		case 4:
			cosTheta = (float)Math.cos(Math.toRadians(thetaZ));
			sinTheta = (float)Math.sin(Math.toRadians(thetaZ));
			
			mZ = new Matrix4x4(
					cosTheta, sinTheta, 0, 0,
					-sinTheta, cosTheta, 0, 0,
					0, 0, 1, 0,
					0, 0, 0, 1);
			mRot = mRot.matrix4x4Mul(mZ);
			break;
		case 5:
			cosTheta = (float)Math.cos(Math.toRadians(thetaX));
			sinTheta = (float)Math.sin(Math.toRadians(thetaX));
			
			mX = new Matrix4x4(
					1, 0, 0, 0, 
					0, cosTheta, sinTheta, 0,
					0, -sinTheta, cosTheta, 0,
					0, 0, 0, 1);
			mRot = mRot.matrix4x4Mul(mX);
			
			cosTheta = (float)Math.cos(Math.toRadians(thetaZ));
			sinTheta = (float)Math.sin(Math.toRadians(thetaZ));
			
			mZ = new Matrix4x4(
					cosTheta, sinTheta, 0, 0,
					-sinTheta, cosTheta, 0, 0,
					0, 0, 1, 0,
					0, 0, 0, 1);
			mRot = mRot.matrix4x4Mul(mZ);
			break;
		case 6:
			cosTheta = (float)Math.cos(Math.toRadians(thetaY));
			sinTheta = (float)Math.sin(Math.toRadians(thetaY));
			
			mY = new Matrix4x4(
					cosTheta, 0, -sinTheta, 0,
					0, 1, 0, 0,
					sinTheta, 0, cosTheta, 0,
					0, 0, 0, 1);
			mRot = mRot.matrix4x4Mul(mY);
			
			cosTheta = (float)Math.cos(Math.toRadians(thetaZ));
			sinTheta = (float)Math.sin(Math.toRadians(thetaZ));
			
			mZ = new Matrix4x4(
					cosTheta, sinTheta, 0, 0,
					-sinTheta, cosTheta, 0, 0,
					0, 0, 1, 0,
					0, 0, 0, 1);
			mRot = mRot.matrix4x4Mul(mZ);
			break;
		case 7:
			cosTheta = (float)Math.cos(Math.toRadians(thetaX));
			sinTheta = (float)Math.sin(Math.toRadians(thetaX));
			
			mX = new Matrix4x4(
					1, 0, 0, 0, 
					0, cosTheta, sinTheta, 0,
					0, -sinTheta, cosTheta, 0,
					0, 0, 0, 1);
			mRot = mRot.matrix4x4Mul(mX);
			
			cosTheta = (float)Math.cos(Math.toRadians(thetaY));
			sinTheta = (float)Math.sin(Math.toRadians(thetaY));
			
			mY = new Matrix4x4(cosTheta, 0, -sinTheta, 0,
					0, 1, 0, 0,
					sinTheta, 0, cosTheta, 0,
					0, 0, 0, 1);
			mRot = mRot.matrix4x4Mul(mY);
			
			cosTheta = (float)Math.cos(Math.toRadians(thetaZ));
			sinTheta = (float)Math.sin(Math.toRadians(thetaZ));
			
			mZ = new Matrix4x4(cosTheta, sinTheta, 0, 0,
					-sinTheta, cosTheta, 0, 0,
					0, 0, 1, 0,
					0, 0, 0, 1);
			mRot = mRot.matrix4x4Mul(mZ);
			break;
		default:
			break;
		}

		return mRot;
	}

}
