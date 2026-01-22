package gym.gym.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Schedule_exercise {

    @NotNull(message = "Schedule ID  cannot be null or empty")
    @NotNull(message = "Exercise ID  cannot be null or empty")
    @NotBlank(message = "Order cannot be null or empty")
    @NotBlank(message = "number of repetition   cannot be null or empty")
    private int id;
    private Integer schedule_id;
    private Integer exercise_id;
    private Integer order;
    private Integer nb_of_repetition;
    private String status;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;



}
