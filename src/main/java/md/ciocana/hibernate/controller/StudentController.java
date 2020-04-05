package md.ciocana.hibernate.controller;

import md.ciocana.hibernate.entity.Student;
import md.ciocana.hibernate.repository.StudentRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        String methodName = new Object() {}
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

    @RequestMapping(value = "/createStudent", method = RequestMethod.GET)
    public Map<String, Object> createStudent(@RequestParam(value = "name") String name,
                                             @RequestParam(value = "age") int age) {

        String methodName = new Object() {}
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
            resultMap.put("studentId", student.getId().toString());
        }
        return resultMap;
    }

    @RequestMapping(value = "/getAllStudents", method = RequestMethod.GET)
    public Map<String, Object> getAllStudents() {
        String methodName = new Object() {}
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

    @RequestMapping(value = "/getAllStudentsByName", method = RequestMethod.GET)
    public Map<String, Object> getAllStudentsByName(@RequestParam(value = "name") String name) {
        String methodName = new Object() {}
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
            log.info(controllerName + studentListStr +" successfully retrieved");
            resultMap.put(status, statusSuccess);
            resultMap.put(studentListStr, studentList);
        }

        return resultMap;
    }
}
