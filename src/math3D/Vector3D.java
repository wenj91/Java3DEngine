package math3D;

import main3D.Const3D;

public class Vector3D {
	public float x, y, z;
	
	public Vector3D(){
		x = y = z = 0;
	}
	
	public Vector3D(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D(Vector3D v){
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public Cylindrical3D point3DToCylindrical(){
		Cylindrical3D c = new Cylindrical3D();
		
		c.r = (float)Math.sqrt(x*x + y*y);
		c.theta = (float)Math.atan(y/x);
		c.z = z;
		
		return c;
	}
	
	public Spherical3D point3DToSpherical3D(){
		Spherical3D s = new Spherical3D();
		
		s.p = (float)Math.sqrt((x*x) + (y* y) + (z*z));
		s.theta = (float)Math.atan(y/x);

		float r = s.p*(float)Math.sin(s.phi);
		s.phi = (float)Math.asin(r/s.p);
		
		return s;
	}
	
	public Vector3D vector3DAdd(Vector3D v0){
		Vector3D v = new Vector3D();
		
		v.x = x + v0.x;
		v.y = y + v0.y;
		v.z = z + v0.z;
		
		return v;
	}
	
	public Vector3D vector3DSub(Vector3D v0){
		Vector3D v = new Vector3D();
		
		v.x = x - v0.x;
		v.y = y - v0.y;
		v.z = z - v0.z;
		
		return v;
	}
	
	public Vector3D vector3DScale(float k){
		Vector3D v = new Vector3D();
		
		v.x = x*k;
		v.y = y*k;
		v.z = z*k;
		
		return v;
	}
	
	public float vector3DDot(Vector3D v1){
		return (x*v1.x + y*v1.y + z*v1.z);
	}
	
	public Vector3D vector3DCross(Vector3D v1){
		Vector3D v = new Vector3D();
		
		v.x =  ( (y * v1.z) - (z * v1.y) );
		v.y = -( (x * v1.z) - (z * v1.x) );
		v.z =  ( (x * v1.y) - (y * v1.x) );
		
		return v;
	}
	
	public float vector3DLength(){
		return (float)Math.sqrt(x*x + y*y + z*z);
	}
	
//	public float vector3DLengthFast(){
//		return 0;
//	}
	
	public Vector3D vector3DNormalize(){
		Vector3D v = new Vector3D();
		
		float n = this.vector3DLength();
		if(n <= Const3D.EPSILON_E5){
			return this;
		}
		
		v.x = x/n;
		v.y = y/n;
		v.z = z/n;
		
		return v;
	}
	
	public Vector3D vector3DBulid(Vector3D term){
		Vector3D v = new Vector3D();
		
		v.x = term.x - x;
		v.y = term.y - y;
		v.z = term.z - z;
		
		return v;
	}
	
	public float vector3DCosTh(Vector3D v1){
		return (this.vector3DDot(v1)/(this.vector3DLength()*v1.vector3DLength()));
	}
	
	public void printVector3D(){
		System.out.println("Vector3D: v = (" + this.x + ", " + this.y + ", " + this.z + ")");
	}
	
	public Vector3D vector3DMulMatrix4x4(Matrix4x4 m){
		Vector3D v = new Vector3D();
		
		v.x = this.x*m.m[0][0] + this.y*m.m[1][0] + this.z*m.m[2][0] + m.m[3][0];
		v.y = this.x*m.m[0][1] + this.y*m.m[1][1] + this.z*m.m[2][1] + m.m[3][1];
		v.z = this.x*m.m[0][2] + this.y*m.m[1][2] + this.z*m.m[2][2] + m.m[3][2];
		
		return v;
	}
	
	public Vector3D vector3DMulMatrix4x3(Matrix4x3 m){
		Vector3D v = new Vector3D();
		
		v.x = this.x*m.m[0][0] + this.y*m.m[1][0] + this.z*m.m[2][0] + m.m[3][0];
		v.y = this.x*m.m[0][1] + this.y*m.m[1][1] + this.z*m.m[2][1] + m.m[3][1];
		v.z = this.x*m.m[0][2] + this.y*m.m[1][2] + this.z*m.m[2][2] + m.m[3][2];
		
		return v;
	}
	
	public Quat vector3DThetaToQuat(float theta){
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
