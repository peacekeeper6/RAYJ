package com.nighthawk.spring_portfolio.controllers;
/* MVC code that shows defining a simple Model, calling View, and this file serving as Controller
 * Web Content with Spring MVCSpring Example: https://spring.io/guides/gs/serving-web-con
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller  // HTTP requests are handled as a controller, using the @Controller annotation
public class Database {
/*
    // @GetMapping handles GET request for /greet, maps it to greeting() method
    @GetMapping("/users")
    // @RequestParam handles variables binding to frontend, defaults, etc
    public String users(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {

        // model attributes are visible to Thymeleaf when HTML is "pre-processed"
        model.addAttribute("name", name);

        // load HTML VIEW (greet.html)
        return "users"; 

    }
*/

    @GetMapping("/songs")
    // @RequestParam handles variables binding to frontend, defaults, etc
    public String songs(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {

        // model attributes are visible to Thymeleaf when HTML is "pre-processed"
        model.addAttribute("name", name);

        // load HTML VIEW (greet.html)
        return "songs"; 

    }

    @GetMapping("/artists")
    // @RequestParam handles variables binding to frontend, defaults, etc
    public String artists(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {

        // model attributes are visible to Thymeleaf when HTML is "pre-processed"
        model.addAttribute("name", name);

        // load HTML VIEW (artists.html)
        return "artists"; 

    }
    
    @GetMapping("/login")
    // @RequestParam handles variables binding to frontend, defaults, etc
    public String login(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {

        // model attributes are visible to Thymeleaf when HTML is "pre-processed"
        model.addAttribute("name", name);

        // load HTML VIEW (login.html)
        return "login"; 

    }

    @GetMapping("/admin")
    // @RequestParam handles variables binding to frontend, defaults, etc
    public String admin(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {

        // model attributes are visible to Thymeleaf when HTML is "pre-processed"
        model.addAttribute("name", name);

        // load HTML VIEW (login.html)
        return "admin"; 

    }

}