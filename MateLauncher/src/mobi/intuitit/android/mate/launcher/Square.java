package mobi.intuitit.android.mate.launcher;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Square {
	// 정점 배열
	float[] vertices = { -0.5f, 0.5f, 0.0f, // v1(좌상단)
			-0.5f, -0.5f, 0.0f, // v2(좌하단)
			0.5f, -0.5f, 0.0f, // v3(우하단)
			0.5f, 0.5f, 0.0f, // v4(우상단)
	};

	// 정점 연결 정보
	byte[] indices = { 0, 3, 2, // v1-v4-v2
			0, 2, 1, // v1-v3-v2
	};

	// 색상 배열
	float[] colors = { 1f, 0, 0, 1f, 0, 1f, 0, 1f, 0, 0, 1f, 1f, 1f, 0, 1f, 1f, };

	FloatBuffer verticesBuffer;
	ByteBuffer indicesBuffer;
	FloatBuffer colorBuffer;

	// 생성자
	public Square() {
		verticesBuffer = MyUtil.getFloatBuffer(vertices);
		indicesBuffer = MyUtil.getByteBuffer(indices);
		colorBuffer = MyUtil.getFloatBuffer(colors);
	}

	public void draw(GL10 gl) {
		// 정점을 가지고 그릴 수 있도록 지정
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// 색상 정보를 가지고 색을 칠할 수 있도록 지정
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		// 그리는 방
		gl.glFrontFace(GL10.GL_CCW);
		// 그럴때 사용할 정점 배열 세팅
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);
		// 색상 세팅
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		// 삼각형의 조합으로 도형을 그린다.
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
				GL10.GL_UNSIGNED_BYTE, indicesBuffer);
		// 정점을 가지고 그릴 수 있는 모드 해제
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		// 색상 정보를 갖고 색칠할 수 있는 모드 해제
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
}
