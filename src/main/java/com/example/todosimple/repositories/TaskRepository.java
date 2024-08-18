package com.example.todosimple.repositories;

import com.example.todosimple.models.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

  //Conceito de Spring Puro - mais usual
  List<Task> findByUser_id(long id);

  /* Conceito Spring JPQL - Spring e SQL.
  @Query(value = "SELECT t FROM Task t WHERE t.user.id = :id")
  List<Task> findByUser_Id(@Param("id") Long id);
  */

  /* Agora vamos usar SQL puro, sem Spring
  @Query(value = "SELECT * FROM task t WHERE t.user_id = :id", nativeQuery = true)
  List<Task> findByUser_Id(@Param("id") Long id);
  */

}
