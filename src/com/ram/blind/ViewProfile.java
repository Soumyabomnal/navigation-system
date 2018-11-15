/**
 @Author : Munna Kumar Singh
*/
package com.ram.blind;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.HttpClient.CustomHttpClient;
import com.ram.blind.R;
import com.util.Global;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewProfile extends Activity
{
    
	String username="";
	String result[];
	
	String userId="";
	String password="",name="",sex="",dob="",cell="",phone="",email="",address="";
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
      {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.view_profile);
    	
    	final Intent homeIntent = new Intent(this,HomePage.class);
    	final Intent editIntent = new Intent(this,UpdateProfile.class);
    	
    	SharedPreferences pref = getApplicationContext().getSharedPreferences("any_prefname",MODE_PRIVATE);
 	    username = pref.getString("username","");//2nd arg is the default value
        
        Button ok = (Button)findViewById(R.id.ok_btn);
        Button editProfile = (Button)findViewById(R.id.edit_profile);
        
        TextView paname = (TextView)findViewById(R.id.name);
        TextView userName = (TextView)findViewById(R.id.user_name);
        TextView pwd = (TextView)findViewById(R.id.password);
        TextView gender = (TextView)findViewById(R.id.sex);
        TextView date_of_birth = (TextView)findViewById(R.id.dob);
        TextView mobile = (TextView)findViewById(R.id.cell);
        TextView telephone = (TextView)findViewById(R.id.phone);
        TextView emailId = (TextView)findViewById(R.id.email);
        TextView add = (TextView)findViewById(R.id.address);
        TextView exception = (TextView)findViewById(R.id.exception);
        
        
        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
    	postParameters.add(new BasicNameValuePair("username",username));
    	
    	String response = null;
	    try {
	            		
	    	    response = CustomHttpClient.executeHttpPost(Global.URL+"res/JSP/User/JSP-For-Android/view_profile.jsp",postParameters);
        	    String res=response.toString();
        	    res = res.trim();
        	    res = res.replaceAll("\'","\\\\'");  // replacing ' to \\' 
        	    //res= res.replaceAll("\\s+",""); for removing space in between words        	              	 
                result = res.split("~");
                
                if(result.length !=0)
                {
	               userId = result[0];
	               username = result[1];
	               password = result[2];
	               name = result[3];
	               sex = result[4];
	               dob = result[5];
	               cell = result[6];
	               phone = result[7];
	               email = result[8];
	               address = result[9];
	               
                }
                else
                {
                	exception.setText("Sorry,Profile Details Not Found!");
                }
                
                paname.setText(name);
                userName.setText(username);
            	pwd.setText(password);
            	gender.setText(sex);
            	date_of_birth.setText(dob);
            	mobile.setText(cell);
            	telephone.setText(phone);
                emailId.setText(email);
                add.setText(address);
        	  
        	 } 
        	 catch (Exception e) 
        	 {
        		exception.setText(e.toString());
        		
        		System.out.println("*********** Exception ***********");
        		e.printStackTrace();
        	 }
        
	    
        ok.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View arg0) 
			{
				startActivity(homeIntent);
			}
		});
        
       
        editProfile.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View arg0) 
			{
				editIntent.putExtra("userDetails",result);
				startActivity(editIntent);
			}
		});
        
      }
}
