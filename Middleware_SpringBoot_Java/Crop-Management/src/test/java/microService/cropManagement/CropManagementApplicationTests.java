package microService.cropManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import microService.cropManagement.models.CropDetails;
import microService.cropManagement.repo.CropRepository;
import microService.cropManagement.service.CropService;

@SpringBootTest
//@RunWith(SpringRunner.class)
@TestMethodOrder(OrderAnnotation.class)
class CropManagementApplicationTests {

	@Autowired
	CropService service;
	
	@MockBean
	CropRepository repo;
	
	
	@Test
	void testReadAll() {
		when(repo.findAll()).thenReturn(Stream
				.of(new CropDetails(201, "abcd", 20.00, 30.00, 8, ""), new CropDetails(202, "efgh", 23.00, 33.00, 8, "")).collect(Collectors.toList()));
		assertEquals(2, service.findAll().size());
	}
	
	@Test
	void getUserbyIdTest() {
		int id=201;
		CropDetails cd1=new CropDetails(201, "abcd", 20.00, 30.00, 8, "");
		System.out.println("Hello sam");
		when(repo.findById(id)).thenReturn(java.util.Optional.of(cd1));
		System.out.println(service.findById(id)+"and"+cd1);
		assertEquals(cd1, service.findById(id));
	}
	
	@Test
	void saveCropTest() {
		CropDetails cp=new CropDetails(201, "abcd", 20.00, 30.00, 8, "");
		when(repo.save(cp)).thenReturn(cp);
		assertEquals(cp, service.save(cp));
	}
	
	@Test
	 void deleteCrop() {
		CropDetails cp=new CropDetails(201, "abcd", 20.00, 30.00, 8, "");
		service.deleteById(201);
		verify(repo).deleteById(any());
	}
	
	@Test
	void getCropByFarmerId() {
		CropDetails cp=new CropDetails(201, "abcd", 20.00, 30.00, 8, "");
		when(repo.findByName("abcd")).thenReturn(Stream
				.of(new CropDetails(201, "abcd", 20.00, 30.00, 8, ""), new CropDetails(202, "abcd", 23.00, 33.00, 8, "")).collect(Collectors.toList()));
		assertEquals(2, service.findByName("abcd").size());
	}
	
	
	
	
		

	
//	
//	@Test
//	@Order(1)
//	public void testCreate () {
//		CropDetails cp = new CropDetails();
//		cp.setId(201);
//		cp.setCropName("Tomato");
//		cp.setPrice(15.00);
//		cp.setQuantity(20.00);
//		cp.setFarmerId(12);
//		service.save(cp);
//		assertNotNull(service.findById(201).get());
//	}
//		
//	@Test
//	@Order(2)
//	public void testReadAll () {
//		List list = service.findAll();
//		assertThat(list).size().isGreaterThan(0);
//	}
//		
//	@Test
//	@Order(3)
//	public void testRead () {
//		CropDetails cp= service.findById(201).get();
//		assertEquals("Tomato", cp.getCropName());
//	}
//		
//	@Test
//	@Order(4)
//	public void testUpdate () {
//		CropDetails cp= service.findById(201).get();
//		cp.setPrice(80.00);
//		service.save(cp);
//		assertNotEquals(15.00, service.findById(201).get().getPrice());
//	}
//		
//	@Test
//	@Order(5)
//	public void testDelete () {
//		service.deleteById(201);
//		assertThat(repo.existsById(201)).isFalse();
//	}
	

}
