package com.example.demo.controller;

import com.example.demo.model.Worker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.*;

@Controller
public class MyController {
    private static final String FILE_NAME = "workers.data";
    @GetMapping("/worker/create")
    public String createWorkers(){
        // Information for all new workers
        String[] names = ["John", "Mary", "Charles", "Becky", "Steve"];
        int[] ages = [18, 18, 22, 52, 23];
        String[] genders = ["male", "female", "male", "female", "male"];
        int[] experiences = [1, 2, 5, 20, 7];

        // Initialize all workers into array "wo"
        Worker[] wo = new Worker[5];
        for (int i = 0; i < wo.length; i++) {
            wo[i] = new Worker();
            wo[i].setName(names[i]);
            wo[i].setAge(ages[i]);
            wo[i].setGender(genders[i]);
            wo[i].setExperience(experiences[i]);
        }

        // Creates an ObjectOutputStream to write the worker array to a file, and handles any errors that occur while writing the file.
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(wo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "create";
    }

    // Helper function to return an array of all the workers
    private Worker[] loadWorkers() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME)))
        {
            return (Worker[]) in.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/worker/{ageValue}")
    public String age(@PathVariable int ageValue, Model model) {
        // Get all the available workers
        Worker[] wo = loadWorkers();
        Worker[] temp_workers = new Worker[5];
        int j = 0;

        for (int i = 0; i < wo.length; i++) {
            if (wo[i].getAge() == ageValue) {
                temp_workers[j] = wo[i];
                j++;
            }
        }

        // Since temp_workers contains j elements inside it with possible null values
        // Get rid of null values by creating a result array of only j number of workers and copying elements
        const final int count = j;
        int i = 0;
        Worker[] workers = new Worker[count];
        for (Worker worker: temp_workers) {
            workers[i] = worker;
            i++;
        }

        model.addAttribute("workers", workers);

        return "ageView";
    }
    @GetMapping("/worker/sex/{gender}")
    public String gender(@PathVariable String gender, Model model) {
        Worker[] wo = loadWorkers();
        Worker[] temp_workers = new Worker[5];
        int j = 0;
        for (int i = 0; i < wo.length; i++) {
            if(wo[i].getGender().equals(gender)) {
                temp_workers[j] = wo[i];
                j++;
            }
        }

        // Since temp_workers contains j elements inside it with possible null values
        // Get rid of null values by creating a result array of only j number of workers and copying elements
        const final int count = j;
        int i = 0;
        Worker[] workers = new Worker[count];
        for (Worker worker: temp_workers) {
            workers[i] = worker;
            i++;
        }

        model.addAttribute("workers", workers);
        return "genderView";
    }
}
