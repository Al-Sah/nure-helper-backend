package devs.nure.filesmanagerservice.feign;

import devs.nure.formslibrary.FileInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(name = "meta-info-service")
public interface FeignFilesMetaInfoService {

    @PostMapping("/api/v1/meta-info/files/")
    void sendFileInformation(@RequestBody FileInfo file);

    @GetMapping(value = "/api/v1/meta-info/files/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    FileInfo getFileInfo(@Valid @RequestBody String fileID);

    @DeleteMapping("/api/v1/meta-info/files/complete_remove")
    void removeFileMetaInfo(@Valid @RequestBody String fileId);

}
