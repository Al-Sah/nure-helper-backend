package devs.nure.filesmanagerservice.feign;

import devs.nure.formslibrary.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(name = "meta-info-service")
public interface FeignFilesMetaInfoService {

    @PostMapping("/api/v1/files/")
    void addFile(@RequestBody FileInfo file);

//    @DeleteMapping("/api/v1/files/")
//    void deleteFile(@Valid @RequestBody ChangeStatusFile deleteFile);
//
//    @PutMapping("/api/v1/files/recover/")
//    void recoverFile(@Valid @RequestBody ChangeStatusFile recoverFile);

    @GetMapping(value = "/api/v1/files/", consumes = MediaType.APPLICATION_JSON_VALUE)
    FileInfo getFileInfo(@Valid @RequestBody String fileID);

    @DeleteMapping("/api/v1/files/complete_remove")
    void completeRemoveFile(@Valid @RequestBody String fileId);

}
