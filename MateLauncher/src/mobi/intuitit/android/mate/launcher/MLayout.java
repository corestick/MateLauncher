package mobi.intuitit.android.mate.launcher;

import mobi.intuitit.android.widget.WidgetCellLayout;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class MLayout extends WidgetCellLayout {
	private boolean mPortrait;

	private final Rect mRect = new Rect();
	private final CellLayout.CellInfo mCellInfo = new CellLayout.CellInfo();
	
	int[] mCellXY = new int[2];

	public MLayout(Context context) {
		this(context, null);
	}

	public MLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.CellLayout, defStyle, 0);
		
		a.recycle();
		setAlwaysDrawnWithCacheEnabled(false);
	}

	@Override
	public void cancelLongPress() {
		super.cancelLongPress();

		// Cancel long press for all children
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			child.cancelLongPress();
		}
	}

	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params) {
		// Generate an id for each view, this assumes we have at most 256x256
		// cells
		// per workspace screen

		super.addView(child, index, params);
	}

	@Override
	public void requestChildFocus(View child, View focused) {
		super.requestChildFocus(child, focused);
		if (child != null) {
			Rect r = new Rect();
			child.getDrawingRect(r);
			requestRectangleOnScreen(r);
		}
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		mCellInfo.screen = ((ViewGroup) getParent()).indexOfChild(this);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
		final CellLayout.CellInfo cellInfo = mCellInfo;

		if (action == MotionEvent.ACTION_DOWN) {
			final Rect frame = mRect;
			final int x = (int) ev.getX() + getScrollX();
			final int y = (int) ev.getY() + getScrollY();
			final int count = getChildCount();

			boolean found = false;
			for (int i = count - 1; i >= 0; i--) {
				final View child = getChildAt(i);

				if ((child.getVisibility()) == VISIBLE
						|| child.getAnimation() != null) {
					child.getHitRect(frame);
					if (frame.contains(x, y)) {
						final CellLayout.LayoutParams lp = (CellLayout.LayoutParams) child
								.getLayoutParams();
						cellInfo.cell = child;
						cellInfo.cellX = lp.x;
						cellInfo.cellY = lp.y;
						cellInfo.valid = true;
						found = true;
						break;
					}
				}
			}

			if (!found) {
				int cellXY[] = mCellXY;

				cellInfo.cell = null;
				cellInfo.cellX = cellXY[0];
				cellInfo.cellY = cellXY[1];
				cellInfo.valid = true;
			}
			setTag(cellInfo);
		} else if (action == MotionEvent.ACTION_UP) {
			cellInfo.cell = null;
			cellInfo.cellX = -1;
			cellInfo.cellY = -1;
			cellInfo.valid = false;
			setTag(cellInfo);
		}

		return false;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO: currently ignoring padding

		int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

		int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

		if (widthSpecMode == MeasureSpec.UNSPECIFIED
				|| heightSpecMode == MeasureSpec.UNSPECIFIED) {
			throw new RuntimeException(
					"CellLayout cannot have UNSPECIFIED dimensions");
		}

		mPortrait = heightSpecSize > widthSpecSize;

		int count = getChildCount();

		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			CellLayout.LayoutParams lp = (CellLayout.LayoutParams) child.getLayoutParams();

			if (mPortrait) {
				lp.setup(lp.width, lp.height, 0, 0, 0, 0);
			} else {
				lp.setup(lp.width, lp.height, 0, 0, 0, 0);
			}

			int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(lp.width,
					MeasureSpec.EXACTLY);
			int childheightMeasureSpec = MeasureSpec.makeMeasureSpec(lp.height,
					MeasureSpec.EXACTLY);
			child.measure(childWidthMeasureSpec, childheightMeasureSpec);
		}

		setMeasuredDimension(widthSpecSize, heightSpecSize);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int count = getChildCount();

		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != GONE) {

				CellLayout.LayoutParams lp = (CellLayout.LayoutParams) child
						.getLayoutParams();

				int childLeft = lp.x;
				int childTop = lp.y;
				int childRight;
				int childBottom;

				if (lp.width < 0)
					childRight = childLeft + child.getMeasuredWidth();
				else
					childRight = childLeft + lp.width;

				if (lp.height < 0)
					childBottom = childTop + child.getMeasuredHeight();
				else
					childBottom = childTop + lp.height;

				child.layout(childLeft, childTop, childRight, childBottom);
			}
		}
	}

	@Override
	protected void setChildrenDrawingCacheEnabled(boolean enabled) {
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View view = getChildAt(i);
			view.setDrawingCacheEnabled(enabled);
			
			view.buildDrawingCache(true);
		}
	}

	@Override
	protected void setChildrenDrawnWithCacheEnabled(boolean enabled) {
		super.setChildrenDrawnWithCacheEnabled(enabled);
	}

	/**
	 * Drop a child at the specified position
	 * 
	 * @param child
	 *            The child that is being dropped
	 * @param targetXY
	 *            Destination area to move to
	 */
	void onDropChild(View child, int x, int y) {
	
		if (child != null) {
			CellLayout.LayoutParams lp = (CellLayout.LayoutParams) child.getLayoutParams();
			lp.x = x;
			lp.y = y;
			lp.isDragging = false;
			child.requestLayout();
			invalidate();
		}
	}

	void onDropAborted(View child) {
		if (child != null) {
			((CellLayout.LayoutParams) child.getLayoutParams()).isDragging = false;
			invalidate();
		}
	}

	/**
	 * Start dragging the specified child
	 * 
	 * @param child
	 *            The child that is being dragged
	 */
	void onDragChild(View child) {
		CellLayout.LayoutParams lp = (CellLayout.LayoutParams) child.getLayoutParams();
		lp.isDragging = true;
	}
	    
    public int[] rectToCell(int width, int height) {
    	return new int[] { 0, 0 };
    }

	void onDragOverChild(View child, int cellX, int cellY) {
		invalidate();
	}

	@Override
	public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new CellLayout.LayoutParams(getContext(), attrs);
	}

	@Override
	protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
		return p instanceof CellLayout.LayoutParams;
	}

	@Override
	protected ViewGroup.LayoutParams generateLayoutParams(
			ViewGroup.LayoutParams p) {
		return new CellLayout.LayoutParams(p);
	}
}
