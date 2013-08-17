package mobi.intuitit.android.mate.launcher;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

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
		Log.e("M-mirror", info.icon_mirror + "");
		if (info.icon_mirror == 1) {
			this.setBackgroundDrawable(flipDrawable(getResources()
					.getDrawable(
							MImageList.getInstance().getIcon(info.mobjectType,
									info.mobjectIcon))));
		} else {
			this.setBackgroundResource(MImageList.getInstance().getIcon(
					info.mobjectType, info.mobjectIcon));
		}
		// this.setCompoundDrawablesWithIntrinsicBounds(0,
		// MImageList.getInstance().getIcon(
		// info.mobjectType, info.mobjectIcon), 0, 0);

	}
	
	Drawable flipDrawable(Drawable d)
	{
	    Matrix m = new Matrix();
	    m.preScale(-1, 1);
	    Bitmap src = ((BitmapDrawable) d).getBitmap();
	    Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
	    dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
	    return new BitmapDrawable(dst);
	}

	public void mirrorImage() {

		ItemInfo info = (ItemInfo) getTag();
		Drawable d = getResources().getDrawable(MImageList.getInstance().getIcon(
				info.mobjectType, info.mobjectIcon));		
		
		this.setBackgroundDrawable(flipDrawable(d));
	}

	// public void setTitle(boolean isModifyMode) {
	// if (isModifyMode) {
	// ItemInfo info = (ItemInfo) this.getTag();
	//
	// if (info.contact_num != null)
	// this.setText(info.contact_name);
	// else
	// this.setText(info.title);
	// } else {
	// this.setText("");
	// }
	// }

	public void startAnimation() {
		Animation anim = new RotateAnimation(-1, 1, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		anim.setDuration(300);

		this.startAnimation(anim);
		// // 수정모드에서 타이틀 표시
		// setTitle(true);
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
	}
}
