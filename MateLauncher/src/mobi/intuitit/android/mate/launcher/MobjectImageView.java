package mobi.intuitit.android.mate.launcher;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MobjectImageView extends ImageView {
	private boolean mBackgroundSizeChanged;
	private Drawable mBackground;

	public MobjectImageView(Context context) {
		super(context);
	}

	public MobjectImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MobjectImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void initMobjectView() {

		// setFocusable(true);
		// mBackground = getBackground();
		// setBackgroundDrawable(null);
		// mBackground.setCallback(this);

		ItemInfo info = (ItemInfo) getTag();

		if (info.reverseIcon == 0) {
			this.setBackgroundResource(MImageList.getInstance().getIcon(
					info.mobjectType, info.mobjectIcon));
		} else {
			Drawable d = getResources().getDrawable(
					MImageList.getInstance().getIcon(info.mobjectType,
							info.mobjectIcon));

			this.setBackgroundDrawable(flipDrawable(d));
		}

		// this.setCompoundDrawablesWithIntrinsicBounds(0, MImageList
		// .getInstance().getIcon(info.mobjectType, info.mobjectIcon), 0,
		// 0);

	}

	Drawable flipDrawable(Drawable d) {
		Matrix m = new Matrix();
		m.preScale(-1, 1);
		Bitmap src = ((BitmapDrawable) d).getBitmap();
		Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(),
				src.getHeight(), m, false);
		dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
		return new BitmapDrawable(dst);
	}

	public void reverseImg() {

		ItemInfo info = (ItemInfo) getTag();

		if (info.reverseIcon == 0) {
			info.reverseIcon = 1;
			Drawable d = getResources().getDrawable(
					MImageList.getInstance().getIcon(info.mobjectType,
							info.mobjectIcon));

			this.setBackgroundDrawable(flipDrawable(d));
		} else {
			info.reverseIcon = 0;
			this.setBackgroundResource(MImageList.getInstance().getIcon(
					info.mobjectType, info.mobjectIcon));
		}

		this.setTag(info);

		MLayout mLayout = (MLayout) (this.getParent());
		final ContentValues values = new ContentValues();
		final ContentResolver cr = mLayout.mLauncher.getContentResolver();
		values.put(LauncherSettings.Favorites.REVERSE_ICON, info.reverseIcon);
		cr.update(LauncherSettings.Favorites.getContentUri(info.id, false),
				values, null, null);
	}

	public BitmapDrawable writeOnDrawable(int resId, String text) {
		Bitmap bm = BitmapFactory.decodeResource(getResources(), resId).copy(
				Bitmap.Config.ARGB_8888, true);

		Paint paint = new Paint();
		paint.setStyle(Style.FILL);
		paint.setColor(Color.BLACK);
		paint.setTextSize(30);

		Canvas canvas = new Canvas(bm);
		canvas.drawText(text, 0, bm.getHeight() / 2, paint);

		return new BitmapDrawable(bm);
	}

	public void setTitle(boolean isModifyMode) {
		ItemInfo info = (ItemInfo) this.getTag();
		
		Log.e("RRR", "isModifyMode=" + isModifyMode);
		
		this.isModifyMode = isModifyMode;
		
//		if (isModifyMode) {
//
//			if (info.contact_num != null)
//			{
//				Log.e("RRR", "contact_name=" + info.contact_name);
//				this.setBackgroundDrawable(writeOnDrawable(MImageList.getInstance().getIcon(
//						info.mobjectType, info.mobjectIcon), info.contact_name));
//			}
//			else if (info.title != null)
//			{
//				Log.e("RRR", "title=" + info.title.toString());
//				this.setBackgroundDrawable(writeOnDrawable(MImageList.getInstance().getIcon(
//						info.mobjectType, info.mobjectIcon), info.title.toString()));
//			}
//		} else {
//			Log.e("RRR", "asd=11");
//			this.setBackgroundDrawable(writeOnDrawable(MImageList.getInstance().getIcon(
//					info.mobjectType, info.mobjectIcon), ""));
//		}
	}

	public void startAnimation() {
		Animation anim = new RotateAnimation(-1, 1, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		anim.setDuration(300);

		this.startAnimation(anim);
	}

	@Override
	protected boolean setFrame(int left, int top, int right, int bottom) {
		if (getLeft() != left || getRight() != right || getTop() != top
				|| getBottom() != bottom) {
			mBackgroundSizeChanged = true;
		}
		return super.setFrame(left, top, right, bottom);
	}

	@Override
	protected boolean verifyDrawable(Drawable who) {
		return who == mBackground || super.verifyDrawable(who);
	}

	@Override
	protected void drawableStateChanged() {
		Drawable d = mBackground;
		if (d != null && d.isStateful()) {
			d.setState(getDrawableState());
		}
		super.drawableStateChanged();
	}

	private boolean isModifyMode = false;
	
	@Override
	public void draw(Canvas canvas) {
		final Drawable background = mBackground;
		if (background != null) {
			final int scrollX = getScrollX();
			final int scrollY = getScrollY();

			if (mBackgroundSizeChanged) {
				background.setBounds(0, 0, getRight() - getLeft(), getBottom()
						- getTop());
				mBackgroundSizeChanged = false;
			}

			if ((scrollX | scrollY) == 0) {
				background.draw(canvas);
			} else {
				canvas.translate(scrollX, scrollY);
				background.draw(canvas);
				canvas.translate(-scrollX, -scrollY);
			}
		}
		
		
		
		ItemInfo info = (ItemInfo) this.getTag();
		

		
		Paint paint = new Paint();
		paint.setStyle(Style.FILL);
		paint.setColor(Color.BLACK);
		paint.setTextSize(20);
		super.draw(canvas);
		
		if (isModifyMode) {
			
			if (info.contact_num != null)
			{
				Log.e("RRR", "contact_name=" + info.contact_name);

				canvas.drawText(info.contact_name, 0, 20, paint);
				
			}
			else if (info.title != null)
			{
				Log.e("RRR", "title=" + info.title.toString());
				canvas.drawText(info.title.toString(), 0, 25, paint);
			}
		} else {
			Log.e("RRR", "asd=11");
			canvas.drawText("", 0, 5, paint);
		}

		
	}
}
