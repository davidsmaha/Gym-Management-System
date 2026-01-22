package gym.gym.controller;

import gym.gym.Responses.ErrorResponse;
import gym.gym.Responses.SuccessResponse;
import gym.gym.Services.ScheduleServices;

import gym.gym.model.mapper.ScheduleMapper;
import gym.gym.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "http://localhost:3000")
@Validated
@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleServices scheduleServices;



    private ScheduleMapper SCheduleMapper;

    public ScheduleController(ScheduleMapper Schedulemapper) {

        this.SCheduleMapper =Schedulemapper;
    }


    @GetMapping("/ListAllSChedules")
    public List<Schedule> getAll() {
        return SCheduleMapper.findall();
    }

    @PostMapping("/Insert")
    public ResponseEntity<?> createSchedule(@RequestBody Schedule schedule) {

        try {
             scheduleServices.saveSchedule(schedule);
            SuccessResponse successResponse = new SuccessResponse("Schedule added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);

        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid Input", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        }
    @GetMapping("/member/{id}")
    public ResponseEntity<?> getSchedulesByMemberId(@PathVariable("id") int id) {
        List<Schedule> schedules = scheduleServices.getSchedulesByMemberId(id);
        if (schedules != null && !schedules.isEmpty()) {
            return ResponseEntity.ok(schedules);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No schedules found for this member");
        }
    }

    @PutMapping("/Update")
    public ResponseEntity<?> updateSchedule(@RequestBody Schedule schedule) {
        try {
            Schedule updatedSchedule = scheduleServices.updateSchedule(schedule);
            SuccessResponse successResponse = new SuccessResponse("Schedule updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        } catch (NoSuchElementException e) {
            ErrorResponse errorResponse = new ErrorResponse("Not Found", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid Input", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse("Update Failed", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
