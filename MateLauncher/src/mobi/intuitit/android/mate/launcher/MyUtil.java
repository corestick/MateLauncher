package mobi.intuitit.android.mate.launcher;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class MyUtil {

	// byte �� �迭�� ByteBuffer ��ü�� ������ִ� �޼ҵ�
	public static ByteBuffer getByteBuffer(byte[] array) {
		// ByteBuffer ��ü ����
		ByteBuffer buffer = ByteBuffer.allocateDirect(array.length);
		// ���ڰ����� ���� �迭�� ���� ByteBuffer�� �־��ش�.
		buffer.put(array);
		// �迭�� ���� �� �� ����ϴ� �������� ��ġ�� ���� ó�� ��ġ�� ��ġ��Ų��.
		buffer.position(0);

		return buffer;
	}

	// float �� �迭�� FloatBuffer ��ü�� ������ִ� �޼���
	public static FloatBuffer getFloatBuffer(float[] array) {
		// ByteBuffer ���� float �� 4����Ʈ �̹Ƿ� *4 �� ��
		ByteBuffer tempBuffer = ByteBuffer.allocateDirect(array.length * 4);
		// �����͸� ������ ��� �������� Ordering ����
		tempBuffer.order(ByteOrder.nativeOrder());
		// ByteBuffer �� FloatBuffer �� ��
		FloatBuffer buffer = tempBuffer.asFloatBuffer();
		// ������ ��
		buffer.put(array);
		// ��ġ �ʱ�ȭ
		buffer.position(0);

		return buffer;
	}

	// int �� �迭�� IntBuffer �� ������ִ� �޼���
	public static IntBuffer getIntBuffer(int[] array) {
		ByteBuffer tempBuffer = ByteBuffer.allocateDirect(array.length * 4);
		tempBuffer.order(ByteOrder.nativeOrder());
		IntBuffer buffer = tempBuffer.asIntBuffer();
		buffer.put(array);
		buffer.position();
		return buffer;
	}

	// short �� �迭�� ShortBuffer�� ������ִ� �޼���
	public static ShortBuffer getShortBuffer(short[] array) {
		ByteBuffer tempBuffer = ByteBuffer.allocateDirect(array.length * 2);
		tempBuffer.order(ByteOrder.nativeOrder());
		ShortBuffer buffer = tempBuffer.asShortBuffer();
		buffer.put(array);
		buffer.position(0);
		return buffer;
	}
}
