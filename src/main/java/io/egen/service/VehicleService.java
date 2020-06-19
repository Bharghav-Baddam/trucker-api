package io.egen.service;

import io.egen.entity.Vehicle;

import java.util.List;

public interface VehicleService {
    List<Vehicle> findAll();

    Vehicle findOne(String id);

    Vehicle create(Vehicle vehicle);

    //Vehicle update(String id, Vehicle vehicle);
    List<Vehicle> update(List<Vehicle> vehicle);

    void delete(String id);

    List<String> geoLocation(String vin);

    List<String> getAlert(String vin);

    List<String> gethighAlert();
}