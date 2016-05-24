package math3D;

import main3D.Const3D;

public class Matrix4x4 {

//	private float m00, m01, m02, m03;
//	private float m10, m11, m12, m13;
//	private float m20, m21, m22, m23;
//	private float m30, m31, m32, m33;
	
	public float[][] m = new float[4][4];
	
	public Matrix4x4(){
		        m[0][0] = m[0][1] = m[0][2] = m[0][3] = 
				m[1][0] = m[1][1] = m[1][2] = m[1][3] =
				m[2][0] = m[2][1] = m[2][2] = m[2][3] =
				m[3][0] = m[3][1] = m[3][2] = m[3][3] = 0;
 	}
	

	
	public Matrix4x4(float m00, float m01, float m02, float m03, float m10,
			float m11, float m12, float m13, float m20, float m21, float m22,
			float m23, float m30, float m31, float m32, float m33) {
		this.m[0][0] = m00;	this.m[0][1] = m01;	this.m[0][2] = m02;	this.m[0][3] = m03;
		this.m[1][0] = m10;	this.m[1][1] = m11;	this.m[1][2] = m12;	this.m[1][3] = m13;
		this.m[2][0] = m20;	this.m[2][1] = m21;	this.m[2][2] = m22;	this.m[2][3] = m23;
		this.m[3][0] = m30;	this.m[3][1] = m31;	this.m[3][2] = m32;	this.m[3][3] = m33;
	}



	public Matrix4x4(Matrix4x4 m){
		for(int i=0; i<m.m.length; i++){
			for(int j=0; j<m.m.length; j++){
				this.m[i][j] = m.m[i][j];
			}
		}
		
	}
	
	public void printMatrix4x4(){
		System.out.println("Matrix4x4: \n" + "|" + m[0][0] + " " +m[0][1] + " " + m[0][2] + " " + m[0][3] + "|\n" + 
				 "|" + m[1][0] + " " + m[1][1] + " " + m[1][2] + " " + m[1][3] + "|\n" +
				 "|" + m[2][0] + " " + m[2][1] + " " + m[2][2] + " " + m[2][3] + "|\n" +
				 "|" + m[3][0] + " " + m[3][1] + " " + m[3][2] + " " + m[3][3] + "|" );
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
	
	public Matrix4x4 matrix4x4Add(Matrix4x4 m0){
		Matrix4x4 m = new Matrix4x4();
		
		m.m[0][0] = this.m[0][0] + m0.m[0][0]; m.m[0][1] = this.m[0][1] + m0.m[0][1]; 
		m.m[0][2] = this.m[0][2] + m0.m[0][2]; m.m[0][3] = this.m[0][3] + m0.m[0][3];		
		m.m[1][0] = this.m[1][0] + m0.m[1][0]; m.m[1][1] = this.m[1][1] + m0.m[1][1];
		m.m[1][2] = this.m[1][2] + m0.m[1][2]; m.m[1][3] = this.m[1][3] + m0.m[1][3];		
		m.m[2][0] = this.m[2][0] + m0.m[2][0]; m.m[2][1] = this.m[2][1] + m0.m[2][1];
		m.m[2][2] = this.m[2][2] + m0.m[2][2]; m.m[2][3] = this.m[2][3] + m0.m[2][3];		
		m.m[3][0] = this.m[3][0] + m0.m[3][0]; m.m[3][1] = this.m[3][1] + m0.m[3][1];
		m.m[3][2] = this.m[3][2] + m0.m[3][2]; m.m[3][3] = this.m[3][3] + m0.m[3][3];
		
		return m;
	}
	
	public float matrix4x4Det(){
		return (this.m[0][0]*(this.m[1][1]*this.m[2][2] - this.m[1][2]*this.m[2][1]) -
		   this.m[0][1]*(this.m[1][0]*this.m[2][2] - this.m[1][2]*this.m[2][0]) +
		   this.m[0][2]*(this.m[1][0]*this.m[2][1] - this.m[1][1]*this.m[2][0]));
	}
	
	public Matrix4x4 matrix4x4Mul(Matrix4x4 m0){
		 Matrix4x4 m = new Matrix4x4();
		for(int i=0; i<m0.m.length; i++){
			for(int j=0; j<m0.m[i].length; j++){
				float sum = 0;
				for(int k=0; k<4; k++){
					sum += this.m[i][k]*m0.m[k][j];
				}
				m.m[i][j] = sum;
			}
		}
		
		return m;
	}	
	
	public Matrix4x4 matrix4x4Inverse(){
		Matrix4x4 mi = new Matrix4x4();
		
		// computes the inverse of a 4x4, assumes that the last
		// column is [0 0 0 1]t
		float det =  ( this.m[0][0]*(this.m[1][1]*this.m[2][2] - this.m[1][2]*this.m[2][1]) -
				   this.m[0][1]*(this.m[1][0]*this.m[2][2] - this.m[1][2]*this.m[2][0]) +
				   this.m[0][2]*(this.m[1][0]*this.m[2][1] - this.m[1][1]*this.m[2][0]));

	// test determinate to see if it's 0
	if (Math.abs(det) < Const3D.EPSILON_E5)
	   return null;

	float det_inv  = 1.0f / det;

	mi.m[0][0] =  det_inv * ( m[1][1] * m[2][2] - m[1][2] * m[2][1] );
	mi.m[0][1] = -det_inv * ( m[0][1] * m[2][2] - m[0][2] * m[2][1] );
	mi.m[0][2] =  det_inv * ( m[0][1] * m[1][2] - m[0][2] * m[1][1] );
	mi.m[0][3] = 0.0f; // always 0
	mi.m[1][0] = -det_inv * ( m[1][0] * m[2][2] - m[1][2] * m[2][0] );
	mi.m[1][1] =  det_inv * ( m[0][0] * m[2][2] - m[0][2] * m[2][0] );
	mi.m[1][2] = -det_inv * ( m[0][0] * m[1][2] - m[0][2] * m[1][0] );
	mi.m[1][3] = 0.0f; // always 0
	mi.m[2][0] =  det_inv * ( m[1][0] * m[2][1] - m[1][1] * m[2][0] );
	mi.m[2][1] = -det_inv * ( m[0][0] * m[2][1] - m[0][1] * m[2][0] );
	mi.m[2][2] =  det_inv * ( m[0][0] * m[1][1] - m[0][1] * m[1][0] );
	mi.m[2][3] = 0.0f; // always 0
	mi.m[3][0] = -( m[3][0] * mi.m[0][0] + m[3][1] * mi.m[1][0] + m[3][2] * mi.m[2][0] );
	mi.m[3][1] = -( m[3][0] * mi.m[0][1] + m[3][1] * mi.m[1][1] + m[3][2] * mi.m[2][1] );
	mi.m[3][2] = -( m[3][0] * mi.m[0][2] + m[3][1] * mi.m[1][2] + m[3][2] * mi.m[2][2] );
	mi.m[3][3] = 1.0f; // always 0

	// return success 		
		return mi;
	}
	
}
