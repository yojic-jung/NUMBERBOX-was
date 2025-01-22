package template;

import lombok.Getter;

import java.sql.Timestamp;

/**
 * 성공 응답에서 사용하는 템플릿
 */
@Getter
public class ResponseData<T> {
    private static final String SUCCESS_MESSAGE = "성공하였습니다.";
    private static final int SUCCESS_STATUS = 200;
    
    private final Timestamp timestamp;
    private final int status;
    private final String message;
    private final T data;

    public ResponseData(T data) {
        this(new Timestamp(System.currentTimeMillis()), SUCCESS_STATUS, SUCCESS_MESSAGE, data);
    }

    public ResponseData(Timestamp timestamp, int status, String message, T data) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
