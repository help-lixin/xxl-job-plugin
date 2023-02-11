package help.lixin.xxl.job.customizer.context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import help.lixin.xxl.job.model.JobParamInfo;
import com.xxl.job.core.context.XxlJobContext;
import com.xxl.job.core.log.XxlJobFileAppender;
import com.xxl.job.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

public class XxlJobHelper {
    private static final Logger logger = LoggerFactory.getLogger(XxlJobHelper.class);

    public XxlJobHelper() {
    }

    public static long getJobId() {
        XxlJobContext xxlJobContext = XxlJobContext.getXxlJobContext();
        return xxlJobContext == null ? -1L : xxlJobContext.getJobId();
    }

    public static JobParamInfo getJobParamInfo(Class tclass) {
        JobParamInfo jobParamInfo = null;
        try {
            XxlJobContext xxlJobContext = XxlJobContext.getXxlJobContext();
            if (xxlJobContext == null) {
                return null;
            }
            String jobParamString = xxlJobContext.getJobParam();
            if (jobParamString != null) {
                jobParamInfo = postForEntity(tclass, jobParamString);
            }
            if (jobParamInfo == null) {
                jobParamInfo = new JobParamInfo();
            }
            long jobIb = getJobId();
            jobParamInfo.setJobId(jobIb == 0 ? null : new Long(jobIb).toString());
        } catch (Exception e) {
            logger.error("Gerp-XXL-Job GerpXxlJobHelper.getJobParamInfo fail info:{}", e);
        }
        return jobParamInfo;
    }

    public static <T> JobParamInfo<T> postForEntity(Class tclass, String params) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        //反序列化的时候如果多了其他属性,不抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaType javaType = mapper.getTypeFactory().constructParametricType(JobParamInfo.class, tclass);
        JobParamInfo<T> resultVo = mapper.readValue(params, javaType);
        return resultVo;
    }

    public static String getJobLogFileName() {
        XxlJobContext xxlJobContext = XxlJobContext.getXxlJobContext();
        return xxlJobContext == null ? null : xxlJobContext.getJobLogFileName();
    }

    public static int getShardIndex() {
        XxlJobContext xxlJobContext = XxlJobContext.getXxlJobContext();
        return xxlJobContext == null ? -1 : xxlJobContext.getShardIndex();
    }

    public static int getShardTotal() {
        XxlJobContext xxlJobContext = XxlJobContext.getXxlJobContext();
        return xxlJobContext == null ? -1 : xxlJobContext.getShardTotal();
    }

    public static boolean log(String appendLogPattern, Object... appendLogArguments) {
        FormattingTuple ft = MessageFormatter.arrayFormat(appendLogPattern, appendLogArguments);
        String appendLog = ft.getMessage();
        StackTraceElement callInfo = (new Throwable()).getStackTrace()[1];
        return logDetail(callInfo, appendLog);
    }

    public static boolean log(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        String appendLog = stringWriter.toString();
        StackTraceElement callInfo = (new Throwable()).getStackTrace()[1];
        return logDetail(callInfo, appendLog);
    }

    private static boolean logDetail(StackTraceElement callInfo, String appendLog) {
        XxlJobContext xxlJobContext = XxlJobContext.getXxlJobContext();
        if (xxlJobContext == null) {
            return false;
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(DateUtil.formatDateTime(new Date())).append(" ").append("[" + callInfo.getClassName() + "#" + callInfo.getMethodName() + "]").append("-").append("[" + callInfo.getLineNumber() + "]").append("-").append("[" + Thread.currentThread().getName() + "]").append(" ").append(appendLog != null ? appendLog : "");
            String formatAppendLog = stringBuffer.toString();
            String logFileName = xxlJobContext.getJobLogFileName();
            long jobId = xxlJobContext.getJobId();
            if (logFileName != null && logFileName.trim().length() > 0) {
                XxlJobFileAppender.appendLog(logFileName, formatAppendLog);
                return true;
            } else {
                return false;
            }

        }
    }

    public static boolean handleSuccess() {
        return handleResult(200, (String) null);
    }

    public static boolean handleSuccess(String handleMsg) {
        return handleResult(200, handleMsg);
    }

    public static boolean handleFail() {
        return handleResult(500, (String) null);
    }

    public static boolean handleFail(String handleMsg) {
        return handleResult(500, handleMsg);
    }

    public static boolean handleTimeout() {
        return handleResult(502, (String) null);
    }

    public static boolean handleTimeout(String handleMsg) {
        return handleResult(502, handleMsg);
    }

    public static boolean handleResult(int handleCode, String handleMsg) {
        XxlJobContext xxlJobContext = XxlJobContext.getXxlJobContext();
        if (xxlJobContext == null) {
            return false;
        } else {
            xxlJobContext.setHandleCode(handleCode);
            if (handleMsg != null) {
                xxlJobContext.setHandleMsg(handleMsg);
            }
            return true;
        }
    }

}