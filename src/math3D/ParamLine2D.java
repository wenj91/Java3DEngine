package math3D;

import main3D.Const3D;

public class ParamLine2D {
	public Vector2D p0, p1;
	public Vector2D v;
	
	public ParamLine2D(){
		p0 = new Vector2D();
		p1 = new Vector2D();
		
		v = new Vector2D();
	}
	
	public ParamLine2D(Vector2D p0, Vector2D p1){
		this.p0 = p0;
		this.p1 = p1;
		
		v = new Vector2D();
		v.x = p1.x - p0.x;
		v.y = p1.y - p0.y;
	}
	
	public ParamLine2D(ParamLine2D pl){
		this.p0 = pl.p0;
		this.p1 = pl.p1;
		this.v = pl.v;
	}
	
	
	public void printParamLine2D(){
		System.out.println("PL2D: p0(" + p0.x + "," + p0.y + ") p1(" + 
	p1.x + "," + p1.y + ") v = (" + v.x + "," + v.y + ") \n");
	}
	
	public Vector2D computeParamLine2D(float t){
		Vector2D v = new Vector2D();
		
		v.x = this.p0.x + this.v.x*t;
		v.y = this.p0.y + this.v.y*t;
		
		return v;
	}
	
	public int intersectParamLine2D(ParamLine2D p1, float t1, float t2){
		
		float detp1p2 = this.v.x*p1.v.y - this.v.y*p1.v.x;
		if(Math.abs(detp1p2) <= Const3D.EPSILON_E5){
			return Const3D.PARAM_LINE_NO_INTERSECT;			
		}
		
		t1 = (p1.v.x*(this.p0.y - p1.p0.y) - p1.v.y*(this.p0.x - p1.p0.x));
		t2 = (this.v.x*(this.p0.y - p1.p0.y) - this.v.y*(this.p0.x - p1.p0.x));
		
		if(t1>=0 && t1<=1 && t2>=0 && t2<=1){
			return Const3D.PARAM_LINE_INTERSECT_IN_SEGMENT;
		}else{
			return Const3D.PARAM_LINE_INTERSECT_OUT_SEGMENT;
		}
	}
	
	public int intersectParamLine2D(ParamLine2D p1, Vector2D p){
		float detp1p2 = this.v.x*p1.v.y - this.v.y*p1.v.x;
		if(Math.abs(detp1p2) <= Const3D.EPSILON_E5){
			return Const3D.PARAM_LINE_NO_INTERSECT;			
		}
		
		p.x = (p1.v.x*(this.p0.y - p1.p0.y) - p1.v.y*(this.p0.x - p1.p0.x));
		p.y = (this.v.x*(this.p0.y - p1.p0.y) - this.v.y*(this.p0.x - p1.p0.x));
		
		if(p.x>=0 && p.x<=1 && p.y>=0 && p.y<=1){
			return Const3D.PARAM_LINE_INTERSECT_IN_SEGMENT;
		}else{
			return Const3D.PARAM_LINE_INTERSECT_OUT_SEGMENT;
		}
	}
}
