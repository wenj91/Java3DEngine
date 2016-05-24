package object3D;

import main3D.Const3D;
import math3D.Matrix4x4;
import math3D.Plane3D;
import math3D.Vector3D;
import math3D.Vector4D;

public class Camera4D {
	int state;
	int attr;

	Vector4D pos;
	Vector4D dir;

	Vector4D u; // UVN���ģ�͵ĳ�������
	Vector4D v;
	Vector4D n;

	Vector4D target;

	private float viewDist; // ˮƽ�Ӿ�ʹ�ֱ�Ӿ�

	float fov; // �ӽ�

	// 3D�ü���
	// �����Ұ����90��,3D�ü��淽�̽�Ϊһ����ƽ�淽��
	float nearClipPlaneZ; // ���ü���
	float farClipPlaneZ; // Զ�ü���

	Plane3D rtClipPlane; // �Ҳü���
	Plane3D ltClipPlane; // ��ü���
	Plane3D tpClipPlane; // �ϲü���
	Plane3D btClipPlane; // �²ü���

	float viewPlaneWidth; // ��ƽ��Ŀ�Ⱥ͸߶�
	float viewPlaneHeight;

	// ��Ļ���ӿ���ͬ���
	private float viewportWidth;
	private float viewportHeight;
	float viewportCenterX; // �ӿ�����
	float viewportCenterY;

	// ��߱�;
	private float aspectRatio;

	// �Ƿ���Ҫ��������ȡ���ڱ任����
	// ����,���ֹ���ʽ����͸�ӱ任,��Ļ�任ʱ,����Ҫ��Щ����
	// Ȼ���ṩ��Щ��������������

	private Matrix4x4 mcam = new Matrix4x4(); // ���ڴ洢�������굽�������任����
	Matrix4x4 mper = new Matrix4x4(); // ���ڴ洢������굽͸������任����
	Matrix4x4 mscr = new Matrix4x4(); // ���ڴ洢͸�����굽��Ļ����任����

	public void initCamera4DV1(int camAttr, // �������
			Vector4D camPos, // ����ĳ�ʼλ��
			Vector4D camDir, // ����ĳ�ʼ�Ƕ�
			Vector4D camTarget, // UVN����ĳ�ʼĿ��λ��
			float nearCilpZ, float farClipZ, // ��Զ�ü���
			float fov, // �ӽ�
			float viewportWidth, float viewportHeight) { // ��Ļ�Ĵ�С

		this.attr = camAttr;

		this.pos = camPos;
		this.dir = camDir;

		this.u = new Vector4D(1, 0, 0, 1);
		this.v = new Vector4D(0, 1, 0, 1);
		this.n = new Vector4D(0, 0, 1, 1);

		if (null != camTarget) {
			this.target = camTarget;
		} else {
			this.target = new Vector4D();
		}

		this.nearClipPlaneZ = nearCilpZ;
		this.farClipPlaneZ = farClipZ;

		this.setViewportWidth(viewportWidth);
		this.setViewportHeight(viewportHeight);

		this.viewportCenterX = (viewportWidth - 1) / 2;
		this.viewportCenterY = (viewportHeight - 1) / 2;

		this.setAspectRatio((float) viewportWidth / (float) viewportHeight);

		this.getMcam().identity();
		this.mper.identity();
		this.mscr.identity();

		this.fov = fov;

		this.viewPlaneWidth = 2.0f;
		this.viewPlaneHeight = 2.0f / getAspectRatio();

		float tanFovDiv2 = (float) Math.tan(Math.toRadians(fov / 2));

		this.setViewDist((0.5f) * (this.viewPlaneWidth) * tanFovDiv2);
		if (fov == 90) {
			Vector3D ptOrigin = new Vector3D(); // �ü����ϵ�һ����,ֱ��ȡ���λ����Ϊ���ϵ�һ����
			ptOrigin.x = camPos.x;
			ptOrigin.y = camPos.y;
			ptOrigin.z = camPos.z;

			Vector3D vn; // �淨��

			// �Ҳü���
			//right
			vn = new Vector3D(1, 0, -1);
			this.rtClipPlane = new Plane3D(ptOrigin, vn);

			// left
			vn = new Vector3D(-1, 0, -1);
			this.ltClipPlane = new Plane3D(ptOrigin, vn);

			// top
			vn = new Vector3D(0, 1, -1);
			this.tpClipPlane = new Plane3D(ptOrigin, vn);

			// bot
			vn = new Vector3D(0, -1, -1);
			this.btClipPlane = new Plane3D(ptOrigin, vn);
		} else {//���ӽǲ�Ϊ90��ʱ
			Vector3D ptOrigin = new Vector3D(); // �ü����ϵ�һ����
			ptOrigin.x = camPos.x;
			ptOrigin.y = camPos.y;
			ptOrigin.z = camPos.z;
			
			Vector3D vn; // �淨��

			// �Ҳü���
			vn = new Vector3D(this.getViewDist(), 0, this.viewPlaneWidth / 2.0f);
			this.rtClipPlane = new Plane3D(ptOrigin, vn);

			// left
			vn = new Vector3D(-this.getViewDist(), 0, -this.viewPlaneWidth / 2.0f);
			this.ltClipPlane = new Plane3D(ptOrigin, vn);

			// top
			vn = new Vector3D(0, this.getViewDist(), -this.viewPlaneWidth / 2.0f);
			this.tpClipPlane = new Plane3D(ptOrigin, vn);

			// bot
			vn = new Vector3D(0, -this.getViewDist(), -this.viewPlaneWidth / 2.0f);
			this.btClipPlane = new Plane3D(ptOrigin, vn);
		}
	}

