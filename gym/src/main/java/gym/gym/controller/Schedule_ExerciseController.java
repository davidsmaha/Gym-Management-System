package gym.gym.controller;

import gym.gym.Responses.ErrorResponse;
import gym.gym.Responses.SuccessResponse;
import gym.gym.Services.SchedulExerciseServices;
import gym.gym.model.mapper.ScheduleExerciseMapper;
import gym.gym.model.Schedule_exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Validated
@RestController
@RequestMapping("/api/scheduleExercise")
public class Schedule_ExerciseController {

    @Autowired
    private SchedulExerciseServices ShedExServices;


    private ScheduleExerciseMapper Mapper;

    public Schedule_ExerciseController(ScheduleExerciseMapper mapper) {

        this.Mapper =mapper;
    }

    @GetMapping("/ListAllScheduleExercise")
    public List<Schedule_exercise> getAll() {
        return Mapper.findall();
    }

    @PostMapping("/Insert")
    public ResponseEntity<?> createSchedEx(@RequestBody Schedule_exercise Schedule_Exercise) {

        try {
             ShedExServices.saveScheduleExercise(Schedule_Exercise);
            SuccessResponse successResponse = new SuccessResponse("Schedule-Exercise added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);

        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid Input", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PutMapping("/Update")
    public ResponseEntity<?> updateScheduleExercise(@RequestBody Schedule_exercise scheduleExercise) {
        try {

            if (scheduleExercise.getSchedule_id() == 0 || scheduleExercise.getExercise_id() == 0) {
                throw new IllegalArgumentException("Schedule ID and Exercise ID are required");
            }


            ShedExServices.updateScheduleExercise(scheduleExercise);
            SuccessResponse successResponse = new SuccessResponse("Schedule-Exercise updated successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update schedule exercise");
        }
    }
}

