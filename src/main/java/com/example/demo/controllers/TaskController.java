package com.example.demo.controllers;

import com.example.demo.Dtos.TaskDto;
import com.example.demo.Services.TaskServiceImp;
import com.example.demo.Services.UserServiceImp;
import com.example.demo.model.AppUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskServiceImp taskService;
    private final UserServiceImp userServiceImp;
    @GetMapping
    public ResponseEntity<?> getTaskOfUser(HttpServletRequest request)
    {
        AppUser user= userServiceImp.getUser(request);
        return ResponseEntity.ok(taskService.getAllTaskOfUser(user));
    }
    @PutMapping("/{taskid}/{status}")
    public ResponseEntity<?> updateTaskOfUser(HttpServletRequest request, @PathVariable Integer taskid, @PathVariable String status)
    {
            AppUser user =userServiceImp.getUser(request);
            taskService.updateTask(taskid,user.getId(),status);
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
    @PostMapping
    public ResponseEntity<?> creatTask(HttpServletRequest request,@RequestBody TaskDto taskDto) {
        AppUser appUser=userServiceImp.getUser(request);
        taskService.creatTask(appUser,taskDto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(HttpServletRequest request,@PathVariable Integer id)
    {
        AppUser user =userServiceImp.getUser(request);
        taskService.deleteTask(id,user.getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
