package devs.nure.metainfoservice.repositories;

import devs.nure.metainfoservice.models.CustomDirectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirectoryRepository extends JpaRepository<CustomDirectory, Long> {

     Optional <CustomDirectory> findByUniqID(String uniqId);
     boolean existsByUniqID(String uniqId);
     void deleteByUniqID(String uniqId);
     List<CustomDirectory> findAllByParentID(String uniqID);
}

