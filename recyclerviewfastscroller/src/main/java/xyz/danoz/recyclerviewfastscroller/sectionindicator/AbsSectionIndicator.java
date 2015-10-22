package xyz.danoz.recyclerviewfastscroller.sectionindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import xyz.danoz.recyclerviewfastscroller.R;
import xyz.danoz.recyclerviewfastscroller.calculation.VerticalScrollBoundsProvider;
import xyz.danoz.recyclerviewfastscroller.calculation.position.VerticalScreenPositionCalculator;
import xyz.danoz.recyclerviewfastscroller.sectionindicator.animation.DefaultSectionIndicatorAlphaAnimator;

/**
 * Abstract base implementation of a section indicator used to indicate the section of a list upon which the user is
 * currently fast scrolling.
 */
public abstract class AbsSectionIndicator<T> extends FrameLayout implements SectionIndicator<T> {

    private static final int[] STYLEABLE = R.styleable.AbsSectionIndicator;

    private float mScrollerHandleHeight = 0.0f;
    private float mScrollerPaddingY = 0.0f;

    private VerticalScrollBoundsProvider mBoundsProvider;
    private VerticalScreenPositionCalculator mScreenPositionCalculator;
    private DefaultSectionIndicatorAlphaAnimator mDefaultSectionIndicatorAlphaAnimator;

    private View mIndicatorLayout;

    public AbsSectionIndicator(Context context) {
        this(context, null);
    }

    public AbsSectionIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbsSectionIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Resources.Theme theme = context.getTheme();
        TypedArray attributes = theme.obtainStyledAttributes(attrs, STYLEABLE, 0, 0);
        try {
            TypedValue typedValue = new TypedValue();
            DisplayMetrics metrics = getResources().getDisplayMetrics();

            theme.resolveAttribute(R.attr.rfs_fast_scroller_handle_height, typedValue, true);
            mScrollerHandleHeight = typedValue.getDimension(metrics);

            theme.resolveAttribute(R.attr.rfs_fast_scroller_handle_padding_y, typedValue, true);
            mScrollerPaddingY = typedValue.getDimension(metrics);

            int layoutId = attributes.getResourceId(R.styleable.AbsSectionIndicator_rfs_section_indicator_layout, getDefaultLayoutId());
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mIndicatorLayout = inflater.inflate(layoutId, this, false);

            addView(mIndicatorLayout);
        } finally {
            attributes.recycle();
        }

        mDefaultSectionIndicatorAlphaAnimator = new DefaultSectionIndicatorAlphaAnimator(this);
    }

    /**
     * @return the default layout for a given implementation of AbsSectionIndicator
     */
    protected abstract int getDefaultLayoutId();

    /**
     * @return the default background color to be used if not provided by client in XML
     * @see {@link #applyCustomBackgroundColorAttribute(int)}
     */
    protected abstract int getDefaultBackgroundColor();

    /**
     * Clients can provide a custom background color for a section indicator
     * @param color provided in XML via the {@link R.styleable#AbsSectionIndicator_backgroundColor} parameter. If not
     *              specified in XML, this defaults to that which is provided by {@link #getDefaultBackgroundColor()}
     */
    protected abstract void applyCustomBackgroundColorAttribute(int color);

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            // Set the maximum scroll y to be at the top of the final scroller handle position.
            float maximumScrollY = getHeight() - mScrollerHandleHeight - (mScrollerPaddingY * 2);

            if (mScreenPositionCalculator == null) {
                mBoundsProvider = new VerticalScrollBoundsProvider(0.0f, maximumScrollY, mScrollerPaddingY);
                mScreenPositionCalculator = new VerticalScreenPositionCalculator(mBoundsProvider);
            } else {
                // Screen height may change.
                mBoundsProvider.setMaximumScrollY(maximumScrollY);
            }
        }
    }

    @Override
    public void setProgress(float progress) {
        float yPositionFromScrollProgress = mScreenPositionCalculator.getYPositionFromScrollProgress(progress);

        // Ensure the bottom of the view aligns with the middle of the handle.
        yPositionFromScrollProgress -= (float) mIndicatorLayout.getHeight();
        yPositionFromScrollProgress += (mScrollerHandleHeight / 2) + mScrollerPaddingY;

        // Don't go below zero pixels.
        yPositionFromScrollProgress = Math.max(0.0f, yPositionFromScrollProgress);
        mIndicatorLayout.setY(yPositionFromScrollProgress);
    }

    @Override
    public void animateAlpha(float targetAlpha) {
        mDefaultSectionIndicatorAlphaAnimator.animateTo(targetAlpha);
    }

    @Override
    public abstract void setSection(T object);
}
