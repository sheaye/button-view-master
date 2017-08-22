package com.sheaye.widget;

/**
 * Created by yexinyan on 2017/8/22.
 */
public enum ButtonShape {
    RECTANGLE(ButtonView.SHAPE_RECTANGLE), CIRCLE(ButtonView.SHAPE_CIRCLE), CIRCLE_RECT(ButtonView.SHAPE_CIRCLE_RECT);

    private int value;

    ButtonShape(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
