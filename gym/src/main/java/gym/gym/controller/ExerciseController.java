package gym.gym.controller;

import gym.gym.Responses.ErrorResponse;
import gym.gym.Responses.SuccessResponse;
import gym.gym.Services.ExerciseServices;
import gym.gym.model.mapper.ExerciseMapper;
import gym.gym.model.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@Validated
@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {

    @Autowired
    private ExerciseServices exerciseServices;


    private ExerciseMapper ExerciseMapper;

    public ExerciseController(ExerciseMapper Exercisemapper) {

        this.ExerciseMapper =Exercisemapper;
    }

    @GetMapping("/ListAllExercises")
    public List<Exercise> getAll() {
        return ExerciseMapper.findall();
    }

    @PostMapping("/Insert")
    public ResponseEntity<?> createExercise(@RequestBody Exercise Ex) {
        try {
             exerciseServices.saveexercise(Ex);
            SuccessResponse successResponse = new SuccessResponse("Exercise added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);

        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid Input", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    @PutMapping("/Update")
    public ResponseEntity<?> updateExercise(@RequestBody Exercise exercise) {
        try {
            exerciseServices.updateExercise(exercise);
            SuccessResponse successResponse = new SuccessResponse("Exercise updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
