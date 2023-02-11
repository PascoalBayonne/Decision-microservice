package pt.bayonne.sensei.decision.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class SSN {
    @Column(name = "ssn")
    private Integer ssn;

    private SSN(Integer ssn) {
        this.ssn = ssn;
    }

    public static SSN create(final Integer ssn) {
        Objects.requireNonNull(ssn, "Social Security Number should not be null");
        Assert.isTrue(String.valueOf(ssn).toCharArray().length == 9, "The social security number should have 9 characters");
        return new SSN(ssn);
    }
}
