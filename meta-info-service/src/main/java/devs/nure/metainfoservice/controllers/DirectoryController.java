package devs.nure.metainfoservice.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class DirectoryController {

    @PostMapping
    public Object addDirectory(@RequestBody Object obj) {
        return null;
    }

    @PutMapping
    public Object updateDirectory(@RequestBody Object obj) {
        return null;
    }

    @DeleteMapping
    public void removeDirectory(@RequestBody Object obj){
    }

    @GetMapping
    public Object showDirectories() {
        return null;
    }

}