package main3D;

import math3D.Vector4D;
import object3D.Camera4D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FrameKeyListener implements KeyListener {

	Camera4D cam;
	public FrameKeyListener(Camera4D cam){
		this.cam = cam;
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		// TODO Auto-generated method stub
		int keyCode = keyEvent.getKeyCode();

		cam.getPos().vector4DPrint();
		cam.getDir().vector4DPrint();

		Vector4D camPos = cam.getPos();
		Vector4D camDir = cam.getDir();
		switch(keyCode){
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			break;
		case KeyEvent.VK_UP:
			camPos.z++;
			break;
		case KeyEvent.VK_DOWN:
			camPos.z--;
			break;
		case KeyEvent.VK_LEFT:
			//camPos.x--;
			cam.buildCamera4DV1MatrixUVN(0);
			break;
		case KeyEvent.VK_RIGHT:
			//camPos.x++;
			camDir.x--;
			camDir.y--;
			break;
		default :
				break;
		}

		cam.setPos(camPos);
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		// TODO Auto-generated method stub


	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {
		// TODO Auto-generated method stub

	}

}
