package gym.gym.Services;

import gym.gym.model.mapper.TrainerMapper;
import gym.gym.model.Trainer;
import gym.gym.model.TrainerLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Service
public class TrainerServices {

        @Autowired
        private TrainerMapper trainerMapper;


    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);


        public Trainer saveTrainer(Trainer trainer) {


            if (!isValidEmail(trainer.getEmail())) {
                throw new NoSuchElementException("Invalid email format");
            }

            if (trainer.getFirst_name()==null) {
                throw new IllegalArgumentException("First name cannot be null or empty");

            }

            if (trainer.getLast_name()==null) {
                throw new IllegalArgumentException("Last name cannot be empty");
            }
            if (trainer.getPhone_number() == 0 || !isValidPhoneNumber(trainer.getPhone_number())) {
                throw new IllegalArgumentException("Phone number must be exactly 8 digits");
            }

            if (trainer.getSpeciality()==null) {
                throw new IllegalArgumentException("Last name cannot be empty");
            }
            if (trainer.getStatus() == null ||
                           (!trainer.getStatus().equalsIgnoreCase("active") &&
                            !trainer.getStatus().equalsIgnoreCase("inactive"))) {
                throw new IllegalArgumentException("Status must be 'active' or 'inactive'");
            }

            if(trainer.getPassword()==null){
                throw new IllegalArgumentException("Password cannot be null");
            }

            trainerMapper.inserttrainer(trainer);

            return trainer;
        }

    public void updateTrainer(Trainer trainer) {

        if (trainer.getFirst_name() != null && trainer.getFirst_name().trim().isEmpty() || "string".equalsIgnoreCase(trainer.getFirst_name().trim())) {
            trainer.setFirst_name(null);
        }
        if (trainer.getLast_name() != null && trainer.getLast_name().trim().isEmpty()|| "string".equalsIgnoreCase(trainer.getLast_name().trim())) {
            trainer.setLast_name(null);
        }
        if (trainer.getEmail() != null && trainer.getEmail().trim().isEmpty() ||  "string".equalsIgnoreCase(trainer.getEmail().trim())) {
            trainer.setEmail(null);
        }
        if (trainer.getStatus() != null && trainer.getStatus().trim().isEmpty() || "string".equalsIgnoreCase(trainer.getStatus().trim())) {
            trainer.setStatus(null);
        }
        if (trainer.getSpeciality() != null && trainer.getSpeciality().trim().isEmpty()|| "string".equalsIgnoreCase(trainer.getSpeciality().trim())) {
            trainer.setSpeciality(null);


        if (trainer.getPhone_number() != null) {
            if (trainer.getPhone_number() == 0) {
                    // Do not update in the database
                } else if (String.valueOf(trainer.getPhone_number()).length() != 8) {
                    throw new IllegalArgumentException("Phone number must be exactly 8 digits.");
                }

                // Check if the trainer exists
                Trainer existingTrainer = trainerMapper.findTrainerById(trainer.getTrainer_id());
                 if (existingTrainer == null) {
                    throw new IllegalArgumentException("Trainer ID not found");
                }

                // Update trainer details
                int rowsUpdated = trainerMapper.updateTrainer(trainer);
                if (rowsUpdated == 0) {
                    throw new RuntimeException("Failed to update trainer");
                }
            }
        }
        }

    public List<Trainer> findByFilters(String firstName, Integer id, String status, int page, int size) {
        int offset = (page - 1) * size;
        return trainerMapper.findByFilters(firstName, id, status, size, offset);
    }

    public Trainer findByEmailAndIdAndPass(TrainerLoginRequest loginRequest) {
        // Validate input parameters from the DTO
        String email = loginRequest.getEmail();
        Integer trainerId = loginRequest.getTrainerId();
        String password = loginRequest.getPassword();

        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (trainerId == null || trainerId <= 0) {
            throw new IllegalArgumentException("Invalid trainer ID");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        // Find trainer by email, ID, and password
        Trainer trainer = trainerMapper.findByEmailAndIdAndPass(email, trainerId, password);

        // Check if trainer exists
        if (trainer != null) {
            return trainer;
        } else {
            throw new IllegalArgumentException("Invalid credentials");
        }
    }
    public void deleteTrainerById(int trainerId) {
        // Check if the trainer exists first if needed
        Trainer existingTrainer = trainerMapper.findTrainerById(trainerId);
        if (existingTrainer == null) {
            throw new NoSuchElementException("Trainer with ID " + trainerId + " not found");
        }

        trainerMapper.deleteTrainerById(trainerId);
    }


    private boolean isValidEmail(String email) {
        return StringUtils.hasText(email) && EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(Integer phoneNumber) {
        if (phoneNumber == null) return false;
        String phoneNumberStr = phoneNumber.toString();
        return phoneNumberStr.matches("\\d{8}");
    }
}
