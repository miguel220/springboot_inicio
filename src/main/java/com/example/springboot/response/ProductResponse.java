package com.example.springboot.response;

import java.util.UUID;

public class ProductResponse {
    private UUID id;
    private String message;

    public ProductResponse(UUID uuid, String message) {
        this.id = uuid;
        this.message = message;
    }

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    
}
