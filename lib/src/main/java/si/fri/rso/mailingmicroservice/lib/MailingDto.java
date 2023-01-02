package si.fri.rso.mailingmicroservice.lib;

import java.util.Map;

public class MailingDto {
    private String type;

    private Map<String, String> invoiceData;

    private Map<String, String> userData;

    private Map<String, String> deliveryData;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getInvoiceData() {
        return invoiceData;
    }

    public void setInvoiceData(Map<String, String> invoiceData) {
        this.invoiceData = invoiceData;
    }

    public Map<String, String> getUserData() {
        return userData;
    }

    public void setUserData(Map<String, String> userData) {
        this.userData = userData;
    }

    public Map<String, String> getDeliveryData() {
        return deliveryData;
    }

    public void setDeliveryData(Map<String, String> deliveryData) {
        this.deliveryData = deliveryData;
    }
}
