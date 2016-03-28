package com.zengcanxiang.ptrlistviewfragment;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zengcanxiang on 2015/12/31.
 */
public abstract class BaseFragment extends Fragment implements UIWritCode {

    public View initView(LayoutInflater inflaterint, int createViewId, ViewGroup container) {
        View view = inflaterint.inflate(createViewId, container, false);
        return view;
    }

    public View getView(int viewId) {
        View view = getActivity().findViewById(viewId);
        return view;
    }

    @Override
    public void inItActivityWritCode() {
        findViews();
        setViewsContent();
        setViewsListener();
        disposeBusiness();
    }

}
