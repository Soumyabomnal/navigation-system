/**
 @Author : Munna Kumar Singh
 Date : Nov 29, 2013
 File : TestActivity.java
 Package : com.dev.mob
*/

package com.ram.blind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class TestActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.yourxml);
		
		Intent intent = new Intent();
		intent.putExtra("name","Munna Kumar Singh");
		setResult(2,intent);
		finish();
	}
}

