package time.system.persistence;


import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date stampIn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date stampOut;

    public TimeStamp(Date stampIn) {
        this.stampIn = stampIn;
    }
}
