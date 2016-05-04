package math3D;

import main3D.Const3D;

public class Vector2D {
	public float x, y;
	
	public Vector2D(){
		x = y = 0;
	}
	
	public Vector2D(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2D(Vector2D v){
		this.x = v.x;
		this.y = v.y;
	}

	public Polar2D point2DToPolar2D() {
		Polar2D po = new Polar2D();

		po.r = (float) Math.sqrt((this.x * this.x) + (this.y * this.y));
		po.theta = (float) Math.atan((this.y) / (this.x));

		return po;
	}

	public void point2DToPolarRTh(float r, float theta) {
		r = (float) Math.sqrt((this.x * this.x) + (this.y * this.y));
		theta = (float) Math.atan((this.y) / (this.x));
	}

	public Vector2D vector2DAdd(Vector2D v0) {
		Vector2D v = new Vector2D();

		v.x = this.x + v0.x;
		v.y = this.y + v0.y;

		return v;
	}

	public Vector2D vector2DSub(Vector2D v0) {
		Vector2D v = new Vector2D();

		v.x = this.x - v0.x;
		v.y = this.y - v0.y;

		return v;
	}

	public Vector2D vector2DScale(float k) {
		Vector2D v = new Vector2D();

		v.x = this.x*k;
		v.y = this.y*k;

		return v;	}

	public float vector2DDot(Vector2D v1) {
		return (this.x*v1.x + this.y*v1.y);
	}

	public float vector2DLength() {
		return (float)Math.sqrt(x*x + y*y);
	}

	public Vector2D vector2DNormalize() {
		Vector2D v = new Vector2D();
		
		float vn = (float)Math.sqrt(x*x + y*y);

		if (vn < Const3D.EPSILON_E5) return this;

		v.x = x/vn;
		v.y = y/vn;
		
		return v;
	}

	public Vector2D vector2DBulid(Vector2D term) {
		Vector2D v = new Vector2D();
		
		v.x = term.x - this.x;
		v.y = term.y - this.y;
		
		return v;
	}

	public float vector2DCosTh(Vector2D v1) {
		return (this.vector2DDot(v1)/(this.vector2DLength()*v1.vector2DLength()));
	}

	public void vector2DPrint() {
		System.out.println("Vector2D: v = (" + this.x + ", " + this.y + ")");
	}
}
