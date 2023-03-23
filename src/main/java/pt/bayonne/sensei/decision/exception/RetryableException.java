package pt.bayonne.sensei.decision.exception;

import lombok.Value;

@Value
public class RetryableException extends RuntimeException{
    String reason;
}
