package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.vo.MenuItemType;

public class MenuItem {

    private MenuItemType menuItemType;
    private String menuPath;
    private String label;
    private String itemType;
    private String url;
    private String trackorType;
    private String dashbourd;
    private String portal;
    private String view;
    private String hideView;
    private String filter;
    private String hideFilter;
    private String visible;

    public MenuItem(MenuItemType menuItemType, String menuPath, String label, String itemType, String url, String trackorType, String dashbourd, String portal, String view, String hideView, String filter, String hideFilter, String visible) {
        this.menuItemType = menuItemType;
        this.menuPath = menuPath;
        this.label = label;
        this.itemType = itemType;
        this.url = url;
        this.trackorType = trackorType;
        this.dashbourd = dashbourd;
        this.portal = portal;
        this.view = view;
        this.hideView = hideView;
        this.filter = filter;
        this.hideFilter = hideFilter;
        this.visible = visible;
    }

    public MenuItemType getMenuItemType() {
        return menuItemType;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public String getLabel() {
        return label;
    }

    public String getItemType() {
        return itemType;
    }

    public String getUrl() {
        return url;
    }

    public String getTrackorType() {
        return trackorType;
    }

    public String getDashbourd() {
        return dashbourd;
    }

    public String getPortal() {
        return portal;
    }

    public String getView() {
        return view;
    }

    public String getHideView() {
        return hideView;
    }

    public String getFilter() {
        return filter;
    }

    public String getHideFilter() {
        return hideFilter;
    }

    public String getVisible() {
        return visible;
    }

}