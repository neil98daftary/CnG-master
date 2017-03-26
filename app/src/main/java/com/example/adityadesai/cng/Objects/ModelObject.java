package com.example.adityadesai.cng.Objects;

import com.example.adityadesai.cng.R;

/**
 * Created by neil on 23/3/17.
 */

public enum ModelObject {
    RED(R.string.red, R.layout.view_items),
    BLUE(R.string.blue, R.layout.view_offers),
    GREEN(R.string.green, R.layout.view_details);

    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }
}
