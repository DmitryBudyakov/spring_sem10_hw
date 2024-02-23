package com.example.spring_sem10_hw.controller;

import com.example.spring_sem10_hw.model.Task;
import com.example.spring_sem10_hw.model.TaskStatus;
import com.example.spring_sem10_hw.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskRepository taskRepository;

    /**
     * Просмотр списка всех задач
     *
     * @return
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Создание задачи со статусом NOT_STARTED и текущим временем
     *
     * @param task
     * @return
     */
    @PostMapping
    public Task addTask(@RequestBody Task task) {
        task.setStatus(TaskStatus.NOT_STARTED);
        task.setDateCreate(LocalDateTime.now());
        return taskRepository.save(task);
    }

    /**
     * Поиск всех задач по определенному статусу
     *
     * @param status
     * @return
     */
    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    /**
     * Возвращает Task по id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            return optionalTask.get();
        } else {
            return null;
        }
    }

    /**
     * Обновление статуса задачи по её id
     *
     * @param id
     * @param updatedTask
     * @return
     */
    @PutMapping("/{id}")
    public Task updateTaskStatus(@PathVariable Long id, @RequestBody Task updatedTask) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setStatus(updatedTask.getStatus());
            return taskRepository.save(task);
        } else {
            return null;
        }
    }

    /**
     * Удаление задачи по id
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }


}
