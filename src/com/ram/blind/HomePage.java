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
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomePage extends Activity
{
	/* Setting(Change Password) Params(Starts) */
	  Dialog dialog=null;
	  AlertDialog.Builder settingsDialog;
	  LinearLayout settingSLayout;
	  EditText un;
	  EditText old_pwd;
	  EditText new_pwd;
	  EditText confirm_pwd;
	  String username="";
	  String oldPwd="";
	  String newPwd="";
	  String confirmPwd="";
	  String oldPwdValue="";
	  String newPwdValue="";
	  String confirmPwdValue="";
	  String validationMsg="";
	 
	  String response = null;
	  String res = null;
	  String result[];
	  /* Setting Params(Ends) */
  @Override
  public void onCreate(Bundle savedInstanceState) 
  {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.home_page);
	
  }
  
//All About Home Menu
  
  @Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		
	  getMenuInflater().inflate(R.menu.home, menu);
		return true;
		
	}
	
  
	@Override
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		  
        switch (item.getItemId())
        {
        case R.id.binfo:
           
        	Intent batteryInfoIntent = new Intent(this,GetBatteryInformation.class);
    		startActivity(batteryInfoIntent);
            return true;
            
  
        case R.id.map:
        	Intent getMapRouteIntent = new Intent(this,GetMapRoute.class);
    		startActivity(getMapRouteIntent);
            return true;
 
        case R.id.view:
        	Intent viewProfileIntent = new Intent(this,ViewProfile.class);
    		startActivity(viewProfileIntent);
            return true;
            
            
        case R.id.edit:

    		SharedPreferences pref = getApplicationContext().getSharedPreferences("any_prefname",MODE_PRIVATE);
     	    String username = pref.getString("username","");//2nd arg is the default value
    		
     	    ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("username",username));
       	
            String response = null;
            String result[];
    	    try 
    	    {
    	            		
    	    	response = CustomHttpClient.executeHttpPost(Global.URL+"res/JSP/User/JSP-For-Android/view_profile.jsp",postParameters);
           	    String res=response.toString();
           	    res = res.trim();
           	    res = res.replaceAll("\'","\\\\'");  // replacing ' to \\' 
           	    //res= res.replaceAll("\\s+",""); for removing space in between words        	              	 
                result = res.split("~");
                
                Intent editIntent = new Intent(this,UpdateProfile.class);
        	    editIntent.putExtra("userDetails",result);
        		startActivity(editIntent);
                 
           	 } 
           	 catch (Exception e) 
           	 {
           		System.out.println("*********** Opps,Exception in HomePage==>EditProfile() ***********");
           		e.printStackTrace();
           	 }
     	    
            return true;
            
            
        case R.id.pass:
        	
     	    showDialog(1);
            return true;
            
            
        case R.id.logout:
        	Intent logoutIntent = new Intent(this,MobNavSys4VisuallyImpaired.class);
    		startActivity(logoutIntent);
            return true;
 
    
 
       
 
        default:
            return super.onOptionsItemSelected(item);
        }
	}
	
	
	
	
	
	
	
	
	public void getMap()
	{
		Intent mapIntent = new Intent(this,GMapsActivity.class);
		
		GPSTracker gps = new GPSTracker(HomePage.this);
	 	double latitude = gps.getLatitude();
	 	double longitude = gps.getLongitude();
		
	 	mapIntent.putExtra("latitude",latitude);
		mapIntent.putExtra("longitude",longitude);
		
		startActivity(mapIntent);
		
	}

	protected Dialog onCreateDialog(int id) 
	  {
		    switch(id) 
		    {
		    
		      case 1:
		    		SharedPreferences pref = getApplicationContext().getSharedPreferences("any_prefname",MODE_PRIVATE);
		     	    String usernamee = pref.getString("username","");//2nd arg is the default value
		     	   
			    	settingsDialog = new AlertDialog.Builder(HomePage.this);
					settingsDialog.setTitle(" Change Password ");
					settingsDialog.setIcon(R.drawable.setings1);
			    	
					settingSLayout = new LinearLayout(HomePage.this);
					settingSLayout.setOrientation(LinearLayout.VERTICAL);
			    	
			    	TextView tv = new TextView(HomePage.this);
			    	TextView tv1 = new TextView(HomePage.this);
			    	TextView tv2 = new TextView(HomePage.this);
			    	TextView tv3 = new TextView(HomePage.this);
			    	TextView tv4 = new TextView(HomePage.this);
			    	//tv4.setTextColor(android.R.color.black);/* Setting The Text Color */
			    	tv.setText("Username");
			    	tv1.setText("Old Password");
			    	tv2.setText("New Password");
			    	tv3.setText("Confirm Password");
			    	tv4.setText(validationMsg);
			    	
			        un = new EditText(HomePage.this);
			    	un.setHint(usernamee);
			    	un.setText(usernamee);
			    	un.setEnabled(false);
			    	
			        old_pwd = new EditText(HomePage.this);
			        old_pwd.setHint("Enter Your Old Password.");
			        old_pwd.setText(oldPwdValue);
			        
			        
			        new_pwd = new EditText(HomePage.this);
			        new_pwd.setHint("Enter Your New Password.");
			        new_pwd.setText(newPwdValue);
			        
			        confirm_pwd = new EditText(HomePage.this);
			        confirm_pwd.setHint("Confirm Your New Password.");
			        confirm_pwd.setText(confirmPwdValue);
			    	
			    	settingSLayout.addView(tv);
			    	settingSLayout.addView(un);
			    	settingSLayout.addView(tv1);
			    	settingSLayout.addView(old_pwd);
			    	settingSLayout.addView(tv2);
			    	settingSLayout.addView(new_pwd);
			    	settingSLayout.addView(tv3);
			    	settingSLayout.addView(confirm_pwd);
			    	settingSLayout.addView(tv4);
			    	
			    	settingsDialog.setView(settingSLayout);
			    	
			    	settingsDialog.setPositiveButton("Change Password",new DialogInterface.OnClickListener()
				     {
					
						public void onClick(DialogInterface dialog, int pos)
						{
							username = un.getText().toString();
							oldPwd = old_pwd.getText().toString().trim(); 
							newPwd = new_pwd.getText().toString().trim();
							confirmPwd = confirm_pwd.getText().toString();
							
							if(oldPwd.equals("") && newPwd.equals("") && confirmPwd.equals(""))
							{
								validationMsg="Please,Enter The Mandatory Fields Value.";
								showDialog(1);
								Toast.makeText(getApplicationContext(),validationMsg,Toast.LENGTH_LONG).show();
							}
							else if(oldPwd.equals(""))
							{
								oldPwdValue="";
								newPwdValue=newPwd;
								confirmPwdValue=confirmPwd;
								validationMsg="Please,Enter The Old Password.";
								old_pwd.requestFocus();
								showDialog(1);
								Toast.makeText(getApplicationContext(),validationMsg,Toast.LENGTH_LONG).show();
							}
							else if(newPwd.equals(""))
							{
								oldPwdValue=oldPwd;
								newPwdValue="";
								confirmPwdValue=confirmPwd;
								validationMsg="New Password Can Not Be Blanked.";
								new_pwd.requestFocus();
								showDialog(1);
								Toast.makeText(getApplicationContext(),validationMsg,Toast.LENGTH_LONG).show();
							}
							else if(confirmPwd.equals(""))
							{
								oldPwdValue=oldPwd;
								newPwdValue=newPwd;
								confirmPwdValue="";
								validationMsg="Confirm Password Can Not Be Blanked.";
								confirm_pwd.requestFocus();
								showDialog(1);
								Toast.makeText(getApplicationContext(),validationMsg,Toast.LENGTH_LONG).show();
							}
							else if(!newPwd.equals(confirmPwd))
							{
								oldPwdValue=oldPwd;
								newPwdValue=newPwd;
								confirmPwdValue=confirmPwd;
								validationMsg="New Password and Confirm Password Is Not Same.";
								showDialog(1);
								Toast.makeText(getApplicationContext(),validationMsg,Toast.LENGTH_LONG).show();
							}
							else
							{
								oldPwdValue="";
								newPwdValue="";
								confirmPwdValue="";
								validationMsg="";
								
								/*System.out.println("Username : " + userName);
								System.out.println("Old Password : " + oldPwd);
								System.out.println("New Password : " + newPwd);
								System.out.println("Confirm Password : " + confirmPwd);*/
								
								/* Processing the main business logic(Changing the password) */
								ArrayList<NameValuePair> changePassParameters = new ArrayList<NameValuePair>();
								changePassParameters.add(new BasicNameValuePair("un",username));
								changePassParameters.add(new BasicNameValuePair("old_Pwd",oldPwd));
								changePassParameters.add(new BasicNameValuePair("new_Pwd",newPwd));
								changePassParameters.add(new BasicNameValuePair("coinfirm_Pwd",confirmPwd));
								
								try 
								{
									response = CustomHttpClient.executeHttpPost(Global.URL+"res/JSP/User/JSP-For-Android/changePassword.jsp",changePassParameters);
			                	    //192.168.1.16 is the System IP address,to get type ipconfig in cmd prompt
			                	    
			                	    res=response.toString();
			                	    // res = res.trim();
			                	    res= res.replaceAll("\\s+","");  
			                	    
			                	    if(res.equals("true"))
			                	    {
			                	    	/* Displaying Confirmation Message  */
			            				AlertDialog.Builder alertBox =new AlertDialog.Builder(HomePage.this);
			            			    alertBox.setTitle("Confirmation Message");
			            			    alertBox.setIcon(R.drawable.ok_icon);
			            		        alertBox.setMessage("Password Changed Sucessfully..");
			            		          
			            			    alertBox.setNegativeButton("OK",new DialogInterface.OnClickListener()
			            					{
			            						
			            						public void onClick(DialogInterface dialog, int pos)
			            						{
			            							Toast.makeText(getApplicationContext(),"Password Changed Sucessfully.",Toast.LENGTH_LONG).show();
			            						}
			            					});
			            			  
			            			    alertBox.show();
			                	    }
			                	    else
			                	    {
			                	    	/* Displaying Confirmation Message  */
			            				AlertDialog.Builder alertBox =new AlertDialog.Builder(HomePage.this);
			            			    alertBox.setTitle("Authuntication Message");
			            			    alertBox.setIcon(R.drawable.cancel_icon);
			            		        alertBox.setMessage("Sorry,Change Password Process Failed,Try Again..");
			            		          
			            			    alertBox.setNegativeButton("OK",new DialogInterface.OnClickListener()
			            					{
			            						
			            						public void onClick(DialogInterface dialog, int pos)
			            						{
			            							Toast.makeText(getApplicationContext(),"Sorry,Change Password Process Failed,Try Again..",Toast.LENGTH_LONG).show();
			            						}
			            					});
			            			  
			            			    alertBox.show();
			                	    }
								}
								catch (Exception e)
								{
									System.out.println("***** Opps,Exception In HomePage-ChangePassword() *****");
									e.printStackTrace();
								}
							}
							
							
							
						}
				     });
				    	
				    	settingsDialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener()
							{
								
								public void onClick(DialogInterface dialog, int pos)
								{
									Toast.makeText(getApplicationContext(),"Change Password Process Canceled...",Toast.LENGTH_LONG).show();
									dialog.dismiss();
									
								}
							});
				    	settingsDialog.show();
		    	        break;
		   
		        		
		    default:
	    		dialog = null;
	    		break;
		    }
		    return dialog;
	 }
	
}



