package com.zand.creditcard.infrastructure.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class S3Service {

  /**
   * Simulates an upload to S3.
   *
   * @param file the file to upload
   * @return the URL of the uploaded file
   */
  public String uploadFile(MultipartFile file) {
    log.info("Uploading file: {}", file.getOriginalFilename());
    return "/s3/bank-statements/john-doe.pdf";
  }
}
