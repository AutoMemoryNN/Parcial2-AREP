package edu.arep.parcial2.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProxyController {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "";

    @GetMapping("/collatz")
    public ResponseEntity<Map<String, String>> collatz(@RequestParam Integer number) {
        try {

            URL obj = new URL(GET_URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);

            // The following invocation perform the connection implicitly before getting the
            // code
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                System.out.println(response.toString());
                HashMap<String, String> res = new HashMap<>();
                res.put("operation", "collatzsequence");
                res.put("input", number.toString());
                res.put("output", response.toString());
                return ResponseEntity.ok(res);
            } else {
                System.out.println("GET request not worked");
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }

}