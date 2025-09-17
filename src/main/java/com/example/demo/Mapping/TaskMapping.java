package com.example.demo.Mapping;

import com.example.demo.Dtos.TaskDto;
import com.example.demo.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapping {

//    public TaskDto mapTaskToDto(Task task) {
//       return new TaskDto(
//                 task.getTitle()
//                ,task.getDescription()
//                ,task.getStatus());
//    }

    public Task mapDtoToTask(TaskDto taskDto) {
        return Task.builder()
                .title(taskDto.title())
                .description(taskDto.description())
                .build();
    }
}
