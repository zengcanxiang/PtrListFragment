package com.zengcanxiang.ptrlistviewfragment;

import android.content.Context;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zengcanxiang.baseAdapter.absListView.HelperAdapter;
import com.zengcanxiang.baseAdapter.absListView.HelperHolder;

import java.util.ArrayList;
import java.util.List;

public class ExampleFragment extends ListViewFragment {
    private List<String> mList;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            mList.add(i + "");
        }
//        默认开启了刷新功能
//        installRefresh(true);
        installLoadMore(true);
        inItActivityWritCode();
    }

    @Override
    public void install() {
//      material风格head
//        materialHeadInstall();
//      文字头部head
        valueHeadInstall("zengcanxiang");
    }

    @Override
    protected void refresh() {
        Toast.makeText(getActivity(), "下拉刷新", Toast.LENGTH_SHORT).show();
        refreshComplete();
    }

    @Override
    protected void loadMore() {
        Toast.makeText(getActivity(), "加载更多", Toast.LENGTH_SHORT).show();
        loadMoreComplete();
    }

    @Override
    protected BaseAdapter bindAdapter() {
        return new MyAdapter(mList, getActivity(), R.layout.test_list_item, R.layout.test_list_item2);
    }

    //listView的适配器
    class MyAdapter extends HelperAdapter<String> {
        public MyAdapter(List data, Context context, int... layoutIds) {
            super(data, context, layoutIds);
        }

        @Override
        public int checkLayout(int position, String item) {
            if (position % 2 == 0) {
                return 1;
            }
            return 0;
        }

        @Override
        public void HelpConvert(HelperHolder helperHolder, int i, String s) {
            TextView testText = helperHolder.getView(R.id.testText);
            testText.setText(i + "");
        }
    }
}
