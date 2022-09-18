package microService.farmerManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import microService.farmerManagement.models.FarmerDetails;
import microService.farmerManagement.repository.FarmerRepository;
import microService.farmerManagement.service.FarmerService;

@SpringBootTest
class FarmerManagementApplicationTests {

	@Autowired
	FarmerService service;
	
	@MockBean
	FarmerRepository repo;
	
	
	@Test
	public void testFindAll() {
		when(repo.findAllFarmer("ROLE_FARMER")).thenReturn(Stream
				.of(new FarmerDetails(201, "abcd","us","pw", "kalyani", "99868758", true,"ROLE_FARMER", "12/11/20", true)).collect(Collectors.toList()));
		assertEquals(1, service.findAll().size());
	}
	
	@Test
	public void getUserbyIdTest() {
		int id=201;
		FarmerDetails fm=new FarmerDetails(201, "abcd","","", "kalyani", "99868758", true,"", "", true);
		System.out.println("Hello sam");
		when(repo.findById(id)).thenReturn(java.util.Optional.of(fm));
		System.out.println(service.findById(id)+"and"+fm);
		assertEquals(fm, service.findById(id).get());
	}
	
	@Test
	public void saveFarmerTest() {
		FarmerDetails fm=new FarmerDetails(201, "abcd","","", "kalyani", "99868758", true, "", "", true);
		when(repo.save(fm)).thenReturn(fm);
		assertEquals(fm, service.save(fm));
	}
	
	@Test
	public void deleteFarmer() {
		FarmerDetails fm=new FarmerDetails(201, "abcd","","", "kalyani", "99868758", true, "", "", true);
		service.deleteById(201);
		verify(repo).deleteById(any());
	}
	
//	@Test
//	public void getCropByFarmerId() {
//		CropDetails cp=new CropDetails(201, "abcd", 20.00, 30.00, 8);
//		when(repo.findByName("abcd")).thenReturn(Stream
//				.of(new CropDetails(201, "abcd", 20.00, 30.00, 8), new CropDetails(202, "abcd", 23.00, 33.00, 8)).collect(Collectors.toList()));
//		assertEquals(2, service.findByName("abcd").size());
//	}
	
	

}
