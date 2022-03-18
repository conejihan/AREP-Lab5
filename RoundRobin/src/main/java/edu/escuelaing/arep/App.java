package edu.escuelaing.arep;

import static spark.Spark.*;

public class App {

    public static void main( String[] args ) {
        port(getPort());
        staticFileLocation("/static");
        HttpClient client = new HttpClient();
        get("/", (req, res) -> {
            res.redirect( "index.html");
            return null;
        });
        get("/results", (req, res) -> {
            res.status(200);
            res.type("application/json");
            String resp = client.getMessage();
            client.changeNumberServer();
            return resp;
        });
        post("/results", (req, res) -> {
            client.postMessage(req.body());
            client.changeNumberServer();
            return "";
        });
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}
