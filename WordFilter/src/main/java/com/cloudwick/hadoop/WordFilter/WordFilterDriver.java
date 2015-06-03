package com.cloudwick.hadoop.WordFilter;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordFilterDriver extends Configured implements Tool {
	public int run(String[] args) throws Exception {
		Job job = new Job(getConf());

		switch (args.length) {
		case 2:
			// By default check by CA as location
			job.getConfiguration().set("state", "CA");
			break;
		case 3:
			// Filter when filter by some other state
			job.getConfiguration().set("state", args[2]);
			break;
		default:
			System.out.printf("Usage: %s [generic options] "
					+ "<input dir> <output Directory> [State By default CA]\n",
					getClass().getSimpleName());
			return -1;
		}

		job.setJarByClass(WordFilterMapper.class);
		job.setJobName(this.getClass().getName());

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setMapperClass(WordFilterMapper.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setNumReduceTasks(2);

		if (job.waitForCompletion(true)) {
			return 0;
		}
		return 1;
	}

	public static void main(String... args) throws Exception {
		int exitCode = ToolRunner.run(new WordFilterDriver(), args);
		System.exit(exitCode);
	}
}