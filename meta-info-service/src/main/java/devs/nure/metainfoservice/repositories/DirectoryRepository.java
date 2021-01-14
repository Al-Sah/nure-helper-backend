package devs.nure.metainfoservice.repositories;

import devs.nure.metainfoservice.models.CustomDirectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectoryRepository extends JpaRepository<CustomDirectory, Long> {

     Optional <CustomDirectory> findByUniqID(String uniqId);

     void deleteByUniqID(String uniqId);


}

