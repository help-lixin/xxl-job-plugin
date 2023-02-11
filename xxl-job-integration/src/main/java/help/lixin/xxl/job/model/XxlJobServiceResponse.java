package help.lixin.xxl.job.model;


public class XxlJobServiceResponse<T> {
    private Integer code;
    private String message;
    private T data;

    public static Builder newBuilder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private Integer code;
        private String message;
        private T data;

        public Builder<T> success() {
            this.code = 200;
            this.message = "SUCCESS";
            return this;
        }


        public Builder<T> code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public XxlJobServiceResponse<T> build() {
            XxlJobServiceResponse<T> response = new XxlJobServiceResponse<>();
            response.setCode(this.code);
            response.setMessage(this.message);
            response.setData(this.data);
            return response;
        }

        public enum CodeEnum {

        }

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> XxlJobServiceResponse<T> success(T data) {
        XxlJobServiceResponse<T> response = new XxlJobServiceResponse<>();
        response.code = 200;
        response.message = "SUCCESS";
        response.data = data;
        return response;
    }

    @Override
    public String toString() {
        return "XxlJobServiceResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
