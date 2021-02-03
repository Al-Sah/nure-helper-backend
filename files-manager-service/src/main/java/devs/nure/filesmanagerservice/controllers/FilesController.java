package devs.nure.filesmanagerservice.controllers;

import devs.nure.filesmanagerservice.services.FilesService;
import devs.nure.formslibrary.CreateFile;
import devs.nure.formslibrary.ErrorMessage;
import devs.nure.formslibrary.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
public class FilesController {

    private final FilesService filesService;
    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostMapping(value = "/save", consumes = "multipart/form-data" )
    public FileInfo saveFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("author") String author,
                             @RequestParam("parentID") String parentID){
        return filesService.manageFile(file, new CreateFile(parentID, author));
    }

    @GetMapping("/{fileID}")
    public ResponseEntity<Resource> uploadFile(@PathVariable String fileID){
        Resource resource = filesService.downloadFile(fileID);
        FileInfo info = filesService.getFileInfo(fileID);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +info.getName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, info.getContentType())
                .header(HttpHeaders.LAST_MODIFIED, info.getLastModification().toString())
                .body(resource);
    }

    @DeleteMapping("/remove")
    public void removeFile(@RequestParam("fileId") String fileId) {
        filesService.removeFile(fileId);
    }


    @ExceptionHandler // TODO ControllerAdvice
    public ResponseEntity<ErrorMessage> handleException(RuntimeException exception) {
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
