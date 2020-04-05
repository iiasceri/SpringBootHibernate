package md.ciocana.hibernate.controller;

import md.ciocana.hibernate.entity.Student;
import md.ciocana.hibernate.repository.StudentRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class StudentController {

    private String status = "status";
    private String statusFailed = "failed";
    private String statusSuccess = "success";
    private String message = "message";
    private String studentListStr = "studentList";

    private Logger log = Logger.getLogger(StudentController.class);

    @Autowired
    StudentRepository studentRepository;

    @RequestMapping(value = "/deleteStudentByName", method = RequestMethod.GET)
    public Map<String, Object> deleteStudentByName(@RequestParam(value = "name") String name) {

        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();

        String controllerName = "/" + methodName + " : ";

        LinkedHashMap<String, Object> resultMap = new LinkedHashMap<>();

        try {
            studentRepository.deleteStudentByName(name);
            log.info(controllerName + "deleted " + name);
            resultMap.put(status, statusSuccess);
            resultMap.put(message, controllerName + "deleted " + name);
        } catch (Exception e) {
            log.error(controllerName + e.getCause());
            resultMap.put(status, statusFailed);
            resultMap.put(message, e.getCause());
        }

        return resultMap;
    }

    @GetMapping(value = "/createStudent")
    public Map<String, Object> createStudent(@RequestParam String name,
                                             @RequestParam int age) {

        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();

        String controllerName = "/" + methodName + " : ";

        LinkedHashMap<String, Object> resultMap = new LinkedHashMap<>();

        if (name.equals("") || age < 0 || age > 120) {
            String messageFail = "invalid name or age";
            log.warn(controllerName + messageFail);
            resultMap.put(status, statusFailed);
            resultMap.put(message, messageFail);
        } else {
            Student student = new Student(name, age);
            studentRepository.save(student);
            log.info(controllerName + "student saved to database");
            resultMap.put(status, statusSuccess);
            resultMap.put("student", student);
        }
        return resultMap;
    }

    @GetMapping(value = "/getAllStudents")
    public Map<String, Object> getAllStudents() {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();

        String controllerName = "/" + methodName + " : ";

        LinkedHashMap<String, Object> resultMap = new LinkedHashMap<>();

        try {
            List<Student> studentList = studentRepository.findAll();
            log.info(controllerName + "");
            resultMap.put(status, statusSuccess);
            resultMap.put(studentListStr, studentList);
        } catch (Exception e) {
            log.error(controllerName + e.getCause());
            resultMap.put(status, statusFailed);
            resultMap.put(message, e.getCause());
        }

        return resultMap;
    }

    @GetMapping(value = "/getAllStudentsByName")
    public Map<String, Object> getAllStudentsByName(@RequestParam(value = "name") String name) {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();

        String controllerName = "/" + methodName + " : ";

        LinkedHashMap<String, Object> resultMap = new LinkedHashMap<>();

        Optional<List<Student>> optionalStudentList = studentRepository.findAllByName(name);
        if (!optionalStudentList.isPresent()) {

            String failMessage = "no students found with this name";
            log.warn(controllerName + failMessage);

            resultMap.put(status, statusFailed);
            resultMap.put(message, failMessage);
            return resultMap;
        } else {
            List<Student> studentList = optionalStudentList.get();
            log.info(controllerName + studentListStr + " successfully retrieved");
            resultMap.put(status, statusSuccess);
            resultMap.put(studentListStr, studentList);
        }

        return resultMap;
    }

    @GetMapping(value = "/getStudentByName")
    public Map<String, Object> getStudentByName(@RequestParam String name) {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();

        String controllerName = "/" + methodName + " : ";

        LinkedHashMap<String, Object> resultMap = new LinkedHashMap<>();

        Student student = studentRepository.findByName(name);

        if (student == null) {
            String failMessage = " student with this name not found";
            resultMap.put(status, statusFailed);
            resultMap.put(message, controllerName + failMessage);
            log.warn(controllerName + failMessage);
        } else {

            resultMap.put(status, statusSuccess);
            resultMap.put("student", student);
            log.info(controllerName+ " tat normal");
        }

        return resultMap;
    }
}
