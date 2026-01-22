package gym.gym.Services;


import gym.gym.model.mapper.ExerciseMapper;
import gym.gym.model.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExerciseServices {

    @Autowired
    private ExerciseMapper Exercisemapper;

    public Exercise saveexercise(Exercise exercise) {


        if (exercise.getName() == null || exercise.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }


        if (exercise.getDescription() == null || exercise.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }


        if (exercise.getStatus() == null || (!exercise.getStatus().equalsIgnoreCase("active") && !exercise.getStatus().equalsIgnoreCase("inactive"))) {
            throw new IllegalArgumentException("Status must be 'active' or 'inactive'");
        }
       Exercisemapper.insertexercise(exercise);

        return exercise;
    }


    public void updateExercise(Exercise exercise) {
        // Trim string fields and convert empty strings to null
        if (exercise.getName() != null && exercise.getName().trim().isEmpty() || "string".equalsIgnoreCase(exercise.getName().trim())) {
            exercise.setName(null);
        }

        if (exercise.getDescription() != null && exercise.getDescription().trim().isEmpty() || "string".equalsIgnoreCase(exercise.getDescription().trim())) {
            exercise.setDescription(null);
        }

        if (exercise.getStatus() != null && exercise.getStatus().trim().isEmpty() || "string".equalsIgnoreCase(exercise.getStatus().trim())) {
            exercise.setStatus(null);
        }
        // Check if the exercise exists
        Exercise existingExercise = Exercisemapper.findExById(exercise.getExercise_id());
        if (existingExercise == null) {
            throw new IllegalArgumentException("Exercise ID not found");
        }


        Exercisemapper.updateExercise(exercise);
    }
}
