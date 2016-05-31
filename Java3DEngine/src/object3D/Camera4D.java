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

	Vector4D u; // UVN相机模型的朝向向量
	Vector4D v;
	Vector4D n;

	Vector4D target;

	private float viewDist; // 水平视距和垂直视距

	float fov; // 视角

	// 3D裁剪面
	// 如果视野不是90°,3D裁剪面方程将为一般性平面方程
	float nearClipPlaneZ; // 近裁剪面
	float farClipPlaneZ; // 远裁剪面

	Plane3D rtClipPlane; // 右裁剪面
	Plane3D ltClipPlane; // 左裁剪面
	Plane3D tpClipPlane; // 上裁剪面
	Plane3D btClipPlane; // 下裁剪面

	float viewPlaneWidth; // 视平面的宽度和高度
	float viewPlaneHeight;

	// 屏幕和视口是同义词
	private float viewportWidth;
	private float viewportHeight;
	float viewportCenterX; // 视口中心
	float viewportCenterY;

	// 宽高比;
	private float aspectRatio;

	// 是否需要下述矩阵取决于变换方法
	// 例如,以手工方式进行透视变换,屏幕变换时,不需要这些矩阵
	// 然而提供这些矩阵提高了灵活性

	private Matrix4x4 mcam = new Matrix4x4(); // 用于存储世界坐标到相机坐标变换矩阵
	Matrix4x4 mper = new Matrix4x4(); // 用于存储相机坐标到透视坐标变换矩阵
	Matrix4x4 mscr = new Matrix4x4(); // 用于存储透视坐标到屏幕坐标变换矩阵

	public void initCamera4DV1(int camAttr, // 相机属性
			Vector4D camPos, // 相机的初始位置
			Vector4D camDir, // 相机的初始角度
			Vector4D camTarget, // UVN相机的初始目标位置
			float nearCilpZ, float farClipZ, // 近远裁剪面
			float fov, // 视角
			float viewportWidth, float viewportHeight) { // 屏幕的大小

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
			Vector3D ptOrigin = new Vector3D(); // 裁剪面上的一个点,直接取相机位置作为面上的一个点
			ptOrigin.x = camPos.x;
			ptOrigin.y = camPos.y;
			ptOrigin.z = camPos.z;

			Vector3D vn; // 面法线

			// 右裁剪面
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
		} else {//当视角不为90°时
			Vector3D ptOrigin = new Vector3D(); // 裁剪面上的一个点
			ptOrigin.x = camPos.x;
			ptOrigin.y = camPos.y;
			ptOrigin.z = camPos.z;
			
			Vector3D vn; // 面法线

			// 右裁剪面
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
		Matrix4x4 mtInv, // 相机平移矩阵逆矩阵
		mxInv, // 相机绕x轴旋转矩阵逆矩阵
		myInv, // 相机绕y轴旋转矩阵逆矩阵
		mzInv, // 相机绕z轴旋转矩阵逆矩阵
		mRot = new Matrix4x4(), // 最终总旋转矩阵
		mtmp; // 用于存储临时矩阵

		// 1. 根据相机位置计算相机平移矩阵的逆矩阵
		mtInv = new Matrix4x4(
				1, 0, 0, 0, 
				0, 1, 0, 0, 
				0, 0, 1, 0, 
				-this.pos.x, -this.pos.y, -this.pos.z, 1);

		// 2. 创建旋转矩阵的逆矩阵
		// 要计算正规旋转矩阵的逆矩阵,可以将其转置
		// 也可以将每个旋转角度取负

		// 首先计算三个旋转矩阵的逆矩阵

		// 提取欧拉角度
		float thetaX = this.dir.x;
		float thetaY = this.dir.y;
		float thetaZ = this.dir.z;

		// 计算角度x的正弦和余弦
		float cosTheta = (float) Math.cos(Math.toRadians(thetaX));
		float sinTheta = (float) -Math.sin(Math.toRadians(thetaX));

		// 建立矩阵
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

		// 现在计算逆旋转矩阵的乘积
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

		// 现在mRot逆旋转矩阵的乘积
		// 接下来将其乘以逆平移矩阵,并将结果存储到相机对象的相机变换矩阵中
		this.setMcam(mtInv.matrix4x4Mul(mRot));

	}

	public void buildCamera4DV1MatrixUVN(int mode){
		Matrix4x4 mtInv, mtUVN;

		//1. 根据相机位置创建逆平移矩阵
		mtInv = new Matrix4x4(1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				-this.pos.x, -this.pos.y, -this.pos.z, 1);
//		matrix4x4Init(&mtInv,
//			1, 0, 0, 0,
//			0, 1, 0, 0,
//			0, 0, 1, 0,
//			-pc->pos.x, -pc->pos.y, -pc->pos.z, 1);

		//2. 确定如何计算目标点
		if(Const3D.UVN_MODE_SPHERICAL == mode){
			//提取方位角和仰角
			float phi = this.dir.x;
			float theta = this.dir.y;

			//计算三角函数
			float sinPhi = (float)Math.sin(phi);
			float cosPhi = (float)Math.cos(phi);

			float sinTheta = (float)Math.sin(theta);
			float cosTheta = (float)Math.cos(theta);

			//计算目标点在单位球面上的位置(x, y, z)
			this.target.x = -1*sinPhi*sinTheta;
			this.target.y = 1*cosPhi;
			this.target.z = 1*sinPhi*cosTheta;
		}

		//至此有了为重新计算UVN所需要的全部参数,观察参考点和目标点
		//第一步: n = <目标位置 - 观察参考点>
		this.n = this.pos.vector4DBulid(this.target);
//		vector4DBuild(&pc->pos, &pc->target, &pc->n);

		//第二步: 将v设置为<0, 1, 0>
		this.v = new Vector4D(0, 1, 0, 1);
//		vector4DInitXYZ(&pc->v, 0, 1, 0);

		//第三步: u = (v x n)
		this.u = this.v.vector4DCross(this.n);
//		vector4DCross(&pc->v, &pc->n, &pc->u);

		//第四步: v = (n x u)
		this.v = this.n.vector4DCross(u);
//		vector4DCross(&pc->n, &pc->u, &pc->v);

		//第五步: 对所有向量都进行归一化
		this.u = this.u.vector4DNormalize();
		this.v = this.v.vector4DNormalize();
		this.n = this.n.vector4DNormalize();
//		
//		vector4DNormalize(&pc->u);
//		vector4DNormalize(&pc->v);
//		vector4DNormalize(&pc->n);

		//将uvn代入,得到uvn旋转矩阵
		mtUVN = new Matrix4x4(this.u.x, this.v.x, this.n.x, 0,
				this.u.y, this.v.y, this.n.y, 0, 
				this.u.z, this.v.z, this.n.z, 0,
				0, 0, 0, 1);
		
//		matrix4x4Init(&mtUVN, 
//			pc->u.x, pc->v.x, pc->n.x, 0,
//			pc->u.y, pc->v.y, pc->n.y, 0, 
//			pc->u.z, pc->v.z, pc->n.z, 0,
//			0, 0, 0, 1);

		//将平移矩阵乘以uvn矩阵,并将结果存储到相机变换矩阵mcam中
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
