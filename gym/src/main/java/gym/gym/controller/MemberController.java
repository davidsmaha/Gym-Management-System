package gym.gym.controller;

import gym.gym.Responses.ErrorResponse;
import gym.gym.Responses.SuccessResponse;
import gym.gym.Services.*;
import gym.gym.model.mapper.Mapper;
import gym.gym.model.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.format.DateTimeParseException;
import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@Validated
@RestController
@RequestMapping("/api/member")
public class    MemberController {

    @Autowired
    private Mapper usermapper;

    @Autowired
     private MemberServices memberservice;



    public MemberController(Mapper usermapper) {

        this.usermapper = usermapper;
    }


    @GetMapping("/ListAllMembers")
    public List<Member> getAll() {
        return usermapper.findall();
    }


    @PostMapping("/Insert")
    public ResponseEntity<?> creatseUser(@RequestBody Member member) {

        try {
            memberservice.saveMember(member);
            SuccessResponse successResponse = new SuccessResponse("Member added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid Input", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (DateTimeParseException e){
            throw new IllegalArgumentException("Invalid date format for 'dob'. Please use the format YYYY-MM-DD.");
        }
    }


    @PutMapping("/Update")
    public ResponseEntity<?> updateMember(@RequestBody Member member) {
        try {
            memberservice.updateMember(member);
            SuccessResponse successResponse = new SuccessResponse("Member updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/byfilter")
    public ResponseEntity<?> findByFirstNamePrefix(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "id", required = false) Integer id,   // Added id parameter
            @RequestParam(value = "status", required = false) String status, // Added status parameter
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {

        // Validate page and size
        if (page <= 0 || size <= 0) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid Input", "Page and size must be positive integers.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Call the service method
        List<Member> members = memberservice.findByFilters(firstName, id, status, page, size);

        if (members == null || members.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse("Not Found", "No members found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        return ResponseEntity.ok(members);
    }



    @DeleteMapping("/DeleteMember")
    public ResponseEntity<?> deleteMemberById(@RequestParam("id") int memberId) {
        try {
            memberservice.deleteMemberById(memberId);
            SuccessResponse successResponse = new SuccessResponse("Member with ID " + memberId + " has been deleted successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid MemberLoginRequest loginRequest) {
        try {
            // Perform login check using the login request DTO
            memberservice.findByEmailAndIdAndPass(loginRequest);
            return ResponseEntity.ok(new SuccessResponse("Login successful"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}




