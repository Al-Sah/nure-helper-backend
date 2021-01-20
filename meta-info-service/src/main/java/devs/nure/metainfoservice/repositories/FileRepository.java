package devs.nure.metainfoservice.repositories;

import devs.nure.metainfoservice.models.CustomFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<CustomFile, Long> {

    Optional<CustomFile> findByUniqID(String uniqId);
    boolean existsByUniqID(String uniqId);
    void removeCustomFileByUniqID(String uniqId);
    void deleteAllByParentID(String uniqId);
    List<CustomFile> findAllByParentID(String uniqID);
}
