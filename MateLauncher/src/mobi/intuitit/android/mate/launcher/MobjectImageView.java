package mobi.intuitit.android.mate.launcher;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MobjectImageView extends RelativeLayout {
	private boolean mBackgroundSizeChanged;
	private Drawable mBackground;

	private ImageView mobjectImageView;
	private LinearLayout layoutSpeechBubble;
	private LinearLayout layoutAvatarMenu;
	private TextView txtSpeechBubble;
	private ScrollView scrollView;

	private boolean mIsAvatar = false;

	public MobjectImageView(Context context) {
		super(context);
	}

	public MobjectImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MobjectImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void initMobjectView(ItemInfo appInfo) {

//		setFocusable(true);
//		mBackground = getBackground();
//		setBackgroundDrawable(null);
//		mBackground.setCallback(this);
		
		mobjectImageView = (ImageView) findViewById(R.id.imgMobject);
		mobjectImageView.setImageResource(MImageList.getInstance().getIcon(
				appInfo.mobjectType, appInfo.mobjectIcon));
		
		if (appInfo.mobjectType == 1) {
			mIsAvatar = true;
			
			layoutSpeechBubble = (LinearLayout) findViewById(R.id.layoutSpeechBubble);
			scrollView = (ScrollView) findViewById(R.id.scrollView);
			txtSpeechBubble = (TextView) findViewById(R.id.txtSpeechBubble);
			
			txtSpeechBubble.setTextColor(Color.BLUE);
			txtSpeechBubble.setPadding(10, 5, 5, 5);
			
			layoutAvatarMenu = (LinearLayout) findViewById(R.id.layoutAvatarMenu);
		}

		Log.e("RRR", "initMAvatarView22");

	}

	// @Override
	// protected boolean setFrame(int left, int top, int right, int bottom) {
	// if (getLeft() != left || getRight() != right || getTop() != top
	// || getBottom() != bottom) {
	// mBackgroundSizeChanged = true;
	// }
	// return super.setFrame(left, top, right, bottom);
	// }

//	@Override
//	protected boolean verifyDrawable(Drawable who) {
//		return who == mBackground || super.verifyDrawable(who);
//	}
//
//	@Override
//	protected void drawableStateChanged() {
//		Drawable d = mBackground;
//		if (d != null && d.isStateful()) {
//			d.setState(getDrawableState());
//		}
//		super.drawableStateChanged();
//	}
//
//	@Override
//	public void draw(Canvas canvas) {
//		final Drawable background = mBackground;
//		if (background != null) {
//			final int scrollX = getScrollX();
//			final int scrollY = getScrollY();
//
//			if (mBackgroundSizeChanged) {
//				background.setBounds(0, 0, getRight() - getLeft(), getBottom()
//						- getTop());
//				mBackgroundSizeChanged = false;
//			}
//
//			if ((scrollX | scrollY) == 0) {
//				background.draw(canvas);
//			} else {
//				canvas.translate(scrollX, scrollY);
//				background.draw(canvas);
//				canvas.translate(-scrollX, -scrollY);
//			}
//		}
//
//		super.draw(canvas);
//	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		
//		Log.e("onLayout", l + ", " + t + ", " + r + ", " + b);
		
		super.onLayout(changed, l, t, r, b);
	}
	
	public boolean isAvatar() {
		return mIsAvatar;
	}

	public void setSpeechMsg(String msg) {
		if (mIsAvatar)
			txtSpeechBubble.setText(msg);
	}

	public void setSpeechBubble() {
		if (mIsAvatar) {
			RelativeLayout.LayoutParams mLayoutParams = (RelativeLayout.LayoutParams) layoutSpeechBubble.getLayoutParams();
			if (mLayoutParams.width == 0) {
				hideAvatarMenu();
				showSpeechBubble();
			} else {
				hideSpeechBubble();
			}
		}
	}
	
	public void setAvatarMenu() {
		if (mIsAvatar) {
			RelativeLayout.LayoutParams mLayoutParams = (RelativeLayout.LayoutParams) layoutAvatarMenu.getLayoutParams();
			if (mLayoutParams.width == 0) {
				hideSpeechBubble();
				showAvatarMenu();
			} else {
				hideAvatarMenu();
			}
		}
	}
	
	public void showSpeechBubble() {
		RelativeLayout.LayoutParams mLayoutParams = (RelativeLayout.LayoutParams) layoutSpeechBubble.getLayoutParams();
		LinearLayout.LayoutParams mScrollViewParams = (LinearLayout.LayoutParams) scrollView.getLayoutParams();
		mLayoutParams.width = 180;
		mLayoutParams.height = 140;
		mScrollViewParams.width = 180;
		mScrollViewParams.height = 120;
		layoutSpeechBubble.setLayoutParams(mLayoutParams);
		scrollView.setLayoutParams(mScrollViewParams);
		
		RelativeLayout.LayoutParams mMobjectParams = (RelativeLayout.LayoutParams) mobjectImageView.getLayoutParams();
		mMobjectParams.addRule(RelativeLayout.BELOW, layoutSpeechBubble.getId());
		mobjectImageView.setLayoutParams(mMobjectParams);
	}
	
	public void hideSpeechBubble() {
		RelativeLayout.LayoutParams mLayoutParams = (RelativeLayout.LayoutParams) layoutSpeechBubble.getLayoutParams();
		LinearLayout.LayoutParams mScrollViewParams = (LinearLayout.LayoutParams) scrollView.getLayoutParams();
		mLayoutParams.width = 0;
		mLayoutParams.height = 0;
		mScrollViewParams.width = 0;
		mScrollViewParams.height = 0;
		layoutSpeechBubble.setLayoutParams(mLayoutParams);
		scrollView.setLayoutParams(mScrollViewParams);
	}
	
	public void showAvatarMenu() {
		RelativeLayout.LayoutParams mLayoutParams = (RelativeLayout.LayoutParams) layoutAvatarMenu.getLayoutParams();
		mLayoutParams.width = 256;
		mLayoutParams.height = 64;
		layoutAvatarMenu.setLayoutParams(mLayoutParams);
		
		RelativeLayout.LayoutParams mMobjectParams = (RelativeLayout.LayoutParams) mobjectImageView.getLayoutParams();
		mMobjectParams.addRule(RelativeLayout.BELOW, layoutAvatarMenu.getId());
		mobjectImageView.setLayoutParams(mMobjectParams);
	}
	
	public void hideAvatarMenu() {
		RelativeLayout.LayoutParams mLayoutParams = (RelativeLayout.LayoutParams) layoutAvatarMenu.getLayoutParams();
		mLayoutParams.width = 0;
		mLayoutParams.height = 0;
		layoutAvatarMenu.setLayoutParams(mLayoutParams);
	}
}
