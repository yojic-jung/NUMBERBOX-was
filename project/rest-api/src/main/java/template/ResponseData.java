package template;

import java.sql.Timestamp;

/**
 * 성공 응답에서 사용하는 템플릿
 */
public class ResponseData<T> {
    private static final String SUCCESS_MESSAGE = "성공하였습니다.";
    private static final int SUCCESS_STATUS = 200;
    private Timestamp timestamp;
    private int status;
    private String message;
    private T data;

    public ResponseData(T data) {
        new ResponseData(new Timestamp(System.currentTimeMillis()), SUCCESS_STATUS, SUCCESS_MESSAGE, data);
    }

    public ResponseData(Timestamp timestamp, int status, String message, T data) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
