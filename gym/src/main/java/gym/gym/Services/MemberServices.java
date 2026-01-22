package gym.gym.Services;

import gym.gym.configuration.ConfigPropertyReader;
import gym.gym.model.mapper.MemberMapper;

import gym.gym.model.Member;
import gym.gym.model.MemberLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Service
public class MemberServices {

    @Autowired
    private MemberMapper MemberMapper;
    @Autowired
    ConfigPropertyReader Config;



    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";
    private static final Pattern DATE_REGEX = Pattern.compile(DATE_PATTERN);

    public void validateDateOfBirth(String dobStr) {
        if (dobStr == null || !DATE_REGEX.matcher(dobStr).matches()) {
            throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD.");
        }

        try {
            LocalDate dob = LocalDate.parse(dobStr);
            int year = dob.getYear();
            if (year < 1900 || year > LocalDate.now().getYear()) {
                throw new IllegalArgumentException("'Year of birth must be between 1900 and the current year");
            }
            // Ensure age is at least 18
            if (Period.between(dob, LocalDate.now()).getYears() < Config.getAgeLimit()) {
                throw new IllegalArgumentException("Member must be at least 18 years old.");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD.");
        }

    }

    public Member saveMember(Member member) {



        validateDateOfBirth(member.getDob().toString());


        if (!isValidEmail(member.getEmail())) {
            throw new NoSuchElementException("Invalid email format");
        }

        if (!StringUtils.hasText(member.getFirst_name())) {
            throw new IllegalArgumentException("First name cannot be null or empty");

        }

        if (!StringUtils.hasText(member.getLast_name())) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }

        if (member.getGender() == null ||
                (!member.getGender().equalsIgnoreCase("male") &&
                 !member.getGender().equalsIgnoreCase("female"))) {
            throw new IllegalArgumentException("Gender must be 'male' or 'female'");
        }

        if (member.getPhone_number() == 0 || !isValidPhoneNumber(member.getPhone_number())) {
            throw new IllegalArgumentException("Phone number must be exactly 8 digits");
        }

        if (member.getStatus() == null ||
                       (!member.getStatus().equalsIgnoreCase("active") &&
                        !member.getStatus().equalsIgnoreCase("inactive"))) {
            throw new IllegalArgumentException("Status must be 'active' or 'inactive'");
        }
            if(member.getPassword() == null){
                throw new IllegalArgumentException("Please enter a Password");
            }


        MemberMapper.insertUser(member);

        return member;
    }

    public void updateMember(Member member) {

        if (member.getFirst_name() != null || member.getFirst_name().trim().isEmpty()|| "string".equalsIgnoreCase(member.getFirst_name().trim())) {
            member.setFirst_name(null);
        }
        if (member.getLast_name() != null || member.getLast_name().trim().isEmpty()|| "string".equalsIgnoreCase(member.getLast_name().trim())) {
            member.setLast_name(null);
        }
        if (member.getGender() != null || member.getGender().trim().isEmpty()|| "string".equalsIgnoreCase(member.getGender().trim())) {
            member.setGender(null);
        }
        if (member.getEmail() != null || member.getEmail().trim().isEmpty() || member.getEmail().equals("string")) {
            member.setEmail(null);
        }
        if (member.getPhone_number() != null) {
            if (member.getPhone_number() == 0) {
                // Do not update in the database
            } else if (String.valueOf(member.getPhone_number()).length() != 8) {
                throw new IllegalArgumentException("Phone number must be exactly 8 digits.");
            }
        }

        if (member.getStatus() != null || member.getStatus().trim().isEmpty() || "string".equalsIgnoreCase(member.getStatus().trim())) {
            member.setStatus(null);
        }
        Member existingMember = MemberMapper.findById(member.getMember_id());
        if (member.getMember_id() == 0 || existingMember==null) {
            throw new IllegalArgumentException("Member ID is required");
        }

        if (member.getDob() != null && member.getDob().equals(LocalDate.now())) {
            member.setDob(null); // Skip updating dob if it's the same as existing
        }
        // Perform the update
        int updatedRows = MemberMapper.updateMember(member);

        if (updatedRows == 0) {
            throw new IllegalArgumentException("Member not found with ID: " + member.getMember_id());
        }


        Member updatedMember = MemberMapper.findById(member.getMember_id());

    }


    public List<Member> findByFilters(String firstName, Integer id, String status, int page, int size) {
        int offset = (page - 1) * size;
        return MemberMapper.findByFilters(firstName, id, status, size, offset);
    }
    public Member findByEmailAndIdAndPass(MemberLoginRequest loginRequest) {
        // Validate input parameters from the DTO
        String email = loginRequest.getEmail();
        Integer memberId = loginRequest.getMemberId();
        String password = loginRequest.getPassword();

        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (memberId == null || memberId <= 0) {
            throw new IllegalArgumentException("Invalid member ID");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        // Find member by email, ID, and password
        Member member = MemberMapper.findByEmailAndIdAndPass(email, memberId, password);

        // Check if member exists
        if (member != null) {
            return member;
        } else {
            throw new IllegalArgumentException("Invalid credentials");
        }
    }



    public Member getMemberById(int id) {
        return MemberMapper.findById(id);
    }



    public void deleteMemberById(int memberId) {
        // Optionally, you can check if the member exists before deleting
        Member member = MemberMapper.findById(memberId);
        if (member == null) {
            throw new NoSuchElementException("Member with ID " + memberId + " not found");
        }
        MemberMapper.deleteMemberById(memberId);
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
