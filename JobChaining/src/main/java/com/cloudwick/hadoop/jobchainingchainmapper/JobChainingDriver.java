// JOB CHAINING WITH SINGLE JOB
// Added dependency for hadoop-mapreduce-client-core in POM.xml

package com.cloudwick.hadoop.jobchainingchainmapper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class JobChainingDriver extends Configured implements Tool {
	public int run(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.printf("Usage: %s [generic options] "
					+ "<input dir> <output Directory>\n", getClass()
					.getSimpleName());
			return -1;
		}

		Job job = new Job(getConf());

		// First configuration for the First Mapper
		Configuration conf1 = new Configuration(false);

		// Second Configuration for the Second Mapper
		Configuration conf2 = new Configuration(false);

		ChainMapper.addMapper(job, Mapper1.class, LongWritable.class,
				Text.class, IntWritable.class, Text.class, conf1);
		ChainMapper.addMapper(job, Mapper2.class, IntWritable.class, Text.class,
				Text.class, Text.class, conf2);

		job.setJarByClass(JobChainingDriver.class);
		job.setJobName("Job Chaining Job");
		job.setNumReduceTasks(0);

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		if (job.waitForCompletion(true));
		return 1;
	}

	public static void main(String... args) throws Exception {
		int exitCode = ToolRunner.run(new JobChainingDriver(), args);
		System.exit(exitCode);
	}
}