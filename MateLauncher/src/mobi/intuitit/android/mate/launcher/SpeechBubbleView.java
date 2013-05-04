package mobi.intuitit.android.mate.launcher;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SpeechBubbleView extends LinearLayout implements
		View.OnClickListener {

	private Launcher mLauncher;
	private TextView mainview;
	private Button smsButton;
	private Button faceButton;
	private Button sendButton;
	private EditText edit;
	

	public SpeechBubbleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void setLauncher(Launcher launcher) {
		mLauncher = launcher;

	}
	
	public void CreateMainView(){
		mainview = new TextView(mLauncher);
	}

	public void CreateSlectView() {
		smsButton = new Button(mLauncher);
		faceButton = new Button(mLauncher);
		smsButton.setText("SMS");
		faceButton.setText("facebook");
		addView(smsButton, 100, 56);
		addView(faceButton, 250, 56);
		smsButton.setOnClickListener(this);
		faceButton.setOnClickListener(this);
	}

	public void CreateSendView() {
		edit = new EditText(mLauncher);
		sendButton = new Button(mLauncher);
		sendButton.setText("Àü¼Û");
		sendButton.setOnClickListener(this);
		addView(edit, 200, 56);
		addView(sendButton, 100, 56);
	}

	public void setLocation(int left, int top, int right, int bottom) {
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		params.gravity = Gravity.TOP;
		params.setMargins(left, top, right, bottom);
		this.setLayoutParams(params);
	}

	@Override
	public void onClick(View v) {
		if (v.equals(smsButton)) {
			removeAllViews();
			CreateSendView();
		} else if (v.equals(sendButton)) {
			removeAllViews();
			CreateSlectView();
		}

	}

}
