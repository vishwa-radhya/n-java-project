// act 6 - mini static file sercer to serve HtML,CSS,Js or images from public/ directory with thread pool.

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StaticFileServing6 {
    private static final int PORT = 8080;
    private static final int THREAD_POOL_SIZE = 10;
    private static final String PUBLIC_DIR = "public";
    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(PORT);
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        System.out.println("Static File HTTP Server started on port " + PORT);

        while (true) {
            Socket client = server.accept();
            threadPool.submit(new ClientHandler(client));
        }
    }
    static class ClientHandler implements Runnable {
        private final Socket socket;
        ClientHandler(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run(){
            try(socket){
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String requestLine = reader.readLine();
                String path ="/";
                if(requestLine!=null && requestLine.startsWith("GET")){
                    String[] parts = requestLine.split(" ");
                    if(parts.length >=2){
                        path = parts[1];
                    }
                }
                if("/".equals(path)){
                    path="/index.html";
                }
                File file = new File(PUBLIC_DIR+path);
                String statusLine;
                byte[] responseBytes;
                if(file.exists() && !file.isDirectory()){
                    responseBytes=Files.readAllBytes(file.toPath());
                    statusLine="HTTP/1.1 200 OK\r\n";
                }else{
                    String html404 = "<html><body><h1>404 Not Found</h1></body></html>";
                    responseBytes=html404.getBytes(StandardCharsets.UTF_8);
                    statusLine="HTTP/1.1 404 Not Found\r\n";
                }
                String contentType = getContentType(path);
                String headers = statusLine+"Content-Type: "+contentType+"; charset=UTF-8\r\n"+"Content-Length: "+responseBytes.length+"\r\n"+"\r\n";
                OutputStream output = socket.getOutputStream();
                output.write(headers.getBytes(StandardCharsets.UTF_8));
                output.write(responseBytes);
                output.flush();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        private String getContentType(String path) {
            if (path.endsWith(".html") || path.endsWith(".htm")) return "text/html";
            if (path.endsWith(".css")) return "text/css";
            if (path.endsWith(".js")) return "application/javascript";
            if (path.endsWith(".png")) return "image/png";
            if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
            if (path.endsWith(".gif")) return "image/gif";
            if (path.endsWith(".svg")) return "image/svg+xml";
            return "text/plain";
        }
    }
}
