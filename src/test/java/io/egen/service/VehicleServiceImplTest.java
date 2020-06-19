//package io.egen.service;
//
//import io.egen.entity.Employee;
//import io.egen.entity.Vehicle;
//import io.egen.repository.EmployeeRepository;
//import io.egen.repository.VehicleRepository;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//
//public class VehicleServiceImplTest {
//    @TestConfiguration
//    static class VehicleServiceImplTestConfiguration {
//
//        @Bean
//        public VehicleService getService() {
//            return new VehicleServiceImpl();
//        }
//    }
//
//    @Autowired
//    private VehicleService vehicleService;
//
//    @MockBean
//    private VehicleRepository vehicleRepository;
//
//    private List<Vehicle> vehicles;
//
//    @Before
//    public void setup() {
//        Vehicle veh = new Vehicle();
//
//        veh.setVin("1HGCR2F3XFA027534");
//        veh.setLatitude(41.803194);
//        veh.setLongitude(-88.144406);
//
//        vehicles = Collections.singletonList(veh);
//
//        Mockito.when(vehicleRepository.findAll())
//                .thenReturn(vehicles);
//
//    }
//
//    @After
//    public void cleanup() {
//    }
//
//    @Test
//    public void findAll() throws Exception {
//        List<Vehicle> result = vehicleService.findAll();
//        Assert.assertEquals("vehicle list should match", vehicles, result);
//    }
//
//    @Test
//    public void create() throws Exception{
//        Vehicle veh = new Vehicle();
//
//        veh.setVin("1HGCR2F3XFA027534");
//        veh.setLatitude(41.803194);
//        veh.setLongitude(-88.144406);
//
//        Mockito.when(vehicleRepository.save(veh))
//                .then(invocationOnMock -> {
//                    veh.setId("new-id");
//                    return veh;
//                });
//
//        Vehicle newVeh = vehicleService.create(veh);
//
//        //verify that the id is not null
//        Assert.assertNotNull("new veh id should exist", newVeh.getId());
//
//        //verify that the id is same as the mock id
//        Assert.assertEquals("veh id should be same", "new-id", newVeh.getId());
//
//        //verify that the repository.save() was called 1 times
//        Mockito.verify(vehicleRepository, Mockito.times(1))
//                .save(veh);
//    }
//
//}