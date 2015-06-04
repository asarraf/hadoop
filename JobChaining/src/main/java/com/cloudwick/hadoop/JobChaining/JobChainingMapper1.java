package com.cloudwick.hadoop.JobChaining;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class JobChainingMapper1 extends Mapper<LongWritable, Text, Text, Text> {
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		String line = value.toString();

		String[] parts = line.split(",");

		if (parts[1].equals(context.getConfiguration().get("state"))) {
			context.write(value, new Text(""));
		}
	}
}