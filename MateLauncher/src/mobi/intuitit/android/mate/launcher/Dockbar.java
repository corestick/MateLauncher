package mobi.intuitit.android.mate.launcher;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Dockbar extends LinearLayout implements View.OnClickListener {

	private Launcher mLauncher;
	private Button mDockButton1;
	private Button mDockButton2;
	private Button mDockButton3;
	private TextView mDockButton4;
	private Workspace mWorkspace;
	private View mAllAppsGrid;

	public Dockbar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
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
		this.setBackgroundColor(Color.GRAY);
		// mDockbar.removeAllViews();
		mDockButton1 = new Button(mLauncher);
		mDockButton2 = new Button(mLauncher);
		mDockButton3 = new Button(mLauncher);
		mDockButton4 = new TextView(mLauncher);

		mDockButton1.setText("button1");
		mDockButton2.setText("button2");
		mDockButton3.setText("button3");

		mDockButton4.setLayoutParams(new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.FILL_PARENT, 0.0F));
		mDockButton4.setTextColor(Color.RED);
		mDockButton4.setTextSize(20);
		mDockButton4.setGravity(Gravity.CENTER);
		mDockButton4.setText("grid");
		// mDockButton4.setCompoundDrawablesWithIntrinsicBounds(0,
		// R.drawable.call, 0, 0);

		addView(mDockButton1, 100, 56);
		addView(mDockButton2, 100, 56);
		addView(mDockButton3, 100, 56);
		addView(mDockButton4, 100, 56);

		mDockButton1.setOnClickListener(this);
		mDockButton2.setOnClickListener(this);
		mDockButton3.setOnClickListener(this);
		mDockButton4.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		if (v.equals(mDockButton1)) {
			Toast.makeText(mLauncher, "b1", Toast.LENGTH_SHORT).show();
			return;
		}
		if (v.equals(mDockButton2)) {
			Toast.makeText(mLauncher, "b2", Toast.LENGTH_SHORT).show();
			return;
		}
		if (v.equals(mDockButton3)) {
			Toast.makeText(mLauncher, "b3", Toast.LENGTH_SHORT).show();
			return;
		}
		if (v.equals(mDockButton4)) {
			final Rect bounds = mWorkspace.mDrawerBounds;
			mLauncher.offsetBoundsToDragLayer(bounds, mAllAppsGrid);
			mAllAppsGrid.setFocusable(true);
			mAllAppsGrid.setVisibility(View.VISIBLE);
			// mSpeechBubbleview.setVisibility(View.INVISIBLE);
			return;
		}

	}
}
