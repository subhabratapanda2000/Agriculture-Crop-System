package microService.cropManagement.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import microService.cropManagement.models.CropDetails;

@Repository
public interface CropRepository extends MongoRepository<CropDetails, Integer> {

	
	@Query("{'cropName':?0}")
	List<CropDetails> findByName(String cropName);
	
	@Query("{'quantity':{$gt : ?0}}")
	List<CropDetails> findByQunatity(double qty);
	
	@Query("{'cropName':?0,'quantity':{$gt : ?1}}")
	List<CropDetails> findByCropNameAndQunatity(String cropName, double qty);
	
	@Query("{'cropName':?0,'price':{$lt : ?1}}")
	List<CropDetails> findByCropNameAndPrice(String cropName, double price);
	
	@Query("{'cropName':?0,'price':{$lt : ?1}, 'quantity':{$gt : ?2}}")
	List<CropDetails> findByCropNameAndPriceAndQunatity(String cropName, double price, double qty);
	
	
	@Query("{'cropName':?0, 'farmerId':?1}")
	Optional<CropDetails> findByNameAndId(String cropName, int fid);
	
	@Query("{'id':?0, 'farmerId':?1}")
	Optional<CropDetails> findByIdAndFid(int cid, int fid);
	
}