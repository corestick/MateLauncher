package mobi.intuitit.android.mate.launcher;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Triangle {
	// 삼각형을 그리기 위한 정점 배열
	float[] vertices = { 0.0f, 2.0f, 0.0f, // v1
			-0.5f, 1.0f, 0.0f, // v2
			0.5f, 1.0f, 0.0f, // v3
	};

	// 정점 배열을 담을 FloatBuffer
	public FloatBuffer verticesBuffer;

	// 생성자
	public Triangle() {
		// 정점 배열을 FloatBuffer 에 담는다.
		verticesBuffer = MyUtil.getFloatBuffer(vertices);
	}

	// 그리는 메서드
	public void draw(GL10 gl) {
		// 정점을 이용해 그릴 수 있도록 세팅
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// 그리기 방향(GL_CW : 시계방향, GL_CCW : 시계 반대 방향)
		gl.glFrontFace(GL10.GL_CCW);
		// 단색으로 색칠하기
		// gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
		gl.glColor4f(0 / 255f, 0 / 255f, 255f / 255f, 1f);
		// 그릴 정점 배열 세팅
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);

		// Draw
		// gl.glDrawArrays(GL10.GL_POINTS, 0, 3); // 점으로 그리기
		// gl.glDrawArrays(GL10.GL_LINES, 0, 3); // 정점을 두 개씩 선으로 연결한다.
		// gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 3); // 점을 순서대로 선으로 연결한다.
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3); // 삼각형을 그린다.
		// gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 3); // 정점 순서대로 삼각형을 그린다.
		// gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 3); // 처음 정점을 기준으로 부채꼴 모양으로
		// 그린다.

		// 정점을 이용해 그릴 수 있도록 세팅된 것 해제
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
}
