package main3D;

public class Const3D {
	
	public static final int WINDOW_POS_X = 100;
	public static final int WINDOW_POS_Y = 100;
	
	public static final int WINDOW_WIDTH = 480;
	public static final int WINDOW_HEIGHT = 480;
	
	public static final float PI = 3.141592654f;
	public static final float PI2 = 6.283185307f;
	public static final float PI_DIV_2 = 1.570796327f;
	public static final float PI_DIV_4 = 0.785398163f;
	public static final float PI_INV = 0.318309886f;
	
	//与定点数相关的常量
	public static final int FIXP16_SHIFT  = 16;
	public static final int FIXP16_MAG = 65536;
	public static final int FIXP16_DP_MASK = 0x0000ffff;
	public static final int FIXP16_WP_MASK = 0xffff0000;
	public static final int FIXP16_ROUND_UP = 0x00008000;

	//针对非常小的数
	public static final float EPSILON_E4 = ((float)1E-4);
	public static final float EPSILON_E5 = ((float)1E-5);
	public static final float EPSILON_E6 = ((float)1E-6);

	//参数化方程常量
	public static final int PARAM_LINE_NO_INTERSECT = 0;
	public static final int PARAM_LINE_INTERSECT_IN_SEGMENT = 1;
	public static final int PARAM_LINE_INTERSECT_OUT_SEGMENT = 2;
	public static final int PARAM_LINE_INTERSECT_EVERYWHERE = 3;
	
	public static final int POLY4DV1_ATTR_2SIDED = 0x0001;
	public static final int POLY4DV1_ATTR_TRANSPARENT = 0x0002;
	public static final int POLY4DV1_ATTR_8BITCOLOR = 0x0004;
	public static final int POLY4DV1_ATTR_RGB16 = 0x0008;
	public static final int POLY4DV1_ATTR_RGGB24 = 0x0010;

	public static final int POLY4DV1_ATTR_SHADE_MODE_PURE = 0x0020;
	public static final int POLY4DV1_ATTR_SHADE_MODE_FLAT = 0x0040;
	public static final int POLY4DV1_ATTR_SHADE_MODE_GOURAUD = 0x0080;
	public static final int POLY4DV1_ATTR_SHADE_MODE_PHONG = 0x0100;

	public static final int POLY4DV1_STATE_ACTIVE = 1;
	public static final int POLY4DV1_STATE_CLIPPED = 2;
	public static final int POLY4DV1_STATE_BACKFACE = 4;

	public static final int RENDERLIST4DV1_MAX_POLYS = 100;

	public static final int CAM_MODEL_EULER = 0;
	public static final int CAM_MODEL_UVN = 1;

	public static final int TRANSFORM_LOCAL_ONLY = 0 ;//对局部/模型顶点列表进行变换
	public static final int TRANSFORM_TRANS_ONLY = 1 ;//对变换后的顶点列表进行变换
	public static final int TRANSFORM_LOCAL_TO_TRANS = 2 ;//对局部顶点列表进行变换

	//剔除标志
	public static final int CULL_OBJECT_X_PLANE = 0x0001;
	public static final int CULL_OBJECT_Y_PLANE = 0x0002;
	public static final int CULL_OBJECT_Z_PLANE = 0x0004;
	//public static final int CULL_OBJECT_XYZ_PLANES (CULL_OBJECT_X_PLANE | CULL_OBJECT_Y_PLANE | CULL_OBJECT_Z_PLANE)

	//物体状态
	public static final int OBJECT4DV1_STATE_ACTIVE = 0x0001;
	public static final int OBJECT4DV1_STATE_VISIBLE = 0x0002;
	public static final int OBJECT4DV1_STATE_CULLED = 0x0004;
	//相机旋转顺序对应的值
	public static final int CAM_ROT_SEQ_XYZ = 0;
	public static final int CAM_ROT_SEQ_YXZ = 1;
	public static final int CAM_ROT_SEQ_XZY = 2;
	public static final int CAM_ROT_SEQ_YZX = 3;
	public static final int CAM_ROT_SEQ_ZYX = 4;
	public static final int CAM_ROT_SEQ_ZXY = 5;

	public static final int UVN_MODE_SPHERICAL = 0;
}
