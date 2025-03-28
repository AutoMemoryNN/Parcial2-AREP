package edu.arep.parcial2.proxy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class ProxyController {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static Boolean flowControl = true;
    private static final String URL1 = "http://localhost:8085/resolve?input=";
    private static final String URL2 = "http://localhost:8084/resolve?input=";

    @GetMapping("/collatz")
    public ResponseEntity<Map<String, String>> collatz(@RequestParam Integer number) {
        try {
            URL obj = null;

            System.out.println(URL1);
            System.out.println(URL2);

            if (flowControl) {
                obj = new URL(URL1 + number.toString());
                System.out.println("llamando a URL1");
                flowControl = false;
            } else {
                obj = new URL(URL2 + number.toString());
                System.out.println("llamando a URL2");
                flowControl = true;
            }
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
                HashMap<String, String> res = new HashMap<>();
                res.put("operation", "http error");
                res.put("input", number.toString());
                return ResponseEntity.ok(res);
            }
        } catch (Exception e) {
            HashMap<String, String> res = new HashMap<>();
            res.put("operation", "error try");
            res.put("input", number.toString());
            res.put("output", e.toString());
            return ResponseEntity.ok(res);
        }

    }

}