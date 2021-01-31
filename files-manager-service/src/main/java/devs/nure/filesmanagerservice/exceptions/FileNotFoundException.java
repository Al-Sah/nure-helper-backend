package devs.nure.filesmanagerservice.exceptions;

public class FileNotFoundException extends RuntimeException{
    public FileNotFoundException(String id) {
        super("File with id [" + id + "] not found");
    }
}
