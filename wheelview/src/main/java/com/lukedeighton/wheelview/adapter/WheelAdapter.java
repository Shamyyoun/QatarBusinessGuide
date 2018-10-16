package com.lukedeighton.wheelview.adapter;

import com.lukedeighton.wheelview.WheelItem;

public interface WheelAdapter {
    WheelItem getMenuItem(int position);

    int getCount();
}
