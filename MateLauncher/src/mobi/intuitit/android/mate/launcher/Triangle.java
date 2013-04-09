package mobi.intuitit.android.mate.launcher;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Triangle {
	// �ﰢ���� �׸��� ���� ���� �迭
	float[] vertices = { 0.0f, 2.0f, 0.0f, // v1
			-0.5f, 1.0f, 0.0f, // v2
			0.5f, 1.0f, 0.0f, // v3
	};

	// ���� �迭�� ���� FloatBuffer
	public FloatBuffer verticesBuffer;

	// ������
	public Triangle() {
		// ���� �迭�� FloatBuffer �� ��´�.
		verticesBuffer = MyUtil.getFloatBuffer(vertices);
	}

	// �׸��� �޼���
	public void draw(GL10 gl) {
		// ������ �̿��� �׸� �� �ֵ��� ����
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// �׸��� ����(GL_CW : �ð����, GL_CCW : �ð� �ݴ� ����)
		gl.glFrontFace(GL10.GL_CCW);
		// �ܻ����� ��ĥ�ϱ�
		// gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
		gl.glColor4f(0 / 255f, 0 / 255f, 255f / 255f, 1f);
		// �׸� ���� �迭 ����
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);

		// Draw
		// gl.glDrawArrays(GL10.GL_POINTS, 0, 3); // ������ �׸���
		// gl.glDrawArrays(GL10.GL_LINES, 0, 3); // ������ �� ���� ������ �����Ѵ�.
		// gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 3); // ���� ������� ������ �����Ѵ�.
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3); // �ﰢ���� �׸���.
		// gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 3); // ���� ������� �ﰢ���� �׸���.
		// gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 3); // ó�� ������ �������� ��ä�� �������
		// �׸���.

		// ������ �̿��� �׸� �� �ֵ��� ���õ� �� ����
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
}
