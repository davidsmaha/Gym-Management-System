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
public class Exercise {


    @NotBlank(message = "Name cannot be null or empty")
    @NotBlank(message = "Description cannot be empty")
    @NotBlank(message = "Status must be either 'active' or 'inactive'")
    private int exercise_id;
    private String name;
    private String description;
    private String status;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;




}
