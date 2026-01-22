package gym.gym.Services;
import gym.gym.model.mapper.MemberMapper;
import gym.gym.model.mapper.ScheduleMapper;
import gym.gym.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ScheduleServices {
    @Autowired
    private MemberMapper membermapper;

    @Autowired
    private ScheduleMapper schedulemapper;
    public Schedule saveSchedule(Schedule schedule) {
        System.out.println("Saving schedule: " + schedule);

        // Validate member ID
        List<Schedule> schedules = schedulemapper.findById(schedule.getMember_id());
        if (schedules.isEmpty()) {
            throw new NoSuchElementException("Member ID " + schedule.getMember_id() + " not found");
        } else if (schedules.size() > 1) {
            throw new RuntimeException("Multiple schedules found for member ID " + schedule.getMember_id());
        }

        // Validate status
        if (schedule.getStatus() == null ||
                (!schedule.getStatus().equalsIgnoreCase("InProgress") &&
                        !schedule.getStatus().equalsIgnoreCase("Completed") &&
                        !schedule.getStatus().equalsIgnoreCase("Cancelled"))) {
            throw new IllegalArgumentException("Status must be 'InProgress', 'Completed', or 'Cancelled'");
        }

        // Insert schedule
        int result = schedulemapper.insertschedule(schedule);
        System.out.println("Insert result: " + result);

        if (result != 1) {
            throw new RuntimeException("Failed to insert schedule");
        }

        return schedule;
    }




    public Schedule updateSchedule(Schedule schedule) {
        // Check if the schedule exists
        Schedule existingSchedule = schedulemapper.findScheduleById(schedule.getSchedule_id());
        if (existingSchedule == null) {
            throw new NoSuchElementException("Schedule ID " + schedule.getSchedule_id() + " not found");
        }

        // Optionally check and update fields
        if (schedule.getStatus() != null && !schedule.getStatus().trim().isEmpty()) {
            existingSchedule.setStatus(schedule.getStatus());
        }
        // Update other fields if needed
        // existingSchedule.setOtherField(schedule.getOtherField());

        // Perform the update
        int rowsUpdated = schedulemapper.updateSchedule(existingSchedule);
        System.out.println("Rows updated: " + rowsUpdated);  // Log rows updated for debugging

        if (rowsUpdated == 0) {
            throw new RuntimeException("Failed to update schedule with ID " + schedule.getSchedule_id());
        }

        return existingSchedule;
    }

    public List<Schedule> getSchedulesByMemberId(int id) {
        return schedulemapper.findSchedByMembId(id);
    }
}



