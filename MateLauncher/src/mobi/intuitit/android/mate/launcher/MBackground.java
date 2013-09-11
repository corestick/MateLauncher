package mobi.intuitit.android.mate.launcher;

import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * 
 * @author ������ ���� �̹����� ���� ����Ʈ �� �ٴ�, ����(����/������) �н� �� ��輱 �н� ��
 */
public class MBackground {

	public static MBackground mBackground = new MBackground();

	private Vector<MBack> mBackList;

	private Bitmap mBitmap;
	private Paint mPaint;
	private Paint mStrokePaint;
	private Path mPath;
	private int mWidth;
	private int mHeight;

	private float mWallPoint;

	private MBackground() {
		mBackList = new Vector<MBack>();

		MBack mBack = new MBack();
		mBack.resId = R.drawable.m_wall_01;
		mBack.leftRGB = Color.rgb(195, 214, 47);
		mBack.rightRGB = Color.rgb(167, 186, 39);
		mBack.bottomRGB = Color.rgb(135, 153, 31);
		mBackList.add(mBack);

		mBack = new MBack();
		mBack.resId = R.drawable.m_wall_02;
		mBack.leftRGB = Color.rgb(247, 181, 182);
		mBack.rightRGB = Color.rgb(240, 120, 126);
		mBack.bottomRGB = Color.rgb(237, 76, 89);
		mBackList.add(mBack);
		
		mBack = new MBack();
		mBack.resId = R.drawable.m_wall_03;
		mBack.leftRGB = Color.rgb(255, 227, 171);
		mBack.rightRGB = Color.rgb(252, 206, 131);
		mBack.bottomRGB = Color.rgb(250, 180, 82);
		mBackList.add(mBack);
		
		mBack = new MBack();
		mBack.resId = R.drawable.m_wall_04;
		mBack.leftRGB = Color.rgb(199, 157, 198);
		mBack.rightRGB = Color.rgb(173, 120, 176);
		mBack.bottomRGB = Color.rgb(157, 93, 161);
		mBackList.add(mBack);
		
		mBack = new MBack();
		mBack.resId = R.drawable.m_wall_05;
		mBack.leftRGB = Color.rgb(247, 190, 215);
		mBack.rightRGB = Color.rgb(240, 163, 198);
		mBack.bottomRGB = Color.rgb(235, 127, 177);
		mBackList.add(mBack);
	}
	
	public static MBackground getInstance() {
		return mBackground;
	}
	
	public Drawable getBackground(int argWidth, int argHeight, int argIdx) {
		
		mPath = new Path();

		this.mWidth = argWidth;
		this.mHeight = argHeight;
		mWallPoint = (float) (mHeight / 2 + (mWidth / 2) / 1.7);
		
		Bitmap bitmap = Bitmap.createBitmap(argWidth,
				argHeight, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		
		paint.setColor(mBackList.get(argIdx).leftRGB);
		canvas.drawPath(mBackground.getLeftPath(), paint);
		
		paint.setColor(mBackList.get(argIdx).rightRGB);
		canvas.drawPath(mBackground.getRightPath(), paint);

		paint.setColor(mBackList.get(argIdx).bottomRGB);
		canvas.drawPath(mBackground.getBottomPath(), paint);

		return (Drawable) (new BitmapDrawable(bitmap));
	}

	public void setBitmap(BitmapDrawable bd) {
		mBitmap = bd.getBitmap();
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setShader(new BitmapShader(mBitmap, TileMode.REPEAT,
				TileMode.REPEAT));
	}

	public Path getLeftPath() {
		mPath.reset();

		mPath.moveTo(0, 0);
		mPath.lineTo(mWidth / 2, 0);
		mPath.lineTo(mWidth / 2, mHeight / 2);
		mPath.lineTo(0, mWallPoint);
		mPath.lineTo(0, 0);

		return mPath;
	}

	public Path getRightPath() {
		mPath.reset();

		mPath.moveTo(mWidth / 2, 0);
		mPath.lineTo(mWidth / 2, mHeight / 2);
		mPath.lineTo(mWidth, mWallPoint);
		mPath.lineTo(mWidth, 0);
		mPath.lineTo(mWidth / 2, 0);

		return mPath;
	}

	public Path getBottomPath() {
		mPath.reset();

		mPath.moveTo(0, mWallPoint);
		mPath.lineTo(mWidth / 2, mHeight / 2);
		mPath.lineTo(mWidth, mWallPoint);
		mPath.lineTo(mWidth, mHeight);
		mPath.lineTo(0, mHeight);
		mPath.lineTo(0, mWallPoint);

		return mPath;
	}

	class MBack {
		int resId;
		int leftRGB;
		int rightRGB;
		int bottomRGB;
	}
}
