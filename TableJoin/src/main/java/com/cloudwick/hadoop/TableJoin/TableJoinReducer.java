package com.cloudwick.hadoop.TableJoin;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TableJoinReducer extends Reducer<IntWritable, Text, Text, Text> {
	@Override
	protected void reduce(IntWritable deptId, Iterable<Text> details,
			Reducer<IntWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {

		Set<String> deptSet = new HashSet<String>();
		Set<String> empSet = new HashSet<String>();

		for (Text info : details) {
			String tempInfo = info.toString();
			if (tempInfo.contains(",")) {
				empSet.add(tempInfo);
			} else {
				deptSet.add(tempInfo);
			}
		}

		StringBuilder outputKey;
		StringBuilder outputValue;
		for (String dept : deptSet) {
			outputKey = new StringBuilder("");
			outputKey.append("<");
			outputKey.append(deptId);
			outputKey.append(",");
			outputKey.append(dept);
			outputKey.append(">");

			for (String emp : empSet) {
				outputValue = new StringBuilder("");
				outputValue.append("<");
				outputValue.append(emp);
				outputValue.append(",");
				outputValue.append(deptId);
				outputValue.append(">");

				context.write(new Text(outputKey.toString()), new Text(
						outputValue.toString()));
			}
		}
	}
}