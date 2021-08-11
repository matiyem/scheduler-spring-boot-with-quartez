package quartez.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * created by Atiye Mousavi
 * Date: 8/8/2021
 * Time: 1:31 PM
 */
@Data
@Entity
public class MemberClass {
    @Id
    @GeneratedValue
    private long id;


    private String name;

    public MemberClass() { }
}
