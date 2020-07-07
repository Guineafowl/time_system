package time.system.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import time.system.persistence.TimeStamp;
import time.system.repository.TimePerDay;
import time.system.repository.TimeStampRepository;

import java.util.Date;
import java.util.List;

@CrossOrigin("http://ts.codebart.pl")
@RestController
@AllArgsConstructor
public class TimeStampController {

    private final TimeStampRepository timeStampRepository;

    @GetMapping("/stamp/status")
    public boolean stampStatus() {
        return timeStampRepository.stampedIn();
    }

    @PostMapping("/stamp/status")
    public void changeStatus() {
        if (timeStampRepository.stampedIn()) {
            TimeStamp timeStamp = timeStampRepository.lastStampIn();
            timeStamp.setStampOut(new Date());
            timeStampRepository.save(timeStamp);
        } else {
            timeStampRepository.save(new TimeStamp(new Date()));
        }
    }

    @GetMapping("/stamp/times")
    public List<TimePerDay> timePerDay() {
        return timeStampRepository.timePerDay();
    }

    @GetMapping("/stamp/in/last")
    public Date lastStampIn() {
        if (timeStampRepository.stampedIn()) {
            return timeStampRepository.lastStampIn().getStampIn();
        }
        throw new IllegalStateException("You are not stamped in");
    }
}
