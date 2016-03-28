# PtrListViewFragment

这个是一个站在巨人肩膀上的项目。。。。

自从下拉刷新在移动段火起来了之后，在Android方面，大家一般使用的下拉刷新框架就是那个几个主流（大神自己写就不要来打脸了），选择那个大家可以参考下这个项目---[Android-Ptr-Comparison][1],这位对这些框架从技术方面进行了详细的比较。

也是在这个项目中，认识了PTR---[android-Ultra-Pull-To-Refresh][2]。

但是，作者在该项目中是没有支持上拉加载更多模块。在很多项目中，自然会比较蛋疼。

为了解决下痛点。自己在使用这个过程中，总结的一些经验写成了这个项目。感谢[巨人][3].

##示例

![默认加载更多][4] ![自定义加载更多底部布局][5] ![方便开发提供的下拉刷新头部1][6] ![方便开发提供的下拉刷新头部2][7]

如果想要更换其他刷新头部，那么PTR这个项目，还是需要自己去琢磨琢磨的，因为每个应用的需求是不一样的。

##使用

使用步骤是很简单的，在DEMO中有。

首先，继承ListViewFragment，重写和实现一些方法。
```java

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
```
refresh()方法和loadMore()方法是重写的，继承该fragment不会强制要求实现，因为可能某些情况是不需要的，就不强制子类重写了。需要的话，自行在子类重写。

对了，这个里面继承的Adapter是我的另一个项目---[BaseAdapter][8]，但是只是在示例中导入了，不会在PtrListViewFragment中出现，如果需要，请自行导入。不为大家的方法数加负担了。

然后在Activity中像平常一样的使用该fragment就行了。

```java

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
        getFragmentManager().beginTransaction().add(R.id.testFragment, fragment).commit();
    }
}
```

在上面代码中可以看见，这个fragment是可以设置自己的加载更多布局的。就像普通的对象赋值一样。

好了，源码也就一个类，有注释，欢迎大家指点，批评！希望能帮到一些需要的人。

## License

This library is licensed under the [Apache Software License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

See [`LICENSE`](LICENSE) for full of the license text.

    Copyright (C) 2015 [Hanks](https://github.com/ZengcxAperson)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


  [1]: https://github.com/zengcanxiang/PtrListViewFragment/blob/master/README.md

  [1]: https://github.com/desmond1121/Android-Ptr-Comparison
  [2]: https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh
  [3]: https://github.com/liaohuqiu
  [4]: https://github.com/zengcanxiang/PtrListViewFragment/blob/master/photo/loadMore_1.png
  [5]: https://github.com/zengcanxiang/PtrListViewFragment/blob/master/photo/loadMore_2.png
  [6]: https://github.com/zengcanxiang/PtrListViewFragment/blob/master/photo/refrsh_1.png
  [7]: https://github.com/zengcanxiang/PtrListViewFragment/blob/master/photo/refrsh_2.png
  [8]: https://github.com/zengcanxiang/BaseAdapter
