package kata.imad.numbertostring.batch.config;

import kata.imad.numbertostring.application.service.NumberTransformationService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class NumberTransformationBatchConfig {
    //jobLuancher --> job --> Step --> item reader
//                          |--- item processor
//                          | --- item write
    private final NumberTransformationService numberTransformationService;
    @Autowired
    public NumberTransformationBatchConfig(NumberTransformationService numberTransformationService) {
        this.numberTransformationService = numberTransformationService;
    }

    @Bean
    public FlatFileItemReader<String> reader(@Value("${input.file.path}") Resource inputFile) {
        return new FlatFileItemReaderBuilder<String>()
                .name("numberItemReader")
                .resource(inputFile)
                .lineMapper((line, lineNumber) -> line)
                .build();
    }

    @Bean
    public FlatFileItemWriter<String> writer(@Value("${output.file.path}") String outputFilePath) {
        WritableResource outputFile = new FileSystemResource(outputFilePath);
        return new FlatFileItemWriterBuilder<String>()
                .name("numberItemWriter")
                .resource(outputFile)
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();
    }

    @Bean
    public Step step(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<String> reader,
            FlatFileItemWriter<String> writer
    ) {
        return new StepBuilder("step",jobRepository)
                .<String,String>chunk(10,transactionManager)
                .reader(reader)
                .processor(
                        item -> {
                            try {
                                int number = Integer.parseInt(item.trim());
                                return number+" "+numberTransformationService.transform(number).getTransformedValue();
                            }catch (NumberFormatException e) {
                                return item;
                            }
                        }
                )
                .writer(writer).build();

    }

    @Bean
    public Job jobNumberToString(JobRepository jobRepository, Step step) {
        return new JobBuilder("step",jobRepository)
                .start(step).build();
    }

}

