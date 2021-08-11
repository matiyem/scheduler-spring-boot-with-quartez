package quartez.web.model;

import lombok.Data;

/**
 * created by Atiye Mousavi
 * Date: 8/8/2021
 * Time: 2:12 PM
 */

@Data
public class FilterRequest {
    private Boolean active;
    private String zipFilter;

    public FilterRequest() { }

}
