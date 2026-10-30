package college.edu.gradefix.controller;

import college.edu.gradefix.service.DashboardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Map;

@RestController 
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService service;

    public DashboardController(DashboardService s){
        service=s;
    }

    @GetMapping("/summary")
    public Map<String,Long> summary() {
        return service.summary();
    }


}
