package model.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import converter.BirthdayConverter;
import enums.Role;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
@TypeDef(name = "dmdev", typeClass = JsonBinaryType.class)
@Table(name = "users", schema = "public")
public class User {

    @Id
    private String username;
    private String firstname;
    private String lastname;
//    @Convert(converter = BirthdayConverter.class)
    @Column(name = "birth_date")
    private Birthday birthDate;
    @Type(type = "dmdev")
//    @Type(type = "jsonb")
    private String info;
    @Enumerated(EnumType.STRING)
    private Role role;
}
