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
        Worker[] wo = new Worker[5];
        wo[0] = new Worker();
        wo[0].setName("John");
        wo[0].setAge(18);
        wo[0].setGender("male");
        wo[1] = new Worker();
        wo[1].setName("Mary");
        wo[1].setAge(18);
        wo[1].setGender("female");
        wo[2] = new Worker();
        wo[2].setName("Charles");
        wo[2].setAge(22);
        wo[2].setGender("male");
        wo[3] = new Worker();
        wo[3].setName("Becky");
        wo[3].setAge(52);
        wo[3].setGender("female");
        wo[4] = new Worker();
        wo[4].setName("Steve");
        wo[4].setAge(23);
        wo[4].setGender("male");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(wo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "create";
    }
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
        Worker[] wo = loadWorkers();
        Worker[] res_wo = new Worker[5];
        int j = 0;

        for (int i = 0; i < 5; i++) {
            if (wo[i].getAge() == ageValue) {
                res_wo[j] = wo[i];
            }
        }

        model.addAttribute(res_wo);

        return "age";
    }
    @GetMapping("/worker/sex/{gender}")
    public String gender(@PathVariable String gender, Model model) {
        Worker[] wo = loadWorkers();
        Worker[] temp_wo = new Worker[5];
        int j = 0;
        for (int i = 0; i < 5; i++) {
            if(wo[i].getGender() == gender) {
                temp_wo[j] = wo[i];
                j++;
            }
        }
        model.addAttribute(temp_wo);
        return "gender";
    }
}
