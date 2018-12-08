import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPConnection {

	public static void main(String[] args) throws Exception {
		
		HTTPConnection http = new HTTPConnection();
		http.sendPost();
	}

	
	// HTTP POST request
		private void sendPost() throws Exception {

			String url = "http://localhost:8082/Server1";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			//add request header
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept", "*/*");
			con.setRequestProperty("Content-Type", "text/xml");
			
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		/*	wr.writeBytes("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\r\n" + 
					"                  xmlns:us=\"http://teja.vin.com/service\">\r\n" + 
					"    <soapenv:Header/>\r\n" + 
					"    <soapenv:Body>\r\n" + 
					"        <us:MinusRequest>\r\n" + 
					"            <us:num1>8</us:num1>\r\n" + 
					"            <us:num2>4</us:num2>\r\n" + 
					"        </us:MinusRequest>\r\n" + 
					"    </soapenv:Body>\r\n" + 
					"</soapenv:Envelope>");*/
			wr.writeBytes("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\r\n" + 
					"                  xmlns:us=\"http://teja.vin.com/service\">\r\n" + 
					"    <soapenv:Header/>\r\n" + 
					"    <soapenv:Body>\r\n" + 
					"        <us:WhichRequest>\r\n" + 
					"            <us:serviceName>MulService</us:serviceName>\r\n" + 
					"        </us:WhichRequest>\r\n" + 
					"    </soapenv:Body>\r\n" + 
					"</soapenv:Envelope>");
			
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			//print result
			System.out.println(response.toString());

		}

	}


