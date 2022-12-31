package si.fri.rso.mailingmicroservice.lib;

import java.util.HashMap;
import java.util.Map;

public class MailingDto {
    private String type;

    private Integer userId;

    private Map<String, String> invoiceData = new HashMap<>();

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

    public Map<String, String> getInvoiceData() {
        return invoiceData;
    }

    public void setInvoiceData(Map<String, String> invoiceData) {
        this.invoiceData = invoiceData;
    }

    public Integer getInvoiceId() {
        return 2;
    }
}
