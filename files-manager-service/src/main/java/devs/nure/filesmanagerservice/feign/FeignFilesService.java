package devs.nure.filesmanagerservice.feign;

import devs.nure.formslibrary.*;
import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "meta-info-service")
public interface FeignFilesService {

    @PostMapping("api/v1/files/")
    void addFile(@RequestBody FileInfo file);

//    @GetMapping("/{fileID}/info")
//    FileInfo showFileInfo(@PathVariable String fileID);

}
