/**
 @Author : Munna Kumar Singh
 Date : Oct 26, 2013
 File : Logout.java
 Package : com.dev.qrcode
*/

package com.ram.blind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Logout extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onBackPressed()//Disabling The Back Button 
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent homeIntent = new Intent(this,MobNavSys4VisuallyImpaired.class);
		startActivity(homeIntent);
	}
}
