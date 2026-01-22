package gym.gym.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Member {

    @NotBlank(message = "First name cannot be null or empty")
    @NotBlank(message = "Gender cannot be empty")
    @NotBlank(message = "Last name cannot be empty")
    @Pattern(regexp = "male|female", message = "Gender must be 'male' or 'female'")

    private int member_id;
    private String first_name;
    private String last_name;
    private String Password;
    private String gender;
    private LocalDate dob;
    private String email;
    private Integer phone_number;
    private String status;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;
}
