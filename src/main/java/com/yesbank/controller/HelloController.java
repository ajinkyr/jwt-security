package com.yesbank.controller;

import java.security.Principal;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HelloController {

	@GetMapping("/api/user")
	public RestMsg HelloWorld() {
		return new RestMsg("Hello World!!");
	}

	@GetMapping("/api/test")
	public RestMsg apitest(@RequestParam String user) {
		return new RestMsg("Hello "+user);
	}

	@GetMapping(value = "/api/hello", produces = "application/json")
	public RestMsg helloUser() {
		// The authenticated user can be fetched using the SecurityContextHolder
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return new RestMsg(String.format("Hello '%s'!", username));
	}

	@GetMapping("/api/admin")
	// If a controller request asks for the Principal user in
	// the method declaration Spring security will provide it.
	public RestMsg helloAdmin(Principal principal) {
		return new RestMsg(String.format("Welcome '%s'!", principal.getName()));
	}

	// A helper class to make our controller output look nice
	public static class RestMsg {
		private String msg;

		public RestMsg(String msg) {
			this.msg = msg;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}

}