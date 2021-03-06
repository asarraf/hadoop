package com.cloudwick.hadoop.VisitorTracker;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class VisitorTrackerMapper extends
		Mapper<LongWritable, Text, Text, Text> {
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		// Assumption: Input schema is (user|website)
		// parts[0]: user identity
		// parts[1]: web site visited
		String[] parts = value.toString().split("\\|");

		// Example first input record = usera|google.com
		// emit: <google.com, usera>

		context.write(new Text(parts[1]), new Text(parts[0]));
	}
}