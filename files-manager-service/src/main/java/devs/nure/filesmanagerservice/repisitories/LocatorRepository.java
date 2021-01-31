package devs.nure.filesmanagerservice.repisitories;

import devs.nure.filesmanagerservice.models.Locator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocatorRepository extends JpaRepository<Locator, Long> {

    Optional<Locator> findFirstByChecksum(String checksum);
    boolean existsByChecksum(String checksum);
    Optional<Locator> findByFileMetaInfoUUID(String fileID);
    List<Locator> findAllByChecksum(String checksum);
}
