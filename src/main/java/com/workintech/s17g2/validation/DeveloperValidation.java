package com.workintech.s17g2.validation;

import com.workintech.s17g2.mapping.DeveloperResponse;
import com.workintech.s17g2.model.Developer;

public class DeveloperValidation {

public static boolean isValidId(int id){
    return id<0;
}

public static boolean isDeveloperValid(Developer developer){
    if(isValidId(developer.getId()) && developer.getName() !=null && developer.getSalary() < 25000 && !developer.getName().isEmpty()){
        return true;
    } else{
        return false;
    }
}
// id ve request body'de girilen değerlerin checki yapılır.
}
