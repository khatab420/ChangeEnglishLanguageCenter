package com.khataab.changeenglishlanguagecenter;

public class MenuItemData {
    private String text;
    private int iconResource;

    public MenuItemData(String text, int iconResource) {
        this.text = text;
        this.iconResource = iconResource;
    }

    public String getText() {
        return text;
    }

    public int getIconResource() {
        return iconResource;
    }
}