	public void buildCamera4DV1MatrixEuler(int camRotSeq) {
		Matrix4x4 mtInv, // ���ƽ�ƾ��������
		mxInv, // �����x����ת���������
		myInv, // �����y����ת���������
		mzInv, // �����z����ת���������
		mRot = new Matrix4x4(), // ��������ת����
		mtmp; // ���ڴ洢��ʱ����

		// 1. �������λ�ü������ƽ�ƾ���������
		mtInv = new Matrix4x4(
				1, 0, 0, 0, 
				0, 1, 0, 0, 
				0, 0, 1, 0, 
				-this.pos.x, -this.pos.y, -this.pos.z, 1);

		// 2. ������ת����������
		// Ҫ����������ת����������,���Խ���ת��
		// Ҳ���Խ�ÿ����ת�Ƕ�ȡ��

		// ���ȼ���������ת����������

		// ��ȡŷ���Ƕ�
		float thetaX = this.dir.x;
		float thetaY = this.dir.y;
		float thetaZ = this.dir.z;

		// ����Ƕ�x�����Һ�����
		float cosTheta = (float) Math.cos(Math.toRadians(thetaX));
		float sinTheta = (float) -Math.sin(Math.toRadians(thetaX));

		// ��������
		mxInv = new Matrix4x4(
				1, 0, 0, 0, 
				0, cosTheta, sinTheta, 0, 
				0, -sinTheta, cosTheta, 0, 
				0, 0, 0, 1);

		cosTheta = (float) Math.cos(Math.toRadians(thetaY));
		sinTheta = (float) -Math.sin(Math.toRadians(thetaY));

		myInv = new Matrix4x4(
				cosTheta, -sinTheta, 0, 0,
				0, 1, 0, 0, sinTheta,
				0, cosTheta, 0, 
				0, 0, 0, 1);

		cosTheta = (float) Math.cos(Math.toRadians(thetaZ));
		sinTheta = (float) -Math.sin(Math.toRadians(thetaZ));

		mzInv = new Matrix4x4(
				cosTheta, sinTheta, 0, 0, 
				-sinTheta, cosTheta, 0, 0, 
				0, 0, 1, 0, 
				0, 0, 0, 1);

		// ���ڼ�������ת����ĳ˻�
		switch (camRotSeq) {
		case Const3D.CAM_ROT_SEQ_XYZ:
			mtmp = mxInv.matrix4x4Mul(myInv);
			mRot = mtmp.matrix4x4Mul(mzInv);
			break;
		case Const3D.CAM_ROT_SEQ_YXZ:
			mtmp = myInv.matrix4x4Mul(mxInv);
			mRot = mtmp.matrix4x4Mul(mzInv);
			break;
		case Const3D.CAM_ROT_SEQ_XZY:
			mtmp = mxInv.matrix4x4Mul(mzInv);
			mRot = mtmp.matrix4x4Mul(myInv);
			break;
		case Const3D.CAM_ROT_SEQ_YZX:
			mtmp = myInv.matrix4x4Mul(mzInv);
			mRot = mtmp.matrix4x4Mul(mxInv);
			break;
		case Const3D.CAM_ROT_SEQ_ZYX:
			mtmp = mzInv.matrix4x4Mul(myInv);
			mRot = mtmp.matrix4x4Mul(mxInv);
			/*
			 * TEST matrix4x4Print(&pc->mcam, "CameraMatrix");
			 * matrix4x4Print(&mRot, "CameraRotMatrix"); matrix4x4Print(&mxInv,
			 * "MXMatrix"); matrix4x4Print(&myInv, "MYMatrix");
			 * matrix4x4Print(&mzInv, "MZMatrix");
			 */
			break;
		case Const3D.CAM_ROT_SEQ_ZXY:
			mtmp = mzInv.matrix4x4Mul(mxInv);
			mRot = mtmp.matrix4x4Mul(myInv);
			break;
		default:
			break;
		}

		// ����mRot����ת����ĳ˻�
		// ���������������ƽ�ƾ���,��������洢��������������任������
		this.setMcam(mtInv.matrix4x4Mul(mRot));

	}

