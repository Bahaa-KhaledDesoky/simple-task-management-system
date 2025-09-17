package com.example.demo.Services;

import com.example.demo.Dtos.TaskDto;
import com.example.demo.Exceptions.TaskForbiddenException;
import com.example.demo.Exceptions.TaskNotFoundException;
import com.example.demo.Mapping.TaskMapping;
import com.example.demo.Repository.TaskRepository;
import com.example.demo.model.AppUser;
import com.example.demo.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImp implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapping taskMapping;

    @Override
    public List<Task> getAllTaskOfUser(AppUser user) {

        try {
            return user.getTaskList();
        }
         catch (RuntimeException e)
        {
            throw new RuntimeException("error will fetch user tasks");
        }

    }
    @Override
    public boolean taskExistforUser(Integer taskId, Integer userId ) {
        Task task=findTaskById(taskId);
        if(task.getUser().getId()==userId)
            return true;
        return false;

    }
    @Override
    public Task findTaskById(Integer id) {
        return taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException());
    }
    @Override
    public void creatTask(AppUser user,TaskDto taskDto) {
        try {
            Task task=taskMapping.mapDtoToTask(taskDto);
            task.setUser(user);
            task.setStatus("Open");
            taskRepository.save(task);
        }
        catch (RuntimeException e)
        {
            throw new RuntimeException("cant create this task");
        }

    }
    @Override
    public boolean deleteTask(Integer taskId,Integer userId) {
        if(taskExistforUser(taskId,userId))
        {
            taskRepository.deleteById(taskId);
            return true;
        }
        else
            throw new TaskForbiddenException();
    }
    @Override
    public void updateTask(Integer taskId,Integer userId) {
        if(taskExistforUser(taskId,userId))
        {
            Task task =findTaskById(taskId);
            task.setStatus("Done");
            taskRepository.save(task);
        }
        else
            throw new TaskForbiddenException();
    }


}
