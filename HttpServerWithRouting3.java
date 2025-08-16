// act 3 - simple routing for hello(/) time(/time) 404(/(other)) || limit: blocking requests handles only one client at a time
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class HttpServerWithRouting3 {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080");
        while(true){
            try(Socket socket = server.accept()){
                Date today = new Date();
                String htmlHello = "<html><body><h1>Hello from my java HTTP Server!</h1></body></html>";
                String htmlTime = "<html><body><h1>Hello from my java HTTP Server!</h1>"+"<p>Current time: " +today+ "</p></body></html>";
                String html404 = "<html><body><h1>Oops an error occured</h1><p>404 not found</p></body></html>";
                InputStreamReader isr = new InputStreamReader(socket.getInputStream());
                String html="";
                String statusLine="HTTP/1.1 200 OK\r\n";
                BufferedReader reader = new BufferedReader(isr);
                String requestLine = reader.readLine();
                //  GET / HTTP/1.1
                if(requestLine != null){
                    // System.out.println("Request: "+requestLine);
                    String path = requestLine.split(" ")[1];
                    if("/".equals(path)){
                        html = htmlHello;
                    }else if("/time".equals(path)){
                        html=htmlTime;
                    }else{
                        html=html404;
                        statusLine="HTTP/1.1 404 Not Found \r\n";
                    }
                }
                byte[] responseBytes = html.getBytes(StandardCharsets.UTF_8);
                // String httpResponse = statusLine+"Content-Type: text/html\r\n"+"Content-Length: "+responseBytes.length+"\r\n"+"\r\n"+html;
                // socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                String headers = statusLine+"Content-Type: text/html; charset=UTF-8\r\n"+"Content-Length: "+responseBytes.length+"\r\n"+"\r\n";
                socket.getOutputStream().write(headers.getBytes(StandardCharsets.UTF_8));
                socket.getOutputStream().write(responseBytes);
            }
        }
    }
}
