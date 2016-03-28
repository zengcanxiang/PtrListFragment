package com.zengcanxiang.ptrlistviewfragment;

/**
 * Created by zengcanxiang on 2016/1/6.
 */
public interface UIWritCode {
    /**
     * <p>在子Activity初始化的时候调用</p>
     * <p>将所有对代码规范的的初始化</p>
     */
    void inItActivityWritCode();

    /**
     * 描述：把所有View找出来
     */
    void findViews();

    /**
     * 描述：设置所有View的内容
     */
    void setViewsContent();

}
