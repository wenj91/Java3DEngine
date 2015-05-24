package math3D;

public class Matrix1x4 {
	public float m00, m01, m02; 
	public static final float m03 = 1;

	public Matrix1x4() {
		m00 = m01 = m02 = 0;
	}

	public Matrix1x4(float m00, float m01, float m02) {
		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
	}

	public Matrix1x4(Matrix1x4 m) {
		this.m00 = m.m00;
		this.m01 = m.m01;
		this.m02 = m.m02;
	}

	public Matrix1x4 matrix1x4Mul4x4(Matrix4x4 m4x4){
		Matrix1x4 m = new Matrix1x4();
		
		m.m00 = this.m00*m4x4.m[0][0] + this.m01*m4x4.m[1][0] + this.m02*m4x4.m[2][0] + m4x4.m[3][0];
		m.m01 = this.m00*m4x4.m[0][1] + this.m01*m4x4.m[1][1] + this.m02*m4x4.m[2][1] + m4x4.m[3][1];
		m.m02 = this.m00*m4x4.m[0][2] + this.m01*m4x4.m[1][2] + this.m02*m4x4.m[2][2] + m4x4.m[3][2];
		
		return m;
	}
	
}
