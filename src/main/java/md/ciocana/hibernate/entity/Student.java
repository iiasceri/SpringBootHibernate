package md.ciocana.hibernate.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Student {

    @Id
    @GenericGenerator(name = "kaugen", strategy = "increment")
    @GeneratedValue(generator = "kaugen")
    private Long id;

    private String name;

    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
