package math3D;

import main3D.Const3D;

public class Vector4D {
	public float x, y, z;
	public float w = 1.0f;
	
	public Vector4D(){
		x = y = z = 0;
	}
	
	public Vector4D(float x, float y, float z, float w){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector4D(Vector3D v){
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public Vector4D vector4DAdd(Vector4D v0){
		Vector4D v = new Vector4D();
		
		v.x = x + v0.x;
		v.y = y + v0.y;
		v.z = z + v0.z;
		
		return v;
	}
	
	public Vector4D vector4DSub(Vector4D v0){
		Vector4D v = new Vector4D();
		
		v.x = x - v0.x;
		v.y = y - v0.y;
		v.z = z - v0.z;
		
		return v;
	}
	
	public Vector4D vector4DScale(float k){
		Vector4D v = new Vector4D();
		
		v.x = x*k;
		v.y = y*k;
		v.z = z*k;
		
		return v;
	}
	
	public float vector4DDot(Vector4D v1){
		return (x*v1.x + y*v1.y + z*v1.z);
	}
	
	public Vector4D vector4DCross(Vector4D v1){
		Vector4D v = new Vector4D();
		
		v.x =  ( (y * v1.z) - (z * v1.y) );
		v.y = -( (x * v1.z) - (z * v1.x) );
		v.z =  ( (x * v1.y) - (y * v1.x) );
		
		return v;
	}
	
	public float vector4DLength(){
		return (float)Math.sqrt(x*x + y*y + z*z);
	}
	
	public float vector4DLengthFast(){
		return (float)Math.sqrt(x*x + y*y + z*z);
	}
	
	public Vector4D vector4DNormalize(){
		Vector4D v = new Vector4D();
		
		float vLength = this.vector4DLength();
		if(vLength < Const3D.EPSILON_E5) 
			return this;
		
		v.x = x/vLength;
		v.y = y/vLength;
		v.z = z/vLength;
		
		return v;
	}
	
	public Vector4D vector4DBulid(Vector4D term){
		Vector4D v = new Vector4D();
		
		v.x = term.x - x;
		v.y = term.y - y;
		v.z = term.z - z;
		
		return v;
	}
	
	public float vector4DCosTh(Vector4D v1){
		return (float)this.vector4DDot(v1)/
				(this.vector4DLength()*v1.vector4DLength());
	}
	
	public void vector4DPrint(){
		System.out.println("Vector4D: v = (" + this.x + ", " + this.y + ", " + this.z + "," + 1 + ")");
	}
	
	public Vector4D vector4DMulMatrix4x4(Matrix4x4 m){
		Vector4D v = new Vector4D();
		
		v.x = this.x*m.m[0][0] + this.y*m.m[1][0] + this.z*m.m[2][0] + this.w*m.m[3][0];
		v.y = this.x*m.m[0][1] + this.y*m.m[1][1] + this.z*m.m[2][1] + this.w*m.m[3][1];
		v.z = this.x*m.m[0][2] + this.y*m.m[1][2] + this.z*m.m[2][2] + this.w*m.m[3][2];
		v.w = this.x*m.m[0][3] + this.y*m.m[1][3] + this.z*m.m[2][3] + this.w*m.m[3][3];
		
		return v;
	}
	
	public Quat vector4DThetaToQuat(float theta){
		Quat q = new Quat();
		
		float thetaDiv2 = theta/2;
		float sinTheta = (float)Math.sin(thetaDiv2);

		q.q0 = (float)Math.cos(thetaDiv2);
		
		q.qv = new Vector3D();		
		q.qv.x = sinTheta*x;
		q.qv.y = sinTheta*y;
		q.qv.z = sinTheta*z;
		
		return q;
	}
}
