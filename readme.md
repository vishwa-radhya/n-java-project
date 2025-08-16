# Mini Java HTTP Server  

A lightweight HTTP server implemented in **pure Java** (using `ServerSocket`) without external frameworks.  
This repository demonstrates the **evolution of an HTTP server**, starting from a simple response server and progressing into a **multi-threaded static file server** with MIME type support.  


##  Evolution Roadmap  

| Step | File                        | Key Feature                                      |
|------|-----------------------------|--------------------------------------------------|
| 1    | `SimpleHttpServer1.java`    | Accepts connections on port 8080                 |
| 2    | `SimpleHttpServerResponse2` | Responds with the current date                   |
| 3    | `HttpServerWithRouting3`    | Adds routing: `/`, `/time`, and 404 fallback     |
| 4    | `MultiThreadedServer4`      | Handles each client on a new thread              |
| 5    | `ServerWithThreadPool5`     | Uses thread pool (`ExecutorService`) for scaling |
| 6    | `StaticFileServing6`        | Serves static files (HTML, CSS, JS, images)      |

---

##  Features  

- **Step 1–2:** Handle HTTP requests & send basic responses  
- **Step 3:** Routing support (`/` → Hello page, `/time` → current date, 404 fallback)  
- **Step 4:** Multi-threaded client handling with `Thread`  
- **Step 5:** Scalable thread-pool implementation with `ExecutorService`  
- **Step 6:** Static file server that serves `HTML`, `CSS`, `JS`, and images from `/public`  

---

##  How to Run  

1. Clone the repository:  
   ```
   git clone https://github.com/vishwa-radhya/n-java-project.git
   cd n-java-project
   javac StaticFileServing6.java
   java StaticFileServing6
    ```
2. open `http://localhost:8080/` in browser
3. Place your static files (HTML, CSS, JS, images) in the public/ directory.
 ### Example
- http://localhost:8080/ → Serves public/index.html
- http://localhost:8080/styles.css → Serves CSS
- http://localhost:8080/script.js → Serves JavaScript
## Tech stack
- Language: Java (ServerSocket, I/O streams, threads)
- Core APIs: java.net, java.io, java.util.concurrent