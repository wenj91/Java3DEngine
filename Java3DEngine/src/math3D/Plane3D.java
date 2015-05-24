package math3D;

import main3D.Const3D;

public class Plane3D {
	Vector3D p;
	Vector3D vn;
	
	public Plane3D(){
		p = new Vector3D();	
		vn = new Vector3D();
	}
	
	public Plane3D(Vector3D p, Vector3D vn){
		this.p = p;
		this.vn = vn;
	}
	
	public Plane3D(float px, float py, float pz,
			float vx, float vy, float vz){
		p = new Vector3D();
		p.x = px;
		p.y = py;
		p.z = pz;
		
		vn = new Vector3D();
		vn.x = vx;
		vn.y = vy;
		vn.z = vz;
	}
	
	public void printPlane3D(){
		System.out.println("PLANE3D: p(" + p.x + "," + p.y + "," + p.z + ") vn = (" +
	vn.x + "," + vn.y + "," + vn.z + ")\n");
	}
	
	public float computePointInPlane3D(Vector3D pt){
		float hs = this.vn.x*(pt.x - this.p.x) + 
				this.vn.y*(pt.y - this.p.y) +
				this.vn.z*(pt.z - this.p.z);
		
		return hs;
	}
	
	public int intersectParamLine3DPlane(ParamLine3D pl, float t, Vector3D pt){
		
		float planeDotLine = pl.v.vector3DDot(this.vn);
		if(Math.abs(planeDotLine) <= Const3D.EPSILON_E5){
			if(Math.abs(this.computePointInPlane3D(pl.p0))<=Const3D.EPSILON_E5){
				return Const3D.PARAM_LINE_INTERSECT_EVERYWHERE;
			}else{
				return Const3D.PARAM_LINE_NO_INTERSECT;
			}
		}
		
		t = -(this.vn.x*pl.p0.x +
				this.vn.y*pl.p0.y + 
				this.vn.z*pl.p0.z -
				this.vn.x*this.p.x -
				this.vn.y*this.p.y -
				this.vn.z*this.p.z)/(planeDotLine);
		
		pt.x = pl.p0.x + pl.v.x*t;
		pt.y = pl.p0.y + pl.v.y*t;
		pt.z = pl.p0.z + pl.v.z*t;
		
		if(t>=0 && t<=1){
			return Const3D.PARAM_LINE_INTERSECT_IN_SEGMENT;
		}else{
			return Const3D.PARAM_LINE_INTERSECT_OUT_SEGMENT;
		}
	}
}
