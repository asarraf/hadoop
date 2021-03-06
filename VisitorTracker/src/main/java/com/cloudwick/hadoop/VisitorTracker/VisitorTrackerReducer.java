package com.cloudwick.hadoop.VisitorTracker;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class VisitorTrackerReducer extends
		Reducer<Text, Text, Text, IntWritable> {
	@Override
	protected void reduce(Text website, Iterable<Text> visitors,
			Reducer<Text, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		Set<String> visitorSet = new HashSet<String>();

		for (Text visitor : visitors) {
			visitorSet.add(visitor.toString());
		}

		// unique
		IntWritable uniqueUsers = new IntWritable(visitorSet.size());
		context.write(website, uniqueUsers);
	}
}