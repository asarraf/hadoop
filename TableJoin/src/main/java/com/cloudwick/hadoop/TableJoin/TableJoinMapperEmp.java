package com.cloudwick.hadoop.TableJoin;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TableJoinMapperEmp extends
		Mapper<LongWritable, Text, IntWritable, Text> {
	private Log LOG = LogFactory.getLog(TableJoinDriver.class);
	@Override
	protected void map(LongWritable key, Text empInfo,
			Mapper<LongWritable, Text, IntWritable, Text>.Context context)
			throws IOException, InterruptedException {
		LOG.info("$$HADOOP-EMP$$" + empInfo.toString());
		
		// Assuming that the file is csv
		String[] empDetails = empInfo.toString().split(",");

		int mapKey = Integer.parseInt(empDetails[2].trim());

		context.write(new IntWritable(mapKey), new Text(empDetails[0] + ","
				+ empDetails[1]));
	}
}