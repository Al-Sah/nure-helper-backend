package devs.nure.filesmanagerservice.feign;

import devs.nure.formslibrary.FileInfo;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import javax.validation.Valid;

@Slf4j
public class MetaInfoServiceFallback implements FallbackFactory<FeignFilesMetaInfoService> {
    @Override
    public FeignFilesMetaInfoService create(Throwable cause) {
        String httpStatus = cause instanceof FeignException ? Integer.toString(((FeignException) cause).status()) : "";

        return new FeignFilesMetaInfoService() {
            @Override
            public void sendFileInformation(FileInfo file) {
                log.error(httpStatus);
                throw new RuntimeException("sendFileInformation Exception");
            }

            @Override
            public FileInfo getFileInfo(@Valid String fileID) {
                throw new RuntimeException("getFileInfo Exception");
            }

            @Override
            public void removeFileMetaInfo(@Valid String fileId) {
                throw new RuntimeException("removeFileMetaInfo Exception");
            }

        };
    }
}
