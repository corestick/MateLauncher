package mobi.intuitit.android.mate.launcher;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

public class MyRenderer implements Renderer {
	// 삼각형 객체의 참조변수
	Triangle triangle;

	// 사각형 객체의 참조변수
	Square square;

	// 생성자
	public MyRenderer() {
		// 삼각형 객체 생성
		triangle = new Triangle();
		// 사각형 객체 생성
		square = new Square();
	}

	// 장면 하나를 그릴때 사용되는 메소드이다.
	// 좌표값이나 축 회전 각도를 조절하면서 반복해서 호출하면
	// 움직이거나 회전하는 모습을 볼 수 있다.

	@Override
	public void onDrawFrame(GL10 gl) {
		// 화면을 초기화 한다.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// 변환행렬 초기화
		gl.glLoadIdentity();
		// 좌표축 이동
		gl.glTranslatef(0.0f, 0.0f, -6.0f);
		// 삼각형을 그린다.
		triangle.draw(gl);
		// 사각형을 그린다.
		square.draw(gl);

	}

	// GLSurfaceView 의 변화가 생겼을 때 호출된다.
	// 가로길이와 세로길이를 인자로 받는다.

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// ViewPort(휴대폰에 보이는 화면) 설정
		gl.glViewport(0, 0, width, height);
		// 3D 로 모델링된 물체를 2D 화면으로 변하는 방식을 지원한다.(투영으로 한다.)
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// 변환 행렬(회전, 좌표 등에 대한 연산작업을 할 때 쓰이는 모든 행렬) 초기화
		gl.glLoadIdentity();
		// 원근 투영 방식을 사용하겠다고 지정
		// 직교 투영 : 눈과 물체와의 거리에 관계없이 모두 동일한 크기로 나온다.
		// 원근 투영 : 눈과 물체와의 거리를 고려하여 원근감이 있게 물체를 투영한다.
		// 45.0f : 사람의 눈은 위로 45도 아래로 45도를 볼 수 있기 때문에 시야각을 45도로 설정한다.
		// 1.0f : 원근 감을 줄 때 가장 가까운 곳의 좌표
		// 30.0f : 원근감을 줄 때 가장 먼 곳의 좌표
		GLU.gluPerspective(gl, 45.0f, (float) width / height, 1.0f, 30.0f);
		// 물체의 이동과 회전을 계산할 때 쓰는 행렬을 로딩한다.
		// ModelView : 3D 공간 상에 위치한 모델이 회전이나 이동하는 것을 말한다.
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// 행렬 초기화
		gl.glLoadIdentity();
	}

	// GLSurfaceView 클래스의 객체가 생성될 때 호출된다.

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// 다면체를 그릴 때 색상 값들이 잘 혼합되어 보일 수 있도록 설정
		gl.glShadeModel(GL10.GL_SMOOTH);
		// 화면 배경 색상 지정
		// Red, Green, Blue, Alpha
		// 빛의 4원색, Alpha(투명도)
		// 값은 0.0~1.0 까지 줄 수 있으며 색상의 농도를 사용한다.
		gl.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
		// 255, 127, 211
		// gl.glClearColor(255f/255f, 127f/255f, 211f/255f, 1.0f);
		// Depth Buffer 초기화
		gl.glClearDepthf(1.0f);
		// Depth Buffer를 사용할 수 있도록 (Depth 값 비교가 가능하도록) 설정해준다.
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// 다면체를 그릴 때 깊이가 작은 것만 그리도록 설장한다.
		// 길이가 클수록 시야에서 멀어지게 되는데 사람의 시야에서 벗어나도
		// 렌더링하며 그리는 작업을 하게된다.
		// 시야에서 벗어나는 물체는 렌더링과 투영작업을 하지 않게 설정하여처리 속도 및 메모리 절약,
		// 전력소모를 막아준다.
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// 투영의 품질을 최상의 품질로 작업한다.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}
}
