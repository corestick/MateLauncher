package mobi.intuitit.android.mate.launcher;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class Dockbar extends GridView implements
		AdapterView.OnItemClickListener {
	
	private Bitmap mTexture;
	private Paint mPaint;
	private int mTextureWidth;
	private int mTextureHeight;
	private Launcher mLauncher;

	public Dockbar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public Dockbar(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.gridViewStyle);
	}

	public Dockbar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.AllAppsGridView, defStyle, 0);
		final int textureId = a.getResourceId(
				R.styleable.AllAppsGridView_texture, 0);
		if (textureId != 0) {
			mTexture = BitmapFactory.decodeResource(getResources(), textureId);
			mTextureWidth = mTexture.getWidth();
			mTextureHeight = mTexture.getHeight();

			mPaint = new Paint();
			mPaint.setDither(false);
		}
		a.recycle();
	}

	@Override
	public void draw(Canvas canvas) {
		final Bitmap texture = mTexture;
		final Paint paint = mPaint;

		final int width = getWidth();
		final int height = getHeight();

		final int textureWidth = mTextureWidth;
		final int textureHeight = mTextureHeight;

		int x = 0;
		int y;

		while (x < width) {
			y = 0;
			while (y < height) {
				canvas.drawBitmap(texture, x, y, paint);
				y += textureHeight;
			}
			x += textureWidth;
		}

		super.draw(canvas);
	}

	public void setLauncher(Launcher launcher) {
		mLauncher = launcher;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

}
