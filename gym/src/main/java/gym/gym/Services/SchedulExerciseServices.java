package gym.gym.Services;


import gym.gym.model.mapper.ExerciseMapper;
import gym.gym.model.mapper.ScheduleExerciseMapper;
import gym.gym.model.mapper.ScheduleMapper;
import gym.gym.model.Exercise;

import gym.gym.model.Schedule;
import gym.gym.model.Schedule_exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SchedulExerciseServices {

     @Autowired
     private ScheduleMapper Schedex;

     @Autowired
     private ExerciseMapper Exer;


    @Autowired
    private ScheduleExerciseMapper ScheduleExerciseMapper;

    public Schedule_exercise  saveScheduleExercise(Schedule_exercise schedule_exercise){
     List<Schedule> Sched= Schedex.findSchedByMembId(schedule_exercise.getSchedule_id());
       Exercise Ex = Exer.findExById(schedule_exercise.getExercise_id());

       if(Sched == null || schedule_exercise.getSchedule_id() == 0){
           throw new IllegalArgumentException("Schedule ID not found");
       }

       if(Ex == null || schedule_exercise.getExercise_id()==0){
           throw new IllegalArgumentException("Exercise ID not found");
       }

        if(schedule_exercise.getStatus() == null ||
                               (!schedule_exercise.getStatus().equalsIgnoreCase("pending") &&
                                !schedule_exercise.getStatus().equalsIgnoreCase("done"))&&
                                !schedule_exercise.getStatus().equalsIgnoreCase("Cancelled")&&
                                !schedule_exercise.getStatus().equalsIgnoreCase("skipped")) {
            throw new IllegalArgumentException("Status must be 'pending' or 'done'  or  'skipped' or 'cancelled'");
        }

        if (schedule_exercise.getOrder() <= 0) {
            throw new IllegalArgumentException("Order must be a positive integer");
        }
        if (schedule_exercise.getNb_of_repetition() <= 0) {
            throw new IllegalArgumentException("Number of repetitions must be a positive integer");
        }

       ScheduleExerciseMapper.insertexerciseschedule(schedule_exercise);
       return  schedule_exercise;
    }


    public void updateScheduleExercise(Schedule_exercise scheduleExercise) {
        List<Schedule> Sched= Schedex.findSchedByMembId(scheduleExercise.getSchedule_id());
        Exercise Ex = Exer.findExById(scheduleExercise.getExercise_id());

        if (Sched == null) {
            throw new IllegalArgumentException("Schedule ID not found");
        }

        if (Ex == null) {
            throw new IllegalArgumentException("Exercise ID not found");
        }
        // Handle schedule_id
        if (scheduleExercise.getSchedule_id() <= 0) {
            scheduleExercise.setSchedule_id(0); // or handle as needed
        }

        // Handle exercise_id
        if (scheduleExercise.getExercise_id() <= 0) {
            scheduleExercise.setExercise_id(0); // or handle as needed
        }

        // Handle status
        if (scheduleExercise.getStatus() != null && scheduleExercise.getStatus().trim().isEmpty() || "string".equalsIgnoreCase(scheduleExercise.getStatus().trim())) {
            scheduleExercise.setStatus(null);
        }
        if (scheduleExercise.getOrder() != null && scheduleExercise.getOrder() <= 0) {
            scheduleExercise.setOrder(null); // Set to null if not valid
        }

        if (scheduleExercise.getNb_of_repetition() != null && scheduleExercise.getNb_of_repetition() <= 0) {
            scheduleExercise.setNb_of_repetition(null); // Set to null if not valid
        }
        int rowsUpdated = ScheduleExerciseMapper.updateScheduleExercise(scheduleExercise);
        if (rowsUpdated == 0) {
            throw new RuntimeException("Failed to update schedule exercise");
        }
    }


}
