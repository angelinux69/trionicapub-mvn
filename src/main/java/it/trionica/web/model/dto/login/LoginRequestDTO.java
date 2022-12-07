package it.trionica.web.model.dto.login;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames=true)
public class LoginRequestDTO {
	
	protected String username; 
	protected String password;

}
