package com.sourabhsurve.SBT_BatchJob_DBToCSV.config;

import com.sourabhsurve.SBT_BatchJob_DBToCSV.entity.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
        @Autowired
        private JobBuilderFactory jobBuilderFactory;

        @Autowired
        private StepBuilderFactory stepBuilderFactory;

        @Autowired
        private DataSource dataSource;

        @Bean
        public JdbcCursorItemReader<User> reader(String  sql) {
            JdbcCursorItemReader<User> reader = new JdbcCursorItemReader<>();
            reader.setDataSource(dataSource);
            reader.setSql(sql);
            reader.setRowMapper(new BeanPropertyRowMapper<>(User.class));
            return reader;
        }

        @Bean
        public FlatFileItemWriter<User> writer() {
            FlatFileItemWriter<User> writer = new FlatFileItemWriter<>();
            writer.setResource(new FileSystemResource("E:\\Springboot\\SBT_BatchJob_DBToCSV\\src\\main\\resources\\users.csv"));
            writer.setLineAggregator(new DelimitedLineAggregator<User>() {{
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<User>() {{
                    setNames(new String[]{"id", "name", "dept", "salary"});
                }});
            }});
            return writer;
        }

        @Bean
        public Step step1() {
            return stepBuilderFactory.get("step1")
                    .<User, User>chunk(10)
                    .reader(reader("SELECT id, name, dept, salary FROM user_tbl"))
                    .writer(writer())
                    .build();
        }

    @Bean
    public UserProcessor processor() {
        return new UserProcessor();
    }

        @Bean
        public Job exportPersonJob() {
            return jobBuilderFactory.get("exportPersonJob")
                    .incrementer(new RunIdIncrementer())
                    .flow(step1())
                    .end()
                    .build();
        }
    }


