import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Map;

import com.hazelcast.com.eclipsesource.json.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import jdk.nashorn.api.scripting.JSObject;
import org.json.*;

public class ServerDBchat {

    public static class Entry
    {
        public String user;
        public String message;

        Entry(String user, String message)
        {
            this.user = user;
            this.message = message;
        }
    };

    static HazelcastInstance hz;
    static Map<Date, Entry> map;

    public static void main(String[] args) throws IOException {
        hz = Hazelcast.newHazelcastInstance();
        map = hz.getMap("map");
        HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(args[1])), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            JSONObject json = new JSONObject(t.getRequestBody().toString());
            if (json.getString("request").equals("send"))
                store(t, json.getJSONObject("message"));
            else if (json.getString("request").equals("fetch"))
                fetch(t, json.getString("last_date"));
            else
                error(t);
        }

        private void response(HttpExchange t, int code, byte[] res) throws IOException {
            if (res == null)
            {
                t.sendResponseHeaders(code, 0);
                return;
            }
            t.sendResponseHeaders(code, res.length);
            OutputStream os = t.getResponseBody();
            os.write(res);
            os.close();
        }

        private void error(HttpExchange t) throws IOException {
            response(t, 500, null);
        }

        private void fetch(HttpExchange t, String str) throws IOException {
            JSONObject obj = new JSONObject();
            // fetch messages
            // sort
            response(t, 200, obj.toString().getBytes());
        }

        private void store(HttpExchange t, JSONObject message) throws IOException {
            map.put(new Date(message.getString("date")),
                    new Entry(message.getString("user"),
                              message.getString("message")));
            response(t, 200, null);
        }
    }
}
