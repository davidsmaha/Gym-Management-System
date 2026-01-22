package gym.gym.controller;

import gym.gym.Responses.ErrorResponse;
import gym.gym.Responses.SuccessResponse;
import gym.gym.Services.TrainerServices;
import gym.gym.model.mapper.TrainerMapper;
import gym.gym.model.Trainer;
import gym.gym.model.TrainerLoginRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Validated
@RestController
@RequestMapping("/api/trainer")
public class TrainerController {


    @Autowired
    private TrainerServices trainerService;

    private TrainerMapper trainerMapper;

    public TrainerController(TrainerMapper Trainermapper) {

        this.trainerMapper =Trainermapper;
    }

    @GetMapping("/ListAllTrainers")
    public List<Trainer> getAll() {
        return trainerMapper.findall();
    }

    @PostMapping("/Insert")
    public ResponseEntity<?> createTrainer(@RequestBody Trainer trainer) {

        try {
           trainerService.saveTrainer(trainer);
            SuccessResponse successResponse = new SuccessResponse("Trainer added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid Input", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    @PutMapping("/Update")
    public ResponseEntity<?> updateTrainer(@RequestBody Trainer trainer) {
        try {
            // Ensure the ID is present and valid
            if (trainer.getTrainer_id() == 0) {
                throw new IllegalArgumentException("Trainer ID is required");
            }

            // Call the service to update the trainer
            trainerService.updateTrainer(trainer);
            SuccessResponse successResponse = new SuccessResponse("Trainer updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update trainer");
        }
    }

    @GetMapping("/listTrainers")
    public ResponseEntity<?> findByFilters(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {

        // Validate page and size
        if (page <= 0 || size <= 0) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid Input", "Page and size must be positive integers.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Call the service method
        List<Trainer> trainers = trainerService.findByFilters(firstName, id, status, page, size);

        if (trainers == null || trainers.isEmpty()) {
            // Return a message indicating no trainers were found
            ErrorResponse errorResponse = new ErrorResponse("Not Found", "No trainers found with the provided filters.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        return ResponseEntity.ok(trainers);
    }

    @DeleteMapping("/DeleteTrainer")
    public ResponseEntity<?> deleteTrainerById(@RequestParam("id") int trainerId) {
        try {
            trainerService.deleteTrainerById(trainerId);
            SuccessResponse successResponse = new SuccessResponse("Trainer with ID " + trainerId + " has been deleted successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid TrainerLoginRequest loginRequest) {
        try {
            // Perform login check using the login request DTO
            trainerService.findByEmailAndIdAndPass(loginRequest);
            return ResponseEntity.ok(new SuccessResponse("Login successful"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
