package gym.gym.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trainer {

     @NotBlank(message = "First name cannot be null or empty")
     @NotBlank(message = "Last name cannot be empty")
     @NotBlank(message = "Status must be either 'active' or 'inactive'")
     private int trainer_id;
     private String first_name;
     private String last_name;
     private String Password;
     private String email;
     private Integer phone_number;
     private String status;
     private String speciality;
     private LocalDateTime created_date;
     private LocalDateTime updated_date;


}
