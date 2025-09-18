package com.example.demo.Services;

import com.example.demo.Dtos.TaskDto;
import com.example.demo.model.AppUser;
import com.example.demo.model.Task;

import java.util.List;

public interface TaskService {
    List<Task> getAllTaskOfUser(AppUser user);
    boolean taskExistforUser(Integer taskId, Integer userId );
    Task findTaskById(Integer id);
    void creatTask(AppUser user,TaskDto taskDto);
    boolean deleteTask(Integer taskId,Integer userId);
     void updateTask(Integer taskId,Integer userId,String status);
}
