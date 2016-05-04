package math3D;

public class Quat {
	public float  q0;
	public Vector3D qv;
	
	public Quat(){
		q0 = 0;
		qv = new Vector3D();
	}
	
	public Quat(float q0, Vector3D qv) {
		this.q0 = q0;
		this.qv = qv;
	}
	
	public Quat(Quat q){
		q0 = q.q0;
		qv = q.qv;
	}
	
	public void printQuat(){
		System.out.println("Quat: q0 = " + q0 +" qv = ( " + qv.x + ", " + qv.y + ", " + qv.z + ")");
	}
	
	public Quat quatAdd(Quat q){
		Quat q0 = new Quat();
		
		q0.q0 = this.q0 + q.q0;
		q0.qv = this.qv.vector3DAdd(q.qv);
		
		return q0;
	}
	
	public Quat quatSub(Quat q0){
			Quat q = new Quat();
			
			q.q0 = this.q0 - q0.q0;
			
			Vector3D qv = new Vector3D();
			qv.x = this.qv.x - q0.qv.x;
			qv.y = this.qv.y - q0.qv.y;
			qv.z = this.qv.z - q0.qv.z;
			q.qv = qv;
			
			return q;
	}
	
	public Quat quatConjugate(){//求共轭四元数
		Quat q = new Quat();
		
		q.q0 = this.q0;
		
		q.qv.x = -this.qv.x;
		q.qv.y = -this.qv.y;
		q.qv.z = -this.qv.z;
		
		return q;
	}
	
	public Quat quatScale(float scale){
		Quat q = new Quat();
		
		q.q0 = this.q0*scale;
		q.qv = this.qv.vector3DScale(scale);
		
		return q;
	} 
	
	/**
	 * 求四元数范数
	 * @return 四元数范数(也就是Quat的长度)
	 */
	public float quatNorm(){
		return ((float)Math.sqrt(q0*q0 + qv.x*qv.x + qv.y*qv.y + qv.z*qv.z));
	}
	
	/**
	 * 求四元数范数的平方
	 * @return 四元数范数的平方(也就是Quat的长度的平方)
	 */
	public float quatNorm2(){
		return (q0*q0 + qv.x*qv.x + qv.y*qv.y + qv.z*qv.z);
	}
	
	public Quat quatNormalize(){
		Quat qn = new Quat();
		
		float qInv = (float)1.0f/this.quatNorm();
		qn.q0 = this.q0*qInv;
		qn.qv = this.qv.vector3DScale(qInv);
		
		return qn;
	}
	
	public Quat quatUnitInverse(){
		Quat tempQ = this.quatNormalize();
		Quat q = new Quat();
		q.q0 = tempQ.q0;
		q.qv.x = -tempQ.qv.x;
		q.qv.y = -tempQ.qv.y;
		q.qv.z = -tempQ.qv.z;
		
		return q;	
	}
	
	public Quat quatMul(Quat q){
		Quat qProd = new Quat();
		
		float p0 = (this.qv.z -this.qv.y)*(q.qv.y - q.qv.z);		
		float p1 = (this.q0 + this.qv.x)*(q.q0 + q.qv.x);		
		float p2 = (this.q0 - this.qv.x)*(q.qv.y + q.qv.z);		
		float p3 = (this.qv.y + this.qv.z)*(q.q0 - q.qv.x);		
		float p4 = (this.qv.z - this.qv.x)*(q.qv.x - q.qv.y);	
		float p5 = (this.qv.z + this.qv.x)*(q.qv.x + q.qv.y);	
		float p6 = (this.q0 + this.qv.y)*(q.q0 - q.qv.z);		
		float p7 = (this.q0 - this.qv.y)*(q.q0 + q.qv.z);		
		
		float p8 = p5 + p6 + p7;		
		float p9 = 0.5f*(p4 + p8);		

		// and finally build up the result with the temporary products

		qProd.q0 = p0 + p9 - p5;
		qProd.qv.x = p1 + p9 - p8;
		qProd.qv.y = p2 + p9 - p7;
		qProd.qv.z = p3 + p9 - p6;

		
		return qProd;
	}
	
	
	/**
	 * this function computes q1*q2*q3 in that order and returns 
	 * @param q0
	 * @param q
	 * @return QuatProd
	 */
	public Quat quatTripleProduct(Quat q0, Quat q1){
		Quat tempQ = this.quatMul(q0);
		Quat q = tempQ.quatMul(q1);
		
		return q;
	}
	
	public Quat eulerZYXToQuat(float thetaZ, float thetaY, float thetaX){
		Quat q = new Quat();
		// this function intializes a quaternion based on the zyx
		// multiplication order of the angles that are parallel to the
		// zyx axis respectively, note there are 11 other possibilities
		// this is just one, later we may make a general version of the
		// the function

		// precompute values
		float cos_z_2 = 0.5f*(float)Math.cos(thetaZ);
		float cos_y_2 = 0.5f*(float)Math.cos(thetaY);
		float cos_x_2 = 0.5f*(float)Math.cos(thetaX);

		float sin_z_2 = 0.5f*(float)Math.sin(thetaZ);
		float sin_y_2 = 0.5f*(float)Math.sin(thetaY);
		float sin_x_2 = 0.5f*(float)Math.sin(thetaX);

		// and now compute quaternion
		q.q0 = cos_z_2*cos_y_2*cos_x_2 + sin_z_2*sin_y_2*sin_x_2;
		q.qv.x = cos_z_2*cos_y_2*sin_x_2 - sin_z_2*sin_y_2*cos_x_2;
		q.qv.y = cos_z_2*sin_y_2*cos_x_2 + sin_z_2*cos_y_2*sin_x_2;
		q.qv.z = sin_z_2*cos_y_2*cos_x_2 - cos_z_2*sin_y_2*sin_x_2;

		return q;
	}
	
	public void quatToVector3DTheta(Vector3D v, float theta){
		// this function converts a unit quaternion into a unit direction
		// vector and rotation angle about that vector

		// extract theta
		theta = (float)Math.acos(this.q0);

		// pre-compute to save time
		float sinf_theta_inv = 1.0f/(float)Math.sin(theta);

		// now the vector
		v.x = this.qv.x*sinf_theta_inv;
		v.y = this.qv.y*sinf_theta_inv;
		v.z = this.qv.z*sinf_theta_inv;

		// multiply by 2
		theta*=2;
	}
	
}
