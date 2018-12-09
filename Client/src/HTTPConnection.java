import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPConnection implements Runnable {

	private String url, postData, response;
	
	public HTTPConnection(String url, String postData) {
		this.response = null;
		this.url = url!=null?url.trim():"";
		this.postData = this.postData!=null?this.postData.trim():"";
	}
	
	public String getResponse() {
		return response;
	}
	
	
	@Override
	public void run() {
		try {
			this.response = this.sendPost(this.url, this.postData);
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}catch(SecurityException e) {}
	}

	
	private String sendPost(String url, String postData) throws MalformedURLException {

		URL obj = new URL(url);
		HttpURLConnection con=null;
		
		try {
			con = (HttpURLConnection) obj.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept", "*/*");
			con.setRequestProperty("Content-Type", "text/xml");
			
			try(DataOutputStream wr = new DataOutputStream(con.getOutputStream());
					BufferedReader in = new BufferedReader(
							new InputStreamReader(con.getInputStream()))) {
				
				wr.writeBytes(postData);
				wr.flush();
				int responseCode = con.getResponseCode();
				
				System.out.println("\nSending 'POST' request to URL : " + url);
				System.out.println("Response Code : " + responseCode);		
				
				if ( responseCode == HttpURLConnection.HTTP_OK ) {
					
					StringBuilder response = new StringBuilder("");
					String inputLine = null;

					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					
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

		String data = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\r\n" + 
				"                  xmlns:us=\"http://teja.vin.com/service\">\r\n" + 
				"    <soapenv:Header/>\r\n" + 
				"    <soapenv:Body>\r\n" + 
				"        <us:WhichRequest>\r\n" + 
				"            <us:serviceName>MulService</us:serviceName>\r\n" + 
				"        </us:WhichRequest>\r\n" + 
				"    </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>";
		
		HTTPConnection http = new HTTPConnection("http://localhost:8082", data);
		Thread t = new Thread(http);
		t.start();
		t.join();
		System.out.println(http.getResponse());
	}

}