	public void buildCamera4DV1MatrixUVN(int mode){
		Matrix4x4 mtInv, mtUVN;

		//1. �������λ�ô�����ƽ�ƾ���
		mtInv = new Matrix4x4(1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				-this.pos.x, -this.pos.y, -this.pos.z, 1);
//		matrix4x4Init(&mtInv,
//			1, 0, 0, 0,
//			0, 1, 0, 0,
//			0, 0, 1, 0,
//			-pc->pos.x, -pc->pos.y, -pc->pos.z, 1);

		//2. ȷ����μ���Ŀ���
		if(Const3D.UVN_MODE_SPHERICAL == mode){
			//��ȡ��λ�Ǻ�����
			float phi = this.dir.x;
			float theta = this.dir.y;

			//�������Ǻ���
			float sinPhi = (float)Math.sin(phi);
			float cosPhi = (float)Math.cos(phi);

			float sinTheta = (float)Math.sin(theta);
			float cosTheta = (float)Math.cos(theta);

			//����Ŀ����ڵ�λ�����ϵ�λ��(x, y, z)
			this.target.x = -1*sinPhi*sinTheta;
			this.target.y = 1*cosPhi;
			this.target.z = 1*sinPhi*cosTheta;
		}

		//��������Ϊ���¼���UVN����Ҫ��ȫ������,�۲�ο����Ŀ���
		//��һ��: n = <Ŀ��λ�� - �۲�ο���>
		this.n = this.pos.vector4DBulid(this.target);
//		vector4DBuild(&pc->pos, &pc->target, &pc->n);

		//�ڶ���: ��v����Ϊ<0, 1, 0>
		this.v = new Vector4D(0, 1, 0, 1);
//		vector4DInitXYZ(&pc->v, 0, 1, 0);

		//������: u = (v x n)
		this.u = this.v.vector4DCross(this.n);
//		vector4DCross(&pc->v, &pc->n, &pc->u);

		//���Ĳ�: v = (n x u)
		this.v = this.n.vector4DCross(u);
//		vector4DCross(&pc->n, &pc->u, &pc->v);

		//���岽: ���������������й�һ��
		this.u = this.u.vector4DNormalize();
		this.v = this.v.vector4DNormalize();
		this.n = this.n.vector4DNormalize();
//		
//		vector4DNormalize(&pc->u);
//		vector4DNormalize(&pc->v);
//		vector4DNormalize(&pc->n);

		//��uvn����,�õ�uvn��ת����
		mtUVN = new Matrix4x4(this.u.x, this.v.x, this.n.x, 0,
				this.u.y, this.v.y, this.n.y, 0, 
				this.u.z, this.v.z, this.n.z, 0,
				0, 0, 0, 1);
		
//		matrix4x4Init(&mtUVN, 
//			pc->u.x, pc->v.x, pc->n.x, 0,
//			pc->u.y, pc->v.y, pc->n.y, 0, 
//			pc->u.z, pc->v.z, pc->n.z, 0,
//			0, 0, 0, 1);

		//��ƽ�ƾ������uvn����,��������洢������任����mcam��
		this.setMcam(mtInv.matrix4x4Mul(mtUVN));
//		matrix4x4Mul(&mtInv, &mtUVN, &pc->mcam);

	}
	
	public static void cameraToPerspectiveObject4D(Object4D o){
			
	//		float alpha = (0.5f*pc->viewportWidth-0.5f);
	//		float beta  = (0.5f*pc->viewPlaneHeight-0.5f);
	//
	//		for (int vertex = 0; vertex < po->verticesNum; vertex++)
	//	    {
	//			float z = po->vListTrans[vertex].z;
	//
	//	    
	//			po->vListTrans[vertex].x = pc->viewDist*po->vListTrans[vertex].x/z;
	//			po->vListTrans[vertex].y = pc->viewDist*po->vListTrans[vertex].y/z;
	//	   
	//	    
	//			po->vListTrans[vertex].x =  po->vListTrans[vertex].x + alpha;
	//			po->vListTrans[vertex].y = -po->vListTrans[vertex].y + beta;
	//
	//	    } 
	}

	public Matrix4x4 getMcam() {
		return mcam;
	}

	public void setMcam(Matrix4x4 mcam) {
		this.mcam = mcam;
	}

	public float getViewDist() {
		return viewDist;
	}

	public void setViewDist(float viewDist) {
		this.viewDist = viewDist;
	}

	public float getAspectRatio() {
		return aspectRatio;
	}

	public void setAspectRatio(float aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

	public float getViewportWidth() {
		return viewportWidth;
	}

	public void setViewportWidth(float viewportWidth) {
		this.viewportWidth = viewportWidth;
	}

	public float getViewportHeight() {
		return viewportHeight;
	}

	public void setViewportHeight(float viewportHeight) {
		this.viewportHeight = viewportHeight;
	}

	public Vector4D getPos() {
		return pos;
	}

	public void setPos(Vector4D pos) {
		this.pos = pos;
	}


	public Vector4D getDir() {
		return dir;
	}

	public void setDir(Vector4D dir) {
		this.dir = dir;
	}


}
