package CrazyKwak.board.home;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/api")
    public ResponseEntity home() {
        return new ResponseEntity("연결 성공!", HttpStatus.OK);
    }

}
