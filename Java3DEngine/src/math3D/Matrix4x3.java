package math3D;

public class Matrix4x3 {
//	private float m00, m01, m02;
//	private float m10, m11, m12;
//	private float m20, m21, m22;
//	private float m30, m31, m32;
	
	public float[][] m = new float[4][3];
	
	public Matrix4x3(){
		m[0][0] = m[0][1] = m[0][2] =
				m[1][0] = m[1][1] = m[1][2] =
				m[2][0] = m[2][1] = m[2][2] =
				m[3][0] = m[3][1] = m[3][2] = 0;
 	}
	
	
	public Matrix4x3(float m00, float m01, float m02, float m10, float m11,
			float m12, float m20, float m21, float m22, float m30, float m31,
			float m32) {
		this.m[0][0] = m00;
		this.m[0][1] = m01;
		this.m[0][2] = m02;
		this.m[1][0] = m10;
		this.m[1][1] = m11;
		this.m[1][2] = m12;
		this.m[2][0] = m20;
		this.m[2][1] = m21;
		this.m[2][2] = m22;
		this.m[3][0] = m30;
		this.m[3][1] = m31;
		this.m[3][2] = m32;
	}


	public Matrix4x3(Matrix4x3 m){
		this.m[0][0] = m.m[0][0];
		this.m[0][1] = m.m[0][1];
		this.m[0][2] = m.m[0][2];
		this.m[1][0] = m.m[1][0];
		this.m[1][1] = m.m[1][1];
		this.m[1][2] = m.m[1][2];
		this.m[2][0] = m.m[2][0];
		this.m[2][1] = m.m[2][1];
		this.m[2][2] = m.m[2][2];
		this.m[3][0] = m.m[3][0];
		this.m[3][1] = m.m[3][1];
		this.m[3][2] = m.m[3][2];
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
	
	public void printMatrix4x3(){
		System.out.println("Matrix4x3: ");
		for(int i=0; i<this.m.length; i++){
			System.out.println();
			for(int j=0; j<this.m[i].length; j++){
				System.out.print(this.m[i][j] + " ");
			}
		}
	}
}
