package com.zengcanxiang.ptrlistviewfragment;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * 整合PTR下拉刷新和新增加载更多功能的fragment
 */
public abstract class ListViewFragment extends BaseFragment implements AbsListView.OnScrollListener {

    protected ListView mListView;
    protected BaseAdapter mAdapter;
    private final int mListView_layoutId = R.layout.base_fragment_listview;
    private final int mListView_viewId = R.id.base_listView;
    private final int mPtr_LayoutId = R.id.base_ptrLayout;
    private int mLoadMore_LayoutId = R.layout.load_more_view;
    private int mLoadMore_viewId = R.id.loadMoreView;
    private boolean isRefresh = true;
    private boolean isLoadMore = false;
    private boolean isBeingLoadMore = false;
    protected PtrFrameLayout mPtrFrameLayout;
    protected View mView;
    protected View mLayout_loadMore;
    protected View mLoadMore_View;
    //How long can not be repeated loading
    //多久之内不能重复加载
    private int repeated_loading = 1500;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = initView(inflater, mListView_layoutId, container);
        mLayout_loadMore = inflater.inflate(mLoadMore_LayoutId, null);
        return mView;

    }

    @Override
    public void findViews() {
        mListView = (ListView) getView(mListView_viewId);
        mPtrFrameLayout = (PtrFrameLayout) getView(mPtr_LayoutId);
    }


    @Override
    public void setViewsContent() {
        mAdapter = bindAdapter();
        mListView.setAdapter(mAdapter);
        install();
        if (isRefresh) {
            mPtrFrameLayout.setDurationToCloseHeader(repeated_loading);
            mPtrFrameLayout.setPtrHandler(new PtrHandler() {
                @Override
                public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                }

                @Override
                public void onRefreshBegin(PtrFrameLayout frame) {
                    mPtrFrameLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refresh();
                        }
                    }, 500);
                }
            });
        }

        if (isLoadMore) {
            mListView.setOnScrollListener(this);
        }
    }

    /**
     * 初始化完成
     */
    protected void initOk(){
        inItActivityWritCode();
    }

    /**
     * 刷新
     */
    protected void refresh() {

    }

    /**
     * 下拉加载
     */
    protected void loadMore() {

    }


    /***
     * 上拉加载完成
     */
    public void loadMoreComplete() {
        isBeingLoadMore = false;
        mListView.removeFooterView(mLoadMore_View);
    }

    /**
     * 下拉刷新完成
     */
    public void refreshComplete() {
        mPtrFrameLayout.refreshComplete();
    }

    /**
     * 是否启动下拉刷新
     *
     * @param refresh 刷新设置
     */
    public void installRefresh(boolean refresh) {
        this.isRefresh = refresh;
    }

    /**
     * 是否启动上拉加载
     *
     * @param loadMore 加载设置
     */
    public void installLoadMore(boolean loadMore) {
        this.isLoadMore = loadMore;
    }

    /**
     * <p>设置控件的自定义属性</p>
     * <p>父类的控件，子类全部可以拿到</p>
     */
    public void install() {
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            // 判断是否滚动到底部
            if (view.getLastVisiblePosition() == view.getCount() - 1) {
                //加载更多功能的代码
                //要加2层判断，防止加载更多重复调用
                if (!isBeingLoadMore) {
                    addFooterView();
                    isBeingLoadMore = true;
                    mListView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadMore();
                        }
                    }, 1200);
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }


    /**
     * 绑定适配器
     *
     * @return 子类返回适配器
     */
    protected abstract BaseAdapter bindAdapter();


    //提供的头部设置,增加便捷性

    /**
     * 文字头部
     *
     * @param headText 头部需要的显示的文字,支持英文，字母
     */
    protected void valueHeadInstall(String headText) {
        StoreHouseHeader header = new StoreHouseHeader(getActivity());
        header.setPadding(0, dp2px(20), 0, dp2px(20));
        header.initWithString(headText);
        header.setTextColor(getResources().getColor(android.R.color.black));
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);
    }

    /**
     * Material风格的头部
     *
     * @param headColor 圆圈内的颜色数组,没有提供则默认为黑色
     */
    protected void materialHeadInstall(int... headColor) {
        MaterialHeader header = new MaterialHeader(getActivity());
        int[] colors = headColor;
        if (colors == null || colors.length == 0) {
            colors = new int[]{getResources().getColor(android.R.color.black)};
        }
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, dp2px(15), 0, dp2px(10));
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);
        mPtrFrameLayout.setPinContent(true);
    }

    /**
     * listView加上加载视图
     */
    private void addFooterView() {
        if (mLoadMore_View == null) {
            mLoadMore_View = mLayout_loadMore.findViewById(mLoadMore_viewId);
        }
        mListView.addFooterView(mLoadMore_View);
    }

    /***
     * dp转px
     */
    private int dp2px(int dp) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm;
        wm = (WindowManager) getActivity().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        float scale = dm.density;
        return (int) (dp * scale + 0.5F);
    }


    /**
     * 获取加载状态
     *
     * @return 加载状态
     */
    public boolean isLoadMore() {
        return isBeingLoadMore;
    }

    /**
     * 获取listView
     *
     * @return listView
     */
    public ListView getListView() {
        return mListView;
    }

    /**
     * 获取适配器
     *
     * @return adapter
     */
    public BaseAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * 设置重复加载间隔时间
     *
     * @param repeated_loading 重复加载间隔时间
     */
    public void setRepeatedLoading(int repeated_loading) {
        this.repeated_loading = repeated_loading;
    }

    /**
     * <p>自定义加载更多底部布局</p>
     * 设置加载更多的布局文件
     *
     * @param mLoadMore_LayoutId 布局文件id
     */
    public void setLoadMore_LayoutId(int mLoadMore_LayoutId) {
        this.mLoadMore_LayoutId = mLoadMore_LayoutId;
    }

    /**
     * <p>自定义加载更多底部布局</p>
     * 设置加载更多的控件
     *
     * @param loadMore_ViewId 控件id
     */
    public void setLoadMore_viewId(int loadMore_ViewId) {
        this.mLoadMore_viewId = loadMore_ViewId;
    }
}
