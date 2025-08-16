// act 4 - creating a new thread per user to counter single user block || although multithreading handles multiple clients(which doesnt scale well many users) coz unlimited thread creation crashes JVM under heavy load
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class MultiThreadedServer4 {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        System.out.println("Listening for connections on port 8080.");;
        while(true){
            Socket client = server.accept();
            new Thread(new ClientHandler(client)).start();
        }
    }
    //inner class
    static class ClientHandler implements Runnable{
        private final Socket socket;
        ClientHandler(Socket socket){
            this.socket=socket;
        }
        public void run(){
            try(socket){
                Date today = new Date();
                String htmlHello = "<html><body><h1>Hello from my java HTTP Server!</h1></body></html>";
                String htmlTime = "<html><body><h1>Hello from my java HTTP Server!</h1>"+"<p>Current time: " +today+ "</p></body></html>";
                String html404 = "<html><body><h1>Oops an error occured</h1><p>404 not found</p></body></html>";
                InputStreamReader isr = new InputStreamReader(socket.getInputStream());
                String html="";
                String statusLine="HTTP/1.1 200 OK\r\n";
                BufferedReader reader = new BufferedReader(isr);
                String requestLine = reader.readLine();
                if(requestLine != null){
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
                String headers = statusLine+"Content-Type: text/html; charset=UTF-8\r\n"+"Content-Length: "+responseBytes.length+"\r\n"+"\r\n";
                OutputStream output = socket.getOutputStream();
                output.write(headers.getBytes(StandardCharsets.UTF_8));
                output.write(responseBytes);
                output.flush();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
