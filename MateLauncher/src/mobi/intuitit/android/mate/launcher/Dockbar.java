package mobi.intuitit.android.mate.launcher;

import mobi.intuitit.android.mate.launcher.R.color;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Dockbar extends LinearLayout implements View.OnClickListener {

	private Launcher mLauncher;
	private TextView [] mDockButton;
	private Workspace mWorkspace;
	private View mAllAppsGrid;	
	private ImageView left;

	public Dockbar(Context context, AttributeSet attrs) {
		super(context, attrs);		
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

		left = new ImageView(mLauncher);		
		addView(left);
		left.setOnClickListener(this);
		LayoutParams param = (LayoutParams) left.getLayoutParams();
		param.weight = 10;
		left.setLayoutParams(param);		
		Drawable drawable4 = getResources().getDrawable(R.drawable.icon_left);		
		left.setImageDrawable(drawable4);		
		
		mDockButton = new TextView[4];	
		for(int i =0 ; i < 4 ; i++){
			mDockButton[i] = new TextView(mLauncher);			
			addView(mDockButton[i]);
			mDockButton[i].setOnClickListener(this);
			LayoutParams param1 = (LayoutParams) mDockButton[i].getLayoutParams();
			param1.weight = 50;
			 mDockButton[i].setLayoutParams(param1);			
			mDockButton[i].setPadding(2, 2, 5, 2);		
			mDockButton[i].setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);
			mDockButton[i].setTextSize(12);
			mDockButton[i].setTextColor(color.bright_text_dark_focused);
		}	
		Drawable drawable = getResources().getDrawable(R.drawable.phone);
		drawable =Utilities.createIconThumbnail(drawable, mLauncher);
		mDockButton[0].setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
		mDockButton[0].setText("전화");
		
		Drawable drawable1 = getResources().getDrawable(R.drawable.call);
		drawable1 =Utilities.createIconThumbnail(drawable1, mLauncher);
		mDockButton[1].setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
		mDockButton[1].setText("연락처");
		
		Drawable drawable2 = getResources().getDrawable(R.drawable.call);
		drawable2 =Utilities.createIconThumbnail(drawable2, mLauncher);
		mDockButton[2].setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
		mDockButton[2].setText("문자");
		
		Drawable drawable3 = getResources().getDrawable(R.drawable.call);
		drawable2 =Utilities.createIconThumbnail(drawable3, mLauncher);
		mDockButton[3].setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
		mDockButton[3].setText("어플리케이션");		
	}
	
	@Override
	public void onClick(View v) {
		if (v.equals(mDockButton[0])) {
			Toast.makeText(mLauncher, "b1", Toast.LENGTH_SHORT).show();
			return;
		}
		else if (v.equals(mDockButton[1])) {
			Toast.makeText(mLauncher, "b2", Toast.LENGTH_SHORT).show();
			return;
		}
		else if (v.equals(mDockButton[2])) {
			Toast.makeText(mLauncher, "b3", Toast.LENGTH_SHORT).show();
			return;
		}
		else if (v.equals(mDockButton[3])) {
			final Rect bounds = mWorkspace.mDrawerBounds;
			mLauncher.offsetBoundsToDragLayer(bounds, mAllAppsGrid);
			mAllAppsGrid.setFocusable(true);
			mAllAppsGrid.setVisibility(View.VISIBLE);
			// mSpeechBubbleview.setVisibility(View.INVISIBLE);
			return;
		}
		else if(v.equals(left)){
		}

	}
}
