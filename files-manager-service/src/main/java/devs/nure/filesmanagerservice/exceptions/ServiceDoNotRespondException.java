package devs.nure.filesmanagerservice.exceptions;

public class ServiceDoNotRespondException extends RuntimeException {
    public ServiceDoNotRespondException(String service) {
        super("Service ["+service+"] do not response");
    }
}
