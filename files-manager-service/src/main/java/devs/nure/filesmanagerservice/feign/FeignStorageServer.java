package devs.nure.filesmanagerservice.feign;

import devs.nure.filesmanagerservice.forms.StoredFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "files-storage-server")
public interface FeignStorageServer {



    @PostMapping(value="/storage/", consumes = "multipart/form-data" )
    String storeFile(@RequestPart("file") MultipartFile file, @RequestPart("uniqID") String uniqID);

    @GetMapping("/storage/")
    @ResponseBody
    Resource getFile(@RequestParam String path);

    @DeleteMapping("/storage/")
    void deleteFile(@RequestParam String path) ;

}
