package xyz.danoz.recyclerviewfastscroller.calculation;

/**
 * {@link Please describe ScrollBoundsProvider!}
 */
public class VerticalScrollBoundsProvider {

    private float mMinimumScrollY;
    private float mMaximumScrollY;

    public VerticalScrollBoundsProvider(float minimumScrollY, float maximumScrollY) {
        mMinimumScrollY = minimumScrollY;
        mMaximumScrollY = maximumScrollY;
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
}
