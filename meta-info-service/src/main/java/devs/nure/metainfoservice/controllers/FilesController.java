package devs.nure.metainfoservice.controllers;

import devs.nure.formslibrary.*;
import devs.nure.metainfoservice.Dto.FileDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/files")
public class FilesController {

    @PostMapping("/")
    public ResponseEntity<?> addFile(@RequestBody FileInfo file){
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping("/")
    public FileDto updateFile(@Valid @RequestBody UpdateFile file){
        return null;
    }

    @DeleteMapping("/")
    public void deleteFile(@Valid @RequestBody DeleteFile file){
    }

    @GetMapping("/{fileID}/info")
    public FileDto showFileInfo(@PathVariable String fileID){
        return null;
    }

}
