package devs.nure.metainfoservice.repositories;

import devs.nure.metainfoservice.models.CustomDirectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryRepository extends JpaRepository<CustomDirectory, Long> {
}

