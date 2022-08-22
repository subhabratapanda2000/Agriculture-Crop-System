package microService.cropManagement.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import microService.cropManagement.models.CropDetails;

public interface CropRepository extends MongoRepository<CropDetails, Integer> {

}
