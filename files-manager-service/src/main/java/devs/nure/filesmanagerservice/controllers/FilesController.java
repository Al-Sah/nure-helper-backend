package devs.nure.filesmanagerservice.controllers;

import devs.nure.filesmanagerservice.services.FilesService;
import devs.nure.formslibrary.*;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/files")
public class FilesController {

    private final FilesService filesService;
    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostMapping(value = "/save", consumes = "multipart/form-data" )
    public FileInfo saveFile(@RequestParam("file") MultipartFile file, @RequestParam("author") String author,
                             @RequestParam("parentID") String parentID){
        return filesService.manageFile(file, new CreateFile(parentID, author));
    }

    @GetMapping("/{fileID}")
    public ResponseEntity<Resource> uploadFile(@PathVariable String fileID){
        return filesService.downloadFile(fileID);
    }

//    @DeleteMapping("/")
//    public void deleteFile(@RequestBody ChangeStatusFile file) {
//        filesService.deleteFile(file);
//    }
//
//    @PutMapping("/recover")
//    void recoverFile(@RequestBody ChangeStatusFile file) {
//        filesService.recoverFile(file);
//    }

    @GetMapping("/")
    public FileInfo getFileInfo(@Valid @RequestBody String fileID) {
        return filesService.getFileInfo(fileID);
    }

    @DeleteMapping("/complete_remove")
    public void completeRemoveFile(@RequestBody String fileId) {
        filesService.completeRemoveFile(fileId);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(RuntimeException exception) {
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
