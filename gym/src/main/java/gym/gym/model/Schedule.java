package gym.gym.model;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Schedule {


    @NotBlank(message = "Status must be either 'active' or 'inactive'")
    private int schedule_id;
    private LocalDate date;
    private String status;
    private int member_id;
}
