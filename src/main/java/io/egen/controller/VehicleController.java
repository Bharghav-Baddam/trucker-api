package io.egen.controller;

import io.egen.entity.Vehicle;
import io.egen.service.VehicleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
//@RequestMapping(value = "/vehicles")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

//    @RequestMapping(method = RequestMethod.GET, value = "/get")
//    public String getMethod() {
//        return "this is get";
//    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Find All Employee",
            notes = "Returns a list of all employees avaialble in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<Vehicle> findAll() {
        return vehicleService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findOneWithVin/{id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Find Vehicle by VIN",
            notes = "Return a single Vehicle or throws 404")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Vehicle findOne(
            @ApiParam(value = "Vin of the Vehicle", required = true) @PathVariable("id") String vehId) {
        return vehicleService.findOne(vehId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/vehicles",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Updates vehicle details based on VIN number",
            notes = "Return a list of vehicles whose values are updated")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<Vehicle> update(@RequestBody List<Vehicle> vehicle) {

        System.out.println("vehicle details " + vehicle);
        return vehicleService.update(vehicle);
        //return "this is great put";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/readings",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Creates a new vehicle with new details",
            notes = "Return a single Vehicle details which is created")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Vehicle postMethod(@RequestBody Vehicle vehicle) {

        System.out.println("vehicle details " + vehicle);
        return vehicleService.create(vehicle);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/location/{id}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Find Vehicle by VIN",
            notes = "Return geoLocation within past 30 minutes or throws 404")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<String> Location(@PathVariable("id") String vin) {
        return vehicleService.geoLocation(vin);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/historicalAlert/{id}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Find Vehicle by VIN",
            notes = "Return all historical alerts of the Vehicle with given VIN or throws 404")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<String> Alert(@PathVariable("id") String vin) {
        return vehicleService.getAlert(vin);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/highAlert",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "List of Vehicles with HIGH alerts",
            notes = "Return a list of all the vehicles whose alerts are HIGH")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<String> highAlert() {
        return vehicleService.gethighAlert();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ApiOperation(value = "Find Vehicle by VIN",
            notes = "Deletes the vehicle with given VIN number")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public void delete(@PathVariable("id") String vehId) {
        vehicleService.delete(vehId);
    }

}


