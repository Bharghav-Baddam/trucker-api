
package io.egen.service;

import io.egen.entity.Vehicle;
import io.egen.exception.ResourceNotFoundException;
import io.egen.repository.Tires2Repository;
import io.egen.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    Tires2Repository tiresRepository;

    @Transactional(readOnly = true)
    public List<Vehicle> findAll() {
        return (List<Vehicle>) vehicleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Vehicle findOne(String id) {

        Optional<Vehicle> existing = vehicleRepository.findById(id);
        if(!existing.isPresent()){
            throw new ResourceNotFoundException("Employee with id "+id+" doesn't exist" );
        }
        return existing.get();
    }

    @Transactional
    public Vehicle create(Vehicle vehicle) {
        tiresRepository.save(vehicle.getTires());

        Optional<Vehicle> existing = vehicleRepository.findById(vehicle.getVin());
        if(existing.isPresent()){
            throw new ResourceNotFoundException("Employee with id "+vehicle.getVin()+" already exist" );
        }

        if(vehicle.getEngineRpm() > vehicle.getRedlineRpm()){
            vehicle.setAlert("HIGH");
        }else if(vehicle.getFuelVolume() < (0.1*vehicle.getMaxFuelVolume())){
            vehicle.setAlert("MEDIUM");
        }else if( (vehicle.getTires().getFrontLeft() < 32 || vehicle.getTires().getFrontLeft() > 36) || (vehicle.getTires().getFrontRight() < 32 || vehicle.getTires().getFrontRight() > 36) || (vehicle.getTires().getRearLeft() < 32 || vehicle.getTires().getRearLeft() > 36) || (vehicle.getTires().getRearRight() < 32 || vehicle.getTires().getRearRight() > 36)){
            vehicle.setAlert("LOW");
        }else if(vehicle.isEngineCoolantLow() == true || vehicle.isCheckEngineLightOn() == true){
            vehicle.setAlert("LOW");
        }

        return vehicleRepository.save(vehicle);
    }

    @Transactional
    public List<Vehicle> update(List<Vehicle> vehicle) {
        for(Vehicle veh : vehicle) {
            Optional<Vehicle> existing = vehicleRepository.findById(veh.getVin());
            if (!existing.isPresent()) {
                vehicleRepository.save(veh);
            }else{
                vehicleRepository.save(veh);
            }
        }
        return vehicle;

    }

    @Transactional
    public void delete(String id) {
        Optional<Vehicle> existing = vehicleRepository.findById(id);
        if(!existing.isPresent()){
            throw new ResourceNotFoundException("Employee with id "+id+" doesn't exist" );
        }
        vehicleRepository.delete(existing.get());
    }

    @Transactional
    public List<String> geoLocation(String vin){
        List<Vehicle> vehicleList = (List<Vehicle>) vehicleRepository.findAll();
        List<String> geoLocationResult = new ArrayList<>();
        for(Vehicle veh : vehicleList){
            Instant vehTimeStamp = null;
            if(veh.getVin().equals(vin)){

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                Instant instant = timestamp.toInstant();
                vehTimeStamp = Instant.parse(veh.getTimestamp());
                long diffInMinutes = java.time.Duration.between( vehTimeStamp, instant).toMinutes();

                System.out.println("vehicle TS "+vehTimeStamp+ " Diff in TS "+ diffInMinutes);

                if(diffInMinutes <= 30){
                    geoLocationResult.add(veh.getVin()+" "+veh.getLatitude()+" "+veh.getLongitude());
                }
            }

        }
        return geoLocationResult;

    }

    @Override
    public List<String> getAlert(String vin) {
        List<Vehicle> vehicleList = (List<Vehicle>) vehicleRepository.findAll();
        List<String> alertResult = new ArrayList<>();
        for(Vehicle veh : vehicleList){
            if(veh.getVin().equals(vin)){
                alertResult.add(veh.getAlert());
            }
        }
        return alertResult;
    }

    @Override
    public List<String> gethighAlert() {
        List<Vehicle> vehicleList = (List<Vehicle>) vehicleRepository.findAll();
        List<String> highAlertVinList = new ArrayList<>();
        for(Vehicle veh : vehicleList){
            Instant vehTimeStamp = null;
            if(veh.getAlert().equals("HIGH")){

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                Instant instant = timestamp.toInstant();
                vehTimeStamp = Instant.parse(veh.getTimestamp());
                long diffInMinutes = java.time.Duration.between( vehTimeStamp, instant).toMinutes();

                System.out.println("vehicle TS "+vehTimeStamp+ " Diff in TS "+ diffInMinutes);

                if(diffInMinutes <= 120){
                    highAlertVinList.add(veh.getVin()+" "+veh.getAlert());
                }
            }

        }
        return highAlertVinList;
    }


}