package md.ciocana.hibernate.repository;

import md.ciocana.hibernate.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<List<Student>> findAllByName(String name);

    Optional<Student> findById(Long id);

    @Transactional
    @Modifying
    @Query("delete from Student s where s.name = :studentName")
    void deleteStudentByName(@Param("studentName") String studentName);

    Student findByName(String name);

    @Transactional
    @Modifying
    @Query("delete from Student s where s.id = :studentId")
    void deleteStudentById(@Param("studentId") Long studentId);

}
