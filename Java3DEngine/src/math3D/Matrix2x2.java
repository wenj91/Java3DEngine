package math3D;

import main3D.Const3D;

public class Matrix2x2 {
	public float m00, m01;
	public float m10, m11;
	
	public Matrix2x2(){
		m00 = m01 = m10 = m11 = 0;
	}
	
	public Matrix2x2(float m00, float m01, float m10, float m11) {
		this.m00 = m00;
		this.m01 = m01;
		this.m10 = m10;
		this.m11 = m11;
	}

	public Matrix2x2(Matrix2x2 m){
		this.m00 = m.m00;
		this.m01 = m.m01;
		this.m10 = m.m10;
		this.m11 = m.m11;
	}
	
	public void printMatrix2x2(){
		System.out.println("M2x2: " + "\n|" + m00 + " " + m01 + "|\n|" +
				m10 + " " + m11 + "|\n");
	}
	
	public float matrix2x2Det(){
		return (m00*m11 - m01*m10);
	}
	
	public void identity(){
		m00 = 1; m01 = 0;
		m10 = 0; m11 = 1;
	}
	
	public Matrix2x2 matrix2x2Add(Matrix2x2 m0){
		Matrix2x2 m = new Matrix2x2();
		
		m.m00 = this.m00 + m0.m00; m.m01 = this.m01 + m0.m01;
		m.m10 = this.m10 + m0.m10; m.m11 = this.m11 + m0.m11;
		
		return m;
	}
	
	public Matrix2x2 matrix2x2Mul(Matrix2x2 m0){
		Matrix2x2 m = new Matrix2x2();
		
		m.m00 = this.m00*m0.m00 + this.m01*m0.m10;
		m.m01 = this.m00*m0.m01 + this.m01*m0.m11;
		
		m.m10 = this.m10*m0.m00 + this.m11*m0.m10;
		m.m11 = this.m10*m0.m01 + this.m11*m0.m11;
		
		return m;
	}
	
	public Matrix2x2 matrix2x2Inverse(){
		Matrix2x2 m = new Matrix2x2();
		
		float det = this.matrix2x2Det();
		if(Math.abs(det) < Const3D.EPSILON_E5) {
			System.out.println("此矩阵无逆矩阵!");
			return null;
		}
		
		float detInv = (float)1.0f/det;
		
		m.m00 = detInv*this.m11;
		m.m01 = -detInv*this.m01;
		m.m10 = -detInv*this.m10;
		m.m11 = detInv*this.m00;
		
		return m;
	}
	
	public void matrix2x2ColSwap(int c, Matrix1x2 m1){
		if(c == 0) {
			this.m00 = m1.m00; this.m10 = m1.m01;	
		}else if(c == 1){
			this.m01 = m1.m00; this.m11 = m1.m01;
		}else{
			return;
		}
	}
	
	public static int solve2x2System(Matrix2x2 a, Matrix1x2 x, Matrix1x2 b){
		// solves the system AX=B and computes X=A(-1)*B
		// by using cramers rule and determinates

		// step 1: compute determinate of A
		float det_A = a.matrix2x2Det();

		// test if det(a) is zero, if so then there is no solution
		if (Math.abs(det_A) < Const3D.EPSILON_E5) return(0);

		// step 2: create x,y numerator matrices by taking A and
		// replacing each column of it with B(transpose) and solve
		Matrix2x2 work_mat; // working matrix

		// solve for x /////////////////

		// copy A into working matrix
		work_mat = new Matrix2x2(a);

		// swap out column 0 (x column)
		work_mat.matrix2x2ColSwap(0, b);

		// compute determinate of A with B swapped into x column
		float det_ABx = work_mat.matrix2x2Det();

		// now solve for X00
		x.m00 = det_ABx/det_A;

		// solve for y /////////////////

		// copy A into working matrix
		work_mat = new Matrix2x2(a);

		// swap out column 1 (y column)
		work_mat.matrix2x2ColSwap(1, b);

		// compute determinate of A with B swapped into y column
		float det_ABy = work_mat.matrix2x2Det();

		// now solve for X01
		x.m01 = det_ABy/det_A;

		// return success
		return 1;
	}
}
