package com.cloudwick.hadoop.TableJoinComparator;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TableJoinComparatorMapper extends
		Mapper<LongWritable, Text, Text, Text> {
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {

	}
}