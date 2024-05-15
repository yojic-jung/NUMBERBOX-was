package template;

import lombok.Getter;

import java.sql.Timestamp;

/**
 * 성공 응답에서 사용하는 템플릿
 */
@Getter
public class ResponseErrData {
    private final Timestamp timestamp;
    private final int status;
    private final String message;
    private final boolean showMessage;

    public ResponseErrData(int status, boolean showMessage, String message) {
        this(new Timestamp(System.currentTimeMillis()), status, showMessage, message);
    }

    public ResponseErrData(Timestamp timestamp, int status, boolean showMessage, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.showMessage = showMessage;
        this.message = message;
    }
}
