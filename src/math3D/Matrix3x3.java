package math3D;

import main3D.Const3D;

public class Matrix3x3 {
//	private float m00, m01, m02;
//	private float m10, m11, m12;
//	private float m20, m21, m22;
	public float[][] m = new float[3][3];
	
	public Matrix3x3(){
		m[0][0] = m[0][1] = m[0][2] = 
				m[1][0] = m[1][1] = m[1][2] = 
				m[2][0] = m[2][1] = m[2][2] = 0;
	}
	

	public Matrix3x3(float m00, float m01, float m02, float m10, float m11,
			float m12, float m20, float m21, float m22) {
		this.m[0][0] = m00;
		this.m[0][1] = m01;
		this.m[0][2] = m02;
		this.m[1][0] = m10;
		this.m[1][1] = m11;
		this.m[1][2] = m12;
		this.m[2][0] = m20;
		this.m[2][1] = m21;
		this.m[2][2] = m22;
	}


	public Matrix3x3(Matrix3x3 m){
		this.m[0][0] = m.m[0][0];
		this.m[0][1] = m.m[0][1];
		this.m[0][2] = m.m[0][2];
		this.m[1][0] = m.m[1][0];
		this.m[1][1] = m.m[1][1];
		this.m[1][2] = m.m[1][2];
		this.m[2][0] = m.m[2][0];
		this.m[2][1] = m.m[2][1];
		this.m[2][2] = m.m[2][2];
	}
	
	
	
	public Matrix3x3 matrix3x3Mul(Matrix3x3 m0){ //t3dlib
		Matrix3x3 m = new Matrix3x3();
		
		for(int i=0; i<m0.m.length; i++){
			for(int j=0; j<m0.m[i].length; j++){
				float sum = 0;
				for(int k=0; k<3; k++){
					sum += this.m[i][k]*m0.m[k][j];
				}
				m.m[i][j] = sum;
			}
		}
		
		return m;
	}
	
	public Matrix3x3 matrix3x3Add(Matrix3x3 m0){
		Matrix3x3 m = new Matrix3x3();
		
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				m.m[i][j] = this.m[i][j] + m0.m[i][j];
			}
		}
		return m;
	}
	
	public Vector3D matrix3x3MulVector3D(Vector3D v){
		Vector3D v0 = new Vector3D();
		
		v0.x = this.m[0][0]*v.x + this.m[1][0]*v.y + this.m[2][0]*v.z;
		v0.y = this.m[0][1]*v.x + this.m[1][1]*v.y + this.m[2][1]*v.z;
		v0.z = this.m[0][2]*v.x + this.m[1][2]*v.y + this.m[2][2]*v.z;
		
		return v0;
	}
	
	public Matrix3x3 matrix3x3Inverse(){
		Matrix3x3 m = new Matrix3x3();
		
		float det = this.matrix3x3Det();
		if(Math.abs(det) < Const3D.EPSILON_E5){
			System.out.println("´Ë¾ØÕóÎÞÄæ¾ØÕó!");
			return null;
		}
		
		float detInv = (float)1.0f/det;
		
		m.m[0][0] = (this.m[1][1]*this.m[2][2] - this.m[1][2]*this.m[2][1])*detInv;
		m.m[1][0] = -(-this.m[1][2]*this.m[2][0] + this.m[1][0]*this.m[2][2])*detInv;
		m.m[2][0] = (-this.m[1][1]*this.m[2][0] + this.m[1][0]*this.m[2][1])*detInv;
		
		m.m[0][1] = -(-this.m[0][2]*this.m[2][1] + this.m[0][1]*this.m[2][2])*detInv;
		m.m[1][1] = (-this.m[0][2]*this.m[2][0] + this.m[0][0]*this.m[2][2])*detInv;
		m.m[2][1] = -(-this.m[0][1]*this.m[2][0] + this.m[0][0]*this.m[2][1])*detInv;
		
		m.m[0][2] = (-this.m[0][2]*this.m[1][1] + this.m[0][1]*this.m[1][2])*detInv;
		m.m[1][2] = -(-this.m[0][2]*this.m[1][0] + this.m[0][0]*this.m[1][2])*detInv;
		m.m[2][2] = (-this.m[0][1]*this.m[1][0] + this.m[0][0]*this.m[1][1])*detInv;
		
		
		return m;
	}
	
	public void printMatrix3x3(){
		System.out.println("Matrix3x3: ");
		System.out.println(" |" + m[0][0] + " " + m[0][1] + " " + m[0][2] + "|");
		System.out.println(" |" + m[1][0] + " " + m[1][1] + " " + m[1][2] + "|");
		System.out.println(" |" + m[2][0] + " " + m[2][1] + " " + m[2][2] + "|");
	}
	
	public void identity(){
		for(int i=0; i<this.m.length; i++){
			for(int j=0; j<this.m[i].length; j++){
				if(i == j){
					m[i][j] = 1;
				}else{
					m[i][j] = 0;
				}
			}
		}
	}
	
	public float matrix3x3Det(){
		return ((float)(this.m[0][0]*this.m[1][1]*this.m[2][2] + 
				this.m[0][1]*this.m[1][2]*this.m[2][0] + 
				this.m[0][2]*this.m[1][0]*this.m[2][1] -
				this.m[0][0]*this.m[1][2]*this.m[2][1] -
				this.m[0][1]*this.m[1][0]*this.m[2][2] -
				this.m[0][2]*this.m[1][1]*this.m[2][0]));
	}
	
	public void matrix3x3ColSwap(int c, Matrix1x3 m1){
		this.m[0][c] = m1.m00;
		this.m[1][c] = m1.m01;
		this.m[2][c] = m1.m02;
	}
	
	public static int solve3x3System(Matrix3x3 a, Matrix1x3 x, Matrix1x3 b){
		// solves the system AX=B and computes X=A(-1)*B
		// by using cramers rule and determinates

		// step 1: compute determinate of A
		float det_A = a.matrix3x3Det();

		// test if det(a) is zero, if so then there is no solution
		if (Math.abs(det_A) < Const3D.EPSILON_E5)
		   return(0);

		// step 2: create x,y,z numerator matrices by taking A and
		// replacing each column of it with B(transpose) and solve
		Matrix3x3 work_mat; // working matrix

		// solve for x /////////////////

		// copy A into working matrix
		work_mat = new Matrix3x3(a);

		// swap out column 0 (x column)
		work_mat.matrix3x3ColSwap(0, b);

		// compute determinate of A with B swapped into x column
		float det_ABx = work_mat.matrix3x3Det();

		// now solve for X00
		x.m00 = det_ABx/det_A;

		// solve for y /////////////////

		// copy A into working matrix
		work_mat = new Matrix3x3(a);

		// swap out column 1 (y column)
		work_mat.matrix3x3ColSwap(1, b);

		// compute determinate of A with B swapped into y column
		float det_ABy = work_mat.matrix3x3Det();

		// now solve for X01
		x.m01 = det_ABy/det_A;

		// solve for z /////////////////

		// copy A into working matrix
		work_mat = new Matrix3x3(a);

		// swap out column 2 (z column)
		work_mat.matrix3x3ColSwap(2, b);

		// compute determinate of A with B swapped into z column
		float det_ABz = work_mat.matrix3x3Det();

		// now solve for X02
		x.m02 = det_ABz/det_A;

		// return success
		return(1);

	}
}
