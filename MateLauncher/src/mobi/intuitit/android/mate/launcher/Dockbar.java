package mobi.intuitit.android.mate.launcher;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Dockbar extends LinearLayout implements View.OnClickListener {

	private Launcher mLauncher;
	private ImageButton[] mDockButton;
	private Workspace mWorkspace;
	private View mAllAppsGrid;

	Bitmap captureView[];

	public Dockbar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setBackgroundResource(R.drawable.dock);
	}

	public void setLauncher(Launcher launcher) {
		mLauncher = launcher;
	}

	public void setWorkspace(Workspace workspace) {
		mWorkspace = workspace;
	}

	public void setAllgridView(View allgridview) {
		mAllAppsGrid = allgridview;
	}

	public void CreateDockbar() {

		mDockButton = new ImageButton[5];
		for (int i = 0; i < 5; i++) {
			mDockButton[i] = new ImageButton(mLauncher);
			addView(mDockButton[i]);
			mDockButton[i].setOnClickListener(this);
			LayoutParams param1 = (LayoutParams) mDockButton[i]
					.getLayoutParams();
			param1.weight = 50;
			mDockButton[i].setLayoutParams(param1);
		}

		mDockButton[0].setBackgroundResource(R.drawable.icon_phone);
		mDockButton[1].setBackgroundResource(R.drawable.icon_contacts);
		mDockButton[2].setBackgroundResource(R.drawable.icon_apps);
		mDockButton[3].setBackgroundResource(R.drawable.icon_setting);
		mDockButton[4].setBackgroundResource(R.drawable.icon_edit);
	}

	public void showDockbar() {
		this.setVisibility(VISIBLE);
	}

	public void hideDockbar() {
		this.setVisibility(INVISIBLE);
	}

	@Override
	public void onClick(View v) {
		if (v.equals(mDockButton[0])) {
			Intent intent = new Intent(Intent.ACTION_CALL_BUTTON);
			mLauncher.startActivity(intent);
			return;
		} else if (v.equals(mDockButton[1])) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("content://contacts/people/"));
			mLauncher.startActivity(intent);
			return;
		} else if (v.equals(mDockButton[2])) {
			final Rect bounds = mWorkspace.mDrawerBounds;
			mLauncher.offsetBoundsToDragLayer(bounds, mAllAppsGrid);
			mAllAppsGrid.setFocusable(true);
			mAllAppsGrid.setVisibility(View.VISIBLE);
			// mSpeechBubbleview.setVisibility(View.INVISIBLE);
			return;
		} else if (v.equals(mDockButton[3])) {
			// Intent intent = mLauncher.getPackageManager()
			// .getLaunchIntentForPackage("com.android.mms");

			final Intent intent = new Intent(
					android.provider.Settings.ACTION_SETTINGS);
			mLauncher.startActivity(intent);
			return;
		} else if (v.equals(mDockButton[4])) {
			// ȭ�� �ٹ̱�
			hideDockbar();
			Launcher.modifyMode = true;
			mLauncher.mMDockbar.showMDockbar();
//			mLauncher.modifyAnimationStart();
			MLayout mLayout = (MLayout) mWorkspace.getChildAt(mWorkspace
					.getCurrentScreen());
			mLayout.hideAllAvatarView();

			// ������忡�� Ÿ��Ʋ ǥ��
//			mLayout.invalidate();
			for (int i = 0; i < mLayout.getChildCount(); i++) {
				if (mLayout.getChildAt(i) instanceof MobjectImageView){
					((MobjectImageView) mLayout.getChildAt(i)).invalidate();
				}
			}
			return;
		}
	}
}
