package io.egen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.egen.entity.Employee;
import io.egen.entity.Tires2;
import io.egen.entity.Vehicle;
import io.egen.repository.VehicleRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest")
public class VehicleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private VehicleRepository repository;

    @Before
    public void setup() {
        Vehicle veh = new Vehicle();

        veh.setVin("1HGCR2F3XFA027534");
        veh.setLatitude(41.803194);
        veh.setLongitude(-88.144406);
        repository.save(veh);

        List<String> geoLocationResult = new ArrayList<>();
        geoLocationResult.add("1HGCR2F3XFA027534"+" "+41.803194+" "+-88.144406);

        List<String> alertResult = new ArrayList<>();
        alertResult.add("HIGH");
        alertResult.add("LOW");

        List<String> highAlertVinList = new ArrayList<>();
        highAlertVinList.add("1HGCR2F3XFA027534");
        highAlertVinList.add("1HGCR2F3XFA027535");
    }

    @After
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void findAll() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/vehicles"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1HGCR2F3XFA027534")));

    }

    @Test
    public void update() {
    }

    @Test
    public void postMethod() throws Exception{
        ObjectMapper mapper = new ObjectMapper();

        Tires2 tires= new Tires2();
        tires.setFrontLeft(34);
        tires.setFrontRight(36);
        tires.setRearLeft(29);
        tires.setRearRight(34);

        Vehicle veh = new Vehicle();

        veh.setVin("1HGCR2F3XFA027535");
        veh.setLatitude(41.803194);
        veh.setLongitude(-88.144406);
        veh.setTimestamp("2017-05-25T17:31:25.268Z");
        veh.setFuelVolume(1.5);
        veh.setSpeed(85);
        veh.setEngineHp(240);
        veh.setCheckEngineLightOn(false);
        veh.setEngineCoolantLow(true);
        veh.setCruiseControlOn(true);
        veh.setEngineRpm(6300);
        veh.setTires(tires);

        mvc.perform(MockMvcRequestBuilders.post("/readings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(veh))
        )
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("1HGCR2F3XFA027535")));


        mvc.perform(MockMvcRequestBuilders.get("/readings"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void location() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/location/1HGCR2F3XFA027534"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));

    }

    @Test
    public void alert() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/historicalAlert/1HGCR2F3XFA027534"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void highAlert() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/highAlert"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }
}