package linkedin.SkillTest.demo.Model;

public class ServiceResponse {

    private String result;
    private String Message;
    private Boolean Status;

    
    public String getMessage() {
        return Message;
    }
    public void setMessage(String message) {
        Message = message;
    }
    public Boolean getStatus() {
        return Status;
    }
    public void setStatus(Boolean status) {
        Status = status;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }


    
}
