package com.ihaozuo.plamexam.common.PullToRefresh;

public interface Pullable {
    /**
     * 判断是否可以下拉，如果不需要下拉功能可以直接return false
     *
     * @return true如果可以下拉否则返回false
     */
    boolean canPullDown();

    /**
     * 判断是否可以上拉，如果不需要上拉功能可以直接return false
     *
     * @return true如果可以上拉否则返回false
     */
    boolean canPullUp();

    /**
     * 判断是否锁定，锁定后无法下拉刷新
     * @return true 已锁定 false 未锁定
     */
    boolean isLocked();

    /**
     * 锁定
     */
    void lock();

    /**
     * 解锁
     */
    void unLock();
}
