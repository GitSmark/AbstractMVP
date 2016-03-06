package com.ttt.common.mvp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by huangxy on 2016/3/6.
 */
public abstract class CommonFragmentActivity extends FragmentActivity implements CommonActivityInterface{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		BeforeCreate();
		super.onCreate(savedInstanceState);
		AfterCreate();
		initView();
		setListener();
		getData();
		Other();
	}
	
	public void BeforeCreate() {}
	public void AfterCreate() {}
	public void getData() {}
	public void Other() {}
	
}