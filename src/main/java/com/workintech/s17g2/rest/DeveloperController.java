package com.workintech.s17g2.rest;

import com.workintech.s17g2.mapping.DeveloperResponse;
import com.workintech.s17g2.model.Developer;
import com.workintech.s17g2.model.DeveloperFactory;
import com.workintech.s17g2.model.JuniorDeveloper;
import com.workintech.s17g2.tax.DeveloperTax;
import com.workintech.s17g2.tax.Taxable;
import com.workintech.s17g2.validation.DeveloperValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/developer")
public class DeveloperController {

    private Map<Integer, Developer> developers;
    private Taxable taxable;

    @PostConstruct
    public void init() {
        developers = new HashMap<>();
    }

    @Autowired
    public DeveloperController(@Qualifier("developerTax") Taxable taxable) {
        this.taxable = taxable;
    }

    @GetMapping
    public List<Developer> getAllDevelopers() {
        return developers.values().stream().toList();
    }

    @GetMapping("/{id}")
    public DeveloperResponse getById(@PathVariable int id) {
        if (!DeveloperValidation.isValidId(id)) {
            return new DeveloperResponse(null, "this id is not valid : " + id, 400);
        }
        if (!developers.containsKey(id)) {
            return new DeveloperResponse(null, "developer with this id not exist : " + id, 400);
        } else {
            return new DeveloperResponse(developers.get(id), "you reach the developer", 200);
        }
    }

    @PostMapping("/")
    public DeveloperResponse save(@RequestBody Developer developer) {
        Developer createdDeveloper = DeveloperFactory.save(developer, taxable);
        if (createdDeveloper == null) {
            return new DeveloperResponse(null, "developer is not valid", 400);
        }
        if (developers.containsKey(developer.getId())) {
            return new DeveloperResponse(null, "developer is already exist.", 400);
        }
        if (!DeveloperValidation.isDeveloperValid(developer)) {
            return new DeveloperResponse(null, "the credientals are not valid", 400);
        }
        developers.put(developer.getId(), createdDeveloper);
        return new DeveloperResponse(createdDeveloper, "the developer created successfully", 201);

// SOR ??? createdDeveloper vs developers.get(developer.getId())
    }


    @PutMapping("/{id}") //?? SOR
    public DeveloperResponse update(@PathVariable int id, @RequestBody Developer developer) {
        if (!developers.containsKey(id)) {
            return new DeveloperResponse(null, "the id is not exist", 400);
        }
        if (!DeveloperValidation.isValidId(id)) {
            return new DeveloperResponse(null, "the id is not valid", 400);
        }

       return new DeveloperResponse(developers.put(id,developer),"the developer updated successfully",200);
    }

    @DeleteMapping("/{id}")
    public DeveloperResponse delete(int id) {
        if (!developers.containsKey(id)) {
            return new DeveloperResponse(null, "the id is not exist", 400);
        }
        Developer developer=developers.get(id);
        developers.remove(id);
        return new DeveloperResponse(developer,"the developer deleted successfully",200);

    }


    private Developer createDeveloper(Developer developer) {
        Developer createdDeveloper;
        if (developer.getExperience().name().equalsIgnoreCase("junior")) {
            createdDeveloper = new JuniorDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary() * taxable.getSimpleTaxRate(), developer.getExperience());
        } else if (developer.getExperience().name().equalsIgnoreCase("mid")) {
            createdDeveloper = new JuniorDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary() * taxable.getMiddleTaxRate(), developer.getExperience());
        } else if (developer.getExperience().name().equalsIgnoreCase("senior")) {
            createdDeveloper = new JuniorDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary() * taxable.getUpperTaxRate(), developer.getExperience());
        } else {
            createdDeveloper = null;
        }
        developers.put(developer.getId(), createdDeveloper);
        return createdDeveloper;
    }


}
