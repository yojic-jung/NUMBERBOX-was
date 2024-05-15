package com.numberbox.appdomain.enumuration.image;

public enum ImagePathType {
    EDITOR(10, "editorImgUpld"),
    HWP(11, "hwpToHtml");

    public final int actionId;
    public final String path;

    ImagePathType(int actionId, String path) {
        this.actionId = actionId;
        this.path = path;
    }
}
