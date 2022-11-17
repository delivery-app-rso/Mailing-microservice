package si.fri.rso.mailingmicroservice.lib;

public class MailingDto {
    private String type;

    private Integer userId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
