package mobi.intuitit.android.mate.launcher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

public class MobjectTextView extends TextView {
	private boolean mBackgroundSizeChanged;
	private Drawable mBackground;

	public MobjectTextView(Context context) {
		super(context);
	}

	public MobjectTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MobjectTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void initMobjectView() {

		setFocusable(true);
		mBackground = getBackground();
		setBackgroundDrawable(null);
		mBackground.setCallback(this);

		ItemInfo info = (ItemInfo) getTag();
		this.setBackgroundResource(MImageList.getInstance().getIcon(
				info.mobjectType, info.mobjectIcon));
	}
	
	public void setTitle(boolean isModifyMode) {
		if(isModifyMode)
		{
			ItemInfo info = (ItemInfo) this.getTag();
	
			if (info.contact_num != null)
				this.setText(info.contact_name);
			else
				this.setText(info.title);
		} else {
			this.setText("");
		}
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
