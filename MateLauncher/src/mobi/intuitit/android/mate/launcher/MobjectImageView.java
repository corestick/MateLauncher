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
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
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

//	public void startAnimation() {
//		Animation anim = new RotateAnimation(-1, 1, Animation.RELATIVE_TO_SELF,
//				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//		anim.setDuration(300);
//
//		this.startAnimation(anim);
//	}

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
		
		super.draw(canvas);
		
		// 타이틀 표시
		ItemInfo info = (ItemInfo) this.getTag();
				
		Paint paint = new Paint();
		paint.setStyle(Style.FILL);
		
		paint.setColor(Color.WHITE);
		paint.setTextSize(20);
		paint.setFakeBoldText(true);
		paint.setAntiAlias(true);
		paint.setShadowLayer(5, 3, 3, Color.RED);
		
		String title = "";
		
		if (Launcher.modifyMode) {
			if (info.contact_num != null)
			{
//				Log.e("RRR", "contact_name=" + info.contact_name);
				title = info.contact_name;
			}
			else if (info.title != null)
			{
//				Log.e("RRR", "title=" + info.title.toString());
				title = info.title.toString();
			}
		}
		
		title = TextUtils.ellipsize(title, new TextPaint(), this.getWidth() / 2, TruncateAt.END).toString();
		
		Rect b = new Rect();
		paint.getTextBounds(title, 0, title.length(), b);
		
		canvas.drawText(title, this.getWidth() / 2 - b.width() / 2, this.getHeight() / 2, paint);
		
	}
}
