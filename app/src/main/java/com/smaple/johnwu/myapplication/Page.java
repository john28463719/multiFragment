package com.smaple.johnwu.myapplication;

/**
 * Created by JohnWu on 4/7/17.
 */
enum Page{
    FIRST(0,  ChildFragment.newInstance("first") , "container1", "first"),
    SECOND(1, ChildFragment.newInstance("second") , "container2", "second"),
    THIRD(2,  ChildFragment.newInstance("third")  , "container3", "third"),
    FOURTH(3, ChildFragment.newInstance("fourth") , "container4", "fourth");

    private int index;
    private String tag;
    private String containerTag;
    private BaseFragment fragment;



    private Page(int index, BaseFragment fragment, String containerTag, String tag){
        this.index = index;
        this.tag = tag;
        this.containerTag = containerTag;

        this.fragment = fragment;
    }

    public static Page findByIndex(int index){
        Page[] pages = Page.values();
        for (Page page: pages) {
            if (page.getIndex() == index){
                return page;
            }
        }
        return null;
    }

    public BaseFragment getFragment() {
        return fragment;
    }

    public String getTag() {
        return tag;
    }

    public String getContainerTag() {
        return containerTag;
    }

    public int getIndex() {
        return index;
    }
}
