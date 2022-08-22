// Demonstrate URL.
import java.net.*;
import java.util.Scanner;

class URLDemo {
  public static void main(String args[]) throws MalformedURLException {

    Scanner myObj = new Scanner(System.in);  // Create a Scanner object
    String urlAddress;
    System.out.println("Enter URL: ");
    urlAddress = myObj.nextLine();  // Read user input

    URL hp = new URL(urlAddress);

    System.out.println("URL INFORMATION");
    System.out.println("Protocol: " + hp.getProtocol());
    System.out.println("Port:     " + hp.getPort());
    System.out.println("Host:     " + hp.getHost());
    System.out.println("File:     " + hp.getFile());
    System.out.println("Ext:      " + hp.toExternalForm());

  }
}
