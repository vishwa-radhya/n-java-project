import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class SimpleHttpServerResponse {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080");
        while(true){
            try(Socket socket = server.accept()){
                Date today = new Date();
                System.out.println(today);
                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
                socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                System.out.println(httpResponse.getBytes("UTF-8"));
            }
        }
    }
}
/*
 * sample console outpus
 * Sat Aug 16 10:52:46 IST 2025
[B@2e817b38
Sat Aug 16 10:52:46 IST 2025
[B@728938a9
Sat Aug 16 10:52:47 IST 2025
[B@21b8d17c
Sat Aug 16 10:53:02 IST 2025
[B@6433a2
Sat Aug 16 10:53:02 IST 2025
[B@5910e440
Sat Aug 16 10:53:02 IST 2025
[B@6267c3bb
Sat Aug 16 10:53:02 IST 2025
[B@533ddba
Sat Aug 16 10:53:04 IST 2025
[B@246b179d
Sat Aug 16 10:53:04 IST 2025
[B@7a07c5b4
Sat Aug 16 10:53:04 IST 2025
[B@26a1ab54
 */

 /*
  * browser response
    Sat Aug 16 10:54:23 IST 2025
  */
