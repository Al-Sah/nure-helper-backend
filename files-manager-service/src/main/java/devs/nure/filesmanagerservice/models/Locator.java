package devs.nure.filesmanagerservice.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Locator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;
    private String path;
    private String checksum;
    private String storedFileUUID;
    private String contentType;
    private String fileMetaInfoUUID;

    public Locator(String checksum, String contentType, String fileMetaInfoUUID) {
        this.checksum = checksum;
        this.contentType =contentType;
        this.fileMetaInfoUUID = fileMetaInfoUUID;
    }
    public Locator(Locator locator){
        this.checksum = locator.getChecksum();
        this.contentType =locator.getContentType();
        this.provider = locator.getProvider();
        this.path = locator.getPath();
        this.storedFileUUID=locator.getStoredFileUUID();
        this.fileMetaInfoUUID = locator.getFileMetaInfoUUID();
    }

}
