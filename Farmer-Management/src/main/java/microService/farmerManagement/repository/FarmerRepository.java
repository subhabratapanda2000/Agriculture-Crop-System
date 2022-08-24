package microService.farmerManagement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import microService.farmerManagement.models.FarmerDetails;


	@Repository
	public interface FarmerRepository extends MongoRepository<FarmerDetails, Integer> {

}
