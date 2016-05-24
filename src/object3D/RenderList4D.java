package object3D;

import java.util.ArrayList;


public class RenderList4D {

		private static RenderList4D renderList4d = null;
		
		private int state;
		private int attr;
		private int polyNum;

		private ArrayList<PolyF4D> polyList = new ArrayList<PolyF4D>();

	    private RenderList4D() {

	       // Exists only to defeat instantiation.

	    }

	 

	    public static synchronized RenderList4D getInstance() {

	       if (renderList4d == null) {

	    	   renderList4d = new RenderList4D();

	       }

	       return renderList4d;

	    }



		public ArrayList<PolyF4D> getPolyList() {
			return polyList;
		}

		public int getState() {
			return state;
		}



		public void setState(int state) {
			this.state = state;
		}



		public int getAttr() {
			return attr;
		}



		public void setAttr(int attr) {
			this.attr = attr;
		}



		public int getPolyNum() {
			return polyNum;
		}



		public void setPolyNum(int polyNum) {
			this.polyNum = polyNum;
		}
}
