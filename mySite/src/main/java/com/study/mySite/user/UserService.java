package com.study.mySite.user;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.study.mySite.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

 
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private static final String UPLOAD_DIR = "src/main/resources/static/imgs/user/";
    
	
	public SiteUser create(String username,String email,String password,MultipartFile imageFile) throws IOException {
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		if(!imageFile.isEmpty()) {
			String fileName = UUID.randomUUID().toString()+"_"+imageFile.getOriginalFilename();
			Path filePath = Paths.get(UPLOAD_DIR,fileName);
			Files.createDirectories(filePath.getParent());
			Files.write(filePath, imageFile.getBytes());
			//사용자 엔티티에 이미지 경로 설정
			user.setImageUrl("/imgs/user/"+fileName);
		}
			
		this.userRepository.save(user);
		return user;
	}
	 
	public SiteUser getUser(String username) {
		Optional<SiteUser> SiteUser = this.userRepository.findByUsername(username);
		if(SiteUser.isPresent()) {
			return SiteUser.get();
		}else {
			throw new DataNotFoundException("siteuser not found");
		}
	}
}
