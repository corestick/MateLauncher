package mobi.intuitit.android.mate.launcher;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

public class MyRenderer implements Renderer {
	// �ﰢ�� ��ü�� ��������
	Triangle triangle;

	// �簢�� ��ü�� ��������
	Square square;

	// ������
	public MyRenderer() {
		// �ﰢ�� ��ü ����
		triangle = new Triangle();
		// �簢�� ��ü ����
		square = new Square();
	}

	// ��� �ϳ��� �׸��� ���Ǵ� �޼ҵ��̴�.
	// ��ǥ���̳� �� ȸ�� ������ �����ϸ鼭 �ݺ��ؼ� ȣ���ϸ�
	// �����̰ų� ȸ���ϴ� ����� �� �� �ִ�.

	@Override
	public void onDrawFrame(GL10 gl) {
		// ȭ���� �ʱ�ȭ �Ѵ�.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// ��ȯ��� �ʱ�ȭ
		gl.glLoadIdentity();
		// ��ǥ�� �̵�
		gl.glTranslatef(0.0f, 0.0f, -6.0f);
		// �ﰢ���� �׸���.
		triangle.draw(gl);
		// �簢���� �׸���.
		square.draw(gl);

	}

	// GLSurfaceView �� ��ȭ�� ������ �� ȣ��ȴ�.
	// ���α��̿� ���α��̸� ���ڷ� �޴´�.

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// ViewPort(�޴����� ���̴� ȭ��) ����
		gl.glViewport(0, 0, width, height);
		// 3D �� �𵨸��� ��ü�� 2D ȭ������ ���ϴ� ����� �����Ѵ�.(�������� �Ѵ�.)
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// ��ȯ ���(ȸ��, ��ǥ � ���� �����۾��� �� �� ���̴� ��� ���) �ʱ�ȭ
		gl.glLoadIdentity();
		// ���� ���� ����� ����ϰڴٰ� ����
		// ���� ���� : ���� ��ü���� �Ÿ��� ������� ��� ������ ũ��� ���´�.
		// ���� ���� : ���� ��ü���� �Ÿ��� ����Ͽ� ���ٰ��� �ְ� ��ü�� �����Ѵ�.
		// 45.0f : ����� ���� ���� 45�� �Ʒ��� 45���� �� �� �ֱ� ������ �þ߰��� 45���� �����Ѵ�.
		// 1.0f : ���� ���� �� �� ���� ����� ���� ��ǥ
		// 30.0f : ���ٰ��� �� �� ���� �� ���� ��ǥ
		GLU.gluPerspective(gl, 45.0f, (float) width / height, 1.0f, 30.0f);
		// ��ü�� �̵��� ȸ���� ����� �� ���� ����� �ε��Ѵ�.
		// ModelView : 3D ���� �� ��ġ�� ���� ȸ���̳� �̵��ϴ� ���� ���Ѵ�.
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// ��� �ʱ�ȭ
		gl.glLoadIdentity();
	}

	// GLSurfaceView Ŭ������ ��ü�� ������ �� ȣ��ȴ�.

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// �ٸ�ü�� �׸� �� ���� ������ �� ȥ�յǾ� ���� �� �ֵ��� ����
		gl.glShadeModel(GL10.GL_SMOOTH);
		// ȭ�� ��� ���� ����
		// Red, Green, Blue, Alpha
		// ���� 4����, Alpha(����)
		// ���� 0.0~1.0 ���� �� �� ������ ������ �󵵸� ����Ѵ�.
		gl.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
		// 255, 127, 211
		// gl.glClearColor(255f/255f, 127f/255f, 211f/255f, 1.0f);
		// Depth Buffer �ʱ�ȭ
		gl.glClearDepthf(1.0f);
		// Depth Buffer�� ����� �� �ֵ��� (Depth �� �񱳰� �����ϵ���) �������ش�.
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// �ٸ�ü�� �׸� �� ���̰� ���� �͸� �׸����� �����Ѵ�.
		// ���̰� Ŭ���� �þ߿��� �־����� �Ǵµ� ����� �þ߿��� �����
		// �������ϸ� �׸��� �۾��� �ϰԵȴ�.
		// �þ߿��� ����� ��ü�� �������� �����۾��� ���� �ʰ� �����Ͽ�ó�� �ӵ� �� �޸� ����,
		// ���¼Ҹ� �����ش�.
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// ������ ǰ���� �ֻ��� ǰ���� �۾��Ѵ�.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}
}
