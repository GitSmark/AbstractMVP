package com.ttt.common.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huangxy on 2016/3/6.
 */
public abstract class CommonFragment extends Fragment implements CommonFragmentInterface {
	
	private View view = null;
	
	@Override
	@Deprecated
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		BeforeCreate();
	}
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
 		view = inflater.inflate(inflateView(), container, false);
		AfterCreate();
		initView(view);
		setListener();
		getData();
		Other();
		return view;
	}
	
	public void BeforeCreate() {}
	public void AfterCreate() {}
	public void getData() {}
	public void Other() {}
	
}
