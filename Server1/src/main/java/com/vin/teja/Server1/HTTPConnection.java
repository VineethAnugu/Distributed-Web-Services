package com.vin.teja.Server1;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPConnection implements Runnable {

	private String url, postData, response;
	private long waitTime;
	
	public HTTPConnection(String url, String postData) {
		this(url, postData, 0L);
	}
	
	public HTTPConnection(String url, String postData, long waitTime) {
		this.response = null;
		this.waitTime = waitTime;
		this.url = url!=null?url.trim():"";
		this.postData = postData!=null?postData:"";
	}
	
	public String getResponse() {
		return response;
	}
	
	
	@Override
	public void run() {
		try {
			Thread.sleep(this.waitTime);
			this.response = this.sendPost(this.url, this.postData);
			System.out.println(response);
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}catch(InterruptedException e) {}
	}

	
	private String sendPost(String url, String postData) throws MalformedURLException {

		URL obj = new URL(url);
		HttpURLConnection con=null;
		
		try {
			con = (HttpURLConnection) obj.openConnection();
			
			con.setReadTimeout(30000);
			con.setConnectTimeout(5000);
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept", "*/*");
			con.setRequestProperty("Content-Type", "text/xml");
			con.setRequestProperty("User-Agent", "curl/7.29.0");
			con.setRequestProperty("Content-Length", "curl/7.29.0");
			
			try {
			
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(postData);
				wr.flush();
				
				int responseCode = con.getResponseCode();
				
				System.out.println("\nSending 'POST' request to URL : " + url);
				System.out.println("Response Code : " + responseCode);		
				
				if ( responseCode == HttpURLConnection.HTTP_OK ) {
				
					BufferedReader in = new BufferedReader(
							new InputStreamReader(con.getInputStream()));
					
					StringBuilder response = new StringBuilder("");
					String inputLine = null;

					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					
					wr.close();
					in.close();
					return response.toString().trim();
					
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "Request Failed";

	}
	
	public static void main(String[] args) throws Exception {


	}

}