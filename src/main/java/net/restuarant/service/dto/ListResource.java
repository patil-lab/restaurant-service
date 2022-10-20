package net.restuarant.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListResource<T> {

    @JsonProperty("totalPage")
    @ApiModelProperty(value = "totalPage", example = "1")
    private Integer totalPage;

    @JsonProperty("content")
    @ApiModelProperty(value = "content")
    private List<T> content;

}
