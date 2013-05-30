package mobi.intuitit.android.mate.launcher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class MobjectImageView extends ViewGroup {
	private static final float CORNER_RADIUS = 8.0f;
	private static final float PADDING_H = 5.0f;
	private static final float PADDING_V = 1.0f;

	private final RectF mRect = new RectF();
	private Paint mPaint;

	private boolean mBackgroundSizeChanged;
	private Drawable mBackground;
	private float mCornerRadius;
	private float mPaddingH;
	private float mPaddingV;

	public MobjectImageView(Context context) {
		super(context);
		init();
	}

	public MobjectImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MobjectImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		setFocusable(true);
		mBackground = getBackground();
		setBackgroundDrawable(null);
		mBackground.setCallback(this);
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

//		final Layout layout = getLayout();
//		final RectF rect = mRect;
//		final int left = getCompoundPaddingLeft();
//		final int top = getExtendedPaddingTop();
//
//		rect.set(left + layout.getLineLeft(0) - mPaddingH,
//				top + layout.getLineTop(0) - mPaddingV, Math.min(
//						left + layout.getLineRight(0) + mPaddingH, getScrollX()
//								+ getRight() - getLeft()),
//				top + layout.getLineBottom(0) + mPaddingV);
//		canvas.drawRoundRect(rect, mCornerRadius, mCornerRadius, mPaint);

		super.draw(canvas);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		
	}
}
