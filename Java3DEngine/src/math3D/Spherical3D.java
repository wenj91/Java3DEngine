package math3D;

public class Spherical3D {
	public float p; //到原点的距离
	public float theta; //线段o->p和正z轴之间的夹角
	public float phi; //线段o->p在x-y平面上的投影与正x轴之间的夹角
	
	public Spherical3D(){
		this.p = 0;
		this.theta = 0;
		this.phi = 0;
	}
	
	public Spherical3D(float p, float theta, float phi) {
		this.p = p;
		this.theta = theta;
		this.phi = phi;
	}
	
	public Spherical3D(Spherical3D s){
		this.p = s.p;
		this.theta = s.theta;
		this.phi = s.phi;
	}
	
	public void printSpherical3D(){
		System.out.println("Spherical: p = " + p + " theta = " + theta + " phi = " + phi);
	}
	
	public Vector3D spherical3DToPoint3D(){
		Vector3D v = new Vector3D();
		
		v.z = p*(float)Math.cos(theta);
		
		float r = p*(float)Math.sin(theta);
		v.x = r*(float)Math.cos(phi);
		v.y = r*(float)Math.sin(phi);
		
		return v;
	}
	
}
