package si.fri.rso.mailingmicroservice.services.minio;

import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;

import si.fri.rso.mailingmicroservice.services.config.MinioProperties;

@RequestScoped
public class MinioHandler {
    private MinioClient minioClient;

    @Inject
    private MinioProperties minioProperties;

    @PostConstruct
    public void initMinioClient() {
        this.minioClient = MinioClient.builder()
                .endpoint(this.minioProperties.getHost(), this.minioProperties.getPort(), false)
                .credentials(this.minioProperties.getAccessKey(), this.minioProperties.getSecret())
                .build();
    }

    public InputStream getInvoiceFromStorage(String fileName) {
        try {
            return this.minioClient.getObject(GetObjectArgs.builder()
                    .bucket("invoices")
                    .object(fileName).build());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
