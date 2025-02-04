package resume;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

@Controller
public class ResumeController {

    @GetMapping("/")
    public String resumePage(Model model) {
        // 현재 날짜 시간
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String currentDateTime = sdf.format(new Date());
        model.addAttribute("currentDateTime", currentDateTime);

        // 외부 API 호출
        String apiUrl = "http://121.130.28.118:8080/BTLMS/ALPAS_TEST.do?name=정이주";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.getForEntity(apiUrl, Map.class);

        // API 결과 처리
        Map<String, String> apiResult = response.getBody();
        if (apiResult != null) {
            model.addAttribute("quote", apiResult.get("text")); // 명언
            model.addAttribute("base64Image", apiResult.get("img")); // Base64 이미지
        }

        // eco 로고
        model.addAttribute("logoPath", "/logo.png");

        return "resume";
    }
}
