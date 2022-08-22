import java.net.URLConnection;
import java.util.Scanner;
import java.net.URL;


public class UrlGetContentExample1{

	public static void main(String args[])throws Exception{

		// Get user input for desired URL
		Scanner scannerObj = new Scanner(System.in);	// Create scanner object
		String urlAddress;
		System.out.print("[?] where to go: ");
		urlAddress = scannerObj.nextLine();		// Read user input

		//Creating url object
		URL url = new URL(urlAddress);
		// URL url = new URL("http://www.example.com/");

		URLConnection connection = url.openConnection( );

		System.out.println(" Given Url is : " + url);
		System.out.println(" The content of given url is: " + url.getContent());

		Object contents = url.getContent();
		System.out.println(" contents.getClass().getName():" + contents.getClass().getName());

		String mimeType = connection.getContentType( );
		System.out.println(" The mime type is : " + mimeType);

		// Some major improvement needed to rest of code, doesn't print the page the way we want.
		
		Scanner sc = new Scanner(url.openStream());
		StringBuffer sb = new StringBuffer();
		while (sc.hasNext()) {
			sb.append(" " + sc.next());
		}
		String result = sb.toString();
		// Removing the HTML tags
		result = result.replaceAll("<[^>]*>", "");
		// Removing the CSS stuff
		result = result.replaceAll("\\{.*\\}", "");
		System.out.println("Contents of the web page: " + result);

	}
}
