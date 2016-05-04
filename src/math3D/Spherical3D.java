package math3D;

public class Spherical3D {
	public float p; //��ԭ��ľ���
	public float theta; //�߶�o->p����z��֮��ļн�
	public float phi; //�߶�o->p��x-yƽ���ϵ�ͶӰ����x��֮��ļн�
	
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
