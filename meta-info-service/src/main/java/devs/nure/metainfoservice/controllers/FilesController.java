package devs.nure.metainfoservice.controllers;

import devs.nure.formslibrary.*;
import devs.nure.metainfoservice.Dto.FileDto;
import devs.nure.metainfoservice.models.State;
import devs.nure.metainfoservice.services.FileService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/files")
public class FilesController {

    private final FileService fileService;

    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }


    @PostMapping("/")
    public void addFile(@RequestBody FileInfo file){
         fileService.addFile(file);

    }

    @PutMapping("/")
    public FileDto updateFile(@Valid @RequestBody UpdateFile file){
        return fileService.updateFile(file);
    }

    @DeleteMapping("/complete_remove")
    public void completeRemoveFile(@Valid @RequestBody String fileUniqID) { // ???
        fileService.completeDeleteFile(fileUniqID);
    }

    @DeleteMapping("/")
    public void deleteFile(@Valid @RequestBody ChangeStatusFile deleteFile) {
        fileService.setStatusFile(deleteFile, State.DELETED);
    }

    @PutMapping("/recover")
    public void recoverFile(@Valid @RequestBody ChangeStatusFile recoverFile) {
        fileService.setStatusFile(recoverFile, State.RECOVERED);
    }

    @GetMapping("/{fileID}/info")
    public FileDto showFileInfo(@PathVariable String fileID){
        return fileService.showFileInfo(fileID);
    }



}
