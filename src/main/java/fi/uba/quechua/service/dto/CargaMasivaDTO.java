package fi.uba.quechua.service.dto;

import java.util.LinkedList;
import java.util.List;

public class CargaMasivaDTO {

    private List<String> warningMessages;

    private List<String> errorMessages;

    private Integer successCount;


    public CargaMasivaDTO() {
        this.warningMessages = new LinkedList<>();
        this.errorMessages = new LinkedList<>();
        this.successCount = 0;
    }

    public List<String> getWarningMessages() {
        return warningMessages;
    }

    public void setWarningMessages(List<String> warningMessages) {
        this.warningMessages = warningMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }
}
