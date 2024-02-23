package com.example.spring_sem10_hw;

import com.example.spring_sem10_hw.controller.TaskController;
import com.example.spring_sem10_hw.model.Task;
import com.example.spring_sem10_hw.model.TaskStatus;
import com.example.spring_sem10_hw.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TaskOperationIntegrationTest {

    @MockBean
    public TaskRepository repository;
    @Autowired
    public TaskController controller;

    @Test
    public void addTaskGoodTest() {
        //Arrange
        Task task = new Task();
        task.setId(1L);
        task.setDescription("Test task");
        task.setStatus(TaskStatus.NOT_STARTED);
        task.setDateCreate(LocalDateTime.now());
        when(repository.save(task)).thenReturn(task);

        //Acts
        Task addedTask = controller.addTask(task);
        LocalDateTime createdTime = addedTask.getDateCreate();

        //Assert
        verify(repository).save(task);
        assertThat(addedTask.getId()).isEqualTo(1L);
        assertThat(addedTask.getDescription()).isEqualTo("Test task");
        assertThat(addedTask.getStatus()).isEqualTo(TaskStatus.NOT_STARTED);
        assertThat(addedTask.getDateCreate()).isEqualTo(createdTime);
    }

    @Test
    public void getAllTasksGoodTest() {
        //Arrange
        Task task = new Task();
        task.setId(1L);
        task.setDescription("Test task");
        task.setStatus(TaskStatus.NOT_STARTED);
        LocalDateTime createdTime = LocalDateTime.now();
        task.setDateCreate(createdTime);
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        when(repository.findAll()).thenReturn(tasks);

        //Acts
        List<Task> foundTasks = controller.getAllTasks();

        //Asserts
        verify(repository).findAll();
        assertThat(foundTasks.get(0).getId()).isEqualTo(1L);
        assertThat(foundTasks.get(0).getDescription()).isEqualTo("Test task");
        assertThat(foundTasks.get(0).getStatus()).isEqualTo(TaskStatus.NOT_STARTED);
        assertThat(foundTasks.get(0).getDateCreate()).isEqualTo(createdTime);
    }

    @Test
    public void getTaskByStatusGoodTask() {
        //Arrange
        Task task = new Task();
        task.setId(1L);
        task.setDescription("Test task");
        task.setStatus(TaskStatus.NOT_STARTED);
        LocalDateTime createdTime = LocalDateTime.now();
        task.setDateCreate(createdTime);
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        when(repository.findByStatus(TaskStatus.NOT_STARTED)).thenReturn(tasks);

        //Acts
        List<Task> tasksByStatus = controller.getTasksByStatus(TaskStatus.NOT_STARTED);

        //Asserts
        verify(repository).findByStatus(TaskStatus.NOT_STARTED);
        assertThat(tasksByStatus.get(0).getId()).isEqualTo(1L);
        assertThat(tasksByStatus.get(0).getDescription()).isEqualTo("Test task");
        assertThat(tasksByStatus.get(0).getStatus()).isEqualTo(TaskStatus.NOT_STARTED);
        assertThat(tasksByStatus.get(0).getDateCreate()).isEqualTo(createdTime);
    }

    @Test
    public void getTaskByIdGoodTest() {
        //Arrange
        Task task = new Task();
        task.setId(1L);
        task.setDescription("Test task");
        task.setStatus(TaskStatus.NOT_STARTED);
        LocalDateTime createdTime = LocalDateTime.now();
        task.setDateCreate(createdTime);
        when(repository.findById(1L)).thenReturn(Optional.of(task));

        //Act
        Task foundTaskById = controller.getTaskById(1L);

        //Assert
        verify(repository).findById(1L);
        assertThat(foundTaskById.getId()).isEqualTo(1L);
        assertThat(foundTaskById.getDescription()).isEqualTo("Test task");
        assertThat(foundTaskById.getStatus()).isEqualTo(TaskStatus.NOT_STARTED);
        assertThat(foundTaskById.getDateCreate()).isEqualTo(createdTime);

    }

    @Test
    public void deleteTaskGoodTest() {
        //Arrange
        Task taskToDelete = new Task();
        taskToDelete.setId(1L);
        taskToDelete.setDescription("Test task");
        taskToDelete.setStatus(TaskStatus.NOT_STARTED);
        LocalDateTime createdTime = LocalDateTime.now();
        taskToDelete.setDateCreate(createdTime);

        //Acts
        controller.deleteTask(1L);

        //Asserts
        verify(repository).deleteById(1L);
    }

    @Test
    public void updateTaskStatusGoodTest() {
        //Arrange
        //Updated Task
        Task updTask = new Task();
        updTask.setId(1L);
        updTask.setDescription("Test task");
        updTask.setStatus(TaskStatus.IN_PROGRESS);
        LocalDateTime createdTime = LocalDateTime.now();
        updTask.setDateCreate(createdTime);
        when(repository.findById(1L)).thenReturn(Optional.of(updTask));
        when(repository.save(updTask)).thenReturn(updTask);

        //Act
        Task resultTask = controller.updateTaskStatus(1L, updTask);

        //Asserts
        verify(repository).save(updTask);
        assertThat(resultTask.getId()).isEqualTo(1L);
        assertThat(resultTask.getDescription()).isEqualTo("Test task");
        assertThat(resultTask.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
        assertThat(resultTask.getDateCreate()).isEqualTo(createdTime);

    }
}
