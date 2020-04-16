import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;

public class Main {

    public final static Logger logger = Logger.getLogger(Main.class.getName());

    public final static String OPEN_SCRIPT = "startArduino.py";
    public final static String CLOSE_SCRIPT = "resetArduino.py";

    private final static int DEFAULT_PORT = 8001;
    private static HttpServer server;

    public static void main(String[] args){
        logger.info("started the program");
        int port = choosePort(args);
        try{
            server = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        }catch(IOException ioe) {
            logger.warning("cant make server stopping the program");
            System.exit(0);
        }
        
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        MyHttpHandler mhh = makeTheContext();
        //start the server
        server.setExecutor(threadPoolExecutor);
        server.start();
        logger.info(" Server started on port "+port);

        runTimeManagment(mhh);
        
        //stop the server
        server.stop(0);
        logger.info("stopped the server");
        System.exit(0);
    }

    private static void runTimeManagment(MyHttpHandler mhh) {
        Scanner scanner = new Scanner(System.in);
        logger.info("waiting for EMPTY scan");
        String string = null;
        while(!(string = scanner.nextLine()).equals("")){
            if(string.equals("r")) {
                try {
                    Runtime.getRuntime().exec("python "+CLOSE_SCRIPT);
                } catch(IOException ioexception) {
                    logger.warning("problem running the close script");
                }
                mhh.reset();
            }
        }
    }

    private static MyHttpHandler makeTheContext(){
        MyHttpHandler mhh = new MyHttpHandler();
        server.createContext("/", mhh);
        logger.fine("made the context");
        return mhh;
    }

    private static int choosePort(String[] args) {
        //if there are args, take the args port, else take the default
        if(args.length > 0){
            try{
                return Integer.parseInt(args[0]);
            } catch(NumberFormatException nfe) {
                logger.warning("argument not formated right");
                //will fall to the default port
            }
        }
            
        return DEFAULT_PORT;
    }

}

class MyHttpHandler implements HttpHandler {

    private boolean hasDone = false;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        handleResponse(httpExchange);
    }

    public void reset(){
        hasDone = false;
    }

    private void handleResponse(HttpExchange httpExchange)  throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        // encode HTML content
        String htmlResponse = "<!DOCTYPE html><html><head><meta charset=\"utf-16\" /><link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css\" integrity=\"sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh\" crossorigin=\"anonymous\"></head><body><div class=\"container mt-5\">\n" +
                "        <h1 class=\"text-center\">!עבודה טובה      </h1>\n" +
                "    </div></body></html>";

        byte[] bytes = htmlResponse.getBytes(StandardCharsets.UTF_16);

        if(!hasDone) {
            hasDone = true;
            try{
                Runtime.getRuntime().exec("python "+Main.OPEN_SCRIPT);
            } catch(IOException ioexception) {
                    Main.logger.warning("problem running the close script");
            }
            
        }
        httpExchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        httpExchange.sendResponseHeaders(200, bytes.length);
        outputStream.write(bytes);
        outputStream.close();
    }
}
