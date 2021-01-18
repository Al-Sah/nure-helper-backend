package devs.nure.metainfoservice.repositories;

import devs.nure.metainfoservice.models.CustomFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<CustomFile, Long> {

    Optional<CustomFile> findByUniqID(String uniqId);
    boolean existsByUniqID(String uniqId);
    void deleteByUniqID(String uniqId);

}
