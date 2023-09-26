package pack.model.container;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UploadDto {
	private String myName;
	private MultipartFile myFile;
}

