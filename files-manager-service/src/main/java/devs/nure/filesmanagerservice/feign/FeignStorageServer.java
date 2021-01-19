package devs.nure.filesmanagerservice.feign;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "files-storage-server")
public interface FeignStorageServer {



    @PostMapping(value="/storage/", consumes = "multipart/form-data" )
    String storeFile(@RequestPart("file") MultipartFile file);

    @GetMapping("/storage/{location}")
    ResponseEntity<Resource> getFile(@PathVariable String location);

}
