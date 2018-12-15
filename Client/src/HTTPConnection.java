import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HTTPConnection implements Runnable {

	private String url, postData, response;
	private long waitTime;
	private static Scanner scanner;
	
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
		
		System.out.println("Enter service from the following available services : ");
		System.out.println("1. Add, 2. Minus, 3. Mul, 4. Div");
		scanner = new Scanner(System.in);
		String service = scanner.nextLine();
		
/*		final Map<String, Integer> serviceLoad = new HashMap<>();
		
		serviceLoad.put("Add", 1);
		serviceLoad.put("Minus", 2);
		serviceLoad.put("Mul", 4);
		serviceLoad.put("Div", 3);
		
		int load = serviceLoad.get(service);*/
		
		// Which Request
		String data = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\r\n" + 
				"                  xmlns:us=\"http://teja.vin.com/service\">\r\n" + 
				"    <soapenv:Header/>\r\n" + 
				"    <soapenv:Body>\r\n" + 
				"        <us:WhichRequest>\r\n" + 
				"            <us:serviceName>"+service+"Service</us:serviceName>\r\n" +
				"            <us:load_inc>1</us:load_inc>\r\n" +
				"        </us:WhichRequest>\r\n" + 
				"    </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>";
		
		
		
		HTTPConnection http = new HTTPConnection("http://localhost:8082", data);
		Thread t = new Thread(http);
		//starting which request connection thread
		t.start();
		long discovery_startTime = System.currentTimeMillis();
		
		try {
			t.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}

		String server = null;
		
		//parsing the which response to retrieve data from it
		try
		{

        MessageFactory mf = MessageFactory.newInstance();

        MimeHeaders header = new MimeHeaders();     
        header.addHeader("Content-Type", "text/xml");

        InputStream is = new ByteArrayInputStream(http.getResponse().getBytes());

        SOAPMessage soapMessage = mf.createMessage(header,is);

        SOAPBody soapBody = soapMessage.getSOAPBody();

        NodeList nodes = soapBody.getElementsByTagName("ns2:Server");

        String Server = null;
        Node node = nodes.item(0);
        Server = node != null ? node.getTextContent() : "";
        System.out.println("Disovery Time :"+(System.currentTimeMillis() - discovery_startTime)+"ms" );
        server = Server;
        
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("Enter two numbers ");
		int a = scanner.nextInt();
		int b = scanner.nextInt();
		
		//service request
		String service_data = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\r\n" + 
				"                  xmlns:us=\"http://teja.vin.com/service\">\r\n" + 
				"    <soapenv:Header/>\r\n" + 
				"    <soapenv:Body>\r\n" + 
				"        <us:"+service+"Request>\r\n" + 
				"            <us:num1>"+a+"</us:num1>\r\n" + 
				"            <us:num2>"+b+"</us:num2>\r\n" + 
				"        </us:"+service+"Request>\r\n" + 
				"    </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>";
		
		
		HTTPConnection http1 = new HTTPConnection(("http://"+server), service_data);
		Thread t1 = new Thread(http1);
		//starting service request connection thread
		t1.start();
		t1.join();
		long startTime = System.currentTimeMillis();
		
		
		try
		{

        MessageFactory mf1 = MessageFactory.newInstance();

        MimeHeaders header1 = new MimeHeaders();     
        header1.addHeader("Content-Type", "text/xml");

        InputStream is1 = new ByteArrayInputStream(http1.getResponse().getBytes());

        SOAPMessage soapMessage1 = mf1.createMessage(header1,is1);

        SOAPBody soapBody1 = soapMessage1.getSOAPBody();

        NodeList nodes2 = soapBody1.getElementsByTagName("ns2:res_num");

        String result = null;
        Node node2 = nodes2.item(0);
        result = node2 != null ? node2.getTextContent() : "";
        System.out.println("Result : "+result);
        System.out.println("Service Time : "+(System.currentTimeMillis() - startTime)+"ms");

		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

