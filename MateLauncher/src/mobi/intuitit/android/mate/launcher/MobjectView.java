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

public class MobjectView extends GridView implements
		AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener,
		DragSource {

	private DragController mDragger;
	private Launcher mLauncher;
	private Bitmap mTexture;
	private Paint mPaint;
	private int mTextureWidth;
	private int mTextureHeight;
	
	public MobjectView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MobjectView(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.gridViewStyle);
	}

	public MobjectView(Context context, AttributeSet attrs, int defStyle) {
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
	public void onDropCompleted(View target, boolean success) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onFinishInflate() {
		setOnItemClickListener(this);
		setOnItemLongClickListener(this);
	}

	public boolean isOpaque() {
		return !mTexture.hasAlpha();
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

	public void setDragger(DragController dragger) {
		mDragger = dragger;
	}

	void setLauncher(Launcher launcher) {
		mLauncher = launcher;
	}

	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		if (!view.isInTouchMode()) {
			return false;
		}
		
		if(!mLauncher.mMDockbar.isDraggable())
			return false;

		ItemInfo app = (ItemInfo) parent
				.getItemAtPosition(position);
		app = new ItemInfo(app);

		mDragger.startDrag(view, this, app, DragController.DRAG_ACTION_COPY);
		mLauncher.closeObjectView();
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		if(mLauncher.mMDockbar.mMDockbarMode == mLauncher.mMDockbar.WALLPAPER_MODE) {
			MLayout mLayout = mLauncher.getCurrentMLayout();
			mLayout.mWallpaperRes = getResources().getDrawable(MImageList.getInstance().wallpaperList.get(arg2));
			
			mLauncher.mMDockbar.mMDockbarMode = mLauncher.mMDockbar.HIDE_MODE;
			this.setVisibility(View.GONE);
		}
		
		if(mLauncher.mMDockbar.mMDockbarMode == mLauncher.mMDockbar.FLOORING_MODE) {
			MLayout mLayout = mLauncher.getCurrentMLayout();
			mLayout.mFlooringRes = getResources().getDrawable(MImageList.getInstance().flooringList.get(arg2));
			
			mLauncher.mMDockbar.mMDockbarMode = mLauncher.mMDockbar.HIDE_MODE;
			mLauncher.mMDockbar.invalidate();
			this.setVisibility(View.GONE);
		}
	}
	
}