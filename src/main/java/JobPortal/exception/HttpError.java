package JobPortal.exception;

import java.util.Objects;
import java.util.LinkedHashMap;

import com.google.gson.Gson;

public class HttpError {

    private static final long serialVersionUID = 1L;

    private String msg;
    
    private int code;

    public int getCode() {
        return code;
    }
    
    public HttpError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public String toString() {
        // TODO: must be json
        LinkedHashMap<String, String> jsonOrderedMap = new LinkedHashMap<String, String>(); 
        LinkedHashMap<Object, Object> map = new LinkedHashMap<Object, Object>();  
        try {
            jsonOrderedMap.put("code", String.valueOf(this.code));
            jsonOrderedMap.put("msg", this.msg);
            map.put("Error", jsonOrderedMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        Gson gson = new Gson();
        String errorJson = gson.toJson(map, LinkedHashMap.class);
        return errorJson;
    }
  
}

