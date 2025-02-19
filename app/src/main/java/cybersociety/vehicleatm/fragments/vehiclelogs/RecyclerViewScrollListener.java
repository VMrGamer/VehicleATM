package cybersociety.vehicleatm.fragments.vehiclelogs;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private static final String TAG = RecyclerViewScrollListener.class.getSimpleName();

    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private volatile boolean mEnabled = true;

    private int mPreLoadCount = 0;

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (mEnabled) {
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();

            if (!(manager instanceof LinearLayoutManager)) {
                throw new IllegalArgumentException("Expected recyclerview to have linear layout manager");
            }

            LinearLayoutManager mLayoutManager = (LinearLayoutManager) manager;

            visibleItemCount = mLayoutManager.getChildCount();
            totalItemCount = mLayoutManager.getItemCount();
            firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

            if (firstVisibleItem + visibleItemCount >= totalItemCount - mPreLoadCount) {
                onEndOfScrollReached(recyclerView);
            }
        }
    }

    public abstract void onEndOfScrollReached(RecyclerView recyclerView);

    public void disableScrollListener() {
        mEnabled = false;
    }

    public void enableScrollListener() {
        mEnabled = true;
    }

    public void setPreLoadCount(int mPreLoadCount) {
        this.mPreLoadCount = mPreLoadCount;
    }
}
