package math3D;

public class Matrix1x3 {

	public float m00, m01, m02;

	public Matrix1x3() {
		m00 = m01 = m02 = 0;
	}

	public Matrix1x3(float m00, float m01, float m02) {
		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
	}

	public Matrix1x3(Matrix1x3 m) {
		this.m00 = m.m00;
		this.m01 = m.m01;
		this.m02 = m.m02;
	}
	
	public Matrix1x3 matrix1x3Mul3x3(Matrix3x3 m3x3){//t3dlib
		Matrix1x3 m = new Matrix1x3();
		
		m.m00 = this.m00*m3x3.m[0][0] + this.m01*m3x3.m[1][0] + this.m02*m3x3.m[2][0];
		m.m01 = this.m00*m3x3.m[0][1] + this.m01*m3x3.m[1][1] + this.m02*m3x3.m[2][1];
		m.m02 = this.m00*m3x3.m[0][2] + this.m01*m3x3.m[1][2] + this.m02*m3x3.m[2][2];
		
		return m;
	}

}
