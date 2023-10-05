package com.songify.song.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SongViewController {

    private Map<Integer, String> database = new HashMap<>();

    @GetMapping("/")
    public String getHomePage(){
        return "home.html";
    }

    @GetMapping("/view/songs")
    public String getSongsPage(Model model){
        database.put(1, "shawnmendes song1");
        database.put(2, "ariana grande song2");
        database.put(3, "arfqfq2");
        database.put(4, "arirety34hy42");

        model.addAttribute("songMap",database);
        return "songs.html";
    }


}
