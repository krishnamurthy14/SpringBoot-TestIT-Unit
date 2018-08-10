package com.kg.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HomeController
 */
@RestController
public class HomeController {
@RequestMapping(value="/")
    public String name()

    {
         return "SpringBoot";

    }
}