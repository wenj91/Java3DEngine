package math3D;

public class Cylindrical3D {
	float r; 
	float theta;
	float z;
	
	public Cylindrical3D(){
		r = 0;
		theta = 0;
		z = 0;
	}
	
	public Cylindrical3D(float r, float theta, float z){
		this.r = r;
		this.theta  = theta;
		this.z = z;
	}
	
	public Cylindrical3D(Cylindrical3D c){
		this.r = c.r;
		this.theta = c.theta;
		this.z = c.z;
	}
	
	public void printCylindrical3D(){
		System.out.println("Cylindrical3D: r = " + r + " theta = " + theta + " z = " + z + "\n" );
	}
	
	public Vector3D cylindrical3DToPoint3D(){
		Vector3D v = new Vector3D();
		
		v.z = z;
		v.x = r*(float)Math.cos(theta);
		v.y = r*(float)Math.sin(theta);
		
		return v;
	}
}
