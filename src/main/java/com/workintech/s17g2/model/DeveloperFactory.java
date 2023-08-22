package com.workintech.s17g2.model;

import com.workintech.s17g2.tax.Taxable;
import org.springframework.web.bind.annotation.RequestBody;

public class DeveloperFactory {
    public static Developer save(@RequestBody Developer developer, Taxable taxable) {
        Developer createdDeveloper;
        if (developer.getExperience().name().equalsIgnoreCase("junior")) {
            createdDeveloper = new JuniorDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary() * taxable.getSimpleTaxRate(), developer.getExperience());
        } else if (developer.getExperience().name().equalsIgnoreCase("mid")) {
            createdDeveloper = new MidDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary() * taxable.getMiddleTaxRate(), developer.getExperience());
        } else if (developer.getExperience().name().equalsIgnoreCase("senior")) {
            createdDeveloper = new SeniorDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary() * taxable.getUpperTaxRate(), developer.getExperience());
        } else {
            createdDeveloper = null;
        }
      return createdDeveloper;

    }


}
