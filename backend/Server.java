import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;

public class Server {

    public static void main(String[] args) throws IOException {
        ExpenseManager manager = new ExpenseManager();

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new RequestHandler(manager));
        server.start();

        System.out.println("Expense Tracker backend running on http://localhost:8080");
    }
}

// This class decides what to do with each request
class RequestHandler implements HttpHandler {

    private ExpenseManager manager;

    public RequestHandler(ExpenseManager manager) {
        this.manager = manager;
    }

    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String query = exchange.getRequestURI().getRawQuery();
        String reply = "";

        if (path.equals("/list")) {
            reply = manager.listAsText();

        } else if (path.equals("/add")) {
            try {
                String date = getParam(query, "date");
                String category = getParam(query, "category");
                double amount = Double.parseDouble(getParam(query, "amount"));
                String description = getParam(query, "description");
                // The | symbol is used as a separator in the file, so remove it from the text
                description = description.replace("|", " ");
                manager.addExpense(date, category, amount, description);
                reply = "OK";
            } catch (NumberFormatException e) {
                reply = "ERROR: amount is not a number";
            }

        } else if (path.equals("/delete")) {
            try {
                int id = Integer.parseInt(getParam(query, "id"));
                manager.deleteExpense(id);
                reply = "OK";
            } catch (NumberFormatException e) {
                reply = "ERROR: id is not a number";
            }

        } else {
            reply = "Unknown request";
        }

        sendText(exchange, reply);
    }

    // Looks inside the query part of the address for one value.
    // Example: for "date=2026-10-17&amount=50", getParam(query, "amount") returns "50"
    private String getParam(String query, String name) {
        if (query == null) {
            return "";
        }
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] parts = pair.split("=", 2);
            if (parts.length == 2 && parts[0].equals(name)) {
                try {
                    return URLDecoder.decode(parts[1], "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    return "";
                }
            }
        }
        return "";
    }

    // Sends the text back to whoever made the request
    private void sendText(HttpExchange exchange, String text) throws IOException {
        byte[] bytes = text.getBytes("UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream out = exchange.getResponseBody();
        out.write(bytes);
        out.close();
    }
}
