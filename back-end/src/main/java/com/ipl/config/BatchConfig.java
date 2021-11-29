package com.ipl.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.ipl.batch_processing.JobCompletionNotificationListener;
import com.ipl.batch_processing.MatchDataProcessor;
import com.ipl.batch_processing.MatchInput;
import com.ipl.model.Match;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	DataSource dataSource;

	private final String[] FIELD_NAMES = new String[] { "id", "city", "date", "player_of_match", "venue", "team1",
			"team2", "toss_winner", "toss_decision", "winner", "result", "result_margin", "method", "umpire1",
			"umpire2" };

	@Bean
	public FlatFileItemReader<MatchInput> reader() {
		return new FlatFileItemReaderBuilder<MatchInput>().name("MatchItemReader")
				.resource(new ClassPathResource("match-data.csv")).linesToSkip(1)
				.lineMapper(new DefaultLineMapper<MatchInput>() {
					{
						setLineTokenizer(new DelimitedLineTokenizer() {
							{
								setDelimiter(DELIMITER_COMMA);
								setNames(FIELD_NAMES);
								setIncludedFields(new int[] { 0, 1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16, 17 });
							}
						});

						setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {
							{
								setTargetType(MatchInput.class);
							}
						});
					}
				}).build();
	}

	@Bean
	public MatchDataProcessor processor() {
		return new MatchDataProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Match> writer() {
		return new JdbcBatchItemWriterBuilder<Match>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO match (id, city, date, player_of_match, venue, team1, team2, toss_winner, toss_decision, match_winner, result, result_margin, umpire1, umpire2) "
						+ " VALUES (:id, :city, :date, :playerOfMatch, :venue, :team1, :team2, :tossWinner, :tossDecision, :matchWinner, :result, :resultMargin, :umpire1, :umpire2)")
				.dataSource(dataSource).build();
	}

	 @Bean
	    public Job importUserJob(JobCompletionNotificationListener listener) {
	        return jobBuilderFactory
	            .get("importUserJob")
	            .incrementer(new RunIdIncrementer())
	            .listener(listener)
	            .flow(stepOne())
	            .end()
	            .build();
	    }

	@Bean
	public Step stepOne() {
		return stepBuilderFactory.get("step1").<MatchInput, Match>chunk(10).reader(reader()).processor(processor())
				.writer(writer()).build();
	}

}
