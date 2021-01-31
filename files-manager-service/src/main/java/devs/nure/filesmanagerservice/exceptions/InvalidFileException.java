package devs.nure.filesmanagerservice.exceptions;

public class InvalidFileException extends RuntimeException {
    public InvalidFileException(String filename) {
        super("Invalid file ["+filename+"]\n File is empty, either no file has been chosen in the multipart " +
                " or the chosen file has no content");
    }
}
