/**
 @Author : Munna Kumar Singh
 Date : Jul 20, 2013
 File : UpdateProfile.java
 Package : com.dev.activity
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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateProfile extends Activity
{
	String username="";
	String result[];
	
	String userId="";
	String password="",name="",sex="",dob="",cell="",phone="",email="",address="";
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_profile);
		
		final Intent homeIntent = new Intent(this,HomePage.class);
		
		Intent intent = getIntent();
    	result=(String[]) intent.getSerializableExtra("userDetails");
		
		Button cancel = (Button)findViewById(R.id.cancel);
        Button update = (Button)findViewById(R.id.update_profile);
        
        final EditText userName = (EditText)findViewById(R.id.p_name);
        final EditText user_name = (EditText)findViewById(R.id.un);
        final EditText pwd = (EditText)findViewById(R.id.pwd);
        final EditText mobile = (EditText)findViewById(R.id.p_cell);
        final EditText telephone = (EditText)findViewById(R.id.p_phone);
        final EditText emailId = (EditText)findViewById(R.id.p_email);
        final EditText add = (EditText)findViewById(R.id.p_address);
        final TextView exception = (TextView)findViewById(R.id.exception);
        
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
        
        userName.setText(name);
        user_name.setText(username);
    	pwd.setText(password);
    	mobile.setText(cell);
    	telephone.setText(phone);
        emailId.setText(email);
        add.setText(address);
        
        cancel.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View v)
			{
				finish();
			}
		});
        
        // Updating The Patient Profile
        
        update.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View v) 
			{
				username = user_name.getText().toString();
				password = pwd.getText().toString();
				name = userName.getText().toString();
				cell = mobile.getText().toString();
				phone = telephone.getText().toString();
				email = emailId.getText().toString();
				address = add.getText().toString();
				
				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	            postParameters.add(new BasicNameValuePair("username",username));
	            postParameters.add(new BasicNameValuePair("password",password));
	            postParameters.add(new BasicNameValuePair("name",name));
	            postParameters.add(new BasicNameValuePair("cell",cell));
	            postParameters.add(new BasicNameValuePair("phone",phone));
	            postParameters.add(new BasicNameValuePair("email",email));
	            postParameters.add(new BasicNameValuePair("address",address));
	           	
	        	   
	         	String response = null;
		        try 
		        { 
	          		
		          	    response = CustomHttpClient.executeHttpPost(Global.URL+"res/JSP/User/JSP-For-Android/update_profile.jsp",postParameters);
		          	    
		          	    String res=response.toString();
		          	    // res = res.trim(); //removing front and trailing byte spaces
		          	    res= res.replaceAll("\\s+","");   //removing spaces in between the words      	              	 
		          	   
		          	    if(res.equals("true"))
		          	    {
		          	    	Toast.makeText(getApplicationContext(), "Profile Updated Sucessfully.....", Toast.LENGTH_LONG).show();
	 						startActivity(homeIntent);
		          	    }
		          	    else
		          	    {
		          	    	Toast.makeText(getApplicationContext(), "Sorry Updation Process Failed,Try It Again...", Toast.LENGTH_SHORT).show();
		          	    }
	          	  }
		          catch (Exception e) 
		          {
		        	exception.setText(e.toString());
	          		System.out.println("******* Exception While Updatiing Patient Profile *******");
	          		e.printStackTrace();
				  }
			
			}
		});
        
	}
}
