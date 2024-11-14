package linkedin.SkillTest.demo.Model;

import java.util.List;

public class Criteria {

    private String id;
    private String param_name;
    private String param_value;
    private List<String> param_list;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getParam_name() {
        return param_name;
    }
    public void setParam_name(String param_name) {
        this.param_name = param_name;
    }
    public String getParam_value() {
        return param_value;
    }
    public void setParam_value(String param_value) {
        this.param_value = param_value;
    }
    public List<String> getParam_list() {
        return param_list;
    }
    public void setParam_list(List<String> param_list) {
        this.param_list = param_list;
    }

    
}
