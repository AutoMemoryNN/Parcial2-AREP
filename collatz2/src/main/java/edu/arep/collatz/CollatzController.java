package edu.arep.collatz;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CollatzController {
    @GetMapping("/resolve")
    public ResponseEntity<String> resolve(@RequestParam Integer input) {
        StringBuilder sb = new StringBuilder();
        sb.append(input.toString());
        if (input == 0) {
            return ResponseEntity.ok(sb.toString());
        }
        Integer nCollatz = input;
        while (nCollatz != 1) {

            if (nCollatz % 2 == 0) {
                nCollatz = (Integer) (nCollatz / 2);
                sb.append(" -> " + nCollatz);
            } else {
                nCollatz = (Integer) (3 * nCollatz + 1);
                sb.append(" -> " + nCollatz);
            }

            System.out.println(nCollatz);
        }

        sb.append(" ->  0");

        return ResponseEntity.ok(sb.toString());
    }
}
