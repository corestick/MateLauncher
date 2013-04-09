package mobi.intuitit.android.mate.launcher;

import android.content.Context;
import android.opengl.GLSurfaceView;


public class MSurfaceView extends GLSurfaceView {

	private MyRenderer mRenderer;
	
	public MSurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mRenderer = new MyRenderer();
		setRenderer(mRenderer);
	}
}
