package microService.cropManagement.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import microService.cropManagement.models.CropDetails;

@Repository
public interface CropRepository extends MongoRepository<CropDetails, Integer> {

	
	@Query("{'cropName':?0}")
	List<CropDetails> findByName(String cropName);
	
	@Query("{'cropName':?0, 'id':?1}")
	List<CropDetails> findByNameAndId(String cropName, int id);
}