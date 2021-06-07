package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import com.tenniscourts.tenniscourts.TennisCourtService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;


    public ScheduleDTO addSchedule(CreateScheduleRequestDTO createScheduleRequestDTO) {
        //TODO: implement addSchedule
        ScheduleDTO newSchedule = ScheduleDTO.builder()
                .startDateTime(createScheduleRequestDTO.getStartDateTime())
                .tennisCourtId(createScheduleRequestDTO.getTennisCourtId())
                .build();
        return scheduleMapper.map(scheduleRepository.saveAndFlush(scheduleMapper.map(newSchedule)));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        //TODO: implement
        return scheduleMapper.map(scheduleRepository.findAllByStartDateTimeAndEndDateTime(startDate,endDate));
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        //TODO: implement
        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).orElseThrow(()->{
            throw new EntityNotFoundException("Schedule not found.");
        });
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
