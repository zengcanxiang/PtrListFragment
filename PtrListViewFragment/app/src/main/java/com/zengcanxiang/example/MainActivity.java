package com.zengcanxiang.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ExampleFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setViewsContent();
    }

    public void findViews() {
        //实例化fragment
        fragment = new ExampleFragment();
    }

    public void setViewsContent() {
//        设置自定义的加载更多布局
//        fragment.setLoadMore_LayoutId(R.layout.test_load_more);
//        fragment.setLoadMore_viewId(R.id.test_loadMoreView);
//        将fragment加载到activity中
        getSupportFragmentManager().beginTransaction().add(R.id.testFragment, fragment).commit();
    }
}
