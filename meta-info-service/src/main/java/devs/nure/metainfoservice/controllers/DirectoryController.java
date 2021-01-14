package devs.nure.metainfoservice.controllers;

import devs.nure.metainfoservice.Dto.DirectoryDto;
import devs.nure.metainfoservice.forms.CreateDirectory;
import devs.nure.metainfoservice.forms.UpdateDirectory;
import devs.nure.metainfoservice.resource.ErrorMessage;
import devs.nure.metainfoservice.services.DirectoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/dirs")
public class DirectoryController {

    private final DirectoryService directoryService;
    public DirectoryController(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    @PostMapping("/create")
    public DirectoryDto addDirectory(@Valid @RequestBody CreateDirectory directory) {
        return null;
    }

    @PutMapping("/update")
    public DirectoryDto updateDirectory(@Valid @RequestBody UpdateDirectory directory) {
        return null;
    }

    @DeleteMapping("/delete")
    public void removeDirectory(@RequestParam String dirUniqID){
    }

    @GetMapping("/{dirUniqID}")
    public Object showDirectoryDescription(@PathVariable String dirUniqID) {
        return null;
    }
    @GetMapping("/info/{dirUniqID}")
    public DirectoryDto showDirectory(@PathVariable String dirUniqID) {
        return null;
    }
    @GetMapping("/tree")
    public Object showDirectoriesTree() {
        return null;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(RuntimeException exception) {
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

}