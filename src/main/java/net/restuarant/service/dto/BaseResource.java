package net.restuarant.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResource {

	@JsonProperty(value = "id")
	@ApiModelProperty(value = "id", example = "1", readOnly = true)
	private Long id;

	@JsonProperty(value = "createTime")
	@ApiModelProperty(value = "createTime", example = "1539669319", readOnly = true, dataType = "java.lang.Long")
	private Timestamp createTime;

	@JsonProperty(value = "updateTime")
	@ApiModelProperty(value = "updateTime", example = "1539669319", readOnly = true, dataType = "java.lang.Long")
	private Timestamp updateTime;

}
