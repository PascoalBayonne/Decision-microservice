package pt.bayonne.sensei.decision.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pt.bayonne.sensei.decision.enumerated.State;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Decision {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private State state;

    private SSN ssn;

    private Decision(State state, SSN ssn) {
        this.state = state;
        this.ssn = ssn;
    }

    public static Decision decide(SSN ssn, LocalDate birthdate) {
        Period creditLimitDuration = Period.ofYears(40);
        var maximumCustomerYears = 70;

        LocalDate maximumAllowedAge = LocalDate.now().plus(creditLimitDuration);

        long customerYearsInTheEndOfCredit = ChronoUnit.YEARS.between(birthdate, maximumAllowedAge);

        if (customerYearsInTheEndOfCredit > maximumCustomerYears) {
            return new Decision(State.REJECTED, ssn);
        } else if (ssn.getSsn() % 2 == 0) {
            return new Decision(State.APPROVED, ssn);
        }
        return new Decision(State.PRE_APPROVED, ssn);
    }

}
