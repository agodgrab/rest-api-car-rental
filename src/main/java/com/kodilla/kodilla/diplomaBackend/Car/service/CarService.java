package com.kodilla.kodilla.diplomaBackend.Car.service;

import com.kodilla.kodilla.diplomaBackend.Car.domain.Car;
import com.kodilla.kodilla.diplomaBackend.Category.domain.Category;
import com.kodilla.kodilla.diplomaBackend.Car.repository.CarRepository;
import com.kodilla.kodilla.diplomaBackend.Category.service.CategoryService;
import com.kodilla.kodilla.diplomaBackend.LogHistory.service.LogHistoryService;
import com.kodilla.kodilla.diplomaBackend.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private LogHistoryService logHistoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    public List<Car> searchCar(LocalDate startDay, LocalDate endDay, String categoryName){
        try{
            Category category = categoryService.getCategory(categoryName);
            return carRepository.searchCarInDateRange(startDay, endDay, category.getId());
        }
        catch(NoSuchElementException e){
            return new ArrayList<>();
        }
    }

    public Car addCar(Car car){
        logHistoryService.saveLog(userService.getAdmin(), "New car added to the company's fleet: " + car.getModel());
        return carRepository.save(car);
    }

    public void deleteCar(Long id){
        logHistoryService.saveLog(userService.getAdmin(), "Car deleted from the company's fleet: " + id);
        carRepository.deleteById(id);
    }

    public Car updateCar(Car car){
        logHistoryService.saveLog(userService.getAdmin(), "Car data update: " + car.getModel() + " (" + car.getId() + ")");
        return carRepository.save(car);
    }

    public Car findCar(long id){ return carRepository.findById(id).orElseThrow(NoSuchElementException::new);

    }
}
