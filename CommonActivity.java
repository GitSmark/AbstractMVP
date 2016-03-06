package com.ttt.common.mvp;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by huangxy on 2016/3/6.
 */
public abstract class CommonActivity extends Activity implements CommonActivityInterface{
	
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