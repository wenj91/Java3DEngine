package math3D;

public class ParamLine3D {

	public Vector3D p0, p1;
	public Vector3D v;
	
	public ParamLine3D(){
		p0 = new Vector3D();
		p1 = new Vector3D();
		
		v = new Vector3D();;
	}
	
	public ParamLine3D(Vector3D p0, Vector3D p1){
		this.p0 = p0;
		this.p1 = p1;
		
		v = new Vector3D();
		v.x = p1.x - p0.x;
		v.y = p1.y - p0.y;
		v.z = p1.z - p0.z;
	}
	
	public ParamLine3D(ParamLine3D pl){
		this.p0 = pl.p0;
		this.p1 = pl.p1;
		this.v = pl.v;
	}
	
	
	public void printParamLine3D(){
		System.out.println("PL3D: p0(" + p0.x + "," + p0.y + "," + p0.z + ") p1(" + 
	p1.x + "," + p1.y  + "," + p1.z + ") v = (" +
				v.x + "," + v.y + "," + v.z + ") \n");
	}

	public Vector3D computeParamLine3D(float t){
		Vector3D v = new Vector3D();
		
		v.x = p0.x + v.x*t;
		v.y = p0.y + v.y*t;
		v.z = p0.z + v.z*t;
		
		return v;
	}
}
