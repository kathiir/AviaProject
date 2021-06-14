//package com.example.demo;
//
//import com.example.demo.app.AviaBackApplication;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import service.internal.FlightService;
//import service.internal.impl.PurchaseServiceImpl;
//import service.mapper.PurchaseMapper;
//import service.models.Purchase;
//
//@SpringBootTest(classes = AviaBackApplication.class)
//class AviaBackApplicationTests {
//
//	@Test
//	void contextLoads() {
//	}
//	@Mock
//	private PurchaseMapper purchaseMapper;
//
//	@Mock
//	private FlightService flightService;
//
//	@InjectMocks
//	private PurchaseServiceImpl purchaseServiceImpl;
//
//
//	@Test
//	public void testGetPurchases() {
//
//		Purchase purchase = new Purchase();
//		purchaseServiceImpl.takePurchase(purchase);
//		purchaseServiceImpl.takePurchase(purchase);
//		purchaseServiceImpl.takePurchase(purchase);
//
//
//	}
//}
