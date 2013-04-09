package mobi.intuitit.android.mate.launcher;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Square {
	// ���� �迭
	float[] vertices = { -0.5f, 0.5f, 0.0f, // v1(�»��)
			-0.5f, -0.5f, 0.0f, // v2(���ϴ�)
			0.5f, -0.5f, 0.0f, // v3(���ϴ�)
			0.5f, 0.5f, 0.0f, // v4(����)
	};

	// ���� ���� ����
	byte[] indices = { 0, 3, 2, // v1-v4-v2
			0, 2, 1, // v1-v3-v2
	};

	// ���� �迭
	float[] colors = { 1f, 0, 0, 1f, 0, 1f, 0, 1f, 0, 0, 1f, 1f, 1f, 0, 1f, 1f, };

	FloatBuffer verticesBuffer;
	ByteBuffer indicesBuffer;
	FloatBuffer colorBuffer;

	// ������
	public Square() {
		verticesBuffer = MyUtil.getFloatBuffer(vertices);
		indicesBuffer = MyUtil.getByteBuffer(indices);
		colorBuffer = MyUtil.getFloatBuffer(colors);
	}

	public void draw(GL10 gl) {
		// ������ ������ �׸� �� �ֵ��� ����
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// ���� ������ ������ ���� ĥ�� �� �ֵ��� ����
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		// �׸��� ��
		gl.glFrontFace(GL10.GL_CCW);
		// �׷��� ����� ���� �迭 ����
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);
		// ���� ����
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		// �ﰢ���� �������� ������ �׸���.
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
				GL10.GL_UNSIGNED_BYTE, indicesBuffer);
		// ������ ������ �׸� �� �ִ� ��� ����
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		// ���� ������ ���� ��ĥ�� �� �ִ� ��� ����
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
}
