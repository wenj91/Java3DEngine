package math3D;

public class Matrix3x2 {
	
	public float m00, m01;
	public float m10, m11;
	public float m20, m21;
	
	public Matrix3x2(){
		m00 = m01 = m10 = m11 = m20 = m21 = 0;
	}
	

	
	public Matrix3x2(float m00, float m01, float m10, float m11, float m20,
			float m21) {
		this.m00 = m00;
		this.m01 = m01;
		this.m10 = m10;
		this.m11 = m11;
		this.m20 = m20;
		this.m21 = m21;
	}



	public Matrix3x2(Matrix3x2 m){
		this.m00 = m.m00;
		this.m01 = m.m01;
		this.m10 = m.m10;
		this.m11 = m.m11;
		this.m20 = m.m20;
		this.m21 = m.m21;
	}
	
	public void printMatrix3x2(){
		System.out.println("Matrix3x2: \n" + m00 + " " + m01 + "\n" + 
	m10 +" " + m11 + "\n" + m20 + " " + m21 + "\n");
	}
}
