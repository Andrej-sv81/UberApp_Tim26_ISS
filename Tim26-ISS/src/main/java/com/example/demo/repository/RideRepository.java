package com.example.demo.repository;

import com.example.demo.model.Driver;
import com.example.demo.model.Review;
import com.example.demo.model.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RideRepository extends JpaRepository<Ride, Integer> {
     Optional<Ride> findOneById(Integer id);

    @Query("select r.driver from Ride r where r.id =?1")
    Driver getDriverOfRide(int rideId);
    @Query("select r.reviews from Ride r where r.id=?1")
    List<Review> getReviews(Integer id);

    //Driver Native Querries
    @Query(value = "SELECT * FROM RIDE WHERE DRIVER_ID = ?1", nativeQuery = true)
    List<Ride> getRides(Integer id);
    @Query(value = "SELECT * FROM RIDE WHERE DRIVER_ID = ?1", nativeQuery = true)
    Page<Ride> getRides(Integer id, Pageable pageable);
    @Query(value = "SELECT * FROM RIDE WHERE DRIVER_ID = ?1 AND START_TIME > ?2", nativeQuery = true)
    Page<Ride> getRides(Integer id, Date from, Pageable pageable);
    @Query(value = "SELECT * FROM RIDE WHERE DRIVER_ID = ?1 AND START_TIME BETWEEN ?2 AND ?3", nativeQuery = true)
    Page<Ride> getRides(Integer id, Date from, Date to, Pageable pageable);


//    @Query("select count(*) from Ride r where r.driver.id = ?1")
//    Optional<Integer> getNumOfRides(Integer driver_id);
//    @Query("select sum(r.totalCost) from Ride r where r.driver.id = ?1")
//    Optional<Integer> getSumOfPrices(Integer driver_id);

    @Query(value = "SELECT COUNT(*) FROM RIDE WHERE DRIVER_ID = ?1 AND MONTH(START_TIME) = ?2", nativeQuery = true)
    Optional<Integer> getNumOfRides(Integer driver_id, Integer month);
    @Query(value = "SELECT SUM(TOTAL_COST) FROM RIDE WHERE DRIVER_ID = ?1 AND MONTH(START_TIME) = ?2", nativeQuery = true)
    Optional<Integer> getSumOfPrices(Integer driver_id, Integer month);

    //Passenger Native Querries
    @Query(value = "SELECT * FROM RIDE WHERE RIDE_ID IN (SELECT RIDE_ID FROM PASSENGER_RIDES WHERE USER_ID= ?1)", nativeQuery = true)
    List<Ride> getRidesPassenger(Integer id);
    @Query(value = "SELECT * FROM RIDE WHERE RIDE_ID IN (SELECT RIDE_ID FROM PASSENGER_RIDES WHERE USER_ID= ?1)", nativeQuery = true)
    Page<Ride> getRidesPassenger(Integer id, Pageable pageable);
    @Query(value = "SELECT * FROM RIDE WHERE RIDE_ID IN (SELECT RIDE_ID FROM PASSENGER_RIDES WHERE USER_ID= ?1) AND START_TIME > ?2", nativeQuery = true)
    Page<Ride> getRidesPassenger(Integer id, Date from, Pageable pageable);
    @Query(value = "SELECT * FROM RIDE WHERE RIDE_ID IN (SELECT RIDE_ID FROM PASSENGER_RIDES WHERE USER_ID= ?1) AND START_TIME BETWEEN ?2 AND ?3", nativeQuery = true)
    Page<Ride> getRidesPassenger(Integer id, Date from, Date to, Pageable pageable);
    @Query(value = "SELECT * FROM RIDE WHERE RIDE_STATE='PENDING' AND RIDE_ID IN (SELECT RIDE_ID FROM PASSENGER_RIDES WHERE USER_ID= ?1)", nativeQuery = true)
    List<Ride> findPendingRides(Integer id);
    @Query(value = "SELECT * FROM RIDE WHERE RIDE_STATE='STARTED' AND DRIVER_ID =?", nativeQuery = true)
    Ride findActiveRideForDriver(Integer driverId);
    @Query(value = "SELECT * FROM RIDE WHERE RIDE_STATE='STARTED' AND RIDE_ID IN (SELECT RIDE_ID FROM PASSENGER_RIDES WHERE USER_ID= ?1)", nativeQuery = true)
    Ride findActiveRideForPassenger(Integer passengerId);
    @Query(value = "SELECT * FROM RIDE WHERE RIDE_STATE='ACCEPTED' AND DRIVER_ID=?",nativeQuery = true)
    List<Ride> acceptedRidesForDriver(Integer driverId);
}
