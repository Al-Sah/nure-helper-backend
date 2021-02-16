package devs.nure.metainfoservice.controllers;

import devs.nure.formslibrary.*;
import devs.nure.metainfoservice.Dto.FileDto;
import devs.nure.metainfoservice.models.State;
import devs.nure.metainfoservice.services.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/meta-info/files")
public class FilesController {

    private final FileService fileService;

    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/") //is used by files-manager service
    public void addFile(@RequestBody FileInfo file){
         fileService.addFile(file);
    }

    @PutMapping("/")
    public void updateFile(@Valid @RequestBody UpdateFile file){
        fileService.updateFile(file);
    }

    @DeleteMapping("/complete_remove") //is used by files-manager service
    public void completeRemoveFile(@Valid @RequestBody String fileId) {
        fileService.completeDeleteFile(fileId);
    }

    @DeleteMapping("/")
    public void deleteFile(@Valid @RequestBody ChangeStatusFile deleteFile) {
        fileService.setFileState(deleteFile, State.DELETED);
    }

    @PutMapping("/recover")
    public void recoverFile(@Valid @RequestBody ChangeStatusFile recoverFile) {
        fileService.setFileState(recoverFile, State.RECOVERED);
    }

    @GetMapping("/{fileID}/info")
    public FileDto showFileInfo(@PathVariable String fileID){
        return fileService.showFileInfo(fileID);
    }

    @GetMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)// used by files manager
    @ResponseBody
    public FileInfo getFileInfo(@Valid @RequestBody String fileID) {
        return fileService.getFileInfo(fileID);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(RuntimeException exception) {
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
