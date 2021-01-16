package devs.nure.filesmanagerservice.controllers;

import devs.nure.filesmanagerservice.services.FilesService;
import devs.nure.filesmanagerservice.services.implementation.FilesServiceImpl;
import devs.nure.formslibrary.FileInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/v1/files")
public class FilesController {

    private final FilesService filesService;
    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostMapping(value = "/save", consumes = "multipart/form-data" )
    public FileInfo saveFile(@RequestParam("file") MultipartFile file){
        return filesService.manageFile(file);
    }

    @GetMapping("/{fileID}")
    public MultipartFile uploadFile(@PathVariable String fileID){
        return null;
    }


}
