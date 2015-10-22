package xyz.danoz.recyclerviewfastscroller.calculation;

/**
 * {@link Please describe ScrollBoundsProvider!}
 */
public class VerticalScrollBoundsProvider {

    private float mMinimumScrollY;
    private float mMaximumScrollY;
    private float mScrollPaddingY;

    public VerticalScrollBoundsProvider(float minimumScrollY, float maximumScrollY, float scrollPaddingY) {
        mMinimumScrollY = minimumScrollY;
        mMaximumScrollY = maximumScrollY;
        mScrollPaddingY = scrollPaddingY;
    }

    public float getMinimumScrollY() {
        return mMinimumScrollY;
    }

    public void setMinimumScrollY(float mMinimumScrollY) {
        this.mMinimumScrollY = mMinimumScrollY;
    }

    public float getMaximumScrollY() {
        return mMaximumScrollY;
    }

    public void setMaximumScrollY(float mMaximumScrollY) {
        this.mMaximumScrollY = mMaximumScrollY;
    }

    public float getScrollPaddingY() {
        return mScrollPaddingY;
    }

    public void setScrollPaddingY(float mScrollPaddingY) {
        this.mScrollPaddingY = mScrollPaddingY;
    }
}
