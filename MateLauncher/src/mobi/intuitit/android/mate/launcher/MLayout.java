package mobi.intuitit.android.mate.launcher;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class MLayout extends LayoutType {
	private boolean mPortrait;

	private final Rect mRect = new Rect();
	private final CellInfo mCellInfo = new CellInfo();
	
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
		final CellInfo cellInfo = mCellInfo;

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
						final LayoutParams lp = (LayoutParams) child
								.getLayoutParams();
						cellInfo.cell = child;
						cellInfo.x = lp.x;
						cellInfo.y = lp.y;
						cellInfo.valid = true;
						found = true;
						break;
					}
				}
			}

			if (!found) {
				int cellXY[] = mCellXY;

				cellInfo.cell = null;
				cellInfo.x = cellXY[0];
				cellInfo.y = cellXY[1];
				cellInfo.valid = true;
			}
			setTag(cellInfo);
		} else if (action == MotionEvent.ACTION_UP) {
			cellInfo.cell = null;
			cellInfo.x = -1;
			cellInfo.y = -1;
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
			LayoutParams lp = (LayoutParams) child.getLayoutParams();

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

				LayoutParams lp = (LayoutParams) child
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
	
	@Override
	void onDropChild(View child, int[] targetXY) {
		// TODO Auto-generated method stub
		
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
			LayoutParams lp = (LayoutParams) child.getLayoutParams();
			lp.x = x;
			lp.y = y;
			lp.isDragging = false;
			child.requestLayout();
			invalidate();
		}
	}

	void onDropAborted(View child) {
		if (child != null) {
			((LayoutParams) child.getLayoutParams()).isDragging = false;
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
		LayoutParams lp = (LayoutParams) child.getLayoutParams();
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
		return new LayoutParams(getContext(), attrs);
	}

	@Override
	protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
		return p instanceof LayoutParams;
	}

	@Override
	protected ViewGroup.LayoutParams generateLayoutParams(
			ViewGroup.LayoutParams p) {
		return new LayoutParams(p);
	}

	@Override
	int getCountX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	int getCountY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	boolean[] getOccupiedCells() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	void saveThumb() {
		// TODO Auto-generated method stub
		
	}

	@Override
	Bitmap getThumb() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	CellInfo findAllVacantCells(boolean[] occupiedCells, View ignoreView) {
		// TODO Auto-generated method stub
		
		/// 생성 위치 부분 수정 해야 한다.
		CellInfo cellInfo = new CellInfo();
		
		cellInfo.cellX = -1;
		cellInfo.cellY = -1;
    	cellInfo.screen = mCellInfo.screen;
    	
    	cellInfo.valid = true;
    	
    	return cellInfo;
	}

	@Override
	CellInfo findAllVacantCellsFromOccupied(boolean[][] occupied, int xCount,
			int yCount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	int[] findNearestVacantArea(int pixelX, int pixelY, int spanX, int spanY,
			CellInfo vacantCells, int[] recycle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	void pointToCellExact(int x, int y, int[] result) {
		// TODO Auto-generated method stub
		
	}
}
