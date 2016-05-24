package main3D;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import object3D.Camera4D;
import object3D.Object4D;
import object3D.Poly4D;
import object3D.RenderList4D;

import math3D.Matrix4x4;
import math3D.Vector4D;
import pipeline3D.Pipeline;

public class GameFrame extends Frame {

	
	Vector4D camPos = new Vector4D(0, 0, -50, 1);
	Vector4D camDir = new Vector4D(0, 0, 0, 1);

	Vector4D vScale = new Vector4D(0.5f, 0.5f, 0.5f, 1), 
			 vPos = new Vector4D(0, 0, 0, 1), 
			 vRot = new Vector4D(0, 0, 0, 1);

	Camera4D cam;
	RenderList4D rList = RenderList4D.getInstance();
	Object4D cube = new Object4D();
	
	Vector4D poly1Pos = new Vector4D(0, 0, 200, 1);

	FrameThread ft;

	int angY;

	private Image offScreenImage = null;

	public static void main(String[] args) {
		
		new GameFrame().launchFrame();
	}

	public void launchFrame() {
		
		engineInit();

		this.setTitle("GameFrame3D");
		this.setBounds(Const3D.WINDOW_POS_X, Const3D.WINDOW_POS_Y,
				Const3D.WINDOW_WIDTH, Const3D.WINDOW_HEIGHT);
		this.setLayout(new FlowLayout());
		this.setVisible(true);
		this.setResizable(false);
		this.setBackground(Color.BLACK);

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}

		});

		this.addKeyListener(new FrameKeyListener());

		ft = new FrameThread();
		Thread t = new Thread(ft);
		t.start();
		
	}

	@Override
	public void paint(Graphics g) {
		
		engineMain(g);

	}

	@Override
	public void update(Graphics g) {
		if (offScreenImage == null)
			offScreenImage = this.createImage(Const3D.WINDOW_WIDTH, Const3D.WINDOW_HEIGHT);

		Graphics offG = offScreenImage.getGraphics();

		Color c = offG.getColor();

		offG.setColor(Color.BLACK);
		offG.fillRect(0, 0, Const3D.WINDOW_WIDTH, Const3D.WINDOW_HEIGHT);
		paint(offG);

		offG.setColor(c);

		g.drawImage(offScreenImage, 0, 0, null);
	}

	class FrameThread implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					repaint();
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void engineInit(){
		//初始化柱体
		cube.attr = 0;
		cube.id = 0;
		cube.state = Const3D.OBJECT4DV1_STATE_ACTIVE;
		cube.name = "cube";
		cube.pos = new Vector4D(0, -100, 200, 1);
		cube.vertNums = 8;
		
		Vector4D[] verts = {
			new Vector4D(50, 50, 50, 1),    //0
			new Vector4D(50, -50, 50, 1),   //1
			new Vector4D(-50, -50, 50, 1),  //2
			new Vector4D(-50, 50, 50, 1),   //3
			new Vector4D(50, 50, -50, 1),   //4
			new Vector4D(50, -50, -50, 1),  //5
			new Vector4D(-50, -50, -50, 1), //6
			new Vector4D(-50, 50, -50, 1),  //7
		};
		
		for(int i=0; i<verts.length; i++){
			cube.vertsLocal.add(verts[i]);
			cube.vertsTrans.add(verts[i]);
		}
		
		int[] indices = {
				0, 1, 2, 0, 2, 3,//top
				7, 6, 5, 7, 5, 4,//bot
				3, 2, 6, 3, 6, 7,//left
				4, 5, 1, 4, 1, 0,//right
				4, 0, 3, 4, 3, 7,//front
				1, 5, 6, 1, 6, 2 //back
		};
		
		cube.polyNum = 12;
		for(int i=0; i<12; i++){
			Poly4D p = new Poly4D();
			p.state = Const3D.POLY4DV1_STATE_ACTIVE;
			p.color = Color.BLUE;
			p.attr = 0;
			p.vList = cube.vertsTrans;
			p.index[0] = indices[0+3*i];
			p.index[1] = indices[1+3*i];
			p.index[2] = indices[2+3*i];
			cube.polyList.add(p);
		}
		
		cam = new Camera4D();
	/*	int camAttr, // 相机类型-->UVN || Eula
		Vector4D camPos, // 相机的初始位置
		Vector4D camDir, // 相机的初始角度
		Vector4D camTarget, // UVN相机的初始目标位置
		float nearCilpZ, float farClipZ, // 近远裁剪面
		float fov, // 视角
		float viewportWidth, float viewportHeight
		*/
		cam.initCamera4DV1(Const3D.CAM_MODEL_EULER, camPos, camDir, null,
				50.0f, 500.0f, 90.0f, 480, 320);
		
	}
	
	public void engineMain(Graphics g){

		if (++angY > 360) {
			angY = 0;
		}	
		Matrix4x4 mRot = Pipeline.buildXYZRotationMatrix4x4(0, angY, 0);
		
		Pipeline.transformObject4D(cube, mRot, Const3D.TRANSFORM_LOCAL_TO_TRANS, 0);
		
		Pipeline.modelToWorldObject4D(cube, Const3D.TRANSFORM_TRANS_ONLY);
		
		cam.buildCamera4DV1MatrixEuler(Const3D.CAM_ROT_SEQ_ZXY);
		
		Pipeline.worldToCameraObject4D(cube, cam);
		
		Pipeline.cameraToPerspectiveObject4D(cube, cam);
		
		Pipeline.perspectiveToScreenObject4D(cube, cam);
		
		Pipeline.removeBackfacesObject4D(cube, cam);
	
		Pipeline.drawObject4D(cube, g);
		
	}
	
	public void engineExit(){
		
	}
	
}
