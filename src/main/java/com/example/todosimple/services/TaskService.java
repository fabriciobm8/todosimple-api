package com.example.todosimple.services;

import com.example.todosimple.models.Task;
import com.example.todosimple.models.User;
import com.example.todosimple.repositories.TaskRepository;
import com.example.todosimple.repositories.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private UserService userService;

  public Task findById(Long id){
    Optional<Task> task = this.taskRepository.findById(id);
    return task.orElseThrow(() -> new RuntimeException(
        "Tarefa Não Encontrada! Id: " + id + ", Tipo: " + Task.class.getName() ));
  }

  public List<Task> findAllByUserId (Long userId) {
    this.userService.findById(userId);
    List<Task> tasks = this.taskRepository.findByUser_Id(userId);
    return tasks;
  }

  @Transactional
  public Task create (Task obj) {
    User user = this.userService.findById(obj.getUser().getId());
    obj.setId(null);
    obj.setUser(user);
    obj = this.taskRepository.save(obj);
    return obj;
  }

  @Transactional
  public Task update (Task obj) {
    Task newObj = findById(obj.getId());
    newObj.setDescription(obj.getDescription());
    return this.taskRepository.save(newObj);
  }

  public void delete  (Long id) {
    findById(id);
    try {
      this.taskRepository.deleteById(id);
    } catch (Exception e) {
      throw new RuntimeException("Não é possível excluir pois há entidades relacionadas!");
    }
  }



}
