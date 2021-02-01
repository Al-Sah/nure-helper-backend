package devs.nure.filesmanagerservice.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import devs.nure.filesmanagerservice.services.FilesService;
import devs.nure.formslibrary.*;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
    @HystrixCommand(fallbackMethod = "fallbackMethodUploadFile")
    public ResponseEntity<Resource> uploadFile(@PathVariable String fileID){
        Resource resource = filesService.downloadFile(fileID);
        FileInfo info = filesService.getFileInfo(fileID);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +info.getName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, info.getContentType())
                .header(HttpHeaders.LAST_MODIFIED, info.getLastModification().toString())
                .body(resource);
    }

    public ResponseEntity<Resource> fallbackMethodUploadFile(@PathVariable String fileID){
        return new ResponseEntity<Resource>(new AbstractResource() {
            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(
                        ("this is fallback\n" + HttpStatus.REQUEST_TIMEOUT.toString())
                                .getBytes(StandardCharsets.UTF_8));
            }
        }, HttpStatus.REQUEST_TIMEOUT);
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
