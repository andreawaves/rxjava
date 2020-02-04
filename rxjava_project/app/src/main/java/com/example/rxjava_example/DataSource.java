package com.example.rxjava_example;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    public static List<Task> createTasksList(){
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Levantarme", true, 3));
        tasks.add(new Task("Tomar agua", false, 2));
        tasks.add(new Task("Lavarme los dientes", true, 1));
        tasks.add(new Task("Cambiarme", false, 0));
        tasks.add(new Task("Tomar desayuno", true, 5));
        return tasks;
    }
}
