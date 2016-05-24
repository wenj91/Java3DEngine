package math3D;

public class Matrix1x2 {
	public float m00, m01;

	public Matrix1x2() {
		this.m00 = 0;
		this.m01 = 0;
	}

	public Matrix1x2(float m00, float m01) {
		this.m00 = m00;
		this.m01 = m01;
	}

	public Matrix1x2(Matrix1x2 m) {
		this.m00 = m.m00;
		this.m01 = m.m01;
	}

	public Matrix1x2 matrix1x2Mul3x2(Matrix3x2 m3x2) {// t3dlib
		Matrix1x2 m = new Matrix1x2();
		
		m.m00 = this.m00*m3x2.m00 + this.m01*m3x2.m10 + m3x2.m20;
		m.m01 = this.m00*m3x2.m01 + this.m01*m3x2.m11 + m3x2.m21;
		
		return m;
	}
}
