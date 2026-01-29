package com.study.mySite.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	private final UserService userService;
	
	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm,BindingResult bindingResult,@RequestParam("imageFile") MultipartFile imgFile) {
		if(bindingResult.hasErrors()) {
			return "signup_form";	
		}
		if(!userCreateForm.getPassword().equals(userCreateForm.getPassword2())){
			bindingResult.rejectValue("password2", "passwordInCorrect","2갸의 비밀번호가 일치하지 않습니다.");
			return "signup_form";
		}		
		
		
		try {
			userService.create(userCreateForm.getUsername()
			, userCreateForm.getEmail()
			, userCreateForm.getPassword()
			, imgFile
			);
			
		}catch(DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed","이미 등록된 이메일입니다.");
			return "signup_form";
			
		}catch(Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed",e.getMessage());
			return "signup_form";
		}
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login(){
		return "login_form";
	} 
}
