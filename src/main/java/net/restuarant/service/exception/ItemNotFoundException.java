package net.restuarant.service.exception;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class ItemNotFoundException extends Exception {
	private static final Gson gson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
	@Expose
	private ApplicationError error;

	public ItemNotFoundException(String message) {
		this.error = new ApplicationError();
		this.getError().setMessage(message);
	}

	public ItemNotFoundException(String message, int code) {
		this.error = new ApplicationError();
		this.getError().setMessage(message);
		this.getError().setCode(code);
	}


	public ApplicationError getError() {
		return this.error;
	}

	public void setError(ApplicationError error) {
		this.error = error;
	}

	public String toJson() {
		return gson.toJson(this);
	}
}
