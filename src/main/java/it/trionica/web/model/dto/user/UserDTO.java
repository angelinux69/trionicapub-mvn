package it.trionica.web.model.dto.user;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames=true)
public class UserDTO {
	
	protected String nome; 
	protected String cognome;

}
