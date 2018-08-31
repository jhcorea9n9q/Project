package com.java.web.hadoop;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import net.sf.json.JSONArray;

public class Project_G_Mapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
		try {
			ProjectBeans PB = new ProjectBeans(value);
			
			Text outputkey = new Text();
			
			if( PB.isGenreYn() && PB.isYearYn() ) {
				List<Map<String,Object>> LISTS = JSONArray.fromObject(PB.getGenre());
				for(int i = 0; i < LISTS.size(); i++) {
					outputkey.set( LISTS.get(i).get("name").toString() );
					IntWritable outputvalue = new IntWritable( 1 );
					context.write(outputkey, outputvalue);
				}
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}
	
}
