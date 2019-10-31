package com.reedelsevier.xmatters;

import java.io.IOException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Hello world!
 *
 */
public class App 
{
    private static Scanner input;
    private static String Authorization = "Basic c2VuZHBhZ2U6c2VuZHBhZ2U=";
    private static String group;
    private static String base_url = "https://relx.xmatters.com/api/xm/1";
    
	public static void main(String args[] )
    {
		getInput();
    }
    
	public static void getInput()
	{
		input = new Scanner(System.in);
        
		System.out.println("###################################");
        System.out.println("1. Get Groups");
        System.out.println("2. Get On-Call");
        System.out.println("3. Quit");
        System.out.println("###################################");
        System.out.print("What would you like to do: ");
                
        int number = input.nextInt();
                
        if (number == 1) {
        	System.out.print("Please input the Group name: ");
        	group = input.next();
        	getGroups(group);
        	getInput();
        } else if (number == 2) {
        	System.out.print("Please input the Group Id: ");
        	group = input.next();
        	getOnCall(group);
        	getInput();
        } else if (number == 3) {
        	System.exit(0);
        } else {
        	System.out.print("Please inpute a valid choice.");
        }
        
        
	}
	
    public static void getGroups(String group)
    {    	
        String endpoint_URL = "/groups";
        String search_query = "?search=" + group; 
        String url = base_url + endpoint_URL + search_query;
        
        OkHttpClient client = new OkHttpClient();

    	Request request = new Request.Builder()
    	  .url(url)
    	  .get()
    	  .addHeader("Authorization", Authorization)
    	  .addHeader("Cache-Control", "no-cache")
    	  .addHeader("Accept-Encoding", "gzip, deflate")
    	  .addHeader("Connection", "keep-alive")
    	  .addHeader("cache-control", "no-cache")
    	  .build();

    	try {
			
    		Response response = client.newCall(request).execute();
			String jsonData = response.body().string();
			JSONObject Jobject = new JSONObject(jsonData);
			JSONArray Jdata = Jobject.getJSONArray("data");
			
			for (int i = 0; i < Jdata.length(); i++) {
				JSONObject Dobject = Jdata.getJSONObject(i);
				System.out.print("Id: " + Dobject.getString("id") + " - ");
				System.out.println("Group Name: " + Dobject.getString("targetName"));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public static void getOnCall(String group)
    {
        String endpoint_URL = "/on-call";
        String search_query = "?groups=" + group; 
        String url = base_url + endpoint_URL + search_query;
        
        OkHttpClient client = new OkHttpClient();

    	Request request = new Request.Builder()
    	  .url(url)
    	  .get()
    	  .addHeader("Authorization", Authorization)
    	  .addHeader("Cache-Control", "no-cache")
    	  .addHeader("Accept-Encoding", "gzip, deflate")
    	  .addHeader("Connection", "keep-alive")
    	  .addHeader("cache-control", "no-cache")
    	  .build();

    	try {
			
    		Response response = client.newCall(request).execute();
			String jsonData = response.body().string();
			JSONObject Jobject = new JSONObject(jsonData);
			JSONArray Jdata = Jobject.getJSONArray("data");
			
			for (int i = 0; i < Jdata.length(); i++) {
				JSONObject Dobject = Jdata.getJSONObject(i);
				JSONArray Dmembers = Dobject.getJSONArray("members");

				for (int j = 0; j < Dmembers.length(); j++) {
					JSONObject Mobject = Dmembers.getJSONObject(j);
					JSONArray Mdata = Mobject.getJSONArray("data");

					for (int k = 0; k < Mdata.length(); k++) {
						JSONObject mobject = Mdata.getJSONObject(k);
						System.out.println(mobject.getString("position"));
					}
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
