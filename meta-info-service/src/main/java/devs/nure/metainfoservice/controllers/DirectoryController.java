package devs.nure.metainfoservice.controllers;

import devs.nure.metainfoservice.Dto.DirectoryDto;
import devs.nure.metainfoservice.forms.CreateDirectory;
import devs.nure.metainfoservice.forms.ChangeStatusDirectory;
import devs.nure.metainfoservice.forms.DirectoryNode;
import devs.nure.metainfoservice.forms.UpdateDirectory;
import devs.nure.metainfoservice.models.State;
import devs.nure.formslibrary.ErrorMessage;
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

    @PostMapping("/")
    public DirectoryDto addDirectory(@Valid @RequestBody CreateDirectory directory) {
        return directoryService.addDirectory(directory);
    }

    @PutMapping("/")
    public DirectoryDto updateDirectory(@Valid @RequestBody UpdateDirectory directory) {
        return directoryService.updateDirectory(directory);
    }

    @PutMapping("/recover")
    public void recoverDirectory(@Valid @RequestBody ChangeStatusDirectory recoverDirectory) {
        directoryService.setDirectoryState(recoverDirectory, State.RECOVERED);
    }

    @DeleteMapping("/")
    public void removeDirectory(@Valid @RequestBody ChangeStatusDirectory deleteDirectory){
        directoryService.removeDirectory(deleteDirectory);
    }

    @DeleteMapping("/complete_remove")
    public void completeRemoveDirectory(@RequestParam("dirUniqID") String dirUniqID) {
        directoryService.completeDeleteDirectory(dirUniqID);
    }

    @GetMapping("/{dirUniqID}/info")
    public DirectoryDto showDirectoryDescription(@PathVariable String dirUniqID) {
        return directoryService.showDirectoryDescription(dirUniqID);
    }

    @GetMapping("/{dirUniqID}")
    public DirectoryNode showDirectory(@PathVariable String dirUniqID) {
        return directoryService.showDirectory(dirUniqID);
    }

    @GetMapping("/tree/{dirUniqID}")
    public Object showDirectoriesTree(@PathVariable String dirUniqID) {
        return directoryService.generateDirectoriesTree(dirUniqID);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(RuntimeException exception) {
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

}