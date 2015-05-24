package math3D;

public class Polar2D {
	float r;
	float theta;
	
	public Polar2D(){
		r = 0;
		theta = 0;
	}
	
	public Polar2D(float r,  float theta){
		this.r = r;
		this.theta = theta;
	}
	
	public Polar2D(Polar2D p){
		this.r = p.r;
		this.theta  = p.theta;
	}
	
	public void printPolar2D(){
		System.out.println("POLAR2D: r = " + r + " theta = " + theta + "\n");
	}
	
	public Vector2D polar2DToPoint2D(){
		Vector2D v = new Vector2D();
		
		v.x = (float)r*(float)Math.cos(theta);
		v.y = (float)r*(float)Math.sin(theta);
		
		return v;
	}
	
}
