package mobi.intuitit.android.mate.launcher;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MDockbar extends LinearLayout implements View.OnClickListener {

	private ImageButton mFurniture;
	private ImageButton mWallpaper;
	private ImageButton mFlooring;
	private ImageButton mAvatar;
	private ImageButton mHomepage;
	private ImageButton mRight;
	
	Launcher launcher;
	
	Context context;
	
	public MDockbar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub	
		this.context = context;
	}
	
	public MDockbar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.setPadding(2, 2, 2, 2);
		///this.setBackgroundResource(R.drawable.mdock);
	}
	
	public void initMDockbar(Launcher launcher) {
		this.launcher = launcher;
		
		mFurniture = new ImageButton(context);
		mFlooring = new ImageButton(context);
		mWallpaper = new ImageButton(context);
		mAvatar = new ImageButton(context);
		mHomepage = new ImageButton(context);
		mRight = new ImageButton(context);
		
		mFurniture.setBackgroundResource(R.drawable.icon_furniture);
		mFlooring.setBackgroundResource(R.drawable.icon_flooring);
		mWallpaper.setBackgroundResource(R.drawable.icon_wallpaper);
		mAvatar.setBackgroundResource(R.drawable.icon_avatar);
		mHomepage.setBackgroundResource(R.drawable.icon_homepage);
		mRight.setBackgroundResource(R.drawable.icon_right);
		
		addView(mFurniture);
		LayoutParams params = (LayoutParams) mFurniture.getLayoutParams();
		params.weight = 50;
		mFurniture.setLayoutParams(params);
		
		addView(mWallpaper);
		params = (LayoutParams) mFurniture.getLayoutParams();
		params.weight = 50;
		mWallpaper.setLayoutParams(params);
		
		addView(mFlooring);
		params = (LayoutParams) mFlooring.getLayoutParams();
		params.weight = 50;
		mFlooring.setLayoutParams(params);
		
		addView(mAvatar);
		params = (LayoutParams) mAvatar.getLayoutParams();
		params.weight = 50;
		mAvatar.setLayoutParams(params);
		
		addView(mHomepage);
		params = (LayoutParams) mHomepage.getLayoutParams();
		params.weight = 50;
		mHomepage.setLayoutParams(params);
		
		addView(mRight);
		params = (LayoutParams) mRight.getLayoutParams();
		params.weight = 10;
		mRight.setLayoutParams(params);
		
		mFurniture.setOnClickListener(this);
		mWallpaper.setOnClickListener(this);
		mFlooring.setOnClickListener(this);
		mAvatar.setOnClickListener(this);
		mHomepage.setOnClickListener(this);
		mRight.setOnClickListener(this);
	}
	
	public void showMDockbar() {
		this.setVisibility(VISIBLE);
	}
	
	public void hideMDockbar() {
		this.setVisibility(INVISIBLE);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stubs
		
		if(v.equals(mFurniture)) {
			
			
			
		}
		
		
		if(v.equals(mRight)) {
			hideMDockbar();
			Launcher.modifyMode = false;
			launcher.mDockbar.showDockbar();
		}
		
	}
	
}
