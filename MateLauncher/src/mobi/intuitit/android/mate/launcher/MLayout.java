/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mobi.intuitit.android.mate.launcher;

import mobi.intuitit.android.widget.WidgetCellLayout;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;

public class MLayout extends WidgetCellLayout {
	private boolean mPortrait;

	private final Rect mRect = new Rect();
	private final CellInfo mCellInfo = new CellInfo();

	int[] mCellXY = new int[2];

	boolean[][] mOccupied;

	private RectF mDragRect = new RectF();

	private boolean mDirtyTag;

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
		final LayoutParams cellParams = (LayoutParams) params;
		cellParams.regenerateId = true;

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
						mDirtyTag = false;
						break;
					}
				}
			}

			if (!found) {
				int cellXY[] = mCellXY;
				final boolean portrait = mPortrait;

				cellInfo.cell = null;
				cellInfo.x = cellXY[0];
				cellInfo.y = cellXY[1];
				cellInfo.valid = true;

				mDirtyTag = true;
			}
			setTag(cellInfo);
		} else if (action == MotionEvent.ACTION_UP) {
			cellInfo.cell = null;
			cellInfo.x = -1;
			cellInfo.y = -1;
			cellInfo.valid = false;
			mDirtyTag = false;
			setTag(cellInfo);
		}

		return false;
	}

	@Override
	public CellInfo getTag() {
		final CellInfo info = (CellInfo) super.getTag();
		if (mDirtyTag && info.valid) {
			final boolean portrait = mPortrait;

			mDirtyTag = false;
		}
		return info;
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
				lp.setup(lp.width, lp.height, 0, 0);
			}

			if (lp.regenerateId) {
				child.setId(((getId() & 0xFF) << 16) | (lp.x & 0xFF) << 8
						| (lp.y & 0xFF));
				lp.regenerateId = false;
			}

			int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(lp.width,
					MeasureSpec.EXACTLY);
			int childheightMeasureSpec = MeasureSpec.makeMeasureSpec(lp.height,
					MeasureSpec.EXACTLY);
			child.measure(childWidthMeasureSpec, childheightMeasureSpec);
		}

		setMeasuredDimension(widthSpecSize, heightSpecSize);

		if (mThumb == null)
			initThumb(widthSpecSize >> 2, heightSpecSize >> 2);
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
				child.layout(childLeft, childTop, childLeft + lp.width,
						childTop + lp.height);
			}
		}

		layoutDrawed = false;
	}

	Bitmap mThumb;
	private Canvas mThumbCanvas;
	private Paint mThumbPaint;

	boolean layoutDrawed = false;

	private void initThumb(int width, int height) {
		if (mThumb == null || mThumb.isRecycled())
			mThumb = Bitmap
					.createBitmap(width, height, Bitmap.Config.ARGB_4444);

		Matrix matrix = new Matrix();
		matrix.setScale(0.25f, 0.25f);
		mThumbCanvas = new Canvas(mThumb);
		mThumbCanvas.concat(matrix);

		mThumbPaint = new Paint();
		mThumbPaint.setDither(true);
		mThumbPaint.setAntiAlias(true);
	}

	synchronized void saveThumb() {
		if (layoutDrawed)
			return;

		if (mThumbCanvas == null)
			initThumb(getWidth() >> 2, getHeight() >> 2);

		setDrawingCacheEnabled(true);

		// Get bitmap
		Bitmap bmp = getDrawingCache();
		if (bmp != null) {
			mThumbCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
			mThumbCanvas.drawBitmap(bmp, 0, 0, mThumbPaint);
		}

		// Clean up
		destroyDrawingCache();
		setDrawingCacheEnabled(false);
		layoutDrawed = true;
	}

	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mThumb != null)
			mThumb.recycle();
		mThumb = null;
		mThumbCanvas = null;
	}

	@Override
	protected void setChildrenDrawingCacheEnabled(boolean enabled) {
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View view = getChildAt(i);
			view.setDrawingCacheEnabled(enabled);
			// reduce cache quality to reduce memory usage
			view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
			// Update the drawing caches
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
	void onDropChild(View child, int[] targetXY) {
		if (child != null) {
			LayoutParams lp = (LayoutParams) child.getLayoutParams();
			lp.x = targetXY[0];
			lp.y = targetXY[1];
			lp.isDragging = false;
			mDragRect.setEmpty();
			child.requestLayout();
			invalidate();
		}
	}

	void onDropAborted(View child) {
		if (child != null) {
			((LayoutParams) child.getLayoutParams()).isDragging = false;
			invalidate();
		}
		mDragRect.setEmpty();
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
		mDragRect.setEmpty();
	}

	/**
	 * Computes the required horizontal and vertical cell spans to always fit
	 * the given rectangle.
	 * 
	 * @param width
	 *            Width in pixels
	 * @param height
	 *            Height in pixels
	 */
	public int[] rectToCell(int width, int height) {
		// Always assume we're working with the smallest span to make sure we
		// reserve enough space in both orientations.
		final Resources resources = getResources();
		int actualWidth = resources.getDimensionPixelSize(R.dimen.cell_width);
		int actualHeight = resources.getDimensionPixelSize(R.dimen.cell_height);
		int smallerSize = Math.min(actualWidth, actualHeight);

		// Always round up to next largest cell
		int spanX = (width + smallerSize) / smallerSize;
		int spanY = (height + smallerSize) / smallerSize;

		return new int[] { spanX, spanY };
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

	public static class LayoutParams extends ViewGroup.MarginLayoutParams {
		/**
		 * Is this item currently being dragged
		 */
		public boolean isDragging;

		// X coordinate of the view in the layout.
		@ViewDebug.ExportedProperty
		int x;
		// Y coordinate of the view in the layout.
		@ViewDebug.ExportedProperty
		int y;

		boolean regenerateId;

		public LayoutParams(Context c, AttributeSet attrs) {
			super(c, attrs);
		}

		public LayoutParams(ViewGroup.LayoutParams source) {
			super(source);
		}

		public LayoutParams(int cellX, int cellY, int cellHSpan, int cellVSpan) {
			super(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		}

		public void setup(int cellWidth, int cellHeight, int widthGap,
				int heightGap, int hStartPadding, int vStartPadding) {
		}

		public void setup(int cellWidth, int cellHeight, int hStartPadding,
				int vStartPadding) {

			if (cellWidth > 0)
				width = cellWidth - leftMargin - rightMargin;

			if (cellHeight > 0)
				height = cellHeight - topMargin - bottomMargin;
		}
	}

	static final class CellInfo implements ContextMenu.ContextMenuInfo {

		View cell;
		int x;
		int y;
		int screen;
		boolean valid;

		@Override
		public String toString() {
			return "Cell[view=" + (cell == null ? "null" : cell.getClass())
					+ ", x=" + x + ", y=" + y + "]";
		}
	}
}
