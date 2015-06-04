package com.cloudwick.hadoop.JobChaining;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class JobChainingDriver extends Configured implements Tool {
	public int run(String[] args) throws Exception {
		Job job1 = new Job(getConf());

		switch (args.length) {
		case 2:
			// By default check by CA as location
			job1.getConfiguration().set("state", "CA");
			break;
		case 3:
			// Filter when filter by some other state
			job1.getConfiguration().set("state", args[2]);
			break;
		default:
			System.out.printf("Usage: %s [generic options] "
					+ "<input dir> <output Directory> [State By default CA]\n",
					getClass().getSimpleName());
			return -1;
		}

		// job1.setJarByClass(JobChainingMapper1.class);
		job1.setJobName(this.getClass().getName());

		FileInputFormat.setInputPaths(job1, new Path(args[0]));
		FileOutputFormat.setOutputPath(job1, new Path(
				"/cloudwick/jobchaining/temp"));

		job1.setMapperClass(JobChainingMapper1.class);

		job1.setMapOutputKeyClass(Text.class);
		job1.setMapOutputValueClass(Text.class);

		job1.setNumReduceTasks(2);

		if (job1.waitForCompletion(true)) {
			return 0;
		}

		Job job2 = new Job(getConf());

		// job2.setJarByClass(JobChainingMapper.class);
		job2.setJobName(this.getClass().getName());

		FileInputFormat.setInputPaths(job2, new Path(
				"/cloudwick/jobchaining/temp"));
		FileOutputFormat.setOutputPath(job2, new Path(args[1]));

		job2.setMapperClass(JobChainingMapper2.class);

		job2.setMapOutputKeyClass(Text.class);
		job2.setMapOutputValueClass(Text.class);

		job2.setNumReduceTasks(2);

		if (job2.waitForCompletion(true)) {
			return 0;
		}
/*
		// Delete temp Directory
		FileSystem fs = FileSystem.get(getConf());
		fs.delete(new Path("/cloudwick/jobchaining/temp"), true); // delete
																	// file,
																	// true for
																	// recursive
*/
		return 1;
	}

	public static void main(String... args) throws Exception {
		int exitCode = ToolRunner.run(new JobChainingDriver(), args);
		System.exit(exitCode);
	}
}