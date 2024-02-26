package models;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Getter
public class Server {
    public URL url;


    public Server(URL url) {
        this.url = url;
    }

    @SneakyThrows
    public Server(String strUrl) {
        this.url = new URL(strUrl);
    }

    public boolean testServer() throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        return con.getResponseCode() == 200;
    }
}
