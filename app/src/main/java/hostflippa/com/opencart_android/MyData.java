package hostflippa.com.opencart_android;

import java.io.Serializable;

/**
 * Created by Inzimam on 14-Oct-17.
 */

public class MyData implements Serializable {
    private String questionId;
    private String questionText;

    public MyData(String questionId, String questionText) {
        this.questionId = questionId;
        this.questionText = questionText;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}
