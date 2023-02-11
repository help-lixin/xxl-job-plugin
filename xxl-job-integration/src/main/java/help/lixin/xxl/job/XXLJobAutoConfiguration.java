package help.lixin.xxl.job;

import help.lixin.xxl.job.config.CommonConfig;
import help.lixin.xxl.job.config.XxlJobConfig;
import help.lixin.xxl.job.properties.JobTasksListProperties;
import help.lixin.xxl.job.properties.XxlJobProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({XxlJobProperties.class, JobTasksListProperties.class})
@ImportAutoConfiguration({CommonConfig.class, XxlJobConfig.class})
@ConditionalOnProperty(prefix = XxlJobProperties.XXL_JOB_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class XXLJobAutoConfiguration {
    private Logger logger = LoggerFactory.getLogger(XXLJobAutoConfiguration.class);

    private XxlJobProperties xxlJobProperties;

    public XXLJobAutoConfiguration(XxlJobProperties xxlJobProperties) {
        this.xxlJobProperties = xxlJobProperties;
    }

    {
        if (logger.isDebugEnabled()) {
            logger.debug("enabled Module [{}] SUCCESS.", XXLJobAutoConfiguration.class.getSimpleName());
        }
    }
}